
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting  {
	private static final int INTERVAL_LENGTH = 10; // for noise-smoothing
	private static final int WINDOW_LENGTH = 10;

	public static void main(String[] args) {
		String[] columnNames = { "time (ms)", "accel-x", "accel-y", "accel-z" };
		CSVData raw = new CSVData("data/StepStair2.csv", columnNames, 1);
		double[][] sensorData = raw.getRows(1, raw.getNumRows() - 1);
		double[] rawData = getMagnitudes(sensorData);
		double[] smoothedData = addNoiseSmoothing(raw.getCol(0), raw.getRows(1, raw.getNumRows() - 1));
		double[] adaptiveThresholdData = getAdaptiveThreshold2(rawData, WINDOW_LENGTH);
		double[] smoothedAdaptiveThresholdData = getAdaptiveThreshold2(smoothedData, WINDOW_LENGTH);
		double[] staticThreshold = getStaticThreshold(rawData);
		Plot2DPanel plot = new Plot2DPanel();
		// add a line plot to the PlotPanel
		plot.addLinePlot("Raw Data", rawData); // blue
		plot.addLinePlot("Smoothed Data", smoothedData); // red
		plot.addLinePlot("Adaptive Threshold", adaptiveThresholdData); // green
		plot.addLinePlot("Smoothed Threshold", smoothedAdaptiveThresholdData); // yellow
		plot.addLinePlot("Static Threshold", staticThreshold ); // orange

		System.out.println("Revised: " + stepCounterRevised(raw.getCol(1), raw.getRows(1, raw.getNumRows() - 1), 10));
		System.out.println("Original: " + stepCounter(raw.getCol(1), raw.getRows(1, raw.getNumRows() - 1)));
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
	
	public static double[] getStaticThreshold(double[] arr){
		double[] thresholds = new double[arr.length];
		double mean = getMean(arr);
		double standardDeviation = getStandardDeviation(arr, mean);
		double threshold = mean + standardDeviation;
		for (int i = 0; i < arr.length; i ++){
			thresholds[i] = threshold;
		}
		return thresholds;
		
	}

	public static int stepCounterRevised(double[] arr, double[][] sensorData, int windowLength) {
		int stepCount = 0;
		arr = getMagnitudes(sensorData);
		arr = addNoiseSmoothing(arr, sensorData);
		double[] thresholds = getAdaptiveThreshold(arr, windowLength);
		for (int i = 1; i < thresholds.length - 1; i++) {
			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1] && arr[i] > thresholds[i]) {

				stepCount++;

			}
		}
		return stepCount;
	}
	

	public static int stepCounter(double[] arr, double[][] sensorData) {
		int stepCount = 0;
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
				if (arr[i] > getMean(arr) + getStandardDeviation(arr, getMean(arr))) {
					stepCount++;
				}
			}
		}
		return stepCount;

	}

	private static double getMean(double[] arr) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		double mean = (double) (sum / arr.length);
		return mean;
	}

	private static double getStandardDeviation(double[] arr, double mean) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += (arr[i] - mean) * (arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(sum / (arr.length - 1));
		return standardDeviation;
	}

	public static double[] addNoiseSmoothing(double[] times, double[][] sensorData) {
		double[] arr = new double[times.length];
		arr = getMagnitudes(sensorData);
		arr = NoiseSmoothing.generalRunningAverage(arr, INTERVAL_LENGTH);
		return arr;
	}

	public static double getMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double[] getMagnitudes(double[][] sensorData) {
		double[] magnitudes = new double[sensorData.length];
		for (int i = 0; i < sensorData.length; i++) {

			magnitudes[i] = getMagnitude(sensorData[i][1], sensorData[i][2], sensorData[i][3]);
		}
		return magnitudes;
	}

	public static double getThresholdForWithinIntervalOfLeftAndRightBoundary(int index, int windowLength, double[] arr, double[] thresholds) {
		double meanForWindow = getMeanForAdaptiveThreshold(index - windowLength, index + windowLength, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
				index - windowLength, index + windowLength);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	public static double getThresholdsForCloseToLeftInterval(int index, int windowLength, double[] arr, double[] thresholds) {
		double meanForWindow = getMeanForAdaptiveThreshold(0, index + windowLength, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow, 0,
				index + windowLength);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	public static double getThresholdsForCloseToRightInterval(int index, int windowLength, double[] arr, double[] thresholds) {

		double meanForWindow = getMeanForAdaptiveThreshold(index - windowLength, arr.length, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
				index - windowLength, arr.length);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	public static double[] getAdaptiveThreshold2(double[] arr, int windowLength) {
		// calculate mean and standard deviation for each point within a window
		// of certain length
		// use these values to calculate a threshold
		// pay attention to three cases:
		// -when the point is within the window number of points of the left and
		// right interval
		// -when the point is within window number points of the right interval
		// but less than window number
		// of points from the left interval
		// -when the point is less than window number of points away from the
		// right interval
		double[] thresholds = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (i + windowLength < arr.length) {
				if (i - windowLength >= 0) {
					thresholds[i] = getThresholdForWithinIntervalOfLeftAndRightBoundary(i, windowLength, arr, thresholds);
				} else {
					thresholds[i] = getThresholdsForCloseToLeftInterval(i, windowLength, arr, thresholds);
				}
			} else {
				thresholds[i] = getThresholdsForCloseToRightInterval(i, windowLength, arr, thresholds);
			}

		}
		return thresholds;
	}

	public static double[] getAdaptiveThreshold(double[] arr, int windowLength) {
		// calculate mean and standard deviation for each point within a window
		// of certain length
		// use these values to calculate a threshold
		// pay attention to three cases:
		// -when the point is within the window number of points of the left and
		// right interval
		// -when the point is within window number points of the right interval
		// but less than window number
		// of points from the left interval
		// -when the point is less than window number of points away from the
		// right interval
		double meanForWindow;
		double standardDeviationForWindow;
		double[] thresholds = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (i + windowLength < arr.length) {
				if (i - windowLength >= 0) {
					meanForWindow = getMeanForAdaptiveThreshold(i - windowLength, i + windowLength, arr);
					standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
							i - windowLength, i + windowLength);
					thresholds[i] = (meanForWindow + standardDeviationForWindow);
				} else {
					meanForWindow = getMeanForAdaptiveThreshold(0, i + windowLength, arr);
					standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow, 0,
							i + windowLength);
					thresholds[i] = (meanForWindow + standardDeviationForWindow);
				}
			} else {
				meanForWindow = getMeanForAdaptiveThreshold(i - windowLength, arr.length, arr);
				standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
						i - windowLength, arr.length);
				thresholds[i] = (meanForWindow + standardDeviationForWindow);
			}

		}
		return thresholds;
	}

	public static double getStandardDeviationForAdaptiveThreshold(double[] arr, double mean, int startInterval,
			int endInterval) {
		double sum = 0;
		for (int i = startInterval; i < endInterval; i++) {
			sum += (arr[i] - mean) * (arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(sum / (double) (endInterval - startInterval));
		return standardDeviation;
	}

	public static double getMeanForAdaptiveThreshold(int startIndex, int endIndex, double[] arr) {
		double sum = 0;
		for (int i = startIndex; i < endIndex; i++) {
			sum += arr[i];

		}
		double mean = sum / (double) (endIndex - startIndex);
		return mean;
	}

}
