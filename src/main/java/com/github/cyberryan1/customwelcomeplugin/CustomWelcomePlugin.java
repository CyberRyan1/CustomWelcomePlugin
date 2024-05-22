package com.github.cyberryan1.customwelcomeplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CustomWelcomePlugin extends JavaPlugin {

    private static CustomWelcomePlugin PLUGIN;
    public static CustomWelcomePlugin getPlugin() { return PLUGIN; }


    private File configFile;
    private File dataFile;
    private FileConfiguration config;
    private FileConfiguration data;

    @Override
    public void onEnable() {
        PLUGIN = this;
        initializeConfig();

        // Initializing /welcome command
        getCommand( "welcome" ).setExecutor( new WelcomeCommand() );
        // Initializing the welcome events
        getServer().getPluginManager().registerEvents( new WelcomeEvents(), this );
    }

    public FileConfiguration getConfig() { return config; }
    public File getConfigFile() { return configFile; }

    public FileConfiguration getData() { return data; }
    public File getDataFile() { return dataFile; }

    // Initializing the config and data YML files
    private void initializeConfig() {
        configFile = new File( getDataFolder(), "config.yml" );
        dataFile = new File( getDataFolder(), "data.yml" );

        if ( configFile.exists() == false ) {
            configFile.getParentFile().mkdirs();
            saveResource( "config.yml", false );
        }
        if ( dataFile.exists() == false ) {
            dataFile.getParentFile().mkdirs();
            saveResource( "data.yml", false );
        }

        config = YamlConfiguration.loadConfiguration( configFile );
        data = YamlConfiguration.loadConfiguration( dataFile );
    }
}
