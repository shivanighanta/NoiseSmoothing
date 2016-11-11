
 import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;


public class StepCounter {
	
	
	public static void main(String[] args) {
		String[] columnNames = { "time", "gyro-x", "gyro-y", "gyro-z" };
		CSVData test = new CSVData("data/35Run.csv", columnNames, 1);
		test.correctTime(test);
		//System.out.println(stepCounter(test.getCol(1), test.getRows(1, test.getNumRows() - 1), 10));
	}

//	public static int countSteps(double[] times, double[][] sensorData, int windowLength) {
//		int stepCount = 0;
//		double[] arr = new double[times.length];
//		arr = calculateMagnitudes(sensorData);
//		// arr = NoiseSmoothing.generalRunningAverage(arr, 3);
//		double[] thresholds = calculateWindow(arr, windowLength);
//		for (int i = 1; i < arr.length; i++) {
//			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
//				if (arr[i] > thresholds[i]) {
//					stepCount++;
//
//				}
//			}
//		}
//		return stepCount;
//	}

//	public static double[] calculateWindow(double[] arr, int windowLength) {
//		double[] result = new double[arr.length];
//		for (int i = 0; i < arr.length; i++) {
//			if (i + windowLength < arr.length) {
//				if (i - windowLength >= 0) {
//					double meanForInterval = calculateMeanInInterval(arr, i - windowLength, i + windowLength);
//					double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval,
//							i - windowLength, i + windowLength);
//					result[i] = (meanForInterval + deviationForInterval);
//				} else {
//					double meanForInterval = calculateMeanInInterval(arr, 0, i + windowLength);
//					double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, 0,
//							i + windowLength);
//					result[i] = (meanForInterval + deviationForInterval);
//				}
//			} else {
//				double meanForInterval = calculateMeanInInterval(arr, i - windowLength, arr.length);
//				double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval,
//						i - windowLength, arr.length);
//				result[i] = (meanForInterval + deviationForInterval);
//			}
//		}
//		return result;
//	}
//
//	public static double[] noiseSmoothing(double[] magnitudes, int averageLength) {
//		double[] result = new double[magnitudes.length - averageLength];
//		result = NoiseSmoothing.generalRunningAverage(magnitudes, averageLength);
//		return result;
//	}
//
//	public static double calculateMagnitude(double x, double y, double z) {
//		return Math.sqrt(x * x + y * y + z * z);
//	}
//
//	private static double[] calculateMagnitudes(double[][] sensorData) {
//		double[] result = new double[sensorData.length];
//		for (int i = 0; i < sensorData.length; i++) {
//			double x = sensorData[i][1];
//			double y = sensorData[i][2];
//			double z = sensorData[i][3];
//			result[i] = calculateMagnitude(x, y, z);
//		}
//		return result;
//	}
//
//	private static double calculateStandardDeviation(double[] arr, double mean) {
//		double sum = 0;
//		for (int i = 0; i < arr.length; i++) {
//			sum += (arr[i] - mean) * (arr[i] - mean);
//		}
//		return Math.sqrt(sum / (arr.length - 1));
//	}
//
//	private static double calculateStandardDeviationInInterval(double[] arr, double mean, int startInterval,
//			int endInterval) {
//		double sum = 0;
//		for (int i = startInterval; i < endInterval; i++) {
//			sum += (arr[i] - mean) * (arr[i] - mean);
//		}
//		return Math.sqrt(sum / (double) (endInterval - startInterval));
//	}
//
//	private static double calculateMean(double[] arr) {
//		double sum = 0;
//		for (int i = 0; i < arr.length; i++) {
//			sum += arr[i];
//		}
//		return sum / arr.length;
//	}
//
//	private static double calculateMeanInInterval(double[] arr, int startIndex, int endIndex) {
//		double sum = 0;
//		for (int i = startIndex; i < endIndex; i++) {
//			sum += arr[i];
//		}
//		return sum / (double) (endIndex - startIndex);
//	}
	
	
}
