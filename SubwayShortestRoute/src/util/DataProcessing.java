package util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.*;
import java.util.List;

import model.BeanStation;

public class DataProcessing {
	// LinkedHashSet�̳�HashSet��ͨ��һ��˫������ά������˳��
	// ������·���ϣ����е�ÿ��Ԫ�ض���һ����·
	public static LinkedHashSet<List<BeanStation>> lineSet = new LinkedHashSet<List<BeanStation>>();

	public DataProcessing() throws IOException {
		String pathname = "data.txt";// ������·��Ϣ�ĵ�ַ
		File filename = new File(pathname); // ������·�����ַ���ת���ɳ���·����������һ���µ�Fileʵ��
		// ��ȡ�ļ���FileInputStream���ֽ���ʽ�����ļ�
		// InputStreamReader��������ֽ�ת��Ϊ�ַ�
		// BufferedReader���ַ��������ж�ȡ�ı��������ַ����Ա���Ч�ض�ȡ�ַ����������
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

		int linenum = Integer.parseInt(br.readLine());// ��ȡһ���ж�������·����������ַ���ת��Ϊint����
		for (int i = 0; i < linenum; i++) {// ѭ��������·��Ϣ
			String content = br.readLine();
			List<BeanStation> line = new ArrayList<BeanStation>();
			// ���� ���ո�ָ��������ݣ��ָ����·����֮���һ��վ��
			String[] word = content.split(" ");
			String linename = word[0];
			int length = word.length;

			for (int j = 1; j < length; j++) {// ѭ�����վ��
				BeanStation newStation = new BeanStation();
				int changeable = 0;
				// �������������·��Ϣ���жϸ�վ�Ƿ�Ϊ����վ
				for (List<BeanStation> l : lineSet) {
					int size = l.size();
					for (int k = 0; k < size; k++) {// ������·�е�վ��
						// �ж�վ���Ƿ���ͬ
						if (l.get(k).getStationName().equals(word[j])) {
							// վ���ظ�������·����ӵ���վ�������վ�㣨List<String> line����
							l.get(k).addLine(linename);
							newStation = l.get(k);
							changeable = 1;
							break;
						}
					}
					if (changeable == 1)
						break;
				}
				if (changeable == 0) {// ������ǻ���վ
					newStation.setStationName(word[j]);
					List<String> thisline = new ArrayList<String>();
					thisline.add(linename);
					newStation.setLine(thisline);
				}
				// �ж��Ƿ�Ϊ���ߣ����ߵĵ�һվ��վ�����������·�����
				if (j == length - 1 && word[j].equals(word[1])) {
					// �����һվ��ӵ���һվ������վ�㣬����һվ��ӵ����һվ������վ��
					line.get(0).addLinkStations(line.get(line.size() - 1));
					line.get(line.size() - 1).addLinkStations(line.get(0));
				} else {
					line.add(newStation);// ����վ����ӵ���·��
				}
			}

			for (int j = 0; j < line.size(); j++) { // ����ÿ����վ���ڵĳ�վ
				// ����ǻ���վ�����ԭ�е�����վ����Ϣ
				List<BeanStation> newlinkStations = line.get(j).getLinkStations();
				if (j != 0) {
					newlinkStations.add(line.get(j - 1));
				}
				if (j != line.size() - 1) {
					newlinkStations.add(line.get(j + 1));
				}
				line.get(j).setLinkStations(newlinkStations);
			}
			lineSet.add(line);
		}
		br.close();
	}
}
