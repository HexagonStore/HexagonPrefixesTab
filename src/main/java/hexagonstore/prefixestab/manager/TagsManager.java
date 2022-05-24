package hexagonstore.prefixestab.manager;

import hexagonstore.prefixestab.utils.hooker.Hooker;
import hexagonstore.prefixestab.utils.hooker.types.HookerType;
import lombok.val;
import me.clip.placeholderapi.PlaceholderAPI;
import hexagonstore.prefixestab.PrefixesPlugin;
import hexagonstore.prefixestab.dao.TagsDao;
import hexagonstore.prefixestab.models.Tag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagsManager {

    private TagsDao tagsDao;
    private Hooker hooker;

    public TagsManager(TagsDao tagsDao, Hooker hooker) {
        this.tagsDao = tagsDao;
        this.hooker = hooker;

        this.startTasks();
    }

    public void applyTag(Player player) {
        switch (hooker.getHookerType()) {
            case LP:
                applyLP(player);
                break;
            case PEX:
                applyPex(player);
                break;
        }
    }

    private void applyLP(Player player) {
        val luckPerms = hooker.getLp();
        val playerGroup = hooker.getLp().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        ;
        val group = luckPerms.getGroupManager().getGroup(playerGroup);

        if (group == null)
            return;

        val tag = tagsDao.getTag(group.getName());
        if (tag != null) addTag(player, tag);
    }

    @Deprecated
    private void applyPex(Player player) {
        val groups = PermissionsEx.getUser(player).getGroupNames();
        String[] array;

        for (int length = (array = groups).length, i = 0; i < length; i++) {
            String playerGroup = array[i];
            Tag tag = tagsDao.getTag(playerGroup);

            if (tag != null)
                addTag(player, tag);
        }
    }

    @Deprecated
    private void addTag(Player player, Tag tag) {
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            for (Player online : Bukkit.getOnlinePlayers()) online.setScoreboard(scoreboard);
        }

        String prefix = tag.getPrefix().replace("&", "§");
        String suffix = PlaceholderAPI.setPlaceholders(player, tag.getSuffix().replace("&", "§"));
        String teamId = tag.getPosition() + "_" + player.getName();

        if (teamId.length() > 16)
            teamId = teamId.substring(0, 16);

        if (prefix.length() > 16)
            prefix = prefix.substring(0, 16);

        if (suffix.length() > 16)
            suffix = suffix.substring(0, 16);

        Team team = scoreboard.getTeam(teamId);
        if (team == null)
            team = scoreboard.registerNewTeam(teamId);

        team.setPrefix(prefix);
        team.setSuffix(suffix);

        team.addEntry(player.getName());
    }

    private void startTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> applyTag(player));
            }
        }.runTaskTimerAsynchronously(PrefixesPlugin.getPlugin(), 0L, 20*30L);
    }
}
