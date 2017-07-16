package com.ahellhound.bukkit.flypayment;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Configuration {

    public boolean getReloadSafetyTeleport() {

        return Main.getInstance().getConfig().getBoolean("OnReloadTeleportToGround");

    }

    public String getItemChargeEnum(int tier) {

        return Main.getInstance().getConfig().getString("ChargeItemNameTier" + tier);

    }

    public String getCostOrderCreatedMessage() {

        return Main.getInstance().getConfig().getString("costOrderCreatedMessage");

    }

    public int getFlyMode(int tier) {

        return Main.getInstance().getConfig().getInt("TimerModeTier" + tier);

    }

    public int getFlyModeTimerAmount() {

        return Main.getInstance().getConfig().getInt("FlyModeOneTimerAmount");

    }

    public int getFlyModeAmount(int tier) {

        return Main.getInstance().getConfig().getInt("TimerAmountTier" + tier);

    }

    public int getItemChargeAmount(int tier) {

        return Main.getInstance().getConfig().getInt("AmountOfItemsToChargeTier" + tier);

    }

    public long getTimerAmount(int tier) {

        return Main.getInstance().getConfig().getLong("FlightTimerTier" + tier);

    }

    public int getBanCheckTimer() {

        return Main.getInstance().getConfig().getInt("BanCheckTimer");

    }

    public int getExpChargeAmount(int tier) {

        return Main.getInstance().getConfig().getInt("ChargeEXPTier" + tier);

    }

    public int getMoneyChargeAmount(int tier) {

        return Main.getInstance().getConfig().getInt("ChargeMoneyTier" + tier);

    }

    public boolean getEconomyAccount(int tier) {

        return Main.getInstance().getConfig().getBoolean("EconomyAccountTier" + tier);

    }

    public String getItemCustomName(int tier) {

        return Main.getInstance().getConfig().getString("ChargeItemCustomNameTier" + tier);

    }

    public String getEconomyAccountName(int tier) {

        return Main.getInstance().getConfig().getString("EconomyAccountNameTier" + tier);

    }

    public int getTier(Player p) {
        int tier = 0;
        if (p.hasPermission("FlyP.Fly.1")) {
            tier = 1;
        }
        if (p.hasPermission("FlyP.Fly.2")) {
            tier = 2;
        }
        if (p.hasPermission("FlyP.Fly.3")) {
            tier = 3;
        }
        if (p.hasPermission("FlyP.Fly.4")) {
            tier = 4;
        }
        if (p.hasPermission("FlyP.Fly.5")) {
            tier = 5;
        }
        if (p.hasPermission("FlyP.Fly.6")) {
            tier = 6;
        }
        if (p.hasPermission("FlyP.Fly.7")) {
            tier = 7;
        }
        if (p.hasPermission("FlyP.Fly.8")) {
            tier = 8;
        }
        if (p.hasPermission("FlyP.Fly.9")) {
            tier = 9;
        }
        if (p.hasPermission("FlyP.Fly.10")) {
            tier = 10;
        }
        if (p.hasPermission("FlyP.Fly.11")) {
            tier = 11;
        }
        if (p.hasPermission("FlyP.Fly.12")) {
            tier = 12;
        }

        return tier;
    }

    public String getWithdrawTimer(int tier) {

        return Main.getInstance().getConfig().getString("WithdrawTimerTier" + tier);

    }

    public int getFlyCheckTimer() {

        return Main.getInstance().getConfig().getInt("FlyCheckTimer");

    }

    public String getFlySpeed(int tier) {

        return Main.getInstance().getConfig().getString("FlySpeedTier" + tier);

    }

    public String getItemMetaTag(int tier) {
        // TODO Testing name error with '|' in item meta (Bukkit/Java converts
        // them to ',')
        return Main.getInstance().getConfig().getString("ItemMetaLoreTier" + tier).replace("&", "\u00A7").replace("|", ",");

    }

    public boolean getFlyBanRequireTime() {

        return Main.getInstance().getConfig().getBoolean("FlyBanRequireTime");

    }

    public int getFlyBanMaxTime() {

        return Main.getInstance().getConfig().getInt("FlyBanMaxTime");

    }

    // **Messages

    public String getPlayerErrorConfigMessage() {

        return Main.getInstance().getConfig().getString("PlayerErrorMessage");

    }

    public String getCommandUsageErrorConfigMessage() {

        return Main.getInstance().getConfig().getString("CommandUsageErrorMessage");

    }

    public String getPermissionErrorConfigMessage() {

        return Main.getInstance().getConfig().getString("PermissionErrorMessage");

    }

    public String getFlyingAlreadyEnabledConfigMessage() {

        return Main.getInstance().getConfig().getString("FlyingAlreadyEnabledMessage");

    }

    public String getFlyingEnabledConfigMessage() {

        return Main.getInstance().getConfig().getString("FlyingEnabledMessage");

    }

    public String getItemRequirementMessage() {

        return Main.getInstance().getConfig().getString("ItemRequirementMessage");

    }

    public String getEXPRequirementMessage() {

        return Main.getInstance().getConfig().getString("EXPRequirementMessage");

    }

    public String getMoneyRequiredMessage() {

        return Main.getInstance().getConfig().getString("MoneyRequirementMessage");

    }

    public String getFlyingEnabledNoTimeLimitMessage() {

        return Main.getInstance().getConfig().getString("FlyingEnabledNoTimeLimitMessage");

    }

    public String getInvalidArgumentMessage() {

        return Main.getInstance().getConfig().getString("InvalidArgumentMessage");

    }

    public void resetFiles() {

        PlayerInfo PlayerInfo = new PlayerInfo();
        BanList BanList = new BanList();
        FreeFly FreeFly = new FreeFly();

        File dataFolder = Bukkit.getServer().getPluginManager().getPlugin("FlyPayment").getDataFolder().getAbsoluteFile();

        String[] files = new String[4];

        files[0] = "config.yml";
        files[1] = "playerInfo.yml";
        files[2] = "banList.yml";
        files[3] = "freeFly.yml";

        for (String i : files) {
            try {

                File oldDir = new File(dataFolder + "\\oldConfigs");

                if (!oldDir.exists()) {

                    oldDir.mkdir();

                }

                Files.move(new File(dataFolder + "\\" + i), new File(dataFolder + "\\oldConfigs\\" + i));

                File deleteFile = new File(dataFolder + "\\" + i);

                deleteFile.delete();

                Main.getInstance().log.info("[" + Main.getInstance().getDescription().getName() + "] " + "File " + i
                        + " moved successfully!");

            } catch (IOException e) {

                Main.getInstance().log.severe("[" + Main.getInstance().getDescription().getName() + "] "
                        + "Old configuration files failed to move!");

                e.printStackTrace();
            }
        }

        Main.getInstance().saveDefaultConfig();
        Main.getInstance().reloadConfig();
        PlayerInfo.saveDefaultPlayerInfoConfig();
        PlayerInfo.reloadPlayerInfoConfig();
        FreeFly.saveDefaultFreeFlyConfig();
        FreeFly.reloadFreeFlyConfig();
        BanList.saveDefaultBanList();
        BanList.reloadBanList();

    }

    public String getVersion() {

        return Main.getInstance().getConfig().getString("PLEASE_DO_NOT_TOUCH_THIS");

    }

    // TODO Add
    public String getFlyingOffMessage() {

        return Main.getInstance().getConfig().getString("FlyingOffIn10Message");

    }

    public String getItemRemovedMessage() {

        return Main.getInstance().getConfig().getString("ItemRemovedMessage");

    }

    public String getEXPRemovedMessage() {

        return Main.getInstance().getConfig().getString("EXPRemovedMessage");

    }

    public String getMoneyRemovedMessage() {

        return Main.getInstance().getConfig().getString("MoneyRemovedMessage");

    }

    public String getTimeLeftMessage() {

        return Main.getInstance().getConfig().getString("TimeLeftMessage");

    }

    public String getFlyingAlreadyDisabledMessage() {

        return Main.getInstance().getConfig().getString("FlyingAlreadyDisabledMessage");

    }

    public String getFlyingDisabledMessage() {

        return Main.getInstance().getConfig().getString("FlyingDisabledMessage");

    }

    public String getSecondsMessage() {

        return Main.getInstance().getConfig().getString("SecondsMessage");

    }

    public String getMinutesMessage() {

        return Main.getInstance().getConfig().getString("MinutesMessage");

    }

    public String getFlyingHasNoTimeLimitMessage() {

        return Main.getInstance().getConfig().getString("FlyingHasNoTimeLimitMessage");

    }

    public String getTimeLeftNotFlyingMessage() {

        return Main.getInstance().getConfig().getString("TimeLeftNotFlyingMessage");

    }

    public String getTimeLeftCommandMessage() {

        return Main.getInstance().getConfig().getString("TimeLeftCommandMessage");

    }

    public String getRequiredBanTimeCommandMessage() {

        return Main.getInstance().getConfig().getString("FlyBanRequiredTimeMessage");
    }

    public String getBanAddedMessage() {

        return Main.getInstance().getConfig().getString("BanAddedMessage");
    }

    public String getMaxBanTimeReachedMessage() {

        return Main.getInstance().getConfig().getString("MaxBanTimeReachedMessage");
    }

    public String getPlayerProtectedFromBanMessage() {

        return Main.getInstance().getConfig().getString("PlayerProtectedFromBanMessage");
    }

    public String getPlayerOfflineMessage() {

        return Main.getInstance().getConfig().getString("PlayerOfflineMessage");

    }

    public String getBanTimeValidationMessage() {

        return Main.getInstance().getConfig().getString("BanTimeValidationMessage");
    }

    public String getCommandBanUsageErrorMessage() {

        return Main.getInstance().getConfig().getString("BanCommandUsageError");
    }

    public String getPlayerNotBannedMessage() {

        return Main.getInstance().getConfig().getString("PlayerNotBannedMessage");
    }

    public String getBanRemovedMessage() {

        return Main.getInstance().getConfig().getString("BanRemovedMessage");
    }

    public String getPlayerBannedCheckMessage() {

        return Main.getInstance().getConfig().getString("PlayerBannedCheckMessage");
    }

    public String getBannedPermanentlyMessage() {

        return Main.getInstance().getConfig().getString("BannedPermanentlyMessage");
    }

    public String getPlayerBannedCantFlyMessage() {

        return Main.getInstance().getConfig().getString("PlayerBannedCantFlyMessage");
    }

    public String getMessagePrefix() {

        return Main.getInstance().getConfig().getString("MessagePrefix");
    }

    public String getBannedWorlds() {

        return Main.getInstance().getConfig().getString("BannedWorlds");
    }

    public String getFreeFlightEnabled() {

        return Main.getInstance().getConfig().getString("FreeFlightEnabled");
    }

    public String getFreeFlightEnabledMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightEnabledMessage");
    }

    public String getFreeFlightIsDisabledMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightIsDisabledMessage");
    }

    public String getFreeFlightEnabledPermanentlyMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightEnabledPermanentlyMessage");
    }

    public String getFreeFlightUsageErrorMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightUsageErrorMessage");
    }

    public String getFreeFlightEnabledTimeMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightEnabledTimeMessage");
    }

    public String getFreeFlightDisabledMessage() {

        return Main.getInstance().getConfig().getString("FreeFlightDisabledMessage");
    }

    public String getChargeOrderUsageErrorMessage() {

        return Main.getInstance().getConfig().getString("ChargeOrderUsageErrorMessage");
    }

    public String getChargeOrderExpMessage() {

        return Main.getInstance().getConfig().getString("ChargeOrderExpMessage");
    }

    public String getChargeOrderItemMessage() {

        return Main.getInstance().getConfig().getString("ChargeOrderItemMessage");
    }

    public int getFlyTempBanAmount() {
        // Gets temp ban time amount

        return Main.getInstance().getConfig().getInt("PvPTempBanTime");
    }

    public String getFlyTempBanMessage() {
        // TODO Auto-generated method stub
        return Main.getInstance().getConfig().getString("FlyTempBanMessage");
    }
}
