package blocktele;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import blocktele.warps.Warp;

public class PortalManager extends HashMap<String, Warp> {
	private static final long serialVersionUID = 2745148816321388123L;
	
	public PortalManager() {}
	
	public PortalManager(Map<? extends String, ? extends Object> values) {
		castedPutAll(values);
	}

	public Warp put(Block block, Warp warp) {
		return put(toString(block), warp);
	}
	
	public Warp get(Block block) {
		return get(toString(block));
	}
	
	public void remove(Block block) {
		remove(toString(block));
	}
	
	public boolean containsKey(Block block) {
		return containsKey(toString(block));
	}
	
	public void castedPutAll(Map<? extends String, ? extends Object> map) {
		for (String k : map.keySet()) {
			put(k, (Warp) map.get(k));
		}
	}
	
	public static String toString (Block block) {
		Location loc = block.getLocation();
		String type = block.getType().toString();
		return type+"@"+loc.getWorld().getName()+";"+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
	}
	
}
