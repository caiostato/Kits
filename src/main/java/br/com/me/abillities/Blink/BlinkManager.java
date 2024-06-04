package br.com.me.abillities.Blink;

import br.com.me.util.ItemUtil;
import br.com.me.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlinkManager {

    public static void addPlayer(Player player){
        BlinkListener.addKit(player);
        BlinkListener.setTpLeft(player);

        StringBuilder msg = new StringBuilder("§6Voce escolheu o kit ");
        String colorKit = StringUtil.colorString("BLINK");
        msg.append(colorKit);
        player.sendMessage(msg.toString());
        player.getInventory().clear();

        player.getInventory().addItem(ItemUtil.createBlinkItem("Blink"));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
    }
}
