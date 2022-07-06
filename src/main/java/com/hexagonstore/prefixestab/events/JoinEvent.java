package com.hexagonstore.prefixestab.events;

import lombok.val;
import com.hexagonstore.prefixestab.manager.PrefixesManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEvent implements Listener {

    private PrefixesManager manager;

    public JoinEvent(PrefixesManager manager, JavaPlugin main) {
        this.manager = manager;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void evento(PlayerJoinEvent e) {
        val player = e.getPlayer();
        manager.updateTag(player);
    }
}
