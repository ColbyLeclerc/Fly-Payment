package com.ahellhound.bukkit.flypayment;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class PlayerInfo {

    private YamlConfiguration playerInfo = null;
    private File playerInfoConfigFile = null;
    private String exp = "exp";
    private String item = "item";
    private String items = "items";
    private String money = "money";
    private final ArrayList<String> defaultArray = new ArrayList<String>(Arrays.asList(exp, item, money));

    // freefly
    public void reloadPlayerInfoConfig() {
        if (playerInfoConfigFile == null) {
            playerInfoConfigFile = new File(Main.getInstance().getDataFolder(), "playerInfo.yml");
        }

        playerInfo = YamlConfiguration.loadConfiguration(playerInfoConfigFile);

        // Look for defaults in the jar
        InputStream defConfigStream = Main.getInstance().getResource("playerInfo.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            playerInfo.setDefaults(defConfig);
        }
    }

    // gets banlist file
    public YamlConfiguration getPlayerInfoConfig() {
        if (playerInfo == null) {
            // reloads ban list
            reloadPlayerInfoConfig();
        }
        return playerInfo;
    }

    public boolean addEntryChargeOrder(Player p, String arg1, String arg2, String arg3) {

        ArrayList<String> argsArray = new ArrayList<String>();

        if (!validateChargeOrderEntries(arg1, arg2, arg3)) {

            return false;

        }

        checkPlayerEntry(p);

        argsArray.add(arg1.toLowerCase());
        argsArray.add(arg2.toLowerCase());
        argsArray.add(arg3.toLowerCase());

        String pl = p.getUniqueId().toString();

        getPlayerInfoConfig().getConfigurationSection(pl).set("ChargeOrder", argsArray);

        savePlayerInfoConfig();
        return true;

    }

    public boolean validateChargeOrderEntries(String args1, String args2, String args3) {

        String args1Lower = args1.toLowerCase();
        String args2Lower = args2.toLowerCase();
        String args3Lower = args3.toLowerCase();

        if ((args1Lower.equals(exp) || args1Lower.equals(items) || args1Lower.equals(item) || args1Lower.equals(money))
                && (args2Lower.equals(exp) || args2Lower.equals(items) || args2Lower.equals(item) || args2Lower.equals(money))
                && (args3Lower.equals(exp) || args3Lower.equals(items) || args3Lower.equals(item) || args3Lower.equals(money))) {

            return true;
        }

        return false;

    }

    public String getChargeOrder(Player p) {

        String pl = p.getUniqueId().toString();

        checkPlayerEntry(p);

        return getPlayerInfoConfig().getConfigurationSection(pl).getString("ChargeOrder");

    }

    public void checkPlayerEntry(Player p) {

        String pl = p.getUniqueId().toString();

        if (getPlayerInfoConfig().getConfigurationSection(pl) == null) {

            getPlayerInfoConfig().createSection(pl);
            getPlayerInfoConfig().getConfigurationSection(pl).set("TempFlyBanTime", 0);
            getPlayerInfoConfig().getConfigurationSection(pl).set("FlyModeOneTime", 0);
            getPlayerInfoConfig().getConfigurationSection(pl).set("ChargeOrder", defaultArray);
            savePlayerInfoConfig();
        }

    }

    public void setFlyModeOneTime(Player p) {

        Configuration config = new Configuration();

        int tier = config.getTier(p);
        int intTime = config.getFlyModeAmount(tier) * 1000;
        long longTime = Long.parseLong(String.valueOf(intTime));

        Main.getInstance().log.severe("intLong = " + longTime);

        checkPlayerEntry(p);

        getPlayerInfoConfig().getConfigurationSection(p.getUniqueId().toString()).set("FlyModeOneTime", longTime + System.currentTimeMillis());

        savePlayerInfoConfig();
    }

    public long getFlyModeOneTime(Player p) {

        checkPlayerEntry(p);

        return getPlayerInfoConfig().getConfigurationSection(p.getUniqueId().toString()).getLong("FlyModeOneTime");
    }

    public void setFlyTempBan(Player pl) {

        String p = pl.getUniqueId().toString();

        Configuration config = new Configuration();

        checkPlayerEntry(pl);

        getPlayerInfoConfig().getConfigurationSection(p).set("TempFlyBanTime",
                (config.getFlyTempBanAmount() * 1000) + System.currentTimeMillis());

        savePlayerInfoConfig();

    }

    public boolean isFlyTempBan(Player p) {

        String pl = p.getUniqueId().toString();

        checkPlayerEntry(p);

        if (getFlyTempBan(p) < System.currentTimeMillis()) {
            // sets player's time to 0, cleans player file
            getPlayerInfoConfig().getConfigurationSection(pl).set("TempFlyBanTime", 0);

            savePlayerInfoConfig();

            return false;

        } else {

            return true;

        }

    }

    public long getFlyTempBan(Player p) {

        String pl = p.getUniqueId().toString();

        checkPlayerEntry(p);

        return getPlayerInfoConfig().getConfigurationSection(pl).getLong("TempFlyBanTime");

    }

    // Saves ban list
    public void savePlayerInfoConfig() {
        if (playerInfo == null || playerInfoConfigFile == null) {
            return;
        }
        try {
            getPlayerInfoConfig().save(playerInfoConfigFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not save configuration to " + playerInfoConfigFile, ex);
        }
    }

    // saves default ban list
    public void saveDefaultPlayerInfoConfig() {
        if (playerInfoConfigFile == null) {
            playerInfoConfigFile = new File(Main.getInstance().getDataFolder(), "playerInfo.yml");
        }
        if (!playerInfoConfigFile.exists()) {
            Main.getInstance().saveResource("playerInfo.yml", false);
        }
    }

}
