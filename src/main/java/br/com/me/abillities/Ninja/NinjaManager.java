package br.com.me.abillities.Ninja;

import br.com.me.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NinjaManager {
    public static void addPlayer(Player player){
        NinjaListener.addKit(player);

        StringBuilder msg = new StringBuilder("§6Voce escolheu o kit ");
        String colorKit = StringUtil.colorString("NINJA");
        msg.append(colorKit);
        player.sendMessage(msg.toString());
        player.getInventory().clear();

        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
    }
}
