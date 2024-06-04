package br.com.me.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class CooldownManager {

    private final Map<UUID, Double> cooldowns = new HashMap<>();


    public void setCooldown(Player player, double seconds) {
        double cooldownEnd = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(player.getUniqueId(), cooldownEnd);
    }

    public boolean isOnCooldown(Player player) {
        return cooldowns.containsKey(player.getUniqueId()) && cooldowns.get(player.getUniqueId()) > System.currentTimeMillis();
    }

    public float getRemainingTime(Player player) {
        return (float) ((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000);
    }

    public void removeCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
    }


}
