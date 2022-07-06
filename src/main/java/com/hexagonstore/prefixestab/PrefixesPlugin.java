package com.hexagonstore.prefixestab;

import com.hexagonstore.prefixestab.dao.PrefixesDao;
import com.hexagonstore.prefixestab.events.JoinEvent;
import com.hexagonstore.prefixestab.manager.PrefixesManager;
import com.hexagonstore.prefixestab.utils.EC_Config;
import com.hexagonstore.prefixestab.utils.hooker.Hooker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PrefixesPlugin extends JavaPlugin {

    private static PrefixesPlugin plugin;

    public static PrefixesPlugin getPlugin() {
        return plugin;
    }

    private EC_Config cfg;
    private PrefixesDao tagsDao;

    private Hooker hooker;
    private PrefixesManager manager;

    @Override
    public void onEnable() {
        plugin = this;

        this.cfg = new EC_Config(this, null, "prefixes.yml", false);

        hooker = new Hooker(this);
        tagsDao = new PrefixesDao(cfg);
        manager = new PrefixesManager(tagsDao, hooker);

        new JoinEvent(manager, this);
    }

    @Override
    public void onDisable() {}
}
