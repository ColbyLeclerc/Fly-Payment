package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Timers {

    // Arrays
    private static ArrayList<Player> withdrawPlayers1 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers2 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers3 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers4 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers5 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers6 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers7 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers8 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers9 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers10 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers11 = new ArrayList<Player>();
    private static ArrayList<Player> withdrawPlayers12 = new ArrayList<Player>();
    private static ArrayList<Player> flyCheckArray = new ArrayList<Player>();
    private static ArrayList<Player> freeFlyPlayers = new ArrayList<Player>();
    // private static ArrayList<Player> flyModeOnePlayers = new
    // ArrayList<Player>();
    // private static ArrayList<Player> flyModeOneRemovePlayers = new
    // ArrayList<Player>();
    private static HashMap<Player, Long> flyModeOnePlayers = new HashMap<Player, Long>();

    public void flyModeOneTimer(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                //PlayerInfo PlayerInfo = new PlayerInfo();
                Flight Flight = new Flight();

                for (@SuppressWarnings("rawtypes") Map.Entry entry : flyModeOnePlayers.entrySet()) {

                    long playerTime = Long.getLong(entry.getValue().toString());


                    if (playerTime < System.currentTimeMillis()) {

                        Flight.disableFlight((Player) entry.getKey(), 0);

                    }

                    // System.out.println(pairs.getKey() + " = " +
                    // pairs.getValue());
                    // it.remove(); // avoids a ConcurrentModificationException
                }

                /*
                 * for (Player i : flyModeOnePlayers){
                 * 
                 * if (PlayerInfo.getFlyModeOneTime(i) <
                 * System.currentTimeMillis()){ //disables player's flight
                 * Flight.disableFlight(i, 0);
                 * 
                 * flyModeOneRemovePlayers.add(i);
                 * 
                 * }
                 * 
                 * }
                 * 
                 * for (Player i: flyModeOneRemovePlayers){
                 * 
                 * removeFlyModeOneArray(i);
                 * 
                 * }
                 */

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer1(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();
                Flight Flight = new Flight();

                if (withdrawPlayers1.size() > -1) {

                    for (int i = 0; i < withdrawPlayers1.size(); i++) {
                        Player p = withdrawPlayers1.get(i);
                        // Charges resources

                        if (!PlayerPayments.chargeResources(p)) {

                            Flight.disableFlight(p, 4);

                        }
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer2(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers2.size() > -1) {

                    for (int i = 0; i < withdrawPlayers2.size(); i++) {
                        Player p = withdrawPlayers2.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer3(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers3.size() > -1) {

                    for (int i = 0; i < withdrawPlayers3.size(); i++) {
                        Player p = withdrawPlayers3.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer4(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers4.size() > -1) {

                    for (int i = 0; i < withdrawPlayers4.size(); i++) {
                        Player p = withdrawPlayers4.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer5(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers5.size() > -1) {

                    for (int i = 0; i < withdrawPlayers5.size(); i++) {
                        Player p = withdrawPlayers5.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer6(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers6.size() > -1) {

                    for (int i = 0; i < withdrawPlayers6.size(); i++) {
                        Player p = withdrawPlayers6.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer7(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers7.size() > -1) {

                    for (int i = 0; i < withdrawPlayers7.size(); i++) {
                        Player p = withdrawPlayers7.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer8(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers8.size() > -1) {

                    for (int i = 0; i < withdrawPlayers8.size(); i++) {
                        Player p = withdrawPlayers8.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer9(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers9.size() > -1) {

                    for (int i = 0; i < withdrawPlayers9.size(); i++) {
                        Player p = withdrawPlayers9.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer10(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers10.size() > -1) {

                    for (int i = 0; i < withdrawPlayers10.size(); i++) {
                        Player p = withdrawPlayers10.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer11(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers11.size() > -1) {

                    for (int i = 0; i < withdrawPlayers11.size(); i++) {
                        Player p = withdrawPlayers11.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void withdrawTimer12(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // flight constructor

                // player payments constructor
                PlayerPayments PlayerPayments = new PlayerPayments();

                if (withdrawPlayers12.size() > -1) {

                    for (int i = 0; i < withdrawPlayers12.size(); i++) {
                        Player p = withdrawPlayers12.get(i);
                        // Charges resources
                        PlayerPayments.chargeResources(p);
                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void freeFlyTimer(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // towny constructor
                Flight Flight = new Flight();
                FreeFly FreeFly = new FreeFly();
                if (freeFlyPlayers.size() > 0) {
                    for (int i = 0; i < freeFlyPlayers.size(); i++) {
                        Player p = freeFlyPlayers.get(i);
                        // checks if can build
                        if (!FreeFly.isFreeFlyTimerEnabled() && freeFlyPlayers.contains(p)) {
                            // flight constructor
                            Flight.disableFreeFlight(p);
                        }
                    }
                }

                if (FreeFly.getFreeFlyTime() != -1) {
                    if (FreeFly.getFreeFlyTime() <= System.currentTimeMillis()) {
                        // sets free flight in config off
                        FreeFly.setFreeFly(false);

                    }
                }

            }
        }, startTime, runEveryXTime);
    }

    public void flyCheckTimer(int startTime, int runEveryXTime) {

        // creates timer with specified time
        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                // towny constructor
                BanList banL = new BanList();
                for (int i = 0; i < flyCheckArray.size(); i++) {
                    Player p = flyCheckArray.get(i);
                    // checks if can build
                    // if (!towny.canBuildTowny(p) || towny.canPvPTowny(p) ||
                    // towny.inPvPAreaTowny(p) || banL.isInBannedWorld(p)) {
                    // flight constructor
                    Flight Flight = new Flight();

                    if (banL.isInBannedWorld(p)) {

                        Flight.disableFlight(p, 6);
                        continue;
                    }

                    // TEST

                }

            }
        }, startTime, runEveryXTime);
    }

    public void removeFromArrays(Player p) {
        // gets array player's in withdraw array
        removeFreeFlyArray(p);
        // removes from flycheck array
        removeFlyCheckArray(p);
        // removes from fly array
        removeFlyModeOneArray(p);

    }

    public void removeFlyModeOneArray(Player p) {

        if (flyModeOnePlayers.containsKey(p)) {

            flyModeOnePlayers.remove(p);

        }

    }

    public void addFlyModeOneArray(Player p) {

        Configuration config = new Configuration();

        if (!flyModeOnePlayers.containsKey(p)) {

            flyModeOnePlayers.put(p, System.currentTimeMillis() + (config.getFlyModeAmount(config.getTier(p)) * 1000));

        }

        /*
         * if (!flyModeOnePlayers.contains(p)){
         * 
         * flyModeOnePlayers.add(p); }
         */

    }

    public void removeFreeFlyArray(Player p) {
        // checks if player is in array
        if (freeFlyPlayers.contains(p)) {
            // removes player from array
            freeFlyPlayers.remove(p);

        }

    }

    public void addFreeFlyArray(Player p) {

        // adds player from array
        freeFlyPlayers.add(p);

    }

    public void removeFlyCheckArray(Player p) {
        // chceks if player is on array
        if (flyCheckArray.contains(p)) {
            // removes player from array
            flyCheckArray.remove(p);
        }

    }

    public void addFlyCheckArray(Player p) {
        // adds player to flycheck array
        flyCheckArray.add(p);

    }

    public boolean isInFlyCheckArray(Player p) {

        if (flyCheckArray.contains(p)) {

            return true;

        } else {

            return false;
        }

    }

    // checks withdraw array
    /*
     * public boolean isInWithdrawArray(Player p, int tier) { switch (tier) {
     * 
     * case 1: if (withdrawPlayers1.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 2: if (withdrawPlayers2.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 3: if (withdrawPlayers3.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 4: if (withdrawPlayers4.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 5: if (withdrawPlayers5.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 6: if (withdrawPlayers6.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 7: if (withdrawPlayers7.contains(p)) { return true; } else { return
     * false; } case 8: if (withdrawPlayers8.contains(p)) { return true; } else
     * { return false; }
     * 
     * case 9: if (withdrawPlayers9.contains(p)) { return true; } else { return
     * false; }
     * 
     * case 10: if (withdrawPlayers10.contains(p)) { return true; } else {
     * return false; }
     * 
     * case 11: if (withdrawPlayers11.contains(p)) { return true; } else {
     * return false; }
     * 
     * case 12: if (withdrawPlayers12.contains(p)) { return true; } else {
     * return false; } default: if (withdrawPlayers1.contains(p)) { return true;
     * } else { return false; } } }
     */

    public boolean isInFreeFlyArray(Player p) {
        if (freeFlyPlayers.contains(p)) {

            return true;

        }

        return false;
    }

    public void removeWithdrawArray(Player p, int tier) {
        switch (tier) {

            case 1:
                withdrawPlayers1.remove(p);
                break;

            case 2:
                withdrawPlayers2.remove(p);
                break;

            case 3:
                withdrawPlayers3.remove(p);
                break;

            case 4:
                withdrawPlayers4.remove(p);
                break;

            case 5:
                withdrawPlayers5.remove(p);
                break;

            case 6:
                withdrawPlayers6.remove(p);
                break;

            case 7:
                withdrawPlayers7.remove(p);
                break;

            case 8:
                withdrawPlayers8.remove(p);
                break;

            case 9:
                withdrawPlayers9.remove(p);
                break;

            case 10:
                withdrawPlayers10.remove(p);
                break;

            case 11:
                withdrawPlayers11.remove(p);
                break;

            case 12:
                withdrawPlayers12.remove(p);
                break;
            default:
                withdrawPlayers12.remove(p);
                break;
        }

    }

    public void addWithdrawArray(Player p, int tier) {

        switch (tier) {

            case 1:
                if (!withdrawPlayers1.contains(p)) {
                    withdrawPlayers1.add(p);
                }
                break;

            case 2:
                if (!withdrawPlayers2.contains(p)) {
                    withdrawPlayers2.add(p);
                }
                break;

            case 3:
                if (!withdrawPlayers3.contains(p)) {
                    withdrawPlayers3.add(p);
                }
                break;

            case 4:
                if (!withdrawPlayers4.contains(p)) {
                    withdrawPlayers4.add(p);
                }
                break;

            case 5:
                if (!withdrawPlayers5.contains(p)) {
                    withdrawPlayers5.add(p);
                }
                break;

            case 6:
                if (!withdrawPlayers6.contains(p)) {
                    withdrawPlayers6.add(p);
                }
                break;

            case 7:
                if (!withdrawPlayers7.contains(p)) {
                    withdrawPlayers7.add(p);
                }
                break;

            case 8:
                if (!withdrawPlayers8.contains(p)) {
                    withdrawPlayers8.add(p);
                }
                break;

            case 9:
                if (!withdrawPlayers9.contains(p)) {
                    withdrawPlayers9.add(p);
                }
                break;

            case 10:
                if (!withdrawPlayers10.contains(p)) {
                    withdrawPlayers10.add(p);
                }
                break;

            case 11:
                if (!withdrawPlayers11.contains(p)) {
                    withdrawPlayers11.add(p);
                }
                break;

            case 12:
                if (!withdrawPlayers12.contains(p)) {
                    withdrawPlayers12.add(p);
                }
                break;
            default:
                if (!withdrawPlayers12.contains(p)) {
                    withdrawPlayers12.add(p);
                }
                break;
        }

    }

}
