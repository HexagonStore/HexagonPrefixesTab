package hexagonstore.prefixestab.utils.hooker;

import hexagonstore.prefixestab.utils.hooker.types.HookerType;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Getter
public class Hooker {

    private PermissionsEx pex;
    private LuckPerms lp;
    private HookerType hookerType;

    public Hooker() {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            lp = LuckPermsProvider.get();
            hookerType = HookerType.LP;
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null) {
            hookerType = HookerType.PEX;
            setupPex();
        }
    }

    private boolean setupPex() {
        RegisteredServiceProvider<PermissionsEx> registration = Bukkit.getServicesManager().getRegistration(PermissionsEx.class);

        if (pex == null)
            return false;

        pex = registration.getProvider();
        return pex != null;
    }
}
