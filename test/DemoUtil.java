

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DemoUtil {
	public static void printMap(Map<String, String> map) {
		Set<String> key = map.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String k = it.next();
			String v = map.get(k);
			System.out.println(" " + k + "\t" + v);
		}
	}
	
	public static HashMap<String, String> parseOpts(String[] args, int beginIndex) {
		HashMap<String, String> params = new HashMap<String, String>();
		for (int i = beginIndex; i < args.length; i += 2) {
			if (i + 1 < args.length) {
				params.put(args[i].substring(1), args[i+1]);
			}
		}
		return params;
	}
}
