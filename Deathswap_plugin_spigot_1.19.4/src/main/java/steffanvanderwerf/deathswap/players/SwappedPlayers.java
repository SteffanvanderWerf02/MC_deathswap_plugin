package steffanvanderwerf.deathswap.players;

import org.bukkit.entity.Player;
import steffanvanderwerf.deathswap.Deathswap;

public class SwappedPlayers {
    private final Deathswap plugin;
    private Player player1;
    private Player player2;

    public SwappedPlayers(Deathswap plugin) {
        this.plugin = plugin;
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

    public Player[] getPlayers() {
        return new Player[] {this.player1, this.player2};
    }
    public void resetPlayers() {
        this.player1 = null;
        this.player2 = null;
    }
}