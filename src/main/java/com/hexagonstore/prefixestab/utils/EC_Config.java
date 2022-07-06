package com.hexagonstore.prefixestab.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EC_Config {

    private JavaPlugin main;
    private boolean isNewFile;
    private File currentDirectory;
    private File file;
    private FileConfiguration fileConfig;

    public EC_Config(JavaPlugin main, String directory, String fileName, boolean isNewFile) {
        this.main = main;
        this.isNewFile = isNewFile;

        createDirectory(directory);
        createFile(directory, fileName);

        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void createDirectory(String directory) {
        this.currentDirectory = main.getDataFolder();
        if(directory != null) {
            this.currentDirectory = new File(main.getDataFolder(), directory.replace("/", File.separator));
            this.currentDirectory.mkdirs();
        }
    }

    public void createFile(String directory, String fileName) {
        file = new File(this.currentDirectory, fileName);
        if(!file.exists()) {
            if(this.isNewFile) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else {
                main.saveResource(directory != null ? directory + File.separator + fileName : fileName, false);
            }
        }
    }

    public FileConfiguration getConfig() {
        return fileConfig;
    }

    public void saveConfig() {
        try {
            fileConfig.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        this.fileConfig.setDefaults(YamlConfiguration.loadConfiguration(file));
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public short getShort(String path) {
        return (short) getConfig().getInt(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public List<?> getList(String path) {
        return getConfig().getList(path);
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public void set(String path, Object value) {
        getConfig().set(path, value);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return getConfig().getIntegerList(path);
    }

    public List<Double> getDoubleList(String path) {
        return getConfig().getDoubleList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return getConfig().getBooleanList(path);
    }

    public List<Byte> getByteList(String path) {
        return getConfig().getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        return getConfig().getCharacterList(path);
    }

    public List<Long> getLongList(String path) {
        return getConfig().getLongList(path);
    }

    public List<Short> getShortList(String path) {
        return getConfig().getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return getConfig().getMapList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return getConfig().getList(path, def);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return getConfig().getConfigurationSection(path);
    }
}