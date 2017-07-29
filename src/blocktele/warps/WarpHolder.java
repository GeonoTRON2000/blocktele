package blocktele.warps;
import java.util.HashMap;
import java.util.Map;

public class WarpHolder extends HashMap<String, Warp> {
	private static final long serialVersionUID = 4232360153377820102L;
	
	public WarpHolder() {}
	
	public WarpHolder(Map<String, Object> map) {
		castedPutAll(map);
	}

	public void castedPutAll(Map<? extends String, ? extends Object> map) {
		for (String k : map.keySet()) {
			put(k, (Warp) map.get(k));
		}
	}
	
}
