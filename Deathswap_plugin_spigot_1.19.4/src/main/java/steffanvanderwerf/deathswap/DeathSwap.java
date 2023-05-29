package steffanvanderwerf.deathswap;

import org.bukkit.plugin.java.JavaPlugin;
import steffanvanderwerf.deathswap.commands.CommandSetDeathSwapPlayers;
import steffanvanderwerf.deathswap.tasks.PlayersTask;
import steffanvanderwerf.deathswap.commands.CommandDeathSwap;

public class DeathSwap extends JavaPlugin {

    private PlayersTask playerTask;
    private boolean playersSet;
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        this.getCommand("setPlayers").setExecutor(new CommandSetDeathSwapPlayers(this));
        this.getCommand("deathswap").setExecutor(new CommandDeathSwap(this, this.playerTask, this.playersSet));
    }
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        getLogger().info("onDisable is called!");
    }

    public PlayersTask getPlayerTask() {
        return this.playerTask;
    }

    public void setPlayersSet(boolean playersSet) {
        this.playersSet = playersSet;
    }

    public boolean arePlayersSet() {
        return this.playersSet;
    }
}