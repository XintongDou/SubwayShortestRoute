package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.BeanRoute;
import model.BeanStation;

public class Subway {
	private static List<BeanStation> traveledStations = new ArrayList<>(); // �Ѿ�BFS����վ��
	private static HashMap<BeanStation, BeanRoute> routeMap = new HashMap<>(); // ·����
	private static int Max = Integer.MAX_VALUE;

	private static BeanStation getnextStation() { // ѡ����һ����ҪBFS��վ��
		int min = Max;// ��̾���վ������ʼ��Ϊ���ֵ
		BeanStation nextStation = null;
		Set<BeanStation> stations = routeMap.keySet();// ��ȡrouteMap�����е�keyֵ

		for (BeanStation s : stations) {// ��������վ��
			if (!traveledStations.contains(s)) {// ��δBFS����վ����ѡ�������С��վ����Ϊ��һ��վ��
				BeanRoute result = routeMap.get(s);
				if (result.getDistance() < min) {
					min = result.getDistance();
					nextStation = result.getEndStation();
				}
			}
		}
		return nextStation;
	}
	// ����վ���ڵ���ͬ��·�ߣ������ж��Ƿ񻻳�
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

	public static BeanRoute BFS(BeanStation startStation, BeanStation endStation) { // BFS�������·��

		for (List<BeanStation> l : DataProcessing.lineSet) { // �����ʼ·����
			for (int i = 0; i < l.size(); i++) {// ����ʼվΪ��㣬��������վΪ�յ㣬���������
				BeanRoute result = new BeanRoute();
				result.setStartStation(startStation);
				result.setEndStation(l.get(i));
				result.setDistance(Max);
				result.setLinechange(0);
				routeMap.put(l.get(i), result);// HashMap�е�Ԫ�ؾ��л����ԣ�����Ҫ�����ظ���������n��վ������n����ʼ�����·
			}
		}
		routeMap.get(startStation).setDistance(0);
		traveledStations.add(startStation);

		// ��ʼ���������������ʼվ���ھ�
		for (BeanStation s : startStation.getLinkStations()) {
			routeMap.get(s).setDistance(1);
			routeMap.get(s).setLastStations(startStation);
			List<String> samelines = getSameLine(startStation, s);
			// ��ȡ��ʼվ�����ھӹ�ͬ���ڵ�·����ΪRoute������·��,������ڵ�·���ж��������ѡ��
			routeMap.get(s).setLine(samelines.get(0));
		}

		BeanStation nextStation = getnextStation(); // ������һ��վ��
		while (nextStation != null) { // ��������ÿһ��վ������·��
			// �����������
			for (BeanStation s : nextStation.getLinkStations()) {
				// �ж��Ƿ������·��������������·��
				if (routeMap.get(nextStation).getDistance() + 1 < routeMap.get(s).getDistance()) {
					routeMap.get(s).setDistance(routeMap.get(nextStation).getDistance() + 1);
					routeMap.get(s).setLastStations(nextStation);
					List<String> samelines = getSameLine(nextStation, s);
					// �ж��Ƿ���Ҫ���ˣ����nextStation����һվ����һվû�й�ͬ����·�ߣ�����Ҫ����
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

	public static List<String> getPath(BeanRoute route) { // �ҳ����·���������ַ�����Ϣ
		List<String> path = new ArrayList<String>();
		List<BeanStation> result = new ArrayList<BeanStation>();
		BeanStation station = route.getLastStations();
		result.add(route.getEndStation());
		while (!station.equals(route.getStartStation()))// �����һվ����������·
		{
			result.add(station);
			station = routeMap.get(station).getLastStations();
		}
		result.add(route.getStartStation());
		Collections.reverse(result);// �����ݵ���·����վ��˳���Ϊ����
		path.add(String.valueOf(route.getDistance()));
		for (int i = 0; i < result.size(); i++) {// �����·��վ����Ϣת�����ַ������
			if (i == 0) {
				path.add(result.get(i).getStationName() + "��" + result.get(i).getLine().get(0) + "��");
			} else {
				if (routeMap.get(result.get(i)).getLinechange() == 1) {
					path.add(result.get(i).getStationName() + "������" + routeMap.get(result.get(i)).getLine() + "��");
				} else {

					if (i == result.size() - 1) {
						path.add(result.get(i).getStationName() + "��" + result.get(i).getLine().get(0) + "��");
					} else {
						path.add(result.get(i).getStationName());
					}
				}
			}

		}
		return path;
	}
}