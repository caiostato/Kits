package br.com.me.abillities.Blink;

import br.com.me.manager.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BlinkListener implements Listener {

    private static final Map<UUID, Boolean> players = new HashMap<>();
    private static final Map<UUID, Integer> tpLeft = new HashMap<>();
    private static final CooldownManager skillCooldownManager = new CooldownManager();

    public static void addKit(Player player) {
        players.put(player.getUniqueId(), true);
    }

    public boolean hasKit(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static void setTpLeft(Player player) {
        tpLeft.put(player.getUniqueId(), 3);
    }

    public boolean isBlinkAction(PlayerInteractEvent event) {
        return event.getAction().name().contains("RIGHT_CLICK") && Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Blink") && event.getItem().getType() == Material.NETHER_STAR;

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (isBlinkAction(event)) {
            Player p = event.getPlayer();

            if (!hasKit(p)) {
                return;
            }

            if (skillCooldownManager.isOnCooldown(p)) {
                p.sendMessage(ChatColor.AQUA + "SKILL COOLDOWN: " + skillCooldownManager.getRemainingTime(p));
                return;
            }

            if (tpLeft.get(p.getUniqueId()) == 0) {
                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "NONE TP LEFT");
            }

            Location PlayerLoc = p.getLocation();

            PlayerLoc.add(PlayerLoc.getDirection().multiply(5));
            p.teleport(PlayerLoc);

            Location blockBelow = PlayerLoc.clone().subtract(0, 1, 0);
            blockBelow.getBlock().setType(Material.LEAVES);

            if (tpLeft.get(p.getUniqueId()) == 1) {
                skillCooldownManager.setCooldown(p, 3);
                tpLeft.put(p.getUniqueId(), 3);
            } else {
                tpLeft.put(p.getUniqueId(), tpLeft.get(p.getUniqueId()) - 1);
            }
        }
    }
}
