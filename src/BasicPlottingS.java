
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlottingS {
	private static final int WINDOW_LENGTH = 3;
	private static final int THRESHOLD_LIMIT = 2;
	private static final int MINIMUM_LIMIT = 10;
	private static final int THRESHOLD_MULTIPLE=5;
	public static void main(String[] args) {
		String[] columnNames={"time", "accel-x", "accel-y", "accel-z"};
		CSVData unsmoothed = new CSVData("data/35Run.csv", columnNames, 1);
		double[] unsmoothedMagnitude = calculateMagnitudes(unsmoothed.getRows(1,unsmoothed.getNumRows()-1));
		double[] smoothedMagnitude = smoothArray(unsmoothed.getCol(0),unsmoothed.getRows(1,unsmoothed.getNumRows()-1));
		double[] unsmoothedMagnitudeThresholds = calculateWindow(unsmoothedMagnitude, 10);
		double[] smoothedMagnitudeThresholds = calculateWindow(smoothedMagnitude, 10);
		Plot2DPanel plot = new Plot2DPanel();
		// add a line plot to the PlotPanel
		plot.addLinePlot("Unsmoothed", unsmoothedMagnitude);
		plot.addLinePlot("Unsmoothed Threshold", unsmoothedMagnitudeThresholds);
		plot.addLinePlot("Unsmoothed Threshold", smoothedMagnitudeThresholds);
		
		//System.out.println(Arrays.toString(unsmoothedMagnitude)); //blue
		//System.out.println(Arrays.toString(unsmoothedMagnitudeThresholds)); // red
		//System.out.println(Arrays.toString(smoothedMagnitudeThresholds)); //green
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	private static double[] smoothArray(double[] times, double[][] sensorData) {
		double[] arr = new double[times.length];
		arr = calculateMagnitudes(sensorData);
		arr = NoiseSmoothing.generalRunningAverage(arr, WINDOW_LENGTH);
		return arr;
	}
	
	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	private static double[] calculateMagnitudes(double[][] sensorData) {
		double[] result = new double[sensorData.length];
		for(int i = 0; i < sensorData.length; i++) {
			double x = sensorData[i][1];
			double y = sensorData[i][2];
			double z = sensorData[i][3];
			result[i] = calculateMagnitude(x,y,z);
		}
		return result;
	}
	/***
	 * Returns array with threshold values
	 * @param arr
	 * @param windowLength
	 * @return
	 */
	public static double[] calculateWindow(double[] arr, int windowLength) {
		double[] result=new double[arr.length];
		for (int i = 0; i < arr.length; i++){
			if(i+windowLength < arr.length) {
				if(i-windowLength >= 0) {
					double meanForInterval = calculateMeanInInterval(arr, i-windowLength, i+windowLength);
					double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, i-windowLength, i+windowLength);
					if(belowThreshold(arr, WINDOW_LENGTH, i, MINIMUM_LIMIT)){
						result[i]=((meanForInterval+deviationForInterval))*THRESHOLD_MULTIPLE;
					}else{
						result[i] = (meanForInterval+deviationForInterval);
					}
				} else {
					double meanForInterval = calculateMeanInInterval(arr, 0, i+windowLength);
					double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, 0, i+windowLength);
					if(belowThreshold(arr, WINDOW_LENGTH, i+WINDOW_LENGTH, MINIMUM_LIMIT)){
						result[i]=((meanForInterval+deviationForInterval))*THRESHOLD_MULTIPLE;
					}else{
						result[i] = (meanForInterval+deviationForInterval);
					}
				}
			} else {
				double meanForInterval = calculateMeanInInterval(arr, i-windowLength, arr.length);
				double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, i-windowLength, arr.length);
				if(belowThreshold(arr, WINDOW_LENGTH, i-WINDOW_LENGTH, MINIMUM_LIMIT)){
					result[i]=((meanForInterval+deviationForInterval))*THRESHOLD_MULTIPLE;
				}else{
					result[i] = (meanForInterval+deviationForInterval);
				}
			}
		}
		return result;
	}
	
	private static double calculateStandardDeviationInInterval(double[] arr, double mean, int startInterval, int endInterval) {
		double sum = 0;
		for (int i = startInterval; i < endInterval; i++) {
			sum += (arr[i] - mean)*(arr[i] - mean);
		}
		return Math.sqrt(sum/(double)(endInterval-startInterval));
	}
	
	private static double calculateMeanInInterval(double[] arr, int startIndex, int endIndex) {
		double sum = 0;
		for(int i = startIndex; i < endIndex; i++) {
			sum += arr[i];
		}
		return sum/(double)(endIndex-startIndex);
	}
	
	private static boolean belowThreshold(double[] arr, int windowLength, int index, int threshold) {
		int underThresholdCounter = 0;
		int totalIterations = 0;
		for(int i = index-windowLength; i < index+windowLength; i++) {
			if(arr[i] < threshold) {
				underThresholdCounter++;
			}
			totalIterations++;
		}
		if((double)underThresholdCounter/(double)totalIterations > 0.8) return true;
		return false;
	}
}