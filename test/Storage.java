

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import lbsyun.ServiceException;
import lbsyun.basestruct.BasicQueryInfo;
import lbsyun.basestruct.GeoPoint;
import lbsyun.basestruct.PoiBasicInfo;
import lbsyun.basestruct.PoiExtInfo;
import lbsyun.basestruct.PropertyInfo;
import lbsyun.constant.CoordType;
import lbsyun.constant.GeoType;
import lbsyun.constant.PropertyType;
import lbsyun.constant.Scope;
import lbsyun.response.Databox;
import lbsyun.response.DataboxMeta;
import lbsyun.response.Page;
import lbsyun.response.Poi;
import lbsyun.service.StorageService;
import lbsyun.utils.StringUtil;

public final class Storage {

	public static Map<String, String> storageUsage = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("createDatabox", "创建databox");
			put("deleteDatabox", "删除databox");
			put("updateDatabox", "修改databox");
			put("getDatabox", "获取单个databox");
			put("listDatabox", "条件查询databox");
			put("createMeta", "创建databox_meta");
			put("deleteMeta", "删除databox_meta");
			put("updateMeta", "修改databox_meta");
			put("getMeta", "获取单个databox_meta");
			put("listMeta", "条件查询databox_meta");
			put("createPoi", "创建Poi");
			put("updatePoi", "修改Poi");
			put("deletePoi", "删除单个Poi");
			put("batchDeletePoi", "批量删除Poi");
			put("getPoi", "获取单个Poi");
			put("listPoi", "条件查询Poi");
			put("createPoiExt", "创建poiext");
			put("updatePoiExt", "修改poiext");
			put("deletePoiExt", "删除poiext");
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
		StorageService service  = new StorageService(ak);
		int index = 0;
		if (args.length == index) {
			System.out.println("Usage: StorageService COMMAND");
			System.out.println("COMMAND为以下命令之一:");
			DemoUtil.printMap(storageUsage);
			return;
		}
		String command = args[index++];
		if (storageUsage.containsKey(command)) {
			try {
				if (command.equals("createDatabox")) {
					if (args.length == index) {
						System.out
								.println("Usage: CreateDatabox name [point|line|flat]");
						return;
					}
					Map<String, String> params = new HashMap<String, String>();
					params.put("name", args[index++]);
					if (args.length > index) {
						String type = args[index++];
						if (type.equals("point")) {
							params.put("geotype", GeoType.POINT.toString());
						} else if (type.equals("line")) {
							params.put("geotype", GeoType.LINE.toString());
						} else if (type.equals("flat")) {
							params.put("geotype", GeoType.FLAT.toString());
						}
					}

					int databoxId = -1;
					if (params.containsKey("geotype")) {
						databoxId = service.createDatabox(params.get("name"),
								GeoType.valueOf(params.get("geotype")));
					} else {
						databoxId = service.createDatabox(params.get("name"));
					}
					if (databoxId >= 0) {
						System.out.println("create success!\n databox's id: "
								+ databoxId);
					} else {
						System.out.println("create failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("updateDatabox")) {
					if (args.length < index + 2) {
						System.out
								.println("Usage: UpdateDatabox id name");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					String name = args[index++];
					if (service.updateDatabox(databoxId, name)){
						System.out.println("update success! ");
						System.out.println("databox's id: "
							+ databoxId + ", databox's name: " + name);
					} else {
						System.out.println("update failed..");
						System.out.println("databox's id: "	+ databoxId);
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("getDatabox")) {
					if (args.length <= index) {
						System.out.println("Usage: getDatabox id [basic|detail]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					Scope scope = Scope.BASIC;
					if (args.length == index) {
						String tmp = args[index++];
						if (tmp.equals("basic")) {
							scope = Scope.BASIC;
						} else if (tmp.equals("detail")) {
							scope = Scope.DETAIL;
						}
					}
					Databox content = service.getDatabox(databoxId, scope);
					if (null != content) {
						System.out.println("databox info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("databox's id: "	+ databoxId);
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("listDatabox")) {
					if (args.length < index + 2) {
						System.out.println("Usage: ListDatabox page_index page_size [name]");
						return;
					}
					
					int pageIndex = Integer.valueOf(args[index++]);
					int pageSize = Integer.valueOf(args[index++]);
					
					String name = null;
					
					if (args.length > index) {
						name = args[index++];
					}					
					
					Page content = service.listDatabox(name, pageIndex, pageSize);
					if (null != content) {
						System.out.println("page info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("deleteDatabox")) {
					if (args.length <= index) {
						System.out.println("Usage: DeleteDatabox id");
						return;
					}
					int databoxId = Integer.valueOf(args[index++]);
					
					if (service.deleteDatabox(databoxId)) {
						System.out.println("delete success!");
						System.out.println("deleted databox's id: " + databoxId);
					} else {
						System.out.println("delete failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("createMeta")) {
					if (args.length < index + 4) {
						System.out.println("Usage: CreateMeta databox_id name key type [magic]");
						return;
					}
					int databoxId = Integer.valueOf(args[index++]);
					
					String name = args[index++];
					String key = args[index++];
					PropertyType type = PropertyType.valueOf(Integer.valueOf(args[index++]));
					boolean magic = false;
					if (args.length > index) {
						magic = args[index++].equals("1");
					}
					PropertyInfo property = new PropertyInfo(name, key, type, magic);
					//int metaId = service.createDataboxMeta(databoxId, name, key, type, magic);
					int metaId = service.createDataboxMeta(databoxId, property);
					if (metaId >= 0) {
						System.out.println("create success!");
						System.out.println("databox_meta's id: " + metaId);
					} else {
						System.out.println("create failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("updateMeta")) {
					if (args.length < index + 1) {
						System.out.println("Usage: updateMeta databox_meta_id [-n name] [-m magic]");
						return;
					}
					int metaId = Integer.valueOf(args[index++]);
					Map<String, String> params = new HashMap<String, String>();
					for (int i = index; i < args.length; i += 2) {
						if(i + 1 < args.length) {
							params.put(args[i], args[i + 1]);
						}
					}
					
					String name = null;
					boolean magic = false;
					if (params.containsKey("n")) {
						name = params.get("n");
					}
					if (params.containsKey("m")) {
						magic = params.get("m").equals("1");
					}
					
					PropertyInfo property = new PropertyInfo();
					property.setMagic(magic);
					property.setName(name);
					
					if (service.updateDataboxMeta(metaId, name, magic)) {
						System.out.println("update success!");
						System.out.println("databox_meta's id: " + metaId);
					} else {
						System.out.println("update failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("getMeta")) {
					if (args.length == index) {
						System.out.println("Usage: GetDataboxMeta id");
						return;
					}
					
					int metaId = Integer.valueOf(args[index++]);
					
					DataboxMeta content = service.getDataboxMeta(metaId);
					if (null != content) {
						System.out.println("databox_meta info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("databox_meta's id: "	+ metaId);
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("listMeta")) {
					if (args.length < index + 1) {
						System.out.println("Usage: ListDataboxMeta databox_id [-n name] [-k key]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					Map<String, String> params = new HashMap<String, String>();
					for (int i = index; i < args.length; i += 2) {
						if(i + 1 < args.length) {
							params.put(args[i], args[i + 1]);
						}
					}
					
					String name = null;
					String key = null;
					if (params.containsKey("-n")) {
						name = params.get("-n");
					}
					if (params.containsKey("-k")) {
						key = params.get("-k");
					}
					
					ArrayList<DataboxMeta> content = service.listDataboxMeta(databoxId, name, key);
					if (null != content) {
						System.out.println("databox_meta info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("deleteMeta")) {
					if (args.length <= index) {
						System.out.println("Usage: DeleteDataboxMeta id");
						return;
					}
					int metaId = Integer.valueOf(args[index++]);
					
					if (service.deleteDataboxMeta(metaId)) {
						System.out.println("delete success!");
						System.out.println("deleted databox_meta's id: " + metaId);
					} else {
						System.out.println("delete failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("createPoi")) {
					if (args.length < index + 5) {
						System.out.println("Usage: CreatePoi databox_id name lat lon baidu|gov|none" +
								"[-address address] [-telephone telephone] " +
								"[-zip_code zip_code] [-poi_tag tags] " +
								"[-category_id id] [-geo_sequence sequence] " +
								"[-icon icon] [-baidu_uid uid]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					String name = args[index++];
					double lat = Double.valueOf(args[index++]);
					double lon = Double.valueOf(args[index++]);
					CoordType type = null;
					String typeString = args[index++];
					if (typeString.equals("baidu")) {
						type = CoordType.BAIDU;
					} else if (typeString.equals("gov")) {
						type = CoordType.GOV;
					} else if (typeString.equals("none")) {
						type = CoordType.NONE;
					}
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					PoiBasicInfo basicInfo = new PoiBasicInfo(name, new GeoPoint(lat, lon), type, params);
					
					long poiId = service.createPoi(databoxId, basicInfo);
					
					if (poiId >= 0) {
						System.out.println("create success!");
						System.out.println("poi's id: " + poiId);
					} else {
						System.out.println("create failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("updatePoi")) {
					if (args.length < index + 1) {
						System.out.println("Usage: update poi_id " +
								"[-name name] [-original_lat lat] " +
								"[original_lon lon] [-original_coord_type 1|2|3] " +
								"[-address address] [-telephone telephone] " +
								"[-zip_code zip_code] [-poi_tag tags] " +
								"[-category_id id] [-geo_sequence sequence] " +
								"[-icon icon] [-baidu_uid uid]");
						return;
					}
					
					int poiId = Integer.valueOf(args[index++]);
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					PoiBasicInfo basicInfo = new PoiBasicInfo(params);
					
					
					if (service.updatePoi(poiId, basicInfo)) {
						System.out.println("update success!");
						System.out.println("poi's id: " + poiId);
					} else {
						System.out.println("update failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("deletePoi")) {
					if (args.length <= index) {
						System.out.println("Usage: DeletePoi id");
						return;
					}
					int poiId = Integer.valueOf(args[index++]);
					
					if (service.deletePoi(poiId)) {
						System.out.println("delete success!");
						System.out.println("deleted poi's id: " + poiId);
					} else {
						System.out.println("delete failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("batchDeletePoi")) {
					if (args.length <= index) {
						System.out.println("Usage: DeletePoi id1 id2 ...");
						return;
					}
					long[] ids = new long[args.length - index];
					for (int i = 0; i < ids.length; ++i) {
						ids[i] = Long.valueOf(args[i + index]);
					}
					
					if (service.deletePoiSet(ids)) {
						System.out.println("delete success!");
						System.out.println("deleted poi's ids: " + StringUtil.join(ids, ","));
					} else {
						System.out.println("delete failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("getPoi")) {
					if (args.length < index + 1) {
						System.out.println("Usage: getPoi id [basic|detail]");
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
					Poi content = service.getPoi(id, scope);
					if (null != content) {
						System.out.println("poi info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("poi's id: "	+ id);
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("listPoi")) {
					if (args.length < index + 3) {
						System.out
								.println("Usage: ListPoi databox_id page_index page_size "
										+ "[-name name] [-poi_importance importance] "
										+ "[-bounds bounds] [-province name] "
										+ "[-province_id id] [-city name] "
										+ "[-city_id id] [-district name] "
										+ "[-district_id id] [-poi_tag tags] "
										+ "[-start_date date] [-end_date date]");
						return;
					}
					
					int databoxId = Integer.valueOf(args[index++]);
					int pageIndex = Integer.valueOf(args[index++]);
					int pageSize = Integer.valueOf(args[index++]);
					
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					
					BasicQueryInfo info = new BasicQueryInfo(params);
					
					Page content = service.listPoi(databoxId, pageIndex, pageSize, info);
					if (null != content) {
						System.out.println("page info:");
						System.out.println(content.toString());
					} else {
						System.out.println("query failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("createPoiExt")) {
					if (args.length < index + 1) {
						System.out.println("Usage: CreatePoiExt poi_id [-k1 v1] [-k2 v2] ...");
						return;
					}
					
					int poiId = Integer.valueOf(args[index++]);
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					PoiExtInfo extInfo = new PoiExtInfo(params);
					
					if (service.createPoiExt(poiId, extInfo)) {
						System.out.println("create success!");
						System.out.println("poi's id: " + poiId);
					} else {
						System.out.println("create failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("updatePoiExt")) {
					if (args.length < index + 1) {
						System.out.println("Usage: UpdatePoiExt poi_id [-k1 v1] [-k2 v2] ...");
						return;
					}
					int poiId = Integer.valueOf(args[index++]);
					Map<String, String> params = DemoUtil.parseOpts(args, index);
					PoiExtInfo extInfo = new PoiExtInfo(params);
					
					if (service.updatePoiExt(poiId, extInfo)) {
						System.out.println("update success!");
						System.out.println("poi's id: " + poiId);
					} else {
						System.out.println("update failed..");
						System.out.println("status: " + service.getStatus());
					}
				} else if (command.equals("deletePoiExt")) {
					if (args.length < index + 2) {
						System.out.println("Usage: DeletePoiExt poi_id id1 [id2 ...]");
						return;
					}
					long poiId = Long.valueOf(args[index++]);
					String[] keys = new String[args.length - index];
					for (int i = 0; i < keys.length; ++i) {
						keys[i] = args[i + index];
					}
					
					if (service.deletePoiExt(poiId, keys)) {
						System.out.println("delete success!");
						System.out.println("poi's id: " + poiId);
						System.out.println("deleted poiext's keys: " + StringUtil.join(keys, ","));
					} else {
						System.out.println("delete failed..");
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
