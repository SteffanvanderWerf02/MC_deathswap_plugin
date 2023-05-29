package steffanvanderwerf.deathswap.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import steffanvanderwerf.deathswap.DeathSwap;
import steffanvanderwerf.deathswap.tasks.PlayerSwapTask;
import steffanvanderwerf.deathswap.tasks.PlayersTask;

public class CommandDeathSwap implements CommandExecutor {
    private final DeathSwap plugin;
    private final Runnable swapPlayers;
    private BukkitTask swapTask;
    private int TswapPlayerId;
    private boolean playersSet;

    public CommandDeathSwap(DeathSwap plugin, PlayersTask task, boolean playersSet) {
        this.plugin = plugin;
        this.swapPlayers = new PlayerSwapTask(plugin, task);
        this.TswapPlayerId = 0;
        this.playersSet = playersSet;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (!sender.hasPermission("deathswap.start")) {
                sender.sendMessage("You don't have permission to start the game!");
                return false;
            }

            if (args.length != 2) {
                sender.sendMessage("Usage: /deathswap [start|stop] [swapTime]");
                return false;
            }

            if (!plugin.arePlayersSet()) {
                sender.sendMessage("Players have not been set. Use /setplayers first.");
                return true;
            }

            if (args[0].equals("start")) {
                return this.startGame(args);
            } else if (args[0].equals("stop")) {
                return this.stopGame();
            }

            return true;
        } else {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
    }

    private boolean startGame(String[] args) {
        if (Bukkit.getScheduler().isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage("Game is already running!");
            return true;
        }

        int secondsPeriod = 0;
        if (Integer.parseInt(args[4]) > 0) {
            try {
                secondsPeriod = Integer.parseInt(args[4]) * 60;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        this.swapTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this.swapPlayers, secondsPeriod * 20, secondsPeriod * 20);
        this.TswapPlayerId = this.swapTask.getTaskId();

        Bukkit.broadcastMessage("Game started!");
        return true;
    }

    private boolean stopGame() {
        if (!Bukkit.getScheduler().isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage("Game is not running!");
            return true;
        }
        this.swapTask.cancel();
        Bukkit.broadcastMessage("Game stopped!");

        return true;
    }



}