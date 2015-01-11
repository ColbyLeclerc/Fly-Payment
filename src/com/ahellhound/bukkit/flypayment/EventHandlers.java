package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventHandlers {
    // Scheduler constructor
    private Scheduler Scheduler = new Scheduler();
    // Configuration constructor
    private Configuration Configuration = new Configuration();
    // RiseMC Timers
    private Timers Timers = new Timers();
    // Flight constructor
    private Flight Flight = new Flight();
    // Configuration constructor
    private Messages Messages = new Messages();
    private PlayerInfo PlayerInfo = new PlayerInfo();
    private BanList BanList = new BanList();
    private int tempBanAmount;

    // Quit event
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player p = event.getPlayer();
        int tier = Configuration.getTier(p);
        Timers.removeFromArrays(p);
        Timers.removeWithdrawArray(p, tier);

    }

    public void onPlayerDamageGivenEvent(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {

            Player p = ((Player) event.getEntity());

            if (Scheduler.getSafePlayer((((Player) event.getEntity()).getName())) && Timers.isInFlyCheckArray(p)) {

                if (tempBanAmount != 0) {

                    PlayerInfo.setFlyTempBan(p);

                    Flight.disableFlight(p, 8);

                }

            }

        }

    }

    public void onPlayerDamageReceiveEvent(EntityDamageByEntityEvent event) {

        tempBanAmount = Configuration.getFlyTempBanAmount();

        if (event.getDamager() instanceof Player) {

            Player p = ((Player) event.getDamager());

            if (Scheduler.getSafePlayer((((Player) event.getDamager()).getName())) && Timers.isInFlyCheckArray(p)) {
                p.sendMessage(Messages.flyTempBan(p));

                if (!BanList.isInBannedWorld(p)) {
                    // if (towny.inPvPAreaTowny(p)) {
                    if (tempBanAmount != 0) {

                        PlayerInfo.setFlyTempBan(p);

                        Flight.disableFlight(p, 8);

                    }

                    // }
                }
            }

        }

    }

    public void onProjectileHitEvent(ProjectileHitEvent event) {

        if (event.getEntity().getShooter() instanceof Player) {

            Player p = ((Player) event.getEntity().getShooter());

            if (Scheduler.getSafePlayer(p.getName()) && Timers.isInFlyCheckArray(p)) {
                if (!BanList.isInBannedWorld(p)) {
                    // if (towny.inPvPAreaTowny(p)) {
                    p.sendMessage(Messages.flyTempBan(p));

                    PlayerInfo.setFlyTempBan(p);

                    Flight.disableFlight(p, 8);
                    // }
                }
            }

        }

    }

    // Kick event
    public void onPlayerKick(PlayerKickEvent event) {
        Player p = event.getPlayer();
        int tier = Configuration.getTier(p);
        Timers.removeFromArrays(p);
        Timers.removeWithdrawArray(p, tier);
    }

    // Player fall damage event
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            Player p = ((Player) event.getEntity());
            if (event.getCause() == DamageCause.FALL && Scheduler.getSafePlayer((((Player) event.getEntity()).getName()))
                    && (!p.hasPermission("flyp.takedamage"))) {

                event.setCancelled(true);

                Scheduler.removeSafePlayer(p.getName());

            }
        }

    }

    public void onPlayerPotionSplashEvent(PotionSplashEvent event) {
        // if player uses potion, cancles poton event and disables flight
        if (event.getEntity() instanceof Player) {

            Player p = ((Player) event.getEntity());

            if (Scheduler.getSafePlayer((((Player) event.getEntity()).getName())) && Timers.isInFlyCheckArray(p)) {
                if (!BanList.isInBannedWorld(p)) {
                    // if (towny.inPvPAreaTowny(p)) {
                    event.setCancelled(true);
                    p.sendMessage(Messages.flyTempBan(p));
                    PlayerInfo.setFlyTempBan(p);

                    Flight.disableFlight(p, 8);
                    // }
                }
            }
            Player pl = ((Player) event.getEntity().getShooter());
            if (Scheduler.getSafePlayer((((Player) event.getEntity().getShooter()).getName())) && Timers.isInFlyCheckArray(pl)) {
                if (!BanList.isInBannedWorld(p)) {
                    // if (towny.inPvPAreaTowny(p)) {
                    event.setCancelled(true);
                    p.sendMessage(Messages.flyTempBan(pl));
                    PlayerInfo.setFlyTempBan(pl);

                    Flight.disableFlight(pl, 8);
                    // }
                }
            }

        }

    }

    public void onPlayerWorldChangeEvent(PlayerChangedWorldEvent event) {

        Player p = event.getPlayer();
        // checks if player is in fly check array
        if (Timers.isInFlyCheckArray(p)) {
            // disables flight if they are, to prevent bug
            Flight.disableFlight(p, 6);

        }

    }
}
