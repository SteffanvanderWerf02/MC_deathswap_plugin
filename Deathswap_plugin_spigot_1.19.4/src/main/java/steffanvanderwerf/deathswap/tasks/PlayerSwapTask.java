package steffanvanderwerf.deathswap.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import steffanvanderwerf.deathswap.DeathSwap;

public class PlayerSwapTask implements Runnable {
    private final DeathSwap plugin;
    private PlayersTask playersTask;

    public PlayerSwapTask(DeathSwap plugin, PlayersTask task) {
        this.plugin = plugin;
        this.playersTask = task;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() < 2) {
            Bukkit.broadcastMessage(ChatColor.RED + "Not enough players in the server to swap.");
            return; // stop the task
        }

        this.swapPlayers();
        Bukkit.broadcastMessage(ChatColor.GREEN + "Players swapped!");
    }

    private void swapPlayers() {
        Location P1Loc = this.playersTask.getPlayer1().getLocation();
        Location P2Loc = this.playersTask.getPlayer2().getLocation();

        this.playersTask.getPlayer1().teleport(P2Loc);
        this.playersTask.getPlayer2().teleport(P1Loc);
    }
}