package com.hexagonstore.prefixestab.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hexagonstore.prefixestab.dao.PrefixesDao;
import com.hexagonstore.prefixestab.models.Prefixe;
import com.hexagonstore.prefixestab.utils.hooker.Hooker;
import com.hexagonstore.prefixestab.utils.hooker.types.HookerType;
import com.hexagonstore.prefixestab.PrefixesPlugin;
import lombok.val;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PrefixesManager {

    private BukkitTask task;
    public final Map<Player, Prefixe> PREFIXES;

    private Hooker hooker;
    private PrefixesDao tagsDao;

    public PrefixesManager(PrefixesDao tagsDao, Hooker hooker) {
        PREFIXES = new HashMap<>();

        this.tagsDao = tagsDao;
        this.hooker = hooker;

        this.init();
    }

    public void updateTag(Player player) {
        PREFIXES.put(player, getTag(player));
        if (player.getScoreboard() == null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        } else {
            update(player.getScoreboard());
        }
        update(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    private void update() {
        for (val player : Bukkit.getOnlinePlayers()) {
            PREFIXES.put(player, getTag(player));
            if (player.getScoreboard() == null) {
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            } else {
                update(player.getScoreboard());
            }
        }
        update(Bukkit.getScoreboardManager().getMainScoreboard());

    }

    private void update(Scoreboard scoreboard) {
        for (Entry<Player, Prefixe> map : PREFIXES.entrySet()) {
            val player = map.getKey();
            val tag = map.getValue();
            val id = tag.getPosition() + "_" + tag.getId();
            Team team;
            if ((team = scoreboard.getTeam(id)) == null) {
                team = scoreboard.registerNewTeam(id);
            }
            if (!team.hasPlayer(player)) {
                team.addPlayer(player);
            }

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, tag.getPrefix())));
                team.setSuffix(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, tag.getSuffix())));
            } else {
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', tag.getPrefix()));
                team.setSuffix(ChatColor.translateAlternateColorCodes('&', tag.getSuffix()));
            }
        }
    }

    private void init() {
        if (task == null) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    update();
                }
            }.runTaskTimerAsynchronously(PrefixesPlugin.getPlugin(), 0L, (20 * 60) /* * minutes*/);
        }
    }

    private Prefixe getTag(Player player) {
        if(hooker.getHookerType().equals(HookerType.LP)) {
            return getLP(player);
        }else {
            return getPex(player);
        }
    }

    private Prefixe getLP(Player player) {
        val luckPerms = hooker.getLp();
        val playerGroup = hooker.getLp().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        ;
        val group = luckPerms.getGroupManager().getGroup(playerGroup);

        if (group == null)
            return null;

        val tag = tagsDao.getTag(group.getName());
        return tag == null ? tagsDao.getDefaultTag() : tag;
    }

    @Deprecated
    private Prefixe getPex(Player player) {
        val groups = PermissionsEx.getUser(player).getGroupNames();
        String[] array;

        for (int length = (array = groups).length, i = 0; i < length; i++) {
            val playerGroup = array[i];

            val tag = tagsDao.getTag(playerGroup);
            return tag == null ? tagsDao.getDefaultTag() : tag;
        }
        return null;
    }
}