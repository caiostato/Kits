package br.com.me.abillities.Kaangaroo;

import br.com.me.manager.CooldownManager;
import br.com.me.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class KaangarooListener implements Listener {

    private static final Map<UUID, Boolean> players = new HashMap<>();
    private static final CooldownManager skillCooldownManager = new CooldownManager();
    private static final Map<UUID, Integer> jumpsLeft = new HashMap<>();

    public static void addKit(Player player) {
        players.put(player.getUniqueId(), true);
    }

    public boolean hasKit(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static void setJumpsLeft(Player player) {
        jumpsLeft.put(player.getUniqueId(), 2);
    }


    public boolean isKaangarooAction(PlayerInteractEvent event) {
        return event.getAction().name().contains("RIGHT_CLICK") && Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Kaangaroo") && event.getItem().getType() == Material.FIREWORK;

    }

    private boolean isBlockBelowPlayer(Player player) {
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        return blockBelow.getType() == Material.AIR;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getItemInHand().getType() == Material.FIREWORK && Objects.equals(event.getItemInHand().getItemMeta().getDisplayName(), "Kaangaroo")){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (isKaangarooAction(event)) {
            Player p = event.getPlayer();

            if (!hasKit(p)) {
                return;
            }

            if (skillCooldownManager.isOnCooldown(p)) {
                p.sendMessage("SKILL COOLDOWN: "+skillCooldownManager.getRemainingTime(p));
                return;
            }
            p.sendMessage("JUMPS LEFT: "+jumpsLeft.get(p.getUniqueId()));

            event.setCancelled(true);

            if(p.isSneaking()) {
                Vector jump = p.getLocation().getDirection().multiply(2.5).setY(0.5); // COM SHIFT
                p.setVelocity(jump);
            } else {
                Vector jump = p.getLocation().getDirection().multiply(1.3).setY(1.5); // SEM SHIFT
                p.setVelocity(jump);
            }

            if(isBlockBelowPlayer(p)) {
                if (jumpsLeft.get(p.getUniqueId()) == 1) {
                    skillCooldownManager.setCooldown(p, 3);
                    jumpsLeft.put(p.getUniqueId(), 2);
                } else {
                    jumpsLeft.put(p.getUniqueId(), jumpsLeft.get(p.getUniqueId()) - 1);
                }
            }



        }
    }
}
