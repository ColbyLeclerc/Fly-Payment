package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

public class Messages {

    private Utilities Utilities = new Utilities();

    private Configuration Configuration = new Configuration();

    // /Messages
    public String playerErrorMessage() {

        // returns message from config
        return Configuration.getPlayerErrorConfigMessage();
    }

    public String costOrderCreated(Player p, String arg1, String arg2, String arg3) {
        // Gets message from config

        return Configuration.getCostOrderCreatedMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%arg1%", arg1)
                .replace("%arg2%", arg2).replace("%arg3%", arg3);
    }

    public String commandUsageErrorMessage(Player p) {
        // Gets message from config

        return Configuration.getCommandUsageErrorConfigMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String permissionErrorMessage(Player p) {

        // returns message from config
        return Configuration.getPermissionErrorConfigMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String flyingAlreadyEnabledMessage(Player p) {

        return Configuration.getFlyingAlreadyEnabledConfigMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String flyingEnabledMessage(Player p) {
        // Gets message from config

        return Configuration.getFlyingEnabledConfigMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("&", "\u00A7");

    }

    public String itemRequirementMessage(Player p, int tier) {
        PlayerPayments PlayerPayments = new PlayerPayments();
        // gets item charge amount
        int itemChargeAmount = Configuration.getItemChargeAmount(tier);
        // gets item name
        String itemName = Configuration.getItemChargeEnum(tier);
        // Converts primitive int to string integer
        String itemChargeAmountString = Utilities.convertIntToString(itemChargeAmount);
        // Gets message from config
        String expAmount = String.valueOf(Configuration.getExpChargeAmount(tier));
        String itemChargeAmountMessage = Configuration.getItemRequirementMessage();
        String itemLore = Configuration.getItemMetaTag(tier);
        String moneyAmount = String.valueOf(Configuration.getMoneyChargeAmount(tier));
        String moneyName;
        String currentEXP = String.valueOf(com.ahellhound.bukkit.flypayment.Utilities.getTotalExperience(p));
        Main.getInstance();
        String currentMoney = String.valueOf(Main.econ.getBalance(p.getName()));
        String currentItemAmount = String.valueOf(PlayerPayments.getPlayerItemAmount(p));

        if (Double.parseDouble(moneyAmount) == 1) {

            Main.getInstance();
            moneyName = Main.econ.currencyNameSingular();

        } else {

            Main.getInstance();
            moneyName = Main.econ.currencyNamePlural();

        }

        // replaces %player% with command sender string name; replaces
        // %ItemAmount% with item name required by tier set for command sender;
        // %ItemName% with item name required by tier set for command sender

        if (itemLore.equals("-1")) {

            itemLore = "No Lore";

        }

        if (expAmount.equals("0")) {

            expAmount = "EXP Payment Disabled";
        }

        if (moneyAmount.equals("0")) {

            moneyAmount = "Money Payment Disabled";
        }

        if (itemChargeAmountString.equals("0")) {

            itemChargeAmountString = "Item Payment Disabled";

        }

        String itemChargeAmountMessageEnd = itemChargeAmountMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7")
                .replace("%ItemAmount%", itemChargeAmountString).replace("%ItemLore%", itemLore).replace("%ItemName%", itemName)
                .replace("%ExpAmount%", expAmount).replace("%MoneyAmount%", moneyAmount).replace("%MoneyName%", moneyName)
                .replace("%CurrentEXP%", currentEXP).replace("%CurrentItemAmount%", currentItemAmount)
                .replace("%CurrentMoney%", currentMoney);
        // returns message from config
        return itemChargeAmountMessageEnd;

    }

    public String EXPRequirementMessage(Player p, int tier) {
        // gets chage amount of exp
        int expChargeAmount = Configuration.getExpChargeAmount(tier);
        // arithmetic for remaing exp needed
        int remainingEXPRequired = (expChargeAmount - p.getTotalExperience());
        // converts primitive int to string integer
        String RemainingEXP = Utilities.convertIntToString(remainingEXPRequired);
        // Gets message from config
        String EXPRequirementMessage = Configuration.getEXPRequirementMessage();
        // replaces %player% with command sender string name; replaces
        // %RemainingEXPNeeded% with exp needed left to make command sender fly
        String EXPRequirementMessageEnd = EXPRequirementMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%RemainingEXPNeeded%", RemainingEXP);
        // returns message from config
        return EXPRequirementMessageEnd;

    }

    public String moneyRequirementMessage(Player p, double moneyRequired, String moneyPlural, String moneySingular) {
        // converts primitive int to string integer
        String moneyRequiredString = Utilities.convertDoubleToString(moneyRequired);
        // Gets message from config s
        String moneyRequiredMessage = Configuration.getMoneyRequiredMessage();
        // replaces %player% with command sender string name; replaces
        // %RemainingEXPNeeded% with exp needed left to make command sender fly
        String moneyRequiredMessageEnd = moneyRequiredMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7")
                .replace("%RemainingMoneyNeeded%", moneyRequiredString).replace("%MoneyNamePlural%", moneyPlural)
                .replace("%MoneyNameSingular%", moneySingular);
        // returns message from config
        return moneyRequiredMessageEnd;

    }

    public String flyingEnabledNoTimelimitMessage(Player p) {
        // Gets message from config

        return Configuration.getFlyingEnabledNoTimeLimitMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String invalidArgumentMessage(Player p) {

        // returns message from config
        return Configuration.getInvalidArgumentMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String flyingOffMessage(Player p) {
        // Gets message from config

        return Configuration.getFlyingOffMessage().replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7");

    }

    public String itemRemovedMessage(Player p, int tier) {
        // Gets message from config
        String itemRemovedMessage = Configuration.getItemRemovedMessage();
        // gets item charge amount
        int itemChargeAmount = Configuration.getItemChargeAmount(tier);
        String itemName = Configuration.getItemChargeEnum(tier);
        // gets item name // Converts int to String
        String itemChargeAmountString = Utilities.convertIntToString(itemChargeAmount);
        String itemRemovedMessageEnd = itemRemovedMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%ItemName%", itemName)
                .replace("%ItemChargeAmount%", itemChargeAmountString);
        // returns message from config
        return itemRemovedMessageEnd;

    }

    public String EXPRemovedMessage(Player p, int EXPChargeAmount) {
        // Gets message from config
        String EXPRemovedMessage = Configuration.getEXPRemovedMessage();
        // Converts int to String
        String EXPChargeAmountString = Utilities.convertIntToString(EXPChargeAmount);
        // replaces config holders
        String EXPRemovedMessageEnd = EXPRemovedMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7")
                .replace("%EXPChargeAmount%", EXPChargeAmountString);
        // returns message from config
        return EXPRemovedMessageEnd;

    }

    public String moneyRemovedMessage(Player p, int tier) {

        // Gets message from config
        String EXPRemovedMessage = Configuration.getEXPRemovedMessage();

        String moneyAmount = String.valueOf(Configuration.getMoneyChargeAmount(tier));
        String moneyName;

        if (Double.parseDouble(moneyAmount) == 1) {

            Main.getInstance();
            moneyName = Main.econ.currencyNameSingular();

        } else {

            Main.getInstance();
            moneyName = Main.econ.currencyNamePlural();

        }

        String EXPRemovedMessageEnd = EXPRemovedMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%MoneyName%", moneyName)
                .replace("%MoneyAmount", moneyAmount);
        // returns message from config
        return EXPRemovedMessageEnd;

    }

    public String moneyRemovedMessage(Player p, int moneyChargeAmount, String moneyPlural, String moneySingular) {
        // Gets message from config
        String moneyRemovedMessage = Configuration.getMoneyRemovedMessage();
        // Converts int to String
        String moneyChargeAmountString = Utilities.convertIntToString(moneyChargeAmount);
        // replaces config holders
        String moneyRemovedMessageEnd = moneyRemovedMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7")
                .replace("%MoneyChargeAmount%", moneyChargeAmountString).replace("%MoneyNamePlural%", moneyPlural)
                .replace("%MoneyNameSingular%", moneySingular);
        // returns message from config
        return moneyRemovedMessageEnd;

    }

    public String timeLeftMessage(Player p, String timeLeft, String minutesOrSeconds) {
        // Gets message from config
        String timeLeftMessage = Configuration.getTimeLeftMessage();
        // replaces config holders
        String timeLeftMessageEnd = timeLeftMessage.replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7").replace("%FlightTime%", timeLeft).replace("%MinutesOrSeconds%", minutesOrSeconds);
        // returns message from config
        return timeLeftMessageEnd;

    }

    public String flyingAlreadyDisabledMessage(Player p) {
        // Gets message from config
        String flyingAlreadyDisabledMessage = Configuration.getFlyingAlreadyDisabledMessage();

        String flyingAlreadyDisabledMessageEnd = flyingAlreadyDisabledMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
        // returns message from config
        return flyingAlreadyDisabledMessageEnd;

    }

    /**
     * *@param reason 0 = Fly Off; 1 = Free Fly Off; 2 = In PvP Zone; 3 =
     * Banned; 4 = No Resources Left; 5 = no build perms; 6 = get reason; 7 =
     * world flight; 8 = no combat reason;
     */
    public String flyingDisabledMessage(Player p, int reason) {
        // Gets message from config
        String flyingDisabledMessage = Configuration.getFlyingDisabledMessage();
        // flight constructor
        Flight Flight = new Flight();
        String stringReason;
        // if reason of disabling is due to more than one thing, get disable
        // reason will find out which it is
        if (reason == 6) {
            reason = Flight.getDisableReason(p);
        }
        // depending on the reason, the reason string in the message is changed
        switch (reason) {

            case (0):

                stringReason = "Flying Turned Off";
                break;

            case (1):
                stringReason = "Free-Fly has been turned off";
                break;

            case (2):
                stringReason = "You're in a PvP Zone";
                break;
            case (3):
                stringReason = "You've been banned from flying";
                break;
            case (4):
                stringReason = "You've ran out of resources to fly";
                break;
            case (5):
                stringReason = "You don't have permission to build in this area";
                break;
            case (7):
                stringReason = "You're not allowed to fly in this world!";
                break;
            case (8):
                stringReason = "You cannot engage in combat while flying!";
                break;
            default:
                stringReason = "Flying disabled";
                break;

        }

        return flyingDisabledMessage.replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7").replace("%Reason%", stringReason);

    }

    public String secondsMessage() {

        return Configuration.getSecondsMessage();

    }

    public String minutesMessage() {
        // Gets message from config

        return Configuration.getMinutesMessage();

    }

    public String timeLeftCommandMessage(Player p, String timeLeft) {
        // Gets message from config

        // returns secondsMessage
        return Configuration.getTimeLeftCommandMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%timeLeftCommand%", timeLeft);

    }

    public String timeLeftNotFlyingMessage(Player p) {
        // Gets message from config

        return Configuration.getTimeLeftNotFlyingMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String flyingHasNoTimeLimitMessage(Player p) {

        return Configuration.getFlyingHasNoTimeLimitMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String requiredBanTimeCommandMessage(Player p) {

        return Configuration.getRequiredBanTimeCommandMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String banAddedMessage(Player p, String bannedP, String timeRaw) {
        // Gets message from config
        String banAddedMessage = Configuration.getBanAddedMessage();
        // converts raw time to milliseconds
        long timeMilliseconds = Utilities.parseStringToMilliseconds(timeRaw);
        // Converts time to nice format
        String time = Utilities.parseLong(timeMilliseconds, false);
        // replaces string
        String banAddedMessageEnd = banAddedMessage.replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7").replace("%BannedPlayerName%", bannedP).replace("%TimeBanned%", time);
        // returns secondsMessage
        return banAddedMessageEnd;

    }

    public String banAddedMessage(Player p, String bannedP, String timeRaw, boolean bool) {
        // Gets message from config
        String banAddedMessage = Configuration.getBanAddedMessage();
        // converts raw time to milliseconds
        long timeMilliseconds = Utilities.parseStringToMilliseconds(timeRaw);
        // Converts time to nice format
        String time = Utilities.parseLong(timeMilliseconds, false);
        if (timeRaw == "-1") {
            // permenent is added
            String banAddedMessageEnd = banAddedMessage.replace("%player%", p.getName())
                    .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%BannedPlayerName%", bannedP)
                    .replace("%TimeBanned%", "an infinite amount of time");
            // returns edited message for infinte ban time
            return banAddedMessageEnd;
        }
        if (timeMilliseconds == 0) {
            // sends player error message
            p.sendMessage(banTimeValidationMessage(p, timeRaw));

        }
        // replaces string
        String banAddedMessageEnd = banAddedMessage.replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7").replace("%BannedPlayerName%", bannedP).replace("%TimeBanned%", time);

        // returns secondsMessage
        return banAddedMessageEnd;

    }

    public String maxBanTimeReachedMessage(Player p) {
        // Converts max ban time to string
        String maxBanTimeConverted = Utilities.parseLong((Configuration.getFlyBanMaxTime() * 1000), false);
        // Gets message from config
        String maxBanTimeReachedMessage = Configuration.getMaxBanTimeReachedMessage();
        // replaces string
        String maxBanTimeReachedMessageEnd = maxBanTimeReachedMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("&", "\u00A7")
                .replace("%MaxBanTime%", maxBanTimeConverted.toString());
        // returns secondsMessage
        return maxBanTimeReachedMessageEnd;

    }

    public String playerProtectedFromBanMessage(Player p, String protectedPlayer) {
        // Gets message from config

        return Configuration.getPlayerProtectedFromBanMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%ProtectedPlayer%", protectedPlayer);

    }

    public String playerOfflineMessage(String bannedPlayer, Player p) {
        // Gets message from config

        return Configuration.getPlayerOfflineMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%BannedPlayerName%", bannedPlayer);

    }

    public String banTimeValidationMessage(Player p, String incorrectEntry) {

        return Configuration.getBanTimeValidationMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%IncorrectEntry%", incorrectEntry);

    }

    public String commandBanUsageErrorMessage(Player p) {
        // Gets message from config

        return Configuration.getCommandBanUsageErrorMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");

    }

    public String playerNotBannedMessage(Player p, String notBannedPlayer) {
        // Gets message from config

        return Configuration.getPlayerNotBannedMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%NotBannedPlayer%", notBannedPlayer);

    }

    public String banRemovedMessage(Player p, String removedPlayer) {
        // Gets message from config

        return Configuration.getBanRemovedMessage().replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7").replace("%UnbannedPlayer%", removedPlayer);

    }

    public String playerBannedCheckMessage(Player p, String removedPlayer) {
        // Gets message from config
        String playerBannedCheckMessage = Configuration.getPlayerBannedCheckMessage();
        // banlist constructor
        BanList BanList = new BanList();
        // timed banned string
        Long timeBanned = BanList.getPlayerBanTime(removedPlayer);
        // checks if player is banned indefinitely
        // utilites constructor
        Utilities Utilites = new Utilities();
        long timeBannedLong = timeBanned;
        // banned time in string
        String timeBannedString = Utilites.parseLong(timeBannedLong, false);
        // replaces string
        String playerBannedCheckMessageEnd = playerBannedCheckMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%BannedPlayer%", removedPlayer)
                .replace("%TimeBannedFor%", timeBannedString);
        // returns message
        return playerBannedCheckMessageEnd;

    }

    public String playerBanCantFlyMessage(Player p) {
        // Gets message from config
        String playerBanCantFlyMessage = Configuration.getPlayerBannedCantFlyMessage();
        // banlist constructor
        BanList BanList = new BanList();
        // timed banned string
        Long timeBanned = BanList.getPlayerBanTime(p.getName());
        Utilities Utilites = new Utilities();
        long timeBannedLong = timeBanned;
        // Long timeBannedLong = Long.parseLong(timeBanned);
        // checks if player is banned indefinitely
        // banned time in string

        if (timeBanned.equals(-1)) {

            return playerBannedPermanentlyMessage(p, p.getName());
        }
        String timeBannedString = Utilites.parseLong(timeBannedLong + System.currentTimeMillis(), false);
        // replaces string
        String playerBanCantFlyMessageEnd = playerBanCantFlyMessage.replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%TimeBannedFor%", timeBannedString);
        // returns secondsMessage
        return playerBanCantFlyMessageEnd;
    }

    public String playerBannedPermanentlyMessage(Player p, String checkedPlayer) {

        return Configuration.getBannedPermanentlyMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7").replace("%BannedPlayer%", checkedPlayer);
    }

    public String freeFlightEnabled(Player p) {

        return Configuration.getFreeFlightEnabledMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String freeFlightIsDisabledMessage(Player p) {

        return Configuration.getFreeFlightIsDisabledMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String freeFlightDisabledMessage(Player p) {

        return Configuration.getFreeFlightDisabledMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String freeFlightEnabledPermanentlyMessage(Player p) {
        // Gets message from config

        return Configuration.getFreeFlightEnabledPermanentlyMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String freeFlightEnabledTimeMessage(Player p, long timeInMilliseconds) {
        // Gets message from config

        String parsedTimeInMilliseconds = Utilities.parseLong(timeInMilliseconds, false);

        return Configuration.getFreeFlightEnabledTimeMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7")
                .replace("%FreeFlyTime%", parsedTimeInMilliseconds);
    }

    public String freeFlightUsageErrorMessage(Player p) {

        return Configuration.getFreeFlightUsageErrorMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String chargeOrderUsageErrorMessage(Player p) {

        return Configuration.getChargeOrderUsageErrorMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String chargeOrderExpMessage(Player p) {

        return Configuration.getChargeOrderExpMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String chargeOrderItemMessage(Player p) {
        // Gets message from config

        return Configuration.getChargeOrderItemMessage().replace("%player%", p.getName())
                .replace("%prefix%", Configuration.getMessagePrefix()).replace("&", "\u00A7");
    }

    public String flyTempBan(Player p) {

        return Configuration.getFlyTempBanMessage().replace("%player%", p.getName()).replace("%prefix%", Configuration.getMessagePrefix())
                .replace("&", "\u00A7");
    }

}
