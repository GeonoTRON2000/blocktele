package blocktele.warps;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import blocktele.BlockTele;

public class Warp implements ConfigurationSerializable {
	private String name;
	private Location location;
	
	public Warp (Map<String, Object> map) {
		this.name = (String) map.get("name");
		this.location = new Location(BlockTele.getMyServer().getWorld((String) map.get("world")), (Double) map.get("x"), (Double) map.get("y"), (Double) map.get("z"), ((Double) map.get("yaw")).floatValue(), ((Double) map.get("pitch")).floatValue());
	}
	
	public Warp (String name, Location location) {
		this.name = name;
		this.location = location;
	}
	
	public Warp(String name, double x, double y, double z) {
		this.name = name;
		this.location = new Location(BlockTele.getMyServer().getWorlds().get(0), x, y, z);
	}
	
	public Warp(String name, World world, double x, double y, double z) {
		this.name = name;
		this.location = new Location(world, x, y, z);
	}
	
	public void rename(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void teleportPlayer(Player p) {
		p.teleport(location);
	}

	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("world", location.getWorld().getName());
		map.put("x", location.getX());
		map.put("y", location.getY());
		map.put("z", location.getZ());
		map.put("yaw", location.getYaw());
		map.put("pitch", location.getPitch());
		return map;
	}
	
	public static Warp deserialize(Map<String, Object> map) {
		return new Warp(map);
	}
	
	public static Warp valueOf(Map<String, Object> map) {
		return deserialize(map);
	}
	
}
