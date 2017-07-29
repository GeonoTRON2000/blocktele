package blocktele.command;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import blocktele.BlockTele;
import blocktele.PortalManager;
import blocktele.player.PlayerSelections;
import blocktele.warps.Warp;
import blocktele.warps.WarpHolder;

public class BlockTeleCommandExecutor implements CommandExecutor {
	private BlockTele plugin;
	private WarpHolder warps;
	private PlayerSelections selections;
	private PortalManager portals;
	
	public BlockTeleCommandExecutor(BlockTele plugin, WarpHolder warps, PlayerSelections selections, PortalManager portals) {
		this.plugin = plugin;
		this.warps = warps;
		this.selections = selections;
		this.portals = portals;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("blocktele")) {
			switch (args.length) {
			case 1:
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("blocktele.reload")) {
						plugin.reloadConfig();
						plugin.reloadWarps();
						sender.sendMessage(ChatColor.GREEN+"[BlockTele] Reload Complete");
						return true;
					} else if (args[0].equalsIgnoreCase("unlink")) {
						if (sender.hasPermission("blocktele.unlink")) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] Only players can unlink portals.");
								return true;
							}
							Player player = (Player) sender;
							if (!selections.containsKey(player.getName())) {
								player.sendMessage(ChatColor.DARK_RED+"[BlockTele] You must select a block to unlink.");
								return true;
							}
							portals.remove(selections.get(player.getName()));
							player.sendMessage(ChatColor.GREEN+"[BlockTele] Portal removed!");
							return true;
						} else {
							sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] You don't have permission to do this.");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] You don't have permission to do this.");
						return true;
					}
				} else {
					return false;
				}
			case 2:
				if (args[0].equalsIgnoreCase("linkwarp")) {
					if (sender.hasPermission("blocktele.link")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] Only players can link portals.");
							return true;
						}
						Player player = (Player) sender;
						if (!selections.containsKey(player.getName())) {
							player.sendMessage(ChatColor.DARK_RED+"[BlockTele] You must select a block to link.");
							return true;
						} else if (selections.get(player.getName()).isEmpty()) {
							player.sendMessage(ChatColor.DARK_RED+"[BlockTele] You must select a block to link.");
							return true;
						}
						if (portals.containsKey(selections.get(player.getName()))) {
							player.sendMessage(ChatColor.DARK_RED+"[BlockTele] That block is already linked to a warp.");
							return true;
						}
						if (!warps.containsKey(args[1].toLowerCase())) {
							player.sendMessage(ChatColor.DARK_RED+"[BlockTele] That warp does not exist.");
							return true;
						}
						portals.put(selections.get(player.getName()), warps.get(args[1].toLowerCase()));
						player.sendMessage(ChatColor.GREEN+"[BlockTele] Block linked!");
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] You don't have permission to do this.");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("setwarp")) {
					if (sender.hasPermission("blocktele.setwarp")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] Only players can set warps.");
							return true;
						}
						Player p = (Player) sender;
						warps.put(args[1].toLowerCase(), new Warp(args[1].replace('_', ' '), p.getLocation()));
						p.sendMessage(ChatColor.GREEN+"[BlockTele] Warp set!");
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] You don't have permission to do this.");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("delwarp")) {
					if (sender.hasPermission("blocktele.setwarp")) {
						warps.remove(args[1].toLowerCase());
						sender.sendMessage(ChatColor.GREEN+"[BlockTele] Warp removed!");
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED+"[BlockTele] You don't have permission to do this.");
						return true;
					}
				} else {
					return false;
				}
			default:
				return false;
			}
		} else {
			return true;
		}
	}

}
