package com.ahellhound.bukkit.flypayment;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class FreeFly {
    private YamlConfiguration freeFly = null;
    private File freeFlyConfigFile = null;

    // gets time in config
    public long getFreeFlyTime() {

        long freeFlyTime = getFreeFlyConfig().getLong("FreeFlyTime");

        return freeFlyTime;
    }

    public void setFreeFlyTime(long time) {
        // sets time
        getFreeFlyConfig().set("FreeFlyTime", time);
        // Saves config
        saveFreeFlyConfig();

    }

    public boolean isFreeFlyTimerEnabled() {

        // checks free fly boolean in config
        if (getFreeFlyConfig().getBoolean("FreeFly")) {
            // returns true if true
            return true;
        }

        return false;
    }

    public void disableFreeFlightConfig() {
        // sets time to 0
        getFreeFlyConfig().set("FreeFlyTime", 0);
        // sets boolean to false
        getFreeFlyConfig().set("FreeFly", false);
        // Saves config
        saveFreeFlyConfig();
    }

    public int getFreeFlyTimeInterval() {

        int getFreeFlyTimeInterval = getFreeFlyConfig().getInt("FreeFlyTimeInterval");

        return getFreeFlyTimeInterval;
    }

    public void enableFreeFlyConfig(String rawTime) {

        // Utilites
        Utilities util = new Utilities();
        // sets free fly on
        setFreeFly(true);
        // util from format to miliseconds
        long convertedTime = util.parseStringToMilliseconds(rawTime);
        // sets the time for free fly
        setFreeFlyTime(convertedTime + System.currentTimeMillis());
    }

    // freefly
    public void reloadFreeFlyConfig() {
        if (freeFlyConfigFile == null) {
            freeFlyConfigFile = new File(Main.getInstance().getDataFolder(), "freeFly.yml");
        }

        freeFly = YamlConfiguration.loadConfiguration(freeFlyConfigFile);

        // Look for defaults in the jar
        InputStream defConfigStream = Main.getInstance().getResource("freeFly.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            freeFly.setDefaults(defConfig);
        }
    }

    // gets banlist file
    public YamlConfiguration getFreeFlyConfig() {
        if (freeFly == null) {
            // reloads ban list
            reloadFreeFlyConfig();
        }
        return freeFly;
    }

    // Saves ban list
    public void saveFreeFlyConfig() {
        if (freeFly == null || freeFlyConfigFile == null) {
            return;
        }
        try {
            getFreeFlyConfig().save(freeFlyConfigFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not save configuration to " + freeFlyConfigFile, ex);
        }
    }

    // saves default ban list
    public void saveDefaultFreeFlyConfig() {
        if (freeFlyConfigFile == null) {
            freeFlyConfigFile = new File(Main.getInstance().getDataFolder(), "freeFly.yml");
        }
        if (!freeFlyConfigFile.exists()) {
            Main.getInstance().saveResource("freeFly.yml", false);
        }
    }

    public boolean getFreeFly() {

        if (getFreeFlyConfig().getBoolean("FreeFly")) {

            return true;
        } else {
            return false;
        }

    }

    public void setFreeFly(boolean bool) {
        // sets boolean
        getFreeFlyConfig().set("FreeFly", bool);
        // Saves config
        saveFreeFlyConfig();

    }

}
