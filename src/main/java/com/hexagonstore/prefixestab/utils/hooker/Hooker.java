package com.hexagonstore.prefixestab.utils.hooker;

import com.hexagonstore.prefixestab.utils.hooker.types.HookerType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Hooker {

    private ru.tehkode.permissions.bukkit.PermissionsEx pex;
    private net.luckperms.api.LuckPerms lp;
    private HookerType hookerType;

    public Hooker(JavaPlugin plugin) {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            lp = net.luckperms.api.LuckPermsProvider.get();
            hookerType = HookerType.LP;
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null) {
            hookerType = HookerType.PEX;
            setupPex();
            return;
        }

        Bukkit.getConsoleSender().sendMessage("§4[HexagonPrefixesTab] §cNão achamos nenhum plugin de permissões compatível para o funcionamento do plugin.");
        Bukkit.getConsoleSender().sendMessage("§4[HexagonPrefixesTab] §cCaso queira adicionar algum outro plugin de permissões, fale com o suporte no discord.");
        Bukkit.getConsoleSender().sendMessage("§4[HexagonPrefixesTab] §cSuporte: §fhttps://dsc.gg/hexagonstore");

        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    private boolean setupPex() {
        RegisteredServiceProvider<ru.tehkode.permissions.bukkit.PermissionsEx> registration = Bukkit.getServicesManager().getRegistration(ru.tehkode.permissions.bukkit.PermissionsEx.class);

        if (pex == null)
            return false;

        pex = registration.getProvider();
        return pex != null;
    }
}
