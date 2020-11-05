package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.BeanRoute;
import model.BeanStation;

public class Subway {
	private static List<BeanStation> traveledStations = new ArrayList<>(); // 已经BFS过的站点
	private static HashMap<BeanStation, BeanRoute> routeMap = new HashMap<>(); // 路径集
	private static int Max = Integer.MAX_VALUE;

	private static BeanStation getnextStation() { // 选择下一个需要BFS的站点
		int min = Max;// 最短经过站数，初始化为最大值
		BeanStation nextStation = null;
		Set<BeanStation> stations = routeMap.keySet();// 获取routeMap中所有的key值

		for (BeanStation s : stations) {// 遍历所有站点
			if (!traveledStations.contains(s)) {// 在未BFS过的站点中选择距离最小的站点作为下一个站点
				BeanRoute result = routeMap.get(s);
				if (result.getDistance() < min) {
					min = result.getDistance();
					nextStation = result.getEndStation();
				}
			}
		}
		return nextStation;
	}
	// 两个站所在的相同的路线，用于判定是否换乘
	private static List<String> getSameLine(BeanStation station1, BeanStation station2) { 
		List<String> line1 = station1.getLine();
		List<String> line2 = station2.getLine();
		List<String> sameline = new ArrayList<String>();
		for (String i : line1) {
			for (String j : line2) {
				if (i.equals(j))
					sameline.add(i);
			}
		}
		return sameline;
	}

	public static BeanRoute BFS(BeanStation startStation, BeanStation endStation) { // BFS计算最短路径

		for (List<BeanStation> l : DataProcessing.lineSet) { // 构造初始路径集
			for (int i = 0; i < l.size(); i++) {// 以起始站为起点，其余所有站为终点，距离无穷大
				BeanRoute result = new BeanRoute();
				result.setStartStation(startStation);
				result.setEndStation(l.get(i));
				result.setDistance(Max);
				result.setLinechange(0);
				routeMap.put(l.get(i), result);// HashMap中的元素具有互异性，不需要考虑重复，若共有n个站点则有n条初始结果线路
			}
		}
		routeMap.get(startStation).setDistance(0);
		traveledStations.add(startStation);

		// 初始化结果集，遍历起始站的邻居
		for (BeanStation s : startStation.getLinkStations()) {
			routeMap.get(s).setDistance(1);
			routeMap.get(s).setLastStations(startStation);
			List<String> samelines = getSameLine(startStation, s);
			// 获取起始站和其邻居共同所在的路线作为Route的所在路线,如果所在的路线有多条则随机选择
			routeMap.get(s).setLine(samelines.get(0));
		}

		BeanStation nextStation = getnextStation(); // 计算下一个站点
		while (nextStation != null) { // 遍历计算每一个站点的最短路径
			// 广度优先搜索
			for (BeanStation s : nextStation.getLinkStations()) {
				// 判断是否是最短路径，是则更新最短路径
				if (routeMap.get(nextStation).getDistance() + 1 < routeMap.get(s).getDistance()) {
					routeMap.get(s).setDistance(routeMap.get(nextStation).getDistance() + 1);
					routeMap.get(s).setLastStations(nextStation);
					List<String> samelines = getSameLine(nextStation, s);
					// 判断是否需要换乘，如果nextStation的上一站和下一站没有共同所在路线，则需要换乘
					if (!samelines.contains(routeMap.get(nextStation).getLine())) {
						routeMap.get(s).setLine(samelines.get(0));
						routeMap.get(s).setLinechange(1);
					} else {
						routeMap.get(s).setLine(routeMap.get(nextStation).getLine());
					}
				}
			}
			traveledStations.add(nextStation);
			nextStation = getnextStation();
		}
		return routeMap.get(endStation);
	}

	public static List<String> getPath(BeanRoute route) { // 找出最短路径并返回字符串信息
		List<String> path = new ArrayList<String>();
		List<BeanStation> result = new ArrayList<BeanStation>();
		BeanStation station = route.getLastStations();
		result.add(route.getEndStation());
		while (!station.equals(route.getStartStation()))// 从最后一站回溯整条线路
		{
			result.add(station);
			station = routeMap.get(station).getLastStations();
		}
		result.add(route.getStartStation());
		Collections.reverse(result);// 将回溯的线路倒序，站点顺序变为正序
		path.add(String.valueOf(route.getDistance()));
		for (int i = 0; i < result.size(); i++) {// 将最短路径站点信息转化成字符串输出
			if (i == 0) {
				path.add(result.get(i).getStationName() + "（" + result.get(i).getLine().get(0) + "）");
			} else {
				if (routeMap.get(result.get(i)).getLinechange() == 1) {
					path.add(result.get(i).getStationName() + "（换乘" + routeMap.get(result.get(i)).getLine() + "）");
				} else {

					if (i == result.size() - 1) {
						path.add(result.get(i).getStationName() + "（" + result.get(i).getLine().get(0) + "）");
					} else {
						path.add(result.get(i).getStationName());
					}
				}
			}

		}
		return path;
	}
}