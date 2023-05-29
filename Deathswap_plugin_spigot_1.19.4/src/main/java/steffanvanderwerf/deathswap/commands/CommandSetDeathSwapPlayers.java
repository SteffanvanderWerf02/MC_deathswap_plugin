package steffanvanderwerf.deathswap.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import steffanvanderwerf.deathswap.DeathSwap;

public class CommandSetDeathSwapPlayers implements CommandExecutor {
    private final DeathSwap plugin;

    public CommandSetDeathSwapPlayers(DeathSwap plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setplayers")) {
            if (args.length < 2) {
                sender.sendMessage("Usage: /setplayers <player1> <player2>");
                return true;
            }

            Player player1 = Bukkit.getPlayer(args[0]);
            Player player2 = Bukkit.getPlayer(args[1]);

            if (player1 == null || player2 == null) {
                sender.sendMessage("One or both players are not online.");
                return true;
            }

            plugin.getPlayerTask().setPlayers(player1, player2);
            sender.sendMessage("Players set: " + player1.getName() + " and " + player2.getName());
            return true;
        }

        return false;
    }
}