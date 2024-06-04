package br.com.me.abillities.Ninja;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

import br.com.me.manager.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class NinjaListener implements Listener {

    private static Map<Entity, Entity> targets = new HashMap<>();

    private static final CooldownManager cooldownSkillManager = new CooldownManager();
    private static final CooldownManager cooldownTargetManager = new CooldownManager();
    private static final CooldownManager cooldownCombatManager = new CooldownManager();

    private static final Map<UUID, Boolean> players = new HashMap<>();

    public boolean hasKit(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static void addKit(Player player) {
        players.put(player.getUniqueId(), true);
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            if (!hasKit((Player) event.getDamager())) {
                return;
            }

        } else {
            return;
        }

        Entity damagedEntity = event.getEntity();
        Entity damagerEntity = event.getDamager();

        if (damagedEntity instanceof Player) {
            damagerEntity.sendMessage("New target: " + damagedEntity.getName());
            setTarget(damagerEntity, damagedEntity);
            cooldownTargetManager.setCooldown((Player) damagerEntity, 25);
        }

        damagerEntity.sendMessage("" + ChatColor.BOLD + "New target: " + damagedEntity.getName());
        setTarget(damagerEntity, damagedEntity);
        cooldownTargetManager.setCooldown((Player) damagerEntity, 25);


        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getDamager() instanceof Player) {
                cooldownCombatManager.setCooldown((Player) damagerEntity, 2);
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        if (!hasKit(event.getPlayer())) {
            return;
        }

        Player currentPlayer = event.getPlayer();
        Entity target = getTarget(currentPlayer);
        boolean combatCooldown = cooldownCombatManager.isOnCooldown(event.getPlayer());
        boolean skillCooldownBool = cooldownSkillManager.isOnCooldown(event.getPlayer());

        if (target != null) {
            if (!skillCooldownBool) {
                if (!combatCooldown) {
                    double distance = getDistance(currentPlayer, target);
                    if (distance < 60) {
                        if (event.isSneaking()) {
                            Location targetLocation = target.getLocation();
                            Location newLocation = targetLocation.clone().add(targetLocation.getDirection());
                            currentPlayer.teleport(newLocation);
                            clearTarget(currentPlayer);
                            cooldownSkillManager.setCooldown(currentPlayer, 6);
                        }
                    } else {

                        currentPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Current distance: " + (int) distance);
                    }
                } else {
                    currentPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "In combat");
                }
            } else {
                currentPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Skill in cooldown:  " + cooldownSkillManager.getRemainingTime(currentPlayer));
            }
        } else {
            currentPlayer.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "None target");
        }
    }

    public static double getDistance(Entity damager, Entity damaged) {
        return damager.getLocation().distance(damaged.getLocation());
    }

    Runnable clearTarget(Entity player) {
        NinjaListener.targets.remove(player);
        player.sendMessage("Target cleared");
        return null;
    }

    private Entity getTarget(Player player) {


        float cooldown = cooldownTargetManager.getRemainingTime(player);
        if (cooldown > 0) {
            return targets.get(player);
        } else {
            targets.remove(player);
            return null;
        }
    }

    private void setTarget(Entity player, Entity target) {
        targets.put(player, target);
    }
}
