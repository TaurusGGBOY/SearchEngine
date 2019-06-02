
/**
 * KNN�㷨������
 * Created by Mistletoe on 2018/05/11 17:13
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNUtils {

	private static double[] originData;

	public void init(double[] outData) {
		this.originData = outData;
	}

	public KNNUtils(double[] outData) {
		init(outData);
	}

	// ����ŷ�Ͼ���
	private double calEulaDistance(double centerPoint, double point) {
		return Math.sqrt((centerPoint - point) * (centerPoint - point));
	}

	// ����һ��������ȼ���Ԫ�صľ�ֵ
	private double calAverage2GetCenterPoint(List<Double> group) {
		int sum = 0;
		for (int i = 0; i < group.size(); i++) {
			sum += group.get(i);
		}
		return sum / group.size();
	}

	// KNN�㷨������
	public Map<Integer, List<Double>> devide(Map<Integer, List<Double>> myMap, double[] centerPoints) {
		int blockNum = myMap.size();
		Map<Integer, List<Double>> newMap = new HashMap<>();
		for (int i = 0; i < blockNum; i++) {
			newMap.put(i, new ArrayList<>());
		}
		boolean loopFlag = true;
		// �����㷨ԭ��:https://blog.csdn.net/zhangphil/article/details/78783629
		do {
			double minNum = Double.MAX_VALUE;
			int keyPosition = -1;
			for (int j = 0; j < originData.length; j++) {

				for (int i = 0; i < blockNum; i++) {
					double ret = calEulaDistance(centerPoints[i], originData[j]);
					if (ret < minNum) {
						keyPosition += 1;
						minNum = ret;
					}
				}
				newMap.get(keyPosition).add(originData[j]);
				if (keyPosition > newMap.size()) {
					keyPosition = -1;
				}
			}
			int equalsNum = 0;
			double[] newCenterPoints = new double[centerPoints.length];
			for (int i = 0; i < blockNum; i++) {
				newCenterPoints[i] = calAverage2GetCenterPoint(newMap.get(i));
			}
			// ��ǰ���ྫ������Ϊ0.01;
			for (int i = 0; i < centerPoints.length; i++) {
				if (Math.abs(newCenterPoints[i] - centerPoints[i]) < 0.01) {
					equalsNum += 1;
				}
			}
			if (equalsNum == centerPoints.length) {
				loopFlag = false;
			} else {
				for (int i = 0; i < centerPoints.length; i++) {
					centerPoints[i] = newCenterPoints[i];
					// ��һ��map���
					newMap.get(i).clear();
				}
			}
		} while (loopFlag);
		return newMap;
	}
}