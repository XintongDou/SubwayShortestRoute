package model;

public class BeanRoute {

	private BeanStation startStation; // ��ʼվ
	private BeanStation endStation; // �յ�վ
	private BeanStation lastStation; // �����վ�����·���е���һվ
	private int distance; // ����
	private String line; // �����վ�ڼ�������
	private int linechange; // ��Ǵ���һվ����վ�Ƿ��л��ˣ�0Ϊ�޻��ˣ�1Ϊ�軻��

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
