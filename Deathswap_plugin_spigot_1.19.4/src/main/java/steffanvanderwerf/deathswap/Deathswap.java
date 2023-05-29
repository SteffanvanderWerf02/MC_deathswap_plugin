package steffanvanderwerf.deathswap;

import org.bukkit.plugin.java.JavaPlugin;
import steffanvanderwerf.deathswap.commands.CommandDeathSwap;
import steffanvanderwerf.deathswap.commands.CommandPlayers;
import steffanvanderwerf.deathswap.players.SwappedPlayers;

public class Deathswap extends JavaPlugin {

    private SwappedPlayers playerSwap;
    private boolean playersSet;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        // Initialize the PlayersTask instance
        this.playerSwap = new SwappedPlayers(this);

        this.getCommand("players").setExecutor(new CommandPlayers(this));
        this.getCommand("deathSwap").setExecutor(new CommandDeathSwap(this, this.playerSwap, this.playersSet));
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        getLogger().info("onDisable is called!");
    }

    public SwappedPlayers getSwappedPlayers() {
        return this.playerSwap;
    }

    public void setPlayersSet(boolean playersSet) {
        this.playersSet = playersSet;
    }

    public boolean arePlayersSet() {
        return this.playersSet;
    }
}