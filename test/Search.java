

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lbsyun.ServiceException;
import lbsyun.basestruct.AdvancedQueryInfo;
import lbsyun.basestruct.Bounds;
import lbsyun.basestruct.GeoPoint;
import lbsyun.constant.Scope;
import lbsyun.response.Page;
import lbsyun.response.Poi;
import lbsyun.service.SearchService;

public final class Search {

	public static Map<String, String> searchUsage = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("searchRegion", "区域检索");
			put("searchBounds", "矩形框检索");
			put("searchNearby", "周边检索");
			put("searchDetail", "详情检索");
		}
	};
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		InputStream in = new BufferedInputStream (new FileInputStream("conf/user.properties"));
		Properties config = new Properties();
		config.load(in);
		String ak = config.getProperty("ak");
		SearchService service  = new SearchService(ak);
		int index = 0;
		if (args.length == index) {
			System.out.println("Usage: StorageService COMMAND");
			System.out.println("COMMAND为以下命令之一:");
			DemoUtil.printMap(searchUsage);
			return;
		}
		String command = args[index++];
		if (searchUsage.containsKey(command)) {
			try {
				if (command.equals("searchRegion")) {
					if (args.length < index + 4) {
						System.out
								.println("Usage: SearchRegion databox_id page_index page_size region_id|region_name "
										+ "[-q query_word] [-tag tag] "
										+ "[-scope 1|2] [-callback callback] "
										+ "[-sort_name name] [-sort_rule rule] [-section name:lower,upper]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					int pageIndex = Integer.valueOf(args[index++]);
					int pageSize = Integer.valueOf(args[index++]);
					String region = args[index++];
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					AdvancedQueryInfo info = new AdvancedQueryInfo(params);
					
					Page content = service.searchRegion(databoxId, region, pageIndex, pageSize, info);
					if (null != content) {
						System.out.println("page info:");
						System.out.println(content.toString());
						
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("searchBounds")) {
					if (args.length < index + 7) {
						System.out
								.println("Usage: SearchBounds databox_id page_index page_size lat1 lon1(左下角坐标) lat2 lon2(右上角坐标) "
										+ "[-q query_word] [-tag tag] "
										+ "[-scope 1|2] [-callback callback] "
										+ "[-sort_name name] [-sort_rule rule] [-section name:lower,upper]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					int pageIndex = Integer.valueOf(args[index++]);
					int pageSize = Integer.valueOf(args[index++]);
					double lat1 = Double.valueOf(args[index++]);
					double lon1 = Double.valueOf(args[index++]);
					double lat2 = Double.valueOf(args[index++]);
					double lon2 = Double.valueOf(args[index++]);
					Bounds bounds = new Bounds(lon1, lon2, lat2, lat1);
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					AdvancedQueryInfo info = new AdvancedQueryInfo(params);
					
					Page content = service.searchBounds(databoxId, bounds, pageIndex, pageSize, info);
					if (null != content) {
						System.out.println("page info:");
						System.out.println(content.toString());
						
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("searchNearby")) {
					if (args.length < index + 5) {
						System.out
								.println("Usage: SearchNearby databox_id page_index page_size lat lon [radius]"
										+ "[-q query_word] [-tag tag] "
										+ "[-scope 1|2] [-callback callback] "
										+ "[-sort_name name] [-sort_rule rule] [-section name:lower,upper]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					int pageIndex = Integer.valueOf(args[index++]);
					int pageSize = Integer.valueOf(args[index++]);
					double lat = Double.valueOf(args[index++]);
					double lon = Double.valueOf(args[index++]);
					int radius = 1000;
					if (!args[index].contains("-")) {
						radius = Integer.valueOf(args[index++]);
					}
					GeoPoint location = new GeoPoint(lat, lon);
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					AdvancedQueryInfo info = new AdvancedQueryInfo(params);
					
					Page content = service.searchNearby(databoxId, location, radius, pageIndex, pageSize, info);
					if (null != content) {
						System.out.println("page info:");
						System.out.println(content.toString());
						
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("searchDetail")) {
					if (args.length < index + 1) {
						System.out.println("Usage: SearchDetail id [basic|detail]");
						return;
					}
					
					int id = Integer.valueOf(args[index++]);
					Scope scope = Scope.BASIC;
					if (args.length > index) {
						String tmp = args[index++];
						if (tmp.equals("basic")) {
							scope = Scope.BASIC;
						} else if (tmp.equals("detail")) {
							scope = Scope.DETAIL;
						}
					}
					Poi content = service.searchDetail(id, scope);
					if (null != content) {
						System.out.println("poi info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("poi's id: "	+ id);
						System.out.println("status: " + service.getStatus());
					}
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("cannot find commmand: " + command);
		}
	}
	

}
