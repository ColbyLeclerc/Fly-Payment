package com.ahellhound.bukkit.flypayment;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerPayments {

    // Config constructor
    private Configuration config = new Configuration();
    private Messages Messages = new Messages();
    // flight constructor
    private Flight Flight = new Flight();
    private PlayerInfo PlayerInfo = new PlayerInfo();

    // withdraw exp method
    public void withdrawEXP(Player p) {
        // amount of exp to charge
        int tier = config.getTier(p);
        int chargeAmount = config.getExpChargeAmount(tier);
        // checks if player has enough exp
        if (chargeAmount == 0) {
            // doesn't do anything

        } else if (Utilities.getTotalExperience(p) < chargeAmount) {
            // disables players flight
            Flight.disableFlight(p, 4);
        } else {
            // removes exp
            int expSet = Utilities.getTotalExperience(p) - (chargeAmount);

            Utilities.setTotalExperience(p, expSet);

            // sends player charge message
            Messages.EXPRemovedMessage(p, chargeAmount);
        }

    }

    public boolean chargeResources(Player p) {

        ArrayList<String> entries = new ArrayList<String>(Arrays.asList(PlayerInfo.getChargeOrder(p).split(", ")));

        int tier = config.getTier(p);

        for (String i : entries) {

            if (i.contains("exp")) {

                if (expRequirementCheck(p) && config.getExpChargeAmount(tier) != 0) {
                    // withdraws exp
                    withdrawEXP(p);
                    return true;

                } else {

                    continue;

                }

            } else if (i.contains("item") || (i.contains("items"))) {
                if (itemRequirementCheck(p) && config.getItemChargeAmount(tier) != 0) {

                    withdrawItem(p);

                    return true;

                } else {

                    continue;

                }

            } else if (i.contains("money")) {

                if (moneyRequirementCheck(p) && config.getMoneyChargeAmount(tier) != 0) {

                    withdrawMoney(p);
                    return true;

                } else {

                    continue;

                }

            }

        }

        return false;

    }

    public boolean moneyRequirementCheck(Player p) {

        Main.getInstance();
        Economy econ = Main.econ;
        double moneyAmount = config.getMoneyChargeAmount(config.getTier(p));

        if (econ.getBalance(p.getName()) >= moneyAmount) {

            return true;

        } else {

            return false;

        }

    }

    public void withdrawMoney(Player p) {

        Main.getInstance();
        Economy econ = Main.econ;
        double moneyAmount = config.getMoneyChargeAmount(config.getTier(p));
        if (moneyAmount == 0) {

        } else if (econ.getBalance(p.getName()) >= moneyAmount) {

            econ.withdrawPlayer(p.getName(), moneyAmount);

            p.sendMessage(Messages.moneyRemovedMessage(p, config.getTier(p)));

        }

    }

    public int getPlayerItemAmount(Player p) {
        int tier = config.getTier(p);
        // Config variables
        int itemChargeAmount = config.getItemChargeAmount(tier);
        // gets item name enum
        String itemChargeEnum = config.getItemChargeEnum(tier);
        // gets inventory server
        PlayerInventory inventory = p.getInventory();
        // checks if item charge is enabled
        if (itemChargeAmount == 0) {
            // returns true if no items are needed to fly

        } else if (config.getItemMetaTag(tier).length() == 2) {
            // checks if player has item and amount
            for (ItemStack is : inventory) {

                if (is == null || is.equals(Material.AIR)) {
                    continue;
                }

                if (is.getItemMeta().getLore() != null) {

                    continue;
                }
                if (!is.getType().equals(Material.getMaterial(itemChargeEnum))) {

                    continue;
                }

                return is.getAmount();

            }

        } else {
            // iterates through player's inventory
            // int itemAmount = 0;
            for (ItemStack is : inventory) {

                if (is == null || is.equals(Material.AIR)) {
                    continue;
                }
                if (!is.hasItemMeta()) {
                    continue;
                }
                ItemMeta itemmeta = is.getItemMeta();
                List<String> lore = itemmeta.getLore();
                if (!is.getType().equals(Material.getMaterial(itemChargeEnum))) {

                    continue;
                }
                if (!lore.contains(config.getItemMetaTag(tier))) {

                    continue;
                }

                return is.getAmount();

            }
        }

        return 0;
    }

    public void withdrawItem(Player p) {

        int tier = config.getTier(p);
        // Config variables
        int itemChargeAmount = config.getItemChargeAmount(tier);
        // gets item name enum
        String itemChargeEnum = config.getItemChargeEnum(tier);
        // gets inventory server
        PlayerInventory inventory = p.getInventory();
        // checks if item charge is enabled
        if (itemChargeAmount == 0) {
            // returns true if no items are needed to fly

        } else if (config.getItemMetaTag(tier).length() == 2) {
            // checks if player has item and amount
            for (ItemStack is : inventory) {

                if (is == null || is.equals(Material.AIR)) {
                    continue;
                }

                if (is.getItemMeta().getLore() != null) {

                    continue;
                }
                if (!is.getType().equals(Material.getMaterial(itemChargeEnum))) {

                    continue;
                }

                if (itemChargeAmount == is.getAmount()) {

                    p.getInventory().removeItem(is);
                    p.getInventory().setContents(p.getInventory().getContents());
                    break;
                }

                /*
                 * if ((!config.getItemCustomName(tier).contains("-1")) &&
                 * is.getItemMeta().hasDisplayName() &&
                 * !is.getItemMeta().getDisplayName
                 * ().contains(config.getItemCustomName(tier))) {
                 * 
                 * continue;
                 * 
                 * }
                 */
                // remove's players item
                is.setAmount(is.getAmount() - itemChargeAmount);
                // updates player's inventory
                p.getInventory().setContents(p.getInventory().getContents());
                break;
            }

        } else {
            // iterates through player's inventory
            // int itemAmount = 0;
            for (ItemStack is : inventory) {

                if (is == null || is.equals(Material.AIR)) {
                    continue;
                }
                if (!is.hasItemMeta()) {
                    continue;
                }
                ItemMeta itemmeta = is.getItemMeta();
                List<String> lore = itemmeta.getLore();
                /*
                 * if
                 * (!is.getType().equals(Material.getMaterial(itemChargeEnum))
                 * || (!config.getItemCustomName(tier).contains("-1")) &&
                 * is.getItemMeta().hasDisplayName() &&
                 * !is.getItemMeta().getDisplayName
                 * ().contains(config.getItemCustomName(tier))) {
                 * 
                 * continue; }
                 */

                if (!lore.toString().contains(config.getItemMetaTag(tier).toString())) {

                    continue;
                }

                if (itemChargeAmount == is.getAmount()) {

                    p.getInventory().removeItem(is);
                    p.getInventory().setContents(p.getInventory().getContents());
                    break;
                }
                // remove's players item
                is.setAmount(is.getAmount() - itemChargeAmount);
                // updates inventory
                p.getInventory().setContents(p.getInventory().getContents());
                break;
            }
        }
    }

    public boolean expRequirementCheck(Player p) {
        int tier = config.getTier(p);
        // gets config exp amount
        int expChargeAmount = config.getExpChargeAmount(tier);

        if (expChargeAmount == 0) {
            // returns true if exp removal is disabled
            return false;
        } else if (expChargeAmount > Utilities.getTotalExperience(p)) {
            // return false if player doesn't have enough exp
            return false;
        } else {
            // returns true if player has enough exp
            return true;
        }
    }

    public boolean itemRequirementCheck(Player p) {
        int tier = config.getTier(p);
        // Config variables
        int itemChargeAmount = config.getItemChargeAmount(tier);
        // gets item name enum
        String itemChargeEnum = config.getItemChargeEnum(tier);
        // gets inventory server
        PlayerInventory inventory = p.getInventory();
        // checks if item charge is enabled
        if (itemChargeAmount == 0) {
            // returns true if no items are needed to fly
            return false;
        }
        // cehcks if item has enum
        if (config.getItemMetaTag(tier).length() == 2) {
            // checks if player has item and amount
            if (!inventory.contains(Material.valueOf(itemChargeEnum), itemChargeAmount)) {
                // returns false if player doesn't have items
                return false;
                // if player has item amount and type returns true
            } else {
                // if player has the requested inventory, returns true
                return true;
            }

        } else {
            // iterates through player's inventory
            int counter = 0;
            for (ItemStack is : inventory) {

                if (is == null || is.getItemMeta().getLore() == null) {

                    continue;
                }

                if (is.equals(Material.AIR)) {

                    continue;
                }

                if (is.hasItemMeta()) {
                    ItemMeta itemmeta = is.getItemMeta();
                    List<String> lore = itemmeta.getLore();

                    // checks if lore is the same as config
                    if (lore.toString().contains(config.getItemMetaTag(tier).toString())) {

                        counter = counter + is.getAmount();

                        // }
                    }

                }

            }
            if (counter >= itemChargeAmount) {
                // returns true if inventory has the required lore item amount
                return true;

            } else {
                // returns false if not
                return false;
            }
        }
    }

    public boolean itemHasMeta(Player p) {
        int tier = config.getTier(p);
        // checks if an item has a meta tag
        if (config.getItemMetaTag(tier).contains("-1")) {
            // ends method
            return false;
        }

        // ends method
        return true;
    }

}
