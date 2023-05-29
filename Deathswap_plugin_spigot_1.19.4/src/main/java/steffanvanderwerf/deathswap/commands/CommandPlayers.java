package steffanvanderwerf.deathswap.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import steffanvanderwerf.deathswap.Deathswap;

public class CommandPlayers implements CommandExecutor {
    private final Deathswap plugin;

    public CommandPlayers(Deathswap plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("players")) {
            if(args[0].equalsIgnoreCase("get")) {
                if (args.length < 1) {
                    sender.sendMessage("Usage: /setplayers <get>");
                    return true;
                }
                Player player1 = plugin.getSwappedPlayers().getPlayer1();
                Player player2 = plugin.getSwappedPlayers().getPlayer2();
                if (player1 == null || player2 == null) {
                    sender.sendMessage("One or both players are not set.");
                    return true;
                }
                sender.sendMessage("Player 1: " + player1.getName());
                sender.sendMessage("Player 2: " + player2.getName());
                return true;
            }else if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage("Usage: /setplayers <set> <player1> <player2>");
                    return true;
                }

                Player player1 = Bukkit.getPlayer(args[1]);
                Player player2 = Bukkit.getPlayer(args[2]);

                if (player1 == null || player2 == null) {
                    sender.sendMessage("One or both players are not online.");
                    return true;
                }

                if (player1 == player2) {
                    sender.sendMessage("Players must be different.");
                    return true;
                }

                plugin.getSwappedPlayers().setPlayers(player1, player2);
                plugin.setPlayersSet(true);
                sender.sendMessage("Players set.");
                return true;
            }
        }
        return false;
    }
}