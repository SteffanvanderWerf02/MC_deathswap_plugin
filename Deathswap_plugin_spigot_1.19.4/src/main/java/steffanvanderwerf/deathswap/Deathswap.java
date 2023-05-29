package steffanvanderwerf.deathswap;

import org.bukkit.plugin.java.JavaPlugin;
import steffanvanderwerf.deathswap.commands.CommandPlayers;
import steffanvanderwerf.deathswap.players.SwappedPlayers;
import steffanvanderwerf.deathswap.commands.CommandDeathSwap;

public class Deathswap extends JavaPlugin {

    private SwappedPlayers playerTask;
    private boolean playersSet;
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        // Initialize the PlayersTask instance
        this.playerTask = new SwappedPlayers(this);

        this.getCommand("setPlayers").setExecutor(new CommandPlayers(this));
        this.getCommand("deathswap").setExecutor(new CommandDeathSwap(this, this.playerTask, this.playersSet));
    }
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        getLogger().info("onDisable is called!");
    }

    public SwappedPlayers getSwappedPlayers() {
        return this.playerTask;
    }

    public void setPlayersSet(boolean playersSet) {
        this.playersSet = playersSet;
    }

    public boolean arePlayersSet() {
        return this.playersSet;
    }
}