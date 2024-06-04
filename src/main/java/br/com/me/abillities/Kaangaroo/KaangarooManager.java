package br.com.me.abillities.Kaangaroo;

import org.bukkit.entity.Player;
import br.com.me.util.ItemUtil;
import br.com.me.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KaangarooManager {

    public static void addPlayer(Player player) {
        KaangarooListener.addKit(player);
        KaangarooListener.setJumpsLeft(player);

        StringBuilder msg = new StringBuilder("§6Voce escolheu o kit ");
        String colorKit = StringUtil.colorString("KAANGAROO");
        msg.append(colorKit);
        player.sendMessage(msg.toString());
        player.getInventory().clear();

        player.getInventory().addItem(ItemUtil.createKaangarooItem("Kaangaroo"));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
    }
}
