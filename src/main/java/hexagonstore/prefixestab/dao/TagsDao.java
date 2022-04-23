package hexagonstore.prefixestab.dao;

import hexagonstore.prefixestab.models.Tag;
import hexagonstore.prefixestab.utils.EC_Config;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TagsDao {

    private Map<String, Tag> tags;

    public TagsDao(EC_Config config) {
        tags = new HashMap<>();
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

                val tag = new Tag(id, groupID, prefix, suffix, position);
                tags.put(groupID.toLowerCase(), tag);
            }
        }else Bukkit.getConsoleSender().sendMessage("§4[HexagonPrefixesTab] §cSection 'Tags' não encontrada, contate o desenvolvedor do plugin.");
    }

    public Tag getTag(String id) {
        return tags.get(id.toLowerCase());
    }
}
