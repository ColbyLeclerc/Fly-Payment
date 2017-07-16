package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

public class Flight {
    // Messages constructor
    private Messages Messages = new Messages();
    // Scheduler constructor
    private Scheduler Scheduler = new Scheduler();
    // risemc constructor
    private Timers Timer = new Timers();
    // Configuration
    private Configuration config = new Configuration();

    // Enables flight
    public void enableFlight(Player p, int tier) {

        PlayerPayments PlayerPayments = new PlayerPayments();
        PlayerInfo PlayerInfo = new PlayerInfo();

        if (config.getFlyMode(tier) == 0) {

            // adds player to withdraw array
            Timer.addWithdrawArray(p, tier);
            Timer.addFlyCheckArray(p);

            if (PlayerPayments.chargeResources(p)) {

                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(Messages.flyingEnabledMessage(p));

            } else {

                String[] stringArray = Messages.itemRequirementMessage(p, tier).split("/n");

                for (String s : stringArray) {

                    p.sendMessage(s);

                }

            }

        } else if (config.getFlyMode(tier) == 1) {

            if (PlayerPayments.chargeResources(p)) {

                p.setAllowFlight(true);
                p.setFlying(true);
                PlayerInfo.setFlyModeOneTime(p);
                Timer.addFlyModeOneArray(p);
                p.sendMessage(Messages.flyingEnabledMessage(p));

            } else {


                String[] stringArray = Messages.itemRequirementMessage(p, tier).split("/n");

                for (String s : stringArray) {

                    p.sendMessage(s);

                }


            }


        }

        // Adds player to hashmap to find time left on flight
        Scheduler.addSafePlayer(p);
    }

    // Disables flight

    /**
     * *@param reason 0 = Fly Off; 1 = Free Fly Off; 2 = In PvP Zone; 3 =
     * Banned; 4 = No Resources Left;
     */
    public void disableFlight(Player p, int reason) {

        int tier = config.getTier(p);

        if (config.getFlyMode(tier) == 0) {

            Timer.removeWithdrawArray(p, tier);
            Timer.removeFlyCheckArray(p);
            // disallows flying
            p.setAllowFlight(false);
            // stops player from flying
            p.setFlying(false);

        } else {

            Timer.removeFlyModeOneArray(p);
            // disallows flying
            p.setAllowFlight(false);
            // stops player from flying
            p.setFlying(false);
        }

        // sends player disable flight message
        p.sendMessage(Messages.flyingDisabledMessage(p, reason));

    }

    // disables freeflight

    /**
     * *@param reason 0 = Fly Off; 1 = Free Fly Off; 2 = In PvP Zone; 3 =
     * Banned; 4 = No Resources Left;
     */
    public void disableFreeFlight(Player p) {
        // disallows flying
        p.setAllowFlight(false);
        // stops player from flying
        p.setFlying(false);
        p.sendMessage(Messages.flyingDisabledMessage(p, 1));
        // removes player from free fly array
        Timer.removeFromArrays(p);
        // sends player disable flight message
    }

    // disables freeflight
    public void enableFreeFlight(Player p) {
        // disallows flying
        p.setAllowFlight(true);
        // stops player from flying
        p.setFlying(true);

        // removes player from free fly array
        Timer.addFreeFlyArray(p);
        Timer.addFlyCheckArray(p);
        // sends player disable flight message
        p.sendMessage(Messages.freeFlightEnabled(p));

        Scheduler.addSafePlayer(p);
    }

    public void enableFlightNoCharge(Player p) {
        // allows players to fly
        p.setAllowFlight(true);
        // makes players fly
        p.setFlying(true);
        // Adds to safe player scheduler
        Scheduler.addSafePlayer(p);
        Scheduler.addSafePlayer(p);

    }

    public int getDisableReason(Player p) {

        BanList banL = new BanList();

        if (banL.isInBannedWorld(p)) {

            return 7;

        } else if (banL.isBannedFly(p)) {

            return 3;
        }

        return 0;

    }

}
