package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

import java.util.HashSet;

public class Scheduler {

    // Hasmap of players safe from fall damage
    private static HashSet<String> safePlayers = new HashSet<String>();

    // Flight constructor
    // Flight Flight = new Flight();
    // configuration constructor
    private Configuration Configuration = new Configuration();
    // RiseMCTimer
    private Timers RiseMCTimers = new Timers();

    /*
     * public void removeFlightTimer(Player p, int tier) {
     * 
     * //checks if player is in the array if (RiseMCTimers.getWithdrawArray(p,
     * tier)){ //removes player from array RiseMCTimers.removeWithdrawArray(p,
     * tier); } if (safePlayers.contains(p.getName())) {
     * safePlayers.remove(p.getName()); } }
     */

    // TODO add command
    public void disableAllPlayersFlight() {

    }

    public void addSafePlayer(Player p) {

        safePlayers.add(p.getName());

    }

    public void removeSafePlayer(String p) {

        safePlayers.remove(p);
    }

    public boolean getSafePlayer(String p) {

        if (safePlayers.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    // clears safe player hashmap
    public void clearSafePlayers(Player p) {

        safePlayers.clear();
    }

    public void enableWithdrawTimer() {

        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer1(0, (Integer.parseInt(Configuration.getWithdrawTimer(1)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer2(0, (Integer.parseInt(Configuration.getWithdrawTimer(2)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer3(0, (Integer.parseInt(Configuration.getWithdrawTimer(3)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer4(0, (Integer.parseInt(Configuration.getWithdrawTimer(4)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer5(0, (Integer.parseInt(Configuration.getWithdrawTimer(5)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer6(0, (Integer.parseInt(Configuration.getWithdrawTimer(6)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer7(0, (Integer.parseInt(Configuration.getWithdrawTimer(7)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer8(0, (Integer.parseInt(Configuration.getWithdrawTimer(8)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer9(0, (Integer.parseInt(Configuration.getWithdrawTimer(9)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer10(0, (Integer.parseInt(Configuration.getWithdrawTimer(10)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer11(0, (Integer.parseInt(Configuration.getWithdrawTimer(11)) * 20));
        // starts tier one timer, gets string setting from config, parses int,
        // then multiples by 20 for tick number
        RiseMCTimers.withdrawTimer12(0, (Integer.parseInt(Configuration.getWithdrawTimer(12)) * 20));

    }

    public void enableFlyCheckTimer() {
        // starts fly check timer
        RiseMCTimers.flyCheckTimer(0, (Configuration.getFlyCheckTimer() * 20));

    }

    public void enableFlyModeOneTimer() {
        RiseMCTimers.flyModeOneTimer(0, (Configuration.getFlyModeTimerAmount() * 20));
    }

    public void enableFreeFlyTimer() {
        //Free fly constructor
        FreeFly FreeFly = new FreeFly();
        //File configuration constructor
        //checks if file exists before enabling timer
        if (FreeFly.getFreeFlyConfig() != null) {
            if (FreeFly.isFreeFlyTimerEnabled()) {
                Main.getInstance().log.severe("Free Fly Time Engaged!");
                RiseMCTimers.freeFlyTimer(0, FreeFly.getFreeFlyTimeInterval() * 20);
            }
        }

    }


    public void disableWithdrawTimer() {

    }

    public void disableFlyCheckTimer() {

    }
}
