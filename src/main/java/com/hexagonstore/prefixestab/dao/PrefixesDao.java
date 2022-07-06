package com.hexagonstore.prefixestab.dao;

import com.hexagonstore.prefixestab.models.Prefixe;
import com.hexagonstore.prefixestab.utils.EC_Config;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PrefixesDao {

    private Map<String, Prefixe> prefixes;
    private Prefixe defaultTag;

    public PrefixesDao(EC_Config config) {
        prefixes = new HashMap<>();
        load(config);
    }

    private void load(EC_Config config) {
        val section = config.getConfigurationSection("Tags");
        if(section != null) {
            for (String id : section.getKeys(false)) {
                val key = section.getConfigurationSection(id);

                val groupID = key.getString("group_id");
                val prefix = key.getString("prefix");
                val suffix = key.getString("suffix");
                val position = String.valueOf(key.getInt("position"));

                val tag = new Prefixe(id, groupID, prefix, suffix, position);
                prefixes.put(groupID.toLowerCase(), tag);
            }
            defaultTag = prefixes.get(config.getString("Defaults.tag"));
        }else Bukkit.getConsoleSender().sendMessage("§4[HexagonPrefixesTab] §cSection 'Tags' não encontrada, contate o desenvolvedor do plugin.");
    }

    public Prefixe getTag(String id) {
        return prefixes.get(id.toLowerCase());
    }
}
