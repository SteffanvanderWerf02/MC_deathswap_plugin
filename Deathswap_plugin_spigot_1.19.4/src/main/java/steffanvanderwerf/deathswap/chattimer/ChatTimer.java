package steffanvanderwerf.deathswap.chattimer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import steffanvanderwerf.deathswap.Deathswap;

public class ChatTimer {
    private final Deathswap plugin;
    private int taskID;
    private int secondsCountdown;

    public ChatTimer(Deathswap plugin, int secondsCountdown) {
        this.plugin = plugin;
        this.secondsCountdown = secondsCountdown;
    }

    public void startSwapSecondsCountdown() {
        final int startCountdown = this.secondsCountdown;

        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (this.secondsCountdown != 0) {
                Bukkit.broadcastMessage(ChatColor.BLUE + "Swapping players in " + this.secondsCountdown + "...");
            } else {
                plugin.getServer().getScheduler().cancelTask(taskID);
                this.secondsCountdown = startCountdown;
            }

            this.secondsCountdown--;
        }, 20L, 20L);
    }

    public void startGameSecondsCountdown() {
        final int startCountdown = this.secondsCountdown;

        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (this.secondsCountdown != 0) {
                Bukkit.broadcastMessage(ChatColor.BLUE + "Game is starting in " + this.secondsCountdown + "...");
            } else {
                Bukkit.broadcastMessage(ChatColor.BLUE + "GO!!!");
                plugin.getServer().getScheduler().cancelTask(taskID);
                this.secondsCountdown = startCountdown;
            }

            this.secondsCountdown--;
        }, 20L, 20L);
    }

    public int getTaskID() {
        return this.taskID;
    }
}