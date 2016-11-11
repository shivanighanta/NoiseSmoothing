
public class StepCounterB {


public static void main(String[] args) {

String[] columnNames={"time", "gyro-x", "gyro-y", "gyro-z"};

CSVData test = new CSVData("data/GyroTest2.csv", columnNames, 1);

System.out.println(countsteps(test.getRows(1,test.getNumRows()-1)));

//System.out.println(countSteps(CSVData.getCol(1),test.getRows(1,test.getNumRows()-1), 10));





}

public static int countsteps(double[][] sensorData) {

int stepCount = 0;

double[] arr = calculateMagnitudesFor(sensorData);

double Mean = calculateMean(arr);

double SD = calculateStandardDeviation(arr, Mean);





for (int i = 1; i < arr.length - 1; i++) {

if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {

if(arr[i] > Mean + SD) {

stepCount++;

}

}

}



return stepCount;



}




public static int countstepsThreshold(double[][] sensorData) {

int stepCount = 0;

double[] arr = calculateMagnitudesFor(sensorData);

double[] MeanThreshold = calculateMeanThreshold(arr);

double[] SDThreshold = calculateStandardDeviationThreshold(arr);



int j=0;




for (int i = 1; i < arr.length - 1; i++) {

if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {

if(arr[i] > MeanThreshold[j] + SDThreshold[j]) {

stepCount++;

}

j++;

}

}



return stepCount;



}


private static double calculateMagnitude(double x, double y, double z) {



return (Math.sqrt(x * x + y * y + z * z));



}





private static double[] calculateMagnitudesFor(double[][] sensorData) {


double[] arr = new double[sensorData.length];

for (int i = 0; i < sensorData.length; i++) {

arr[i] = calculateMagnitude(sensorData[i][0], sensorData[i][1], sensorData[i][2]);

}

return arr;

}



private static double calculateMean(double[] arr) {

double sum = 0;

for (int i = 0; i < arr.length; i++) {

sum += arr[i];

}



return sum / arr.length;

}

private static double[] calculateMeanThreshold(double[] arr) {

double[] calc = new double[(int) Math.ceil(arr.length/3)];

int j=0;

for (int i = 0; i < arr.length; i=i+3) {

//System.out.println(arr.length);

calc[j]=(((arr[i])+(arr[i+1])+(arr[i+2]))/3);

j++;

}



return calc;

}



private static double calculateStandardDeviation(double[] arr, double mean) {

double sum = 0;

for (int i = 0; i < arr.length; i++) {

sum += (mean - arr[i]) * (mean - arr[i]);

}

sum = sum / (arr.length - 1);

sum = Math.sqrt(sum);

return sum;

}

private static double[] calculateStandardDeviationThreshold(double[] arr) {

double[] arrayMean = new double[arr.length/3];

arrayMean = calculateMeanThreshold(arr);

double[] arraySD = new double[arr.length/3];

int j=0;


for (int i = 0; i < arr.length; i = i+3) {

double a = arr[i];

double b = arr[i+1];

double c = arr[i+2];


double m = arrayMean[j];


double summed = (m-a)*(m-b)*(m-c)*(m-a)*(m-b)*(m-c);

double divided = summed/2.0;


double rooted = Math.pow(divided, 0.5);


arraySD[j] = rooted;


j++;

}



return arraySD;

}

}

//	public static int countSteps(double[][] sensorData) {

//	int stepCount = 0;

//	double[] arr = calculateMagnitudes(sensorData);

//	double Mean = calculateMean(arr);

//	double SD = calculateStandardDeviation(arr, Mean);

//

//

//

//	for (int i = 1; i < arr.length - 1; i++) {

//	if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {

//	if(arr[i] > Mean + SD) {

//	stepCount++;

//	System.out.println(stepCount + " " + times[i]/1000);

//	}

//	}

//	}

//

//	return stepCount;

//

//	}

//	private static int countSteps(double[] times, double[][] sensorData, int windowLength) {

//	int stepCount = 0;

//	double[] arr = new double[times.length];

//	arr = calculateMagnitudes(sensorData);

//	//arr = NoiseSmoothing.generalRunningAverage(arr, 3);

//	double[] thresholds = calculateWindow(arr, windowLength);

//	for(int i = 1; i < arr.length; i++) {

//	if (arr[i] > arr[i-1] && arr[i] > arr[i+1]) {

//	if(arr[i] > thresholds[i]) {

//	stepCount++;

//	System.out.println(stepCount +" " + times[i]/1000);

//	}

//	}

//	}

//	return stepCount;

//	}

//

//	public static double calculateMagnitude(double x, double y, double z) {

//	return Math.sqrt(x*x + y*y + z*z);

//	}

//

//	private static double[] calculateMagnitudes(double[][] sensorData) {

//	double[] result = new double[sensorData.length];

//	for(int i = 0; i < sensorData.length; i++) {

//	double x = sensorData[i][1];

//	double y = sensorData[i][2];

//	double z = sensorData[i][3];

//	result[i] = calculateMagnitude(x,y,z);

//	}

//	return result;

//	}

//

//	public static double[] calculateWindow(double[] arr, int windowLength) {

//	double[] result=new double[arr.length];

//	for (int i = 0; i < arr.length; i++){

//	if(i+windowLength < arr.length) {

//	if(i-windowLength >= 0) {

//	double meanForInterval = calculateMeanInInterval(arr, i-windowLength, i+windowLength);

//	double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, i-windowLength, i+windowLength);

//	result[i] = (meanForInterval+deviationForInterval);

//	} else {

//	double meanForInterval = calculateMeanInInterval(arr, 0, i+windowLength);

//	double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, 0, i+windowLength);

//	result[i] = (meanForInterval+deviationForInterval);

//	}

//	} else {

//	double meanForInterval = calculateMeanInInterval(arr, i-windowLength, arr.length);

//	double deviationForInterval = calculateStandardDeviationInInterval(arr, meanForInterval, i-windowLength, arr.length);

//	result[i] = (meanForInterval+deviationForInterval);

//	}

//	}

//	return result;

//	}

//

//	private static double calculateStandardDeviationInInterval(double[] arr, double mean, int startInterval, int endInterval) {

//	double sum = 0;

//	for (int i = startInterval; i < endInterval; i++) {

//	sum += (arr[i] - mean)*(arr[i] - mean);

//	}

//	return Math.sqrt(sum/(double)(endInterval-startInterval));

//	}

//

//	private static double calculateMeanInInterval(double[] arr, int startIndex, int endIndex) {

//	double sum = 0;

//	for(int i = startIndex; i < endIndex; i++) {

//	sum += arr[i];

//	}

//	return sum/(double)(endIndex-startIndex);

//	}

//}
}
