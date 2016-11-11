
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class NoiseSmoothing {
	
	
	public static void main(String[] args) {
		
	}
	
	
	/***
	 * Returns a new array whose elements are each an average of n adjacent elements of array
	 * @param array the sample signal values (to be averaged)
	 * @return double[] an array, each of whose elements is the average of n adjacent values from array
	 */
	public static double[] generalRunningAverage(double[] array, int n) {
		double[] average = new double[array.length-(n-1)];
		double sumAverage = 0;
		for(int i = 0; i < array.length-(n-1); i++) {
			for(int j = 0; j < n; j++) {
				sumAverage += array[i+j];		
			}
			average[i] = sumAverage/n;
			sumAverage = 0;
		}
		return average;
	}
	
	
}
