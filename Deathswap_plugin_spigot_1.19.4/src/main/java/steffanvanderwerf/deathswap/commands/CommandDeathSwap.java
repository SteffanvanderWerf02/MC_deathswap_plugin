package steffanvanderwerf.deathswap.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import steffanvanderwerf.deathswap.Deathswap;
import steffanvanderwerf.deathswap.chattimer.ChatTimer;
import steffanvanderwerf.deathswap.players.SwappedPlayers;
import steffanvanderwerf.deathswap.tasks.PlayerSwapTask;

public class CommandDeathSwap implements CommandExecutor {
    private final Deathswap plugin;
    private final Runnable swapPlayers;
    private BukkitTask swapTask;
    private int TswapPlayerId;

    private final int countdownTimeSeconds;

    public CommandDeathSwap(Deathswap plugin, SwappedPlayers task) {
        this.plugin = plugin;
        this.swapPlayers = new PlayerSwapTask(plugin, task);
        this.TswapPlayerId = 0;
        this.countdownTimeSeconds = 10;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (!sender.hasPermission("deathswap.start") && !sender.hasPermission("deathswapt.stop")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to start/stop the game!");
                return false;
            }

            if (!plugin.arePlayersSet()) {
                Bukkit.broadcastMessage(ChatColor.RED + "Players are not set! Use /players set <player1> <player2> first.");
                return false;
            }

            if (args[0].equals("start")) {

                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /deathswap [start] [swapTime]");
                    return false;
                }

                return this.startGame(args);
            } else if (args[0].equals("stop")) {

                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "Usage: /deathswap [stop]");
                    return false;
                }

                return this.stopGame();
            }
            return false;
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }
    }

    private boolean startGame(String[] args) {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        if (scheduler.isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "Game is already running!");
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

        ChatTimer countdown = new ChatTimer(this.plugin, countdownTimeSeconds);
        countdown.startGameSecondsCountdown();

        this.setupGame();

        this.swapTask = scheduler.runTaskTimerAsynchronously(this.plugin, this.swapPlayers, secondsPeriod * 20L, secondsPeriod * 20L);
        this.TswapPlayerId = this.swapTask.getTaskId();

        return true;
    }

    private void setupGame() {

        for (Player player : plugin.getSwappedPlayers().getPlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.setWalkSpeed(0.25f);
            player.setHealth(20);
            player.setFoodLevel(20);

            player.getPlayer().getInventory().clear();
        }

        World world = Bukkit.getServer().getWorld("world");
        world.setTime(0L);
        world.setPVP(false);
        world.setDifficulty(Difficulty.HARD);
    }

    private boolean stopGame() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        if (!scheduler.isQueued(this.TswapPlayerId)) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "Game is not running!");
            return true;
        }

        World world = Bukkit.getServer().getWorld("world");
        world.setPVP(true);
        world.setDifficulty(Difficulty.PEACEFUL);

        this.swapTask.cancel();
        this.plugin.getSwappedPlayers().resetPlayers();
        this.plugin.setPlayersSet(false);
        Bukkit.broadcastMessage(ChatColor.BLUE + "Game stopped!");

        return true;
    }
}