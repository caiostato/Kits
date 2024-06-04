package br.com.me.command;

import br.com.me.abillities.Blink.BlinkManager;
import br.com.me.abillities.Checkpoint.CheckpointManager;
import br.com.me.abillities.Kaangaroo.KaangarooManager;
import br.com.me.abillities.Ninja.NinjaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitSelectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {
            System.out.println(args[0]);

            Player player = (Player) commandSender;

            switch (args[0]) {
                case "checkpoint":
                    CheckpointManager.addPlayer(player);
                case "ninja":
                    NinjaManager.addPlayer(player);
                case "blink":
                    BlinkManager.addPlayer(player);
                case "kaangaroo":
                    KaangarooManager.addPlayer(player);
                    break;
                default:
                    break;
            }


        }

        return false;
    }
}
