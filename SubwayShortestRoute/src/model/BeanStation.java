package model;

import java.util.ArrayList;
import java.util.List;

public class BeanStation {

	private String stationName;// վ��
	private List<String> line = new ArrayList<String>();// ������·������վ�ж���
	private List<BeanStation> linkStations = new ArrayList<BeanStation>();// ����վ��

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String name) {
		this.stationName = name;
	}

	public List<String> getLine() {
		return line;
	}

	public void setLine(List<String> line) {
		this.line = line;
	}

	public void addLine(String line) {
		this.line.add(line);
	}

	public List<BeanStation> getLinkStations() {
		return linkStations;
	}

	public void setLinkStations(List<BeanStation> linkStations) {
		this.linkStations = linkStations;
	}

	public void addLinkStations(BeanStation linkStation) {
		this.linkStations.add(linkStation);
	}

}
