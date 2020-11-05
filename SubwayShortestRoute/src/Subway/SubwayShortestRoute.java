package Subway;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.BeanRoute;
import model.BeanStation;
import java.io.*;
import util.DataProcessing;
import util.Subway;

public class SubwayShortestRoute {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new DataProcessing();// 预处理地铁信息
		// 提示输入起始站和目的站名
		System.out.println("请输入起始站名：");
		String startName = new Scanner(System.in).nextLine();
		System.out.println("请输入目的站名：");
		String endName = new Scanner(System.in).nextLine();

		if (startName.equals(endName)) {
			System.out.println("起始站与目的站不能相同。");
		} else {
			// 遍历线路集查找是否站名是否存在
			BeanStation startStation = null;
			BeanStation endStation = null;
			for (List<BeanStation> l : DataProcessing.lineSet) {
				for (int i = 0; i < l.size(); i++) {
					if (startName.equals(l.get(i).getStationName())) {
						startStation = l.get(i);
					}
					if (endName.equals(l.get(i).getStationName())) {
						endStation = l.get(i);
					}
				}
			}
			if (startStation == null || endStation == null) {
				System.out.println("输入的站名不存在。");
			} else {
				// BFS得出最短路径
				List<String> path = Subway.getPath(Subway.BFS(startStation, endStation));
				System.out.println("查询结果：");
				for (int i = 0; i < path.size(); i++) {
					if (i == 0) {
						System.out.print("最短路径站数:");
					}
					System.out.println(path.get(i));
				}

			}
		}

	}

}
