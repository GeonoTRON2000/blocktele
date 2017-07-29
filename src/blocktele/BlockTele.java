package blocktele;
import java.io.File;
import java.io.IOException;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import blocktele.command.BlockTeleCommandExecutor;
import blocktele.listener.BlockTeleListener;
import blocktele.player.PlayerSelections;
import blocktele.warps.Warp;
import blocktele.warps.WarpHolder;

public class BlockTele extends JavaPlugin {
	private static Server server;
	private File warpFile;
	private FileConfiguration warps;
	private WarpHolder warpHolder;
	private PlayerSelections selections;
	private PortalManager portals;
	private BlockTeleCommandExecutor commandExecutor;
	
	public void onEnable() {
		BlockTele.server = getServer();
		ConfigurationSerialization.registerClass(Warp.class);
		saveDefaultConfig();
		loadWarps();
		if (warps.isConfigurationSection("warps")) {
			this.warpHolder = new WarpHolder(warps.getConfigurationSection("warps").getValues(false));
		} else {
			this.warpHolder = new WarpHolder();
		}
		this.selections = new PlayerSelections();
		if (warps.isConfigurationSection("portals")) {
			this.portals = new PortalManager(warps.getConfigurationSection("portals").getValues(false));
		} else {
			this.portals = new PortalManager();
		}
		this.commandExecutor = new BlockTeleCommandExecutor(this, warpHolder, selections, portals);
		getCommand("blocktele").setExecutor(commandExecutor);
		getServer().getPluginManager().registerEvents(new BlockTeleListener(this, selections, portals), this);
	}
		
	public void saveWarps() throws IOException {
		if (warpFile == null) {
			warpFile = new File(getDataFolder(), "warps.yml");
		}
		warps.createSection("warps", warpHolder);
		warps.createSection("portals", portals);
		warps.save(warpFile);
	}
	
	public static Server getMyServer() {
		return server;
	}
	
	public void onDisable() {
		try {
			saveWarps();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWarps() {
		saveDefaultWarps();
		warps = YamlConfiguration.loadConfiguration(warpFile);
		warps.setDefaults(YamlConfiguration.loadConfiguration(this.getClass().getResourceAsStream("/warps.yml")));
	}
	
	public void reloadWarps() {
		try {
			saveWarps();
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveDefaultWarps();
		warps = YamlConfiguration.loadConfiguration(warpFile);
		warps.setDefaults(YamlConfiguration.loadConfiguration(this.getClass().getResourceAsStream("/warps.yml")));
	}
	
	public void saveDefaultWarps() {
		if (warpFile == null) {
			warpFile = new File(getDataFolder(), "warps.yml");
		}
		if (!warpFile.exists()) {
			saveResource("warps.yml", false);
		}
	}
	
}
