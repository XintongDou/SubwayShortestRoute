package util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.*;
import java.util.List;

import model.BeanStation;

public class DataProcessing {
	// LinkedHashSet继承HashSet，通过一个双向链表维护插入顺序
	// 储存线路集合，其中的每个元素都是一条线路
	public static LinkedHashSet<List<BeanStation>> lineSet = new LinkedHashSet<List<BeanStation>>();

	public DataProcessing() throws IOException {
		String pathname = "data.txt";// 地铁线路信息的地址
		File filename = new File(pathname); // 将给定路径名字符串转换成抽象路径名来创建一个新的File实例
		// 读取文件：FileInputStream以字节形式读入文件
		// InputStreamReader将读入的字节转化为字符
		// BufferedReader从字符输入流中读取文本并缓冲字符，以便有效地读取字符，数组和行
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

		int linenum = Integer.parseInt(br.readLine());// 读取一共有多少条线路，将读入的字符串转换为int类型
		for (int i = 0; i < linenum; i++) {// 循环读入线路信息
			String content = br.readLine();
			List<BeanStation> line = new ArrayList<BeanStation>();
			// 按“ ”空格分割读入的内容，分割出线路名和之后的一串站名
			String[] word = content.split(" ");
			String linename = word[0];
			int length = word.length;

			for (int j = 1; j < length; j++) {// 循环添加站点
				BeanStation newStation = new BeanStation();
				int changeable = 0;
				// 遍历已输入的线路信息，判断该站是否为换乘站
				for (List<BeanStation> l : lineSet) {
					int size = l.size();
					for (int k = 0; k < size; k++) {// 遍历线路中的站点
						// 判断站名是否相同
						if (l.get(k).getStationName().equals(word[j])) {
							// 站名重复，将线路名添加到该站点的所在站点（List<String> line）中
							l.get(k).addLine(linename);
							newStation = l.get(k);
							changeable = 1;
							break;
						}
					}
					if (changeable == 1)
						break;
				}
				if (changeable == 0) {// 如果不是换乘站
					newStation.setStationName(word[j]);
					List<String> thisline = new ArrayList<String>();
					thisline.add(linename);
					newStation.setLine(thisline);
				}
				// 判断是否为环线，环线的第一站的站名会出现在线路的最后
				if (j == length - 1 && word[j].equals(word[1])) {
					// 将最后一站添加到第一站的相邻站点，将第一站添加到最后一站的相邻站点
					line.get(0).addLinkStations(line.get(line.size() - 1));
					line.get(line.size() - 1).addLinkStations(line.get(0));
				} else {
					line.add(newStation);// 将该站点添加到线路中
				}
			}

			for (int j = 0; j < line.size(); j++) { // 处理每个车站相邻的车站
				// 如果是换乘站则存在原有的相邻站点信息
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
