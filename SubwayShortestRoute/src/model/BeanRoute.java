package model;

public class BeanRoute {

	private BeanStation startStation; // 起始站
	private BeanStation endStation; // 终点站
	private BeanStation lastStation; // 到达该站的最短路径中的上一站
	private int distance; // 距离
	private String line; // 到达该站在几号线上
	private int linechange; // 标记从上一站到该站是否有换乘，0为无换乘，1为需换乘

	public BeanStation getStartStation() {
		return startStation;
	}

	public void setStartStation(BeanStation startStation) {
		this.startStation = startStation;
	}

	public BeanStation getEndStation() {
		return endStation;
	}

	public void setEndStation(BeanStation endStation) {
		this.endStation = endStation;
	}

	public BeanStation getLastStations() {
		return lastStation;
	}

	public void setLastStations(BeanStation lastStations) {
		this.lastStation = lastStations;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getLinechange() {
		return linechange;
	}

	public void setLinechange(int linechange) {
		this.linechange = linechange;
	}

}
