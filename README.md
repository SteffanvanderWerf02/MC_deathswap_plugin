# Death Swap plugin for Minecraft 1.16.x

Adds the `/deathswap` command and `/players` to a Bukkit/Spigot/PaperMC Minecraft Servers to swap players in the server randomly every X minutes.

The swapping has a 10 seconds countdown in the chat before it happens

---

### Setup

- Downloaded latest `.jar` from https://github.com/SteffanvanderWerf02/MC_deathswap_plugin/releases/tag/V1.0 
- Put the downloaded file into your server's `plugins` folder

---

### Commands

- `/deathSwap [start|stop] [swapTime]`
    - **Permissions**
        - `death_swap.start`
    - `First argument`
        - `start` Start the Death Swap continuous task to swap players every X minutes
        - `stop` Stop ongoing Death Swap continuous task
    - `Second argument` 
        - Period on which swaps happen, in minutes.
- `/players [set|get] [player1] [player2]`
    - **Permissions**
        - `players.set`
    - `First argument`
        - `set` Asign the players to the minigame
        - `get` See the currunt playing players
    - `Second argument`
        - First player name to setup for the game
    - `Third argument`
        - Second player name to setup for the game
---

## Build from source

- **IntelliJ IDEA**
    - Clone the project
        ```bash
        git clone https://github.com/SteffanvanderWerf02/MC_deathswap_plugin.git
        ```
    - Go to the maven menu en click on Lifecycle > install
    - Compiled `.jar` file will be located at `target/Deathswap_spigot_1.19.4`

- **Maven**
    - Run Maven Build
        ```bash
        mvn install
        ```
    - Compiled `.jar` file will be located at `target/Deathswap_spigot_1.19.4`

