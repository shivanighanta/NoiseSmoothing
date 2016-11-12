
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	private static final int INTERVAL_LENGTH = 10; // for noise-smoothing
	private static final int WINDOW_LENGTH = 10;

	public static void main(String[] args) {
		String[] columnNames = { "time (ms)", "accel-x", "accel-y", "accel-z" };
		CSVData raw = new CSVData("data/26Stair.csv", columnNames, 1);
		double[][] sensorData = raw.getRows(1, raw.getNumRows() - 1);
		double[] rawData = getMagnitudes(sensorData);
		double[] smoothedData = addNoiseSmoothing(raw.getCol(0), raw.getRows(1, raw.getNumRows() - 1));
		double[] adaptiveThresholdData = getAdaptiveThreshold(rawData, WINDOW_LENGTH);
		double[] smoothedAdaptiveThresholdData = getAdaptiveThreshold(smoothedData, WINDOW_LENGTH);
		double[] staticThreshold = getStaticThreshold(rawData);
		Plot2DPanel plot = new Plot2DPanel();
		// add a line plot to the PlotPanel
		plot.addLinePlot("Raw Data", rawData); // blue
		plot.addLinePlot("Smoothed Data", smoothedData); // red
		plot.addLinePlot("Static Threshold", staticThreshold); // green
		plot.addLinePlot("Adaptive Threshold", adaptiveThresholdData); // yellow
		plot.addLinePlot("Smoothed Threshold", smoothedAdaptiveThresholdData); // orange

		System.out.println("Revised: " + stepCounterRevised(raw.getCol(1), raw.getRows(1, raw.getNumRows() - 1), 10));
		System.out.println("Original: " + stepCounter(raw.getCol(1), raw.getRows(1, raw.getNumRows() - 1)));

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	public static double[] getStaticThreshold(double[] arr) {
		double[] thresholds = new double[arr.length];
		double mean = getMean(arr);
		double standardDeviation = getStandardDeviation(arr, mean);
		double threshold = mean + standardDeviation;
		for (int i = 0; i < arr.length; i++) {
			thresholds[i] = threshold;
		}
		return thresholds;

	}

	/***
	 * Counts the number of steps with an adaptive threshold after noise
	 * smoothing the data A step is counted if the resultant of all 3 components
	 * of acceleration from sensorData is above the adaptive threshold for that
	 * point and its magnitude is greater than its two adjacent points
	 * 
	 * @param arr
	 *            array of times corresponding to each resultant magnitude from
	 *            the three acceleration components (x,y,z) from sensorData
	 * @param sensorData
	 *            2D array of sensor data. Includes times in ms and the three
	 *            acceleration components
	 * @param windowLength
	 *            length of window the thresholds are calculated from
	 * @return number of steps counted from the sensor data
	 */
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

	/***
	 * Counts the number of steps from a constant threshold
	 * 
	 * @param arr
	 *            array of times corresponding to each resultant magnitude from
	 *            the 3 components of acceleration from sensorData
	 * @param sensorData
	 *            2-D array of sensor data including times in ms and the three
	 *            acceleration components
	 * @return number of steps counted from sensorData
	 */
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

	/**
	 * Returns average from 1D array of values
	 * 
	 * @param arr
	 *            1D array of values
	 * @return mean of 1D array
	 */
	private static double getMean(double[] arr) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		double mean = (double) (sum / arr.length);
		return mean;
	}

	/**
	 * Returns standard deviation of a 1D array of values
	 * 
	 * @param arr
	 *            1D array of values to calculate standard deviation from
	 * @param mean
	 *            average of the 1D array
	 * @return standard deviation of arr
	 */
	private static double getStandardDeviation(double[] arr, double mean) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += (arr[i] - mean) * (arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(sum / (arr.length - 1));
		return standardDeviation;
	}

	/***
	 * Returns the array that is the average of INTERVAL_LENGTH points which
	 * noise smooths the data
	 * 
	 * @param times
	 *            array of times corresponding to each resultant magnitude from
	 *            the 3 components of acceleration from sensorData
	 * @param sensorData
	 *            2-D array of sensor data including times(ms) and the three
	 *            acceleration components (x,y,z)
	 * @return array of the average of INTERVAL_LENGTH points
	 */
	public static double[] addNoiseSmoothing(double[] times, double[][] sensorData) {
		double[] arr = new double[times.length];
		arr = getMagnitudes(sensorData);
		arr = NoiseSmoothing.generalRunningAverage(arr, INTERVAL_LENGTH);
		return arr;
	}

	/**
	 * Returns the magnitude of the resultant vector of three input magnitudes
	 * of vectors
	 * 
	 * @param x
	 *            magnitude of the x-component vector
	 * @param y
	 *            magnitude of the y-component vector
	 * @param z
	 *            magnitude of the z-component vector
	 * @return magnitude of the resultant vector from the x, y, and z vectors
	 */
	public static double getMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns a 1D array with magnitudes of the resultant vectors of the x,y,
	 * and z vectors for multiple rows Calculates the magnitude of each row of
	 * the x,y,and z vector components for multiple rows of sensorData
	 * 
	 * @param sensorData
	 *            2D array of sensor data. Includes times in ms and the x, y,
	 *            and z vector components
	 * @return 1D array with magnitudes of the resultant vectors of the x,y,and
	 *         z vectors for multiple rows
	 */
	public static double[] getMagnitudes(double[][] sensorData) {
		double[] magnitudes = new double[sensorData.length];
		for (int i = 0; i < sensorData.length; i++) {

			magnitudes[i] = getMagnitude(sensorData[i][1], sensorData[i][2], sensorData[i][3]);
		}
		return magnitudes;
	}

	/**
	 * Returns double of a threshold value
	 * 
	 * @param index
	 *            index to set the threshold value from
	 * @param windowLength
	 *            the number of datq points away from each arr value to
	 *            calculate threshold from
	 * @param arr
	 *            array of acceleration magnitudes thresholds are calculated
	 *            from
	 * @return double of threshold value
	 */

	public static double getThresholdForWithinIntervalOfLeftAndRightBoundary(int index, int windowLength, double[] arr,
			double[] thresholds) {
		double meanForWindow = getMeanForAdaptiveThreshold(index - windowLength, index + windowLength, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
				index - windowLength, index + windowLength);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	/**
	 * Returns a double threshold value when the point is within window number
	 * points of the right interval but less than window number of points from
	 * the left interval
	 * 
	 * @param index
	 *            index to set the threshold value from
	 * @param windowLength
	 *            the number of datq points away from each arr value to
	 *            calculate threshold from
	 * @param arr
	 *            array of acceleration magnitudes thresholds are calculated
	 *            from
	 * @return double threshold value
	 */

	public static double getThresholdsForCloseToLeftInterval(int index, int windowLength, double[] arr,
			double[] thresholds) {
		double meanForWindow = getMeanForAdaptiveThreshold(0, index + windowLength, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow, 0,
				index + windowLength);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	/**
	 * Returns a double threshold value when the point is within window number
	 * points of the left interval but less than window number of points from
	 * the right interval
	 * 
	 * @param index
	 *            index to set the threshold value from
	 * @param windowLength
	 *            the number of datq points away from each arr value to
	 *            calculate threshold from
	 * @param arr
	 *            array of acceleration magnitudes thresholds are calculated
	 *            from
	 * @return double threshold value
	 */
	public static double getThresholdsForCloseToRightInterval(int index, int windowLength, double[] arr,
			double[] thresholds) {

		double meanForWindow = getMeanForAdaptiveThreshold(index - windowLength, arr.length, arr);
		double standardDeviationForWindow = getStandardDeviationForAdaptiveThreshold(arr, meanForWindow,
				index - windowLength, arr.length);
		thresholds[index] = (meanForWindow + standardDeviationForWindow);
		return thresholds[index];
	}

	/**
	 * Returns array of threshold values for input window length from each value
	 * of arr.
	 * 
	 * @param arr
	 *            array of acceleration magnitudes that thresholds are
	 *            calculated from
	 * @param windowLength
	 *            the number of datq points away from each arr value to
	 *            calculate threshold from
	 * @return array of threshold values
	 */

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
		double[] thresholds = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (i + windowLength < arr.length) {
				if (i - windowLength >= 0) {
					thresholds[i] = getThresholdForWithinIntervalOfLeftAndRightBoundary(i, windowLength, arr,
							thresholds);
				} else {
					thresholds[i] = getThresholdsForCloseToLeftInterval(i, windowLength, arr, thresholds);
				}
			} else {
				thresholds[i] = getThresholdsForCloseToRightInterval(i, windowLength, arr, thresholds);
			}

		}
		return thresholds;
	}

	/**
	 * Returns standard deviation of a 1D array in a range of points
	 * 
	 * @param arr
	 *            1D array of values to calculate the standard deviation from
	 * @param mean
	 *            mean of the 1D array
	 * @param startInterval
	 *            starting index of interval
	 * @param endInterval
	 *            ending index of interval
	 * @return standard deviation of in the interval
	 */
	public static double getStandardDeviationForAdaptiveThreshold(double[] arr, double mean, int startInterval,
			int endInterval) {
		double sum = 0;
		for (int i = startInterval; i < endInterval; i++) {
			sum += (arr[i] - mean) * (arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(sum / (double) (endInterval - startInterval));
		return standardDeviation;
	}

	/**
	 * Returns average of 1D array in a range of points
	 * 
	 * @param startIndex
	 *            starting index of interval
	 * @param endIndex
	 *            ending index of interval * @param arr 1D array of values
	 * @return mean of 1D array
	 */

	public static double getMeanForAdaptiveThreshold(int startIndex, int endIndex, double[] arr) {
		double sum = 0;
		for (int i = startIndex; i < endIndex; i++) {
			sum += arr[i];

		}
		double mean = sum / (double) (endIndex - startIndex);
		return mean;
	}

}
