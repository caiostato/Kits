package br.com.me.abillities.Checkpoint;

import br.com.me.manager.CooldownManager;
import br.com.me.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CheckpointListener implements Listener {


    private static final Map<UUID, Boolean> players = new HashMap<>();
    private static final Map<UUID, Location> lastLocation = new HashMap<>();

    private static final CooldownManager cooldownManager = new CooldownManager();


    public static void addKit(Player player) {
        players.put(player.getUniqueId(), true);
    }

    public boolean hasKit(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {

        if (Objects.equals(event.getItemInHand().getItemMeta().getDisplayName(), "Checkpoint Fence") && event.getItemInHand().getType() == Material.DARK_OAK_FENCE) {

            if (lastLocation.get(event.getPlayer().getUniqueId()) != null) {
                event.getPlayer().getWorld().getBlockAt(lastLocation.get(event.getPlayer().getUniqueId())).setType(Material.AIR);
            }

            event.getPlayer().getInventory().setItem(0, ItemUtil.createCheckpointFence("Checkpoint Fence"));
            event.getPlayer().sendMessage("§6Checkpoint confirmado!");
            lastLocation.put(event.getPlayer().getUniqueId(), event.getBlockPlaced().getLocation());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {


        if (event.getAction().name().contains("RIGHT_CLICK") && Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Checkpoint Fence") && event.getItem().getType() == Material.DARK_OAK_FENCE) {

            if (!hasKit(event.getPlayer())) {
                return;
            }

            if (cooldownManager.isOnCooldown(event.getPlayer())) {
                event.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Skill em cooldown: " + cooldownManager.getRemainingTime(event.getPlayer()));
                return;
            }

            if (lastLocation.get(event.getPlayer().getUniqueId()) == null) {
                event.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Checkpoint nao confirmado");
                return;
            }

            event.getPlayer().getWorld().getBlockAt(lastLocation.get(event.getPlayer().getUniqueId())).setType(Material.AIR);
            event.getPlayer().teleport(lastLocation.get(event.getPlayer().getUniqueId()));
            lastLocation.put(event.getPlayer().getUniqueId(), null);
            cooldownManager.setCooldown(event.getPlayer(), 5);
        }

    }
}
