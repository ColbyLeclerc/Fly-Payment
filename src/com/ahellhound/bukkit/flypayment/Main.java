package com.ahellhound.bukkit.flypayment;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
    //TODO Add Gradle to project
    // eliminate Variables
    public static Economy econ = null;
    // eliminate Variables
    public static Permission permission = null;
    // Config instance variable
    private static Main instance;
    // Reload config instance variable
    private static Main reloadInstance;
    Logger log = Logger.getLogger("Minecraft");
    PlayerInfo PlayerInfo = new PlayerInfo();
    // Messages constructor
    private Messages Messages = new Messages();
    private Timers Timers = new Timers();
    private Flight Flight = new Flight();
    private BanList BanList = new BanList();
    private Configuration Configuration = new Configuration();
    private FreeFly FreeFly = new FreeFly();
    private Scheduler Scheduler = new Scheduler();
    private Utilities Utilities = new Utilities();
    // Event Handlers constructor
    private EventHandlers EventHandlers = new EventHandlers();

    // Main instance
    public static Main getInstance() {

        return instance;

    }

    // Reload instance
    public static Main getReloadInstance() {
        return reloadInstance;

    }

    @Override
    public void onEnable() {

        instance = this;

        // reloads config
        reloadConfiguration();
        // reloads ban list
        BanList.reloadBanListConfig();
        // reloads free fly config
        FreeFly.reloadFreeFlyConfig();
        // reploads player info
        PlayerInfo.reloadPlayerInfoConfig();

        // checks permission
        if (!setupPermissions()) {
            log.severe(String.format("[%s] - Disabled! You NEED Vault, with a Permission plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // checks economy
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled! You NEED Vault, with an Economy plugin and a Permission plugin!", getDescription()
                    .getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();

        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        // saves default config
        saveDefaultConfig();
        // saves banlist
        BanList.saveDefaultBanList();
        // saves free fly file
        FreeFly.saveDefaultFreeFlyConfig();
        // saves default
        PlayerInfo.saveDefaultPlayerInfoConfig();
        // Starts timers
        Scheduler.enableFlyModeOneTimer();
        Scheduler.enableWithdrawTimer();
        Scheduler.enableFlyCheckTimer();
        Scheduler.enableFreeFlyTimer();

        // gets plugin manager
        getServer().getPluginManager().registerEvents(this, this);
        log.info("[" + getDescription().getName() + "] " + getDescription().getName() + " version " + getDescription().getVersion()
                + " is now enabled.");
        log.info("[" + getDescription().getName() + "]" + " Made By AhellHound");

        try {
            if (!("3.3").equals(String.valueOf(Configuration.getVersion()))) {

                Configuration.resetFiles();
                log.severe("[" + getDescription().getName() + "] " + "Configuration files out of date. Loading new configuration file...");

            }
        } catch (NullPointerException exception) {

            Configuration.resetFiles();
            log.severe("[" + getDescription().getName() + "] " + "Configuration file out of date. Loading new configuration file...");

        }

    }

    // Reloads configuration, creates tiers
    public void reloadConfiguration() {
        reloadConfig();
        reloadInstance = this;

    }

    // Hooks into Vault
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
                net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    // Hooks into Vault
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(
                net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            if (sender instanceof Player && ((Player) sender).getPlayer().hasPermission("flyp.reload")) {
                reloadConfig();
                ((Player) sender).getPlayer().sendMessage("FlyPayment version " + getDescription().getVersion() + " has been Reloaded.");
                log.info("[" + getDescription().getName() + "] " + getDescription().getName() + " version " + getDescription().getVersion()
                        + " has been reloaded");
                return true;
            } else if (sender instanceof Player) {

                ((Player) sender).getPlayer().sendMessage(Messages.permissionErrorMessage(((Player) sender)));

            }

            reloadConfig();
            log.info("[" + getDescription().getName() + "] " + getDescription().getName() + " version " + getDescription().getVersion()
                    + " has been reloaded");

            return true;
        }

        // Player check
        if (!(sender instanceof Player)) {
            // sends console message
            sender.sendMessage(Messages.playerErrorMessage());
            // ends method
            return true;
        }

        // player sender
        Player p = (Player) sender;

        // tier variable
        int tier = Configuration.getTier(p);

        // if permission node isn't within the 1-12 range
        if (tier == 0 && (!FreeFly.isFreeFlyTimerEnabled())) {
            // sends player message
            p.sendMessage(Messages.permissionErrorMessage(p));
            // method return
            return true;
        }

        // checks if fly on has only one argument
        if (args.length == 0) {

            String[] stringArray = Messages.commandUsageErrorMessage(p).split("/n");

            for (String s : stringArray) {

                p.sendMessage(s);

            }

            return true;
        }
        // Arg's check

        // Checks to see if 'Flying ' is enabled
        if (args[0].equalsIgnoreCase("on")) {
            // Ban list constructor

            if (BanList.isBannedFly(p)) {
                // sends player message about ban
                p.sendMessage(Messages.playerBanCantFlyMessage(p));

                return true;
            }

            // checks if player can fly
            if (p.getAllowFlight()) {
                // sends player message
                p.sendMessage(Messages.flyingAlreadyEnabledMessage(p));
                // returns true
                return true;
            }

            if (FreeFly.isFreeFlyTimerEnabled()) {

                // enables free flight
                Flight.enableFreeFlight(p);
                return true;
            }
            // TODO fix
            // If player has flyp.bypass, gives them fly without checks TEST
            if (p.hasPermission("flyp.bypass")) {

                // sends player message about flying
                p.sendMessage(Messages.flyingEnabledMessage(p));
                // enables flight for no charge
                Timers RiseMCTimers = new Timers();
                p.setAllowFlight(true);
                p.setFlying(true);
                RiseMCTimers.addFlyCheckArray(p);
                Scheduler.addSafePlayer(p);
                // TODO make method
                // ends method
                return true;

            }

            if (PlayerInfo.isFlyTempBan(p)) {
                // Sends player tempban fly message
                p.sendMessage(Messages.flyTempBan(p));
                return true;
            }

            if (!p.getAllowFlight()) {
                // TODO add an estimated time w/ items and exp in inventory

                // Flight constructor
                Flight Flight = new Flight();
                // enables flight
                Flight.enableFlight(p, tier);
                // sends player message about flight
                return true;
            }
        }

        // The 'off' argument
        else if (args[0].equalsIgnoreCase("off")) {

            if (!p.getAllowFlight()) {
                if (!Timers.isInFlyCheckArray(p)) {
                    // sends player message saying flights already enabled
                    p.sendMessage(Messages.flyingAlreadyDisabledMessage(p));
                    return true;
                } else {
                    Flight.disableFlight(p, 0);
                }

            }

            if (Timers.isInFreeFlyArray(p)) {
                // Flight constructor
                Flight.disableFreeFlight(p);
                return true;

            }
            // ends flight
            Flight.disableFlight(p, 0);
            // Reload argument

        } else if (args[0].equalsIgnoreCase("disableAll")) {
            // permission check
            if (!p.hasPermission("flyp.disableall")) {
                p.sendMessage(Messages.permissionErrorMessage(p));
                return true;
            }
            // TODO
            // Disables everyone's flight
            Scheduler.disableAllPlayersFlight();

        } else if (args[0].equalsIgnoreCase("cost") || args[0].equalsIgnoreCase("costs")) {

            if (args.length != 4) {

                p.sendMessage(Messages.chargeOrderUsageErrorMessage(p));
                return true;
            }

            if (PlayerInfo.addEntryChargeOrder(p, args[1], args[2], args[3])) {

                p.sendMessage(Messages.costOrderCreated(p, args[1], args[2], args[3]));

                return true;
                // Player Info constructor
                // TODO ADD PLAYER MESSAGE
            } else {
                p.sendMessage(Messages.chargeOrderUsageErrorMessage(p));
                return true;
            }

        } else if (args[0].equalsIgnoreCase("freefly")) {

            if (args.length == 1) {
                p.sendMessage(Messages.freeFlightUsageErrorMessage(p));
                return true;
            }

            // permission check
            if (!(p.hasPermission("FlyP.freefly"))) {
                // Sends player no permission message
                p.sendMessage(Messages.permissionErrorMessage(p));
                // returns true
                return true;

            }

            if (args[1].equalsIgnoreCase("off")) {

                // check if free fly is on
                if (FreeFly.getFreeFly()) {
                    // Disables free fly
                    FreeFly.disableFreeFlightConfig();

                    p.sendMessage(Messages.freeFlightDisabledMessage(p));

                    return true;

                } else {
                    // messages user flying enabled

                    p.sendMessage(Messages.freeFlightIsDisabledMessage(p));
                    return true;
                }

            } else if (args[1].equalsIgnoreCase("on")) {

                // TODO test

                if (args.length == 2) {
                    // TODO Fix file saving
                    // sets free fly to true
                    FreeFly.setFreeFly(true);
                    // sets to -1 for unlimited
                    FreeFly.setFreeFlyTime(-1);
                    // sends player message
                    p.sendMessage(Messages.freeFlightEnabledPermanentlyMessage(p));
                    // ends method
                    return true;

                }
                // converts time arg to string
                String timeFreeFly = args[2];

                // Converts time banned to milliseconds
                long timeFreeFlyMilliseconds = Utilities.parseStringToMilliseconds(timeFreeFly);
                // checks if player entered incorrect time format
                if (timeFreeFlyMilliseconds == 0) {
                    // sends player message if ban was incorrect
                    p.sendMessage(Messages.banTimeValidationMessage(p, timeFreeFly));
                    // ends method
                    return true;
                } else {

                    // enables free flight
                    FreeFly.enableFreeFlyConfig(timeFreeFly);
                    Scheduler.enableFreeFlyTimer();
                    p.sendMessage(Messages.freeFlightEnabledTimeMessage(p, timeFreeFlyMilliseconds));
                    return true;
                }

            } else {
                // sends player invalid args message
                p.sendMessage(Messages.freeFlightUsageErrorMessage(p));
                return true;
            }

        } else if (args[0].equalsIgnoreCase("ban")) {

            if (!(p.hasPermission("FlyP.ban.add"))) {
                // Sends player no permission message
                p.sendMessage(Messages.permissionErrorMessage(p));
                // returns true
                return true;
            }

            // checks if ban is lacking arguments
            if ((args.length == 1) || (args.length == 4)) {
                // sends player command usage error message
                p.sendMessage(Messages.commandBanUsageErrorMessage(p));
                // ends method
                return true;

            }

            // Arg amount check
            if (args.length == 3) {
                // converts banned player argument to string
                String bannedPlayer = args[1];

                // checks if player is exempt from being banned

                try {
                    if (getServer().getPlayer(bannedPlayer).hasPermission("FlyP.ban.protect")) {
                        // tells command sender player was not banned because
                        // protected from fly ban
                        p.sendMessage(Messages.playerProtectedFromBanMessage(p, bannedPlayer));
                        // ends method
                        return true;
                    }
                } catch (NullPointerException e) {

                }
                // converts time arg to string
                String timeBanned = args[2];
                // Converts time banned to milliseconds
                long timeBannedMilliseconds = Utilities.parseStringToMilliseconds(timeBanned);
                // checks if player entered incorrect time format
                if (timeBannedMilliseconds == 0) {
                    // sends player message if ban was incorrect
                    p.sendMessage(Messages.banTimeValidationMessage(p, timeBanned));
                    // ends method
                    return true;
                }
                // checks max ban time
                if (BanList.hasMaxBanTime()) {
                    // checks if command sender has override command to bypass
                    // max ban time
                    if (p.hasPermission("FlyP.ban.override")) {
                        // adds player to ban list
                        BanList.addPlayerBanList(bannedPlayer, timeBanned);
                        // Sends player message saying person was banned
                        p.sendMessage(Messages.banAddedMessage(p, bannedPlayer, timeBanned));
                        // ends method
                        return true;
                    }

                    // checks if ban is longer than max time
                    if (timeBannedMilliseconds > BanList.getMaxBanTime()) {
                        // sends player message saying ban was too long
                        p.sendMessage(Messages.maxBanTimeReachedMessage(p));
                        // returns true, ends method
                        return true;
                        // if ban time is within max ban time limit, runs ban
                        // list add
                    } else {

                        // adds player to ban list
                        BanList.addPlayerBanList(bannedPlayer, timeBanned);
                        // Sends player message saying person was banned
                        p.sendMessage(Messages.banAddedMessage(p, bannedPlayer, timeBanned));
                        // ends method
                        return true;
                    }

                    // if no max ban, then runs ban list add
                } else {

                    // adds player to ban list
                    BanList.addPlayerBanList(bannedPlayer, timeBanned);
                    // Sends player message saying person was banned
                    p.sendMessage(Messages.banAddedMessage(p, bannedPlayer, timeBanned));
                    // ends method
                    return true;

                }

                // doesn't include time
            } else if (args.length == 2) {
                // checks if command sender has override pemrission to bypass
                // max ban time
                if (p.hasPermission("FlyP.ban.override")) {
                    // banned player
                    String bannedPlayer = args[1];
                    // time banned permanent
                    String timeBanned = ("-1");
                    // adds player to ban list
                    BanList.addPlayerBanList(bannedPlayer, timeBanned);
                    // Sends player message saying person was banned
                    p.sendMessage(Messages.banAddedMessage(p, bannedPlayer, timeBanned, true));
                    // ends method
                    return true;
                }

                // checks if player needs to enter time
                if (BanList.getRequireBanTime()) {

                    // sends player message requiring to set a ban time
                    p.sendMessage(Messages.requiredBanTimeCommandMessage(p));
                    // ends method
                    return true;

                } else {

                    // banned player
                    String bannedPlayer = args[1];
                    // time banned permanent
                    String timeBanned = ("-1");
                    // adds player to ban list
                    BanList.addPlayerBanList(bannedPlayer, timeBanned);
                    // Sends player message saying person was banned
                    p.sendMessage(Messages.banAddedMessage(p, bannedPlayer, timeBanned, true));
                    // ends method
                    return true;

                }

            }
        } else if (args[0].equalsIgnoreCase("removeban")) {
            // checks if player has ban removal permission
            if (!p.hasPermission("FlyP.ban.remove")) {
                // Sends player no permission message
                p.sendMessage(Messages.permissionErrorMessage(p));
                // returns true
                return true;
            }
            // checks if incorrect amount of args are given
            if (args.length >= 3 || args.length < 2) {
                // sends player command usage error emssage
                p.sendMessage(Messages.commandBanUsageErrorMessage(p));
                // ends method
                return true;

            }
            // assigns variable to player being removed
            String removedPlayer = args[1];
            // checks if player is banned
            if (BanList.isBannedFly(removedPlayer)) {
                // removes player ban
                BanList.removeBan(removedPlayer);
                // sends ban removed message
                p.sendMessage(Messages.banRemovedMessage(p, removedPlayer));
                return true;

            } else {
                // Sends command sender message player was not banned to begin
                // with
                p.sendMessage(Messages.playerNotBannedMessage(p, removedPlayer));
                return true;
            }

            // end of removeban command
        } else if (args[0].equalsIgnoreCase("checkban")) {
            // checks if player has ban removal permission
            if (!p.hasPermission("FlyP.ban.check")) {
                // Sends player no permission message
                p.sendMessage(Messages.permissionErrorMessage(p));
                // returns true
                return true;
            }
            // checks if incorrect amount of args are given
            if (args.length >= 3 || args.length < 2) {
                // sends player command usage error message
                p.sendMessage(Messages.commandBanUsageErrorMessage(p));
                // ends method
                return true;

            }
            // assigns player to variable
            String checkedPlayer = args[1];
            // checks ban
            if (BanList.isBannedFly(checkedPlayer)) {
                // checks if player is permanently banned
                if (BanList.getPlayerBanTime(checkedPlayer).equals(-1)) {

                    // sends command sender different message about permanent
                    // ban
                    p.sendMessage(Messages.playerBannedPermanentlyMessage(p, checkedPlayer));
                    return true;
                }
                // Sends command sender message about player's ban and their
                // time
                p.sendMessage(Messages.playerBannedCheckMessage(p, checkedPlayer));
                return true;
            } else {
                // sends command to sender saying player is not banned
                p.sendMessage(Messages.playerNotBannedMessage(p, checkedPlayer));
                return true;
            }
        } else {
            p.sendMessage(Messages.invalidArgumentMessage(p));
            return true;
        }

        // Method return
        return true;
    }

    // player fall damage event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerFallDamageEvent(EntityDamageEvent event) {

        EventHandlers.onPlayerFallDamage(event);

    }

    // player quit event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent event) {

        EventHandlers.onPlayerQuit(event);

    }

    // player kick event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        EventHandlers.onPlayerDamageReceiveEvent(event);

    }

    // player recieve damage event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamageEvent(EntityDamageEvent event) {

        EventHandlers.onPlayerDamageGivenEvent(event);

    }

    // player give damage event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerKickEvent(PlayerKickEvent event) {

        EventHandlers.onPlayerKick(event);

    }

    // player uses potion
    @EventHandler(priority = EventPriority.HIGHEST)
    public void potionSplashEvent(PotionSplashEvent event) {

        EventHandlers.onPlayerPotionSplashEvent(event);

    }

    // Arrow event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void projectileHitEvent(ProjectileHitEvent event) {

        EventHandlers.onProjectileHitEvent(event);

    }

    // World change event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangedWorldEvent(PlayerChangedWorldEvent event) {

        EventHandlers.onPlayerWorldChangeEvent(event);

    }

}
