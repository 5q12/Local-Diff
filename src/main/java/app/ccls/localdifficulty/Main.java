package app.ccls.localdifficulty;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements CommandExecutor {


    @Override
    public void onEnable() {
        getLogger().info("LocalDifficultyPlugin has been enabled");
        this.getCommand("localdifficulty").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("LocalDifficultyPlugin has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Chunk chunk = player.getLocation().getChunk();
            double localDifficulty = getLocalDifficulty(player.getWorld(), chunk.getX(), chunk.getZ());
            player.sendMessage("Local Difficulty: " + localDifficulty);
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    private double getLocalDifficulty(World world, int chunkX, int chunkZ) {
        long inhabitedTime = world.getChunkAt(chunkX, chunkZ).getInhabitedTime();
        long dayTime = world.getFullTime();
        double difficulty = calculateDifficulty(inhabitedTime, dayTime, world.getDifficulty().getValue());
        return difficulty;
    }

    private double calculateDifficulty(long inhabitedTime, long dayTime, int worldDifficulty) {
        double regionalDifficulty = (inhabitedTime / 24000.0) + (dayTime / 24000.0) + worldDifficulty;
        return Math.min(6.75, regionalDifficulty);
    }
}