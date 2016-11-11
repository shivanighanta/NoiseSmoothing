
import java.util.Arrays;

public class CSVDataTester {
	public static void main(String[] args){
		String[] columnNames={"time", "gyro-x", "gyro-y", "gyro-z"};
		CSVData test= new CSVData("/Users/shivanighanta/Downloads/HIMU-2016-10-14_10-24-50.txt",columnNames,1 );
		
		System.out.println(Arrays.toString(test.getCol(3)));
	}
}