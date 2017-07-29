package blocktele.listener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import blocktele.BlockTele;
import blocktele.PortalManager;
import blocktele.player.PlayerSelections;
import blocktele.warps.Warp;

public class BlockTeleListener implements Listener {
	private BlockTele plugin;
	private PlayerSelections selections;
	private PortalManager portals;

	public BlockTeleListener(BlockTele plugin, PlayerSelections selections, PortalManager portals) {
		this.plugin = plugin;
		this.selections = selections;
		this.portals = portals;
	}
	
	@EventHandler
	public void onBlockBreak (BlockBreakEvent e) {
		Block b = e.getBlock();
		if (portals.containsKey(b)) {
			if (e.getPlayer().hasPermission("blocktele.unlink"))
				portals.remove(b);
			else {
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.DARK_RED+"[BlockTele] This block is a portal.  You can't destroy portals.");
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		Material key = asMaterial(plugin.getConfig().getString("key"));
		if (e.getPlayer().getItemInHand().getType() == key && e.getPlayer().hasPermission("blocktele.select")) {
			selections.put(e.getPlayer().getName(), e.getClickedBlock());
			e.getPlayer().sendMessage(ChatColor.DARK_AQUA+"[BlockTele] Block selected!");
			return;
		} else if (portals.containsKey(e.getClickedBlock()) && e.getPlayer().hasPermission("blocktele.use")) {
			e.setCancelled(true);
			Warp warp = portals.get(e.getClickedBlock());
			warp.teleportPlayer(e.getPlayer());
			if (plugin.getConfig().getBoolean("showTeleportMessage")) {
				e.getPlayer().sendMessage(ChatColor.DARK_AQUA+"[BlockTele] You have been teleported to "+warp.getName()+".");
			}
			return;
		}
	}
	
	private Material asMaterial(String s) {
		if (s == null) {
			return Material.GOLD_AXE;
		} else {
			try {
				return Material.valueOf(s);
			} catch (Throwable e) {
				return Material.GOLD_AXE;
			}
		}
	}
	
}
