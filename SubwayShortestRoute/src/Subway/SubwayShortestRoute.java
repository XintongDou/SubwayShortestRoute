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
		new DataProcessing();// Ԥ���������Ϣ
		// ��ʾ������ʼվ��Ŀ��վ��
		System.out.println("��������ʼվ����");
		String startName = new Scanner(System.in).nextLine();
		System.out.println("������Ŀ��վ����");
		String endName = new Scanner(System.in).nextLine();

		if (startName.equals(endName)) {
			System.out.println("��ʼվ��Ŀ��վ������ͬ��");
		} else {
			// ������·�������Ƿ�վ���Ƿ����
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
				System.out.println("�����վ�������ڡ�");
			} else {
				// BFS�ó����·��
				List<String> path = Subway.getPath(Subway.BFS(startStation, endStation));
				System.out.println("��ѯ�����");
				for (int i = 0; i < path.size(); i++) {
					if (i == 0) {
						System.out.print("���·��վ��:");
					}
					System.out.println(path.get(i));
				}

			}
		}

	}

}
