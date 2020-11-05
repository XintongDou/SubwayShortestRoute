package model;

import java.util.ArrayList;
import java.util.List;

public class BeanStation {

	private String stationName;// 站名
	private List<String> line = new ArrayList<String>();// 所在线路，换乘站有多条
	private List<BeanStation> linkStations = new ArrayList<BeanStation>();// 相邻站点

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
