package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    /**
     * From ESSENTIALS
     */
    //This method is used to update both the recorded total experience and displayed total experience.
    //We reset both types to prevent issues.
    public static void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        //This following code is technically redundant now, as bukkit now calulcates levels more or less correctly
        //At larger numbers however... player.getExp(3000), only seems to give 2999, putting the below calculations off.
        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player);
            amount -= expToLevel;
            if (amount >= 0) {
                // give until next level
                player.giveExp(expToLevel);
            } else {
                // give the rest
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    private static int getExpAtLevel(final Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(final int level) {
        if (level > 29) {
            return 62 + (level - 30) * 7;
        }
        if (level > 15) {
            return 17 + (level - 15) * 3;
        }
        return 17;
    }

    public static int getExpToLevel(final int level) {
        int currentLevel = 0;
        int exp = 0;

        while (currentLevel < level) {
            exp += getExpAtLevel(currentLevel);
            currentLevel++;
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    //This method is required because the bukkit player.getTotalExperience() method, shows exp that has been 'spent'.
    //Without this people would be able to use exp and then still sell it.
    public static int getTotalExperience(final Player player) {
        int exp = (int) Math.round(getExpAtLevel(player) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0) {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int getExpUntilNextLevel(final Player player) {
        int exp = (int) Math.round(getExpAtLevel(player) * player.getExp());
        int nextLevel = player.getLevel();
        return getExpAtLevel(nextLevel) - exp;
    }

    public String convertIntToString(int intToConvert) {

        // Converts primitive int to Integer
        Integer convertedInteger = new Integer(intToConvert);
        // Converts Integer to String
        String convertedIntegerString = convertedInteger.toString();

        return convertedIntegerString;
    }

    public String convertDoubleToString(double doubleToConvert) {

        // Converts primitive double to Double
        Double convertedDouble = new Double(doubleToConvert);
        // Convert Double to String
        String convertedDoubleString = convertedDouble.toString();

        return convertedDoubleString;
    }

    public String convertTicksToMinutes(double ticksTimer) {
        // Converts ticks to seconds
        double convertedSeconds = (ticksTimer / 20);
        // Decimal is placed to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.00");
        // Sees if converted ticks is greater or equal to 60
        if (convertedSeconds >= 60) {
            // Converts ticks to minutes
            double convertedMinutes = (convertedSeconds / 60);
            // Converts double to string
            String convertedMinutesString = df.format(convertedMinutes);
            // returns string in minutes
            return convertedMinutesString;
        }

        // Applies decimal format to convertedSecondsDouble
        String convertedTicksString = df.format(convertedSeconds);
        // returns string thats less than 60
        return convertedTicksString;
    }

    public String convertMinutesOrSeconds(double timeTicks) {
        // Messages constructor
        Messages Messages = new Messages();
        // Determines if time is greater than or less than a minute
        if (timeTicks < 1200) {

            // Gets seconds message
            String secondsConverted = Messages.secondsMessage();

            return secondsConverted;
        }

        // Gets minutes message
        String minutesConverted = Messages.minutesMessage();
        // Returns minutes message
        return minutesConverted;
    }

    public boolean getTimerIsZero(Player p) {
        // configuration constructor
        Configuration Configuration = new Configuration();
        // gets player's tier
        int tier = Configuration.getTier(p);
        // Checks if configuration has 0 as timer
        if (Configuration.getTimerAmount(tier) == 0) {

            return true;
        }

        return false;
    }

    public long parseStringToMilliseconds(String string) {
        List<String> list = new ArrayList<String>();

        String c;
        int goBack = 0;
        for (int i = 0; i < string.length(); i++) {
            c = String.valueOf(string.charAt(i));
            if (c.matches("[a-zA-Z]")) {
                list.add(string.substring(goBack, i + 1));
                goBack = i + 1;

            }
        }
        // Cleanse
        long amount;
        long total = 0;
        char ch;
        for (String st : list) {
            ch = st.charAt(st.length() - 1);
            if (st.length() != 1 && String.valueOf(ch).matches("[M,w,d,h,m,s]")) {
                // Total milliseconds
                amount = Math.abs(Integer.parseInt(st.substring(0, st.length() - 1)));
                switch (ch) {
                    case 's':
                        total += (amount * 1000);
                        break;
                    case 'm':
                        total += (amount * 1000 * 60);
                        break;
                    case 'h':
                        total += (amount * 1000 * 3600);
                        break;
                    case 'd':
                        total += (amount * 1000 * 3600 * 24);
                        break;
                    case 'w':
                        total += (amount * 1000 * 3600 * 24 * 7);
                        break;
                }

            }
        }

        if (total == 0)
            return 0;

        return total;
    }
    /**From ESSENTIALS
     *
     */

    /**
     * @param milliseconds Milliseconds to convert to words
     * @param abbreviate   For example, if true, 293000 -> "10m-53s", otherwise
     *                     "10 minutes and 53 seconds"
     * @return Time in words
     */
    public String parseLong(long milliseconds, boolean abbreviate) {
        // String[] units = new String[5];
        List<String> units = new ArrayList<String>();
        long amount;

        amount = milliseconds / (7 * 24 * 60 * 60 * 1000);
        units.add(amount + "w");

        amount = milliseconds / (24 * 60 * 60 * 1000) % 7;
        units.add(amount + "d");

        amount = milliseconds / (60 * 60 * 1000) % 24;
        units.add(amount + "h");

        amount = milliseconds / (60 * 1000) % 60;
        units.add(amount + "m");

        amount = milliseconds / 1000 % 60;
        units.add(amount + "s");

        // Sort into order
        String[] array = new String[5];
        char end;
        for (String s : units) {
            end = s.charAt(s.length() - 1);
            switch (end) {
                case 'w':
                    array[0] = s;
                case 'd':
                    array[1] = s;
                case 'h':
                    array[2] = s;
                case 'm':
                    array[3] = s;
                case 's':
                    array[4] = s;
            }
        }

        units.clear();
        for (String s : array)
            if (!s.startsWith("0"))
                units.add(s);

        // Append
        StringBuilder sb = new StringBuilder();
        String word, count, and;
        char c;
        for (String s : units) {
            if (!abbreviate) {
                c = s.charAt(s.length() - 1);
                count = s.substring(0, s.length() - 1);
                switch (c) {
                    case 'w':
                        word = "week" + (count.equals("1") ? "" : "s");
                        break;
                    case 'd':
                        word = "day" + (count.equals("1") ? "" : "s");
                        break;
                    case 'h':
                        word = "hour" + (count.equals("1") ? "" : "s");
                        break;
                    case 'm':
                        word = "minute" + (count.equals("1") ? "" : "s");
                        break;
                    default:
                        word = "second" + (count.equals("1") ? "" : "s");
                        break;
                }

                and = s.equals(units.get(units.size() - 1)) ? "" : s.equals(units.get(units.size() - 2)) ? " and " : ", ";
                sb.append(count + " " + word + and);
            } else {
                sb.append(s);
                if (!s.equals(units.get(units.size() - 1)))
                    sb.append("-");
            }

        }
        return /*sb.toString().trim().length() == 0 ? null : */sb.toString().trim();

    }

    // End Test

    public String convertLongToString(long longNumber) {
        // converts primitive type to object
        Long longObject = longNumber;
        // converts object to string
        String convertedLong = longObject.toString();

        return convertedLong;
    }

}
