package br.com.me.abillities.Checkpoint;

import br.com.me.util.ItemUtil;
import br.com.me.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckpointManager {

    public static void addPlayer(Player player){
        CheckpointListener.addKit(player);

        StringBuilder msg = new StringBuilder("§6Voce escolheu o kit ");
        String colorKit = StringUtil.colorString("CHECKPOINT");
        msg.append(colorKit);
        player.sendMessage(msg.toString());
        player.getInventory().clear();

        player.getInventory().addItem(ItemUtil.createCheckpointFence("Checkpoint Fence"));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
    }

}
