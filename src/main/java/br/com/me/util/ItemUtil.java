package br.com.me.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack createCheckpointFence(String name) {
        ItemStack item = new ItemStack(Material.DARK_OAK_FENCE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>();
        lore.add("Você pode se teletransportar para o inimigo");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createNinjaItem(String name) {
        ItemStack item = new ItemStack(Material.EMERALD);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>();
        lore.add("Você pode se teletransportar para o inimigo");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createBlinkItem(String name) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>();
        lore.add("Você pode se teletransportar 5 blocos a frente");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createKaangarooItem(String name) {
        ItemStack item = new ItemStack(Material.FIREWORK);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>();
        lore.add("Este item pode aumentar seu pulo!");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createMenuItem() {
        ItemStack item = new ItemStack(Material.CHEST);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Menu");



        item.setItemMeta(meta);

        return item;
    }
}
