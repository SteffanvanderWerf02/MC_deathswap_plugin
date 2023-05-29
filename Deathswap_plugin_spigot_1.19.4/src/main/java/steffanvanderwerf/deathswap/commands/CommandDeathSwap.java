package steffanvanderwerf.deathswap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import steffanvanderwerf.deathswap.Deathswap;
import steffanvanderwerf.deathswap.players.SwappedPlayers;
import steffanvanderwerf.deathswap.tasks.PlayerSwapTask;

public class CommandDeathSwap implements CommandExecutor {
    private final Deathswap plugin;
    private final Runnable swapPlayers;
    private BukkitTask swapTask;
    private int TswapPlayerId;
    private boolean playersSet;

    public CommandDeathSwap(Deathswap plugin, SwappedPlayers task, boolean playersSet) {
        this.plugin = plugin;
        this.swapPlayers = new PlayerSwapTask(plugin, task);
        this.TswapPlayerId = 0;
        this.playersSet = playersSet;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (!sender.hasPermission("deathswap.start") && !sender.hasPermission("deathswap.stop")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to start/stop the game!");
                return false;
            }

            if (!plugin.arePlayersSet()) {
                Bukkit.broadcastMessage(ChatColor.RED +"Players are not set! Use /players set <player1> <player2> first.");
                return false;
            }

            if (args[0].equals("start")) {

                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED +"Usage: /deathswap [start] [swapTime]");
                    return false;
                }

                return this.startGame(args);
            } else if (args[0].equals("stop")) {

                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED +"Usage: /deathswap [stop]");
                    return false;
                }

                return this.stopGame();
            }
            return false;
        } else {
            sender.sendMessage(ChatColor.RED +"You must be a player to use this command!");
            return false;
        }
    }

    private boolean startGame(String[] args) {
        if (Bukkit.getScheduler().isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage(ChatColor.BLUE +"Game is already running!");
            return true;
        }

        int secondsPeriod = 0;
        if (Integer.parseInt(args[1]) > 0) {
            try {
                secondsPeriod = Integer.parseInt(args[1]) * 60;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        this.swapTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this.swapPlayers, secondsPeriod * 20, secondsPeriod * 20);
        this.TswapPlayerId = this.swapTask.getTaskId();

        Bukkit.broadcastMessage(ChatColor.GREEN +"Game started!");
        return true;
    }

    private boolean stopGame() {

        if (!Bukkit.getScheduler().isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage(ChatColor.BLUE +"Game is not running!");
            return true;
        }

        this.swapTask.cancel();
        this.plugin.getSwappedPlayers().resetPlayers();
        Bukkit.broadcastMessage(ChatColor.BLUE +"Game stopped!");

        return true;
    }
}