package hexagonstore.prefixestab;

import hexagonstore.prefixestab.events.ExitEvent;
import hexagonstore.prefixestab.events.JoinEvent;
import hexagonstore.prefixestab.manager.TagsManager;
import hexagonstore.prefixestab.utils.EC_Config;
import hexagonstore.prefixestab.utils.hooker.Hooker;
import lombok.Getter;
import hexagonstore.prefixestab.dao.TagsDao;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PrefixesPlugin extends JavaPlugin {

    private static PrefixesPlugin plugin;

    public static PrefixesPlugin getPlugin() {
        return plugin;
    }

    private EC_Config cfg;
    private TagsDao tagsDao;

    private Hooker hooker;
    private TagsManager manager;

    @Override
    public void onEnable() {
        plugin = this;

        this.cfg = new EC_Config(this, null, "config.yml", false);

        hooker = new Hooker();
        tagsDao = new TagsDao(cfg);
        manager = new TagsManager(tagsDao, hooker);

        new JoinEvent(manager, this);
        new ExitEvent(this);
    }

    @Override
    public void onDisable() {}
}
