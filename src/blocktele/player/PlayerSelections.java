package blocktele.player;
import java.util.HashMap;
import org.bukkit.block.Block;

public class PlayerSelections extends HashMap<String, Block> {
	private static final long serialVersionUID = -2805705292089806728L;
	
	@Override
	public Block put(String key, Block value) {
		if (value == null) {
			remove(key);
		}
		return super.put(key, value);
	}
	
}
