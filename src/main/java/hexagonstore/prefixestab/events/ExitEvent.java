package hexagonstore.prefixestab.events;

import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExitEvent implements Listener {

    public ExitEvent(JavaPlugin main) {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void evento(PlayerQuitEvent e) {
        val player = e.getPlayer();
        val scoreboard = player.getScoreboard();
        if (scoreboard == null) return;

        if (scoreboard.getObjective("showhealth") != null) scoreboard.getObjective("showhealth").unregister();

        try {
            for (String scoreboards : scoreboard.getEntries()) scoreboard.resetScores(scoreboards);

            scoreboard.getObjectives().clear();
            scoreboard.getTeams().clear();

        } catch (Exception ignored) {}
    }
}
