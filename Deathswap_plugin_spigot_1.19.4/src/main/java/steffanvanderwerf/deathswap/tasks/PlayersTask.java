package steffanvanderwerf.deathswap.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import steffanvanderwerf.deathswap.DeathSwap;

public class PlayersTask {
    private final DeathSwap plugin;
    private Player player1;
    private Player player2;
    private BukkitRunnable runnable;

    public PlayersTask(DeathSwap plugin) {
        this.plugin = plugin;
    }

    public void start() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (player1 == null || player2 == null) {
                    // One or both players are not set, handle the situation
                    return;
                }

                // Perform actions involving both players
                player1.sendMessage("Hello, " + player2.getName() + "!");
                player2.sendMessage("Greetings, " + player1.getName() + "!");
            }
        };
        runnable.runTaskTimer(plugin, 0, 20); // Run every second
    }

    public void stop() {
        if (runnable != null) {
            runnable.cancel();
        }
    }

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}