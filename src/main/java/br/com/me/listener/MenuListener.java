package br.com.me.listener;

import br.com.me.abillities.Blink.BlinkManager;
import br.com.me.abillities.Checkpoint.CheckpointManager;
import br.com.me.abillities.Kaangaroo.KaangarooManager;
import br.com.me.abillities.Ninja.NinjaManager;
import br.com.me.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.Objects;

public class MenuListener implements Listener {

    private void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, "Menu");

        menu.setItem(1, ItemUtil.createCheckpointFence("Kit Checkpoint"));
        menu.setItem(3, ItemUtil.createNinjaItem("Kit Ninja"));
        menu.setItem(5, ItemUtil.createBlinkItem("Kit Blink"));
        menu.setItem(7, ItemUtil.createKaangarooItem("Kit Kaangaroo"));

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (Objects.equals(event.getCurrentItem().getItemMeta().getDisplayName(), "Kit Checkpoint") && event.getCurrentItem().getType() == Material.DARK_OAK_FENCE) {
            CheckpointManager.addPlayer(player);
        } else if (Objects.equals(event.getCurrentItem().getItemMeta().getDisplayName(), "Kit Ninja") && event.getCurrentItem().getType() == Material.EMERALD) {
            NinjaManager.addPlayer(player);
        } else if (Objects.equals(event.getCurrentItem().getItemMeta().getDisplayName(), "Kit Blink") && event.getCurrentItem().getType() == Material.NETHER_STAR) {
            BlinkManager.addPlayer(player);
        } else if (Objects.equals(event.getCurrentItem().getItemMeta().getDisplayName(), "Kit Kaangaroo") && event.getCurrentItem().getType() == Material.FIREWORK) {
            KaangarooManager.addPlayer(player);
        }
        player.closeInventory();
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        p.getInventory().clear();
        p.getInventory().setItem(0, ItemUtil.createMenuItem());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction().name().contains("RIGHT_CLICK") && Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Menu") && event.getItem().getType() == Material.CHEST) {
            openMenu(event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (Objects.equals(event.getItemInHand().getItemMeta().getDisplayName(), "Menu") && event.getItemInHand().getType() == Material.CHEST) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        event.setCancelled(true);
        if (event.getEntity() instanceof Player) {
            Player p = event.getEntity();
            DamageCause cause = p.getLastDamageCause().getCause();

            if (cause == DamageCause.FALL) {
                p.setHealth(1.0);
            }
        }
    }

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == DamageCause.FALL) {
                if (event.getDamage() > 2) {
                    event.setDamage(event.getDamage());
                } else {
                    event.setDamage(2);
                }
            }
        }
    }

}
