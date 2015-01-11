package com.ahellhound.bukkit.flypayment;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class BanList {
    // Utilities constructor
    Utilities Utilities = new Utilities();
    // configuration constructor
    private Configuration Configuration = new Configuration();
    // File configuration methods
    private YamlConfiguration banList = null;
    private File banListConfigFile = null;

    public void addPlayerBanList(Player pl, String rawTime) {
        UUID p = pl.getUniqueId();
        // converts string to long
        long timeFormatted = Utilities.parseStringToMilliseconds(rawTime);
        // checks if player was banned indefinitely
        if (rawTime == "-1") {
            // changes time formatted string to stay '-1'
            int timeFormattedString = -1;
            // creates player name in file
            getBanList().getConfigurationSection("Bans").set(p.toString(), timeFormattedString);
            // Reloads ban list
            saveBanList();
        } else {
            // converts long to string
            // String timeFormattedString =
            // Utilities.convertLongToString(timeFormatted);
            // creates player name in file
            if (getBanList().getConfigurationSection("Bans") == null) {

                getBanList().createSection("Bans");

            }

            getBanList().getConfigurationSection("Bans").set(p.toString(), timeFormatted + System.currentTimeMillis());

            // disables player's flight
            disableBannedPlayerFlight(pl);
            // Saves ban list
            saveBanList();
        }

    }

    public void addPlayerBanList(String pl, String rawTime) {

        UUID p = Bukkit.getServer().getPlayer(pl).getUniqueId();

        // converts string to long
        long timeFormatted = Utilities.parseStringToMilliseconds(rawTime);
        // checks if player was banned indefinitely
        if (rawTime == "-1") {
            // changes time formatted string to stay '-1'
            int timeFormattedString = -1;
            // creates player name in file
            getBanList().getConfigurationSection("Bans").set(p.toString(), timeFormattedString);
            // Reloads ban list
            saveBanList();
        } else {
            // converts long to string
            // String timeFormattedString =
            // Utilities.convertLongToString(timeFormatted);
            // creates player name in file
            if (getBanList().getConfigurationSection("Bans") == null) {

                getBanList().createSection("Bans");

            }

            getBanList().getConfigurationSection("Bans").set(p.toString(), timeFormatted + System.currentTimeMillis());

            // disables player's flight
            disableBannedPlayerFlight(p);
            // Saves ban list
            saveBanList();
        }
    }

    public void disableBannedPlayerFlight(Player pl) {

        UUID p = pl.getUniqueId();

        Flight Flight = new Flight();

        Player pInstance = Main.getInstance().getServer().getPlayer(p);
        // checks if player is flying, then disables ban
        if (pInstance.getAllowFlight()) {
            // disbales player's flight
            Flight.disableFlight(pInstance, 3);
        }
    }

    public void disableBannedPlayerFlight(UUID p) {

        Flight Flight = new Flight();

        Player pInstance = Main.getInstance().getServer().getPlayer(p);
        // checks if player is flying, then disables ban
        if (pInstance.getAllowFlight()) {
            // disbales player's flight
            Flight.disableFlight(pInstance, 3);
        }
    }

    public boolean isBannedFly(Player p) {
        // updates ban list
        updateBanList();
        String configBanList = getBanList().getConfigurationSection("Bans").getString(p.getUniqueId().toString());
        // Checks file for player name
        if (configBanList == null) {
            // returns true if banned
            return false;
        }
        // returns false if player not banned
        return true;
    }

    public boolean isBannedFly(String pl) {

        UUID p = Bukkit.getServer().getPlayer(pl).getUniqueId();
        // updates ban list
        updateBanList();
        String configBanList = getBanList().getConfigurationSection("Bans").getString(p.toString());
        // Checks file for player name
        if (configBanList == null) {
            // returns true if banned
            return false;
        }
        // returns false if player not banned
        return true;
    }

    public void updateBanList() {

        // puts all the player names into a string set
        Set<String> playerString = getBanList().getConfigurationSection("Bans").getKeys(false);
        // current time
        long currentTime = System.currentTimeMillis();
        // iterates through the whole set, looking for players with a time thats
        // equal to or above the current time. If so, removes ban
        for (String playerUUID : playerString) {
            // checks if the player's time is less than or equal to current
            // time, and if player is permanently banned (-1)

            if ((!getBanList().getConfigurationSection("Bans").getString(playerUUID).contains("-1"))
                    && getBanList().getConfigurationSection("Bans").getLong(playerUUID) <= currentTime) {
                // removes player ban
                removeBan(playerUUID);

                saveBanList();

            }

        }

    }

    public void removeBan(String pl) {

        UUID p = Bukkit.getServer().getPlayer(pl).getUniqueId();

        getBanList().getConfigurationSection("Bans").set(p.toString(), null);
        // Saves ban list
        saveBanList();

    }

    public boolean getRequireBanTime() {
        // checks in config if ban time is required
        if (!Configuration.getFlyBanRequireTime()) {
            // returns false if false is entered in config
            return false;
        }
        // returns true if ban is required
        return true;
    }

    public boolean hasMaxBanTime() {
        // checks if config has max ban time, 0 is false
        if (Configuration.getFlyBanMaxTime() == 0) {
            // returns true
            return false;

        }
        // returns false if no max ban time
        return true;
    }

    public int getMaxBanTime() {
        // gets seconds for ban time
        int banTime = Configuration.getFlyBanMaxTime();
        // converts seconds to milliseconds
        int banTimeMilliseconds = (banTime * 1000);
        // returns ban time
        return banTimeMilliseconds;
    }

    public Long getPlayerBanTime(String pl) {

        String p = Bukkit.getServer().getPlayer(pl).getUniqueId().toString();

        updateBanList();
        // gets ban time set in config
        int banTimeInt = getBanList().getConfigurationSection("Bans").getInt(p);

        // checks if player is permanently ban
        if (banTimeInt == -1) {

            return -1L;
        }
        // parses string
        // Long parsedTime = Long.;
        // correctly formats time
        Long banTime = getBanList().getConfigurationSection("Bans").getLong(p);
        // Long parsedTimeLong = banTime;
        // tostring
        // String banTimeString = String.valueOf(banTime -
        // System.currentTimeMillis());
        Long banTimeParsed = banTime - System.currentTimeMillis();
        // returns ban time
        return banTimeParsed;
    }

    public boolean isInBannedWorld(Player p) {
        String bannedWorlds = Configuration.getBannedWorlds();
        ArrayList<String> bannedWorldsArray = new ArrayList<String>(Arrays.asList(bannedWorlds.split(" ")));
        String playerWorld = p.getWorld().getName();
        if (bannedWorldsArray.contains(playerWorld)) {
            // returns true
            return true;
        }

        // returns false
        return false;
    }

    public void reloadBanList() {
        // reloads ban list
        reloadBanListConfig();
    }

    // Loads banlist config if not present
    public void reloadBanListConfig() {
        if (banListConfigFile == null) {
            banListConfigFile = new File(Main.getInstance().getDataFolder(), "banList.yml");
        }
        banList = YamlConfiguration.loadConfiguration(banListConfigFile);

        // Look for defaults in the jar
        InputStream defConfigStream = Main.getInstance().getResource("banList.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            banList.setDefaults(defConfig);
        }
    }

    // gets banlist file
    public YamlConfiguration getBanList() {
        if (banList == null) {
            // reloads ban list
            reloadBanListConfig();
        }
        return banList;
    }

    // Saves ban list
    public void saveBanList() {
        if (banList == null || banListConfigFile == null) {
            return;
        }
        try {
            getBanList().save(banListConfigFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + banListConfigFile, ex);
        }
    }

    // saves default ban list
    public void saveDefaultBanList() {
        if (banListConfigFile == null) {
            banListConfigFile = new File(Main.getInstance().getDataFolder(), "banList.yml");
        }
        if (!banListConfigFile.exists()) {
            Main.getInstance().saveResource("banList.yml", false);
        }
    }

}
