package steffanvanderwerf.deathswap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                    sender.sendMessage("Usage: /players get");
                    return true;
                }

                Player player1 = plugin.getSwappedPlayers().getPlayer1();
                Player player2 = plugin.getSwappedPlayers().getPlayer2();

                if (checkIfPlayersSet(player1, player2, sender)){
                    sender.sendMessage(ChatColor.GOLD + "Player 1: " + player1.getName());
                    sender.sendMessage(ChatColor.DARK_AQUA + "Player 2: " + player2.getName());

                    return true;
                }
                return false;
            }else if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED +"Usage: /players set <player1> <player2>");
                    return true;
                }

                Player player1 = Bukkit.getPlayer(args[1]);
                Player player2 = Bukkit.getPlayer(args[2]);

                if (checkIfPlayersSet(player1, player2, sender)){
                    if (player1 == player2) {
                        sender.sendMessage(ChatColor.RED +"Players must be different.");
                        return false;
                    }
                }

                plugin.getSwappedPlayers().setPlayers(player1, player2);
                plugin.setPlayersSet(true);

                sender.sendMessage(ChatColor.GOLD +"Players set.");
                return true;
            }
        }
        return false;
    }

    public boolean checkIfPlayersSet(Player player1, Player player2, CommandSender sender) {
        if (player1 == null || player2 == null) {
            sender.sendMessage("One or both players are not set.");
            return false;
        }

        return true;
    }
}