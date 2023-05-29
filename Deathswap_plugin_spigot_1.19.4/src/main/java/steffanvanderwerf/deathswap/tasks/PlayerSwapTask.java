package steffanvanderwerf.deathswap.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import steffanvanderwerf.deathswap.Deathswap;
import steffanvanderwerf.deathswap.chattimer.ChatTimer;
import steffanvanderwerf.deathswap.players.SwappedPlayers;

public class PlayerSwapTask implements Runnable {
    private final Deathswap plugin;
    private SwappedPlayers swappedPlayers;

    public PlayerSwapTask(Deathswap plugin, SwappedPlayers task) {
        this.plugin = plugin;
        this.swappedPlayers = task;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        this.waitForCountdown(10);

        if (Bukkit.getOnlinePlayers().size() < 2) {
            Bukkit.broadcastMessage(ChatColor.RED + "Not enough players in the server to swap.");
            return; // stop the task
        }

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::swapPlayers);
    }

    public void waitForCountdown(int seconds) {
        ChatTimer countdown = new ChatTimer(plugin, seconds);
        countdown.startSwapSecondsCountdown();

        while (Bukkit.getScheduler().isQueued(countdown.getTaskID())) ;
    }
    private void swapPlayers() {
        Location P1Loc = this.swappedPlayers.getPlayer1().getLocation();
        Location P2Loc = this.swappedPlayers.getPlayer2().getLocation();

        Bukkit.getScheduler().runTask(plugin, () -> {
            this.swappedPlayers.getPlayer1().teleport(P2Loc);
            this.swappedPlayers.getPlayer2().teleport(P1Loc);
        });

        Bukkit.broadcastMessage(ChatColor.GREEN + "Players swapped!");
    }
}