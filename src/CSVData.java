//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//
///***
// * 
// * @author shivanighanta
// *
// */
//public class CSVData {
//	private double[][] data;
//	private String[] columnNames;
//	private String filePathToCSV;
//	private int numRows;
//
//	public CSVData(String[] lines, String[] columnNames) {
//		// number of data points
//		this.numRows = lines.length;
//		int numColumns = columnNames.length;
//		// create storage for column names
//		this.columnNames = columnNames;
//		// create storage for data
//		this.data = new double[numRows][numColumns];
//		for (int i = 0; i < lines.length; i++) {
//			String line = lines[i];
//			String[] coords = line.split(",");
//			for (int j = 0; j < numColumns; j++) {
//				if (coords[j].endsWith("#"))
//					coords[j] = coords[j].substring(0, coords[j].length() - 1);
//				double val = Double.parseDouble(coords[j]);
//				data[i][j] = val;
//				System.out.print(val + " ");
//			}
//			System.out.println();
//		}
//	}
//
//	private static String readFileAsString(String filepath) {
//		StringBuilder output = new StringBuilder();
//		try (Scanner scanner = new Scanner(new File(filepath))) {
//			while (scanner.hasNext()) {
//				String line = scanner.nextLine();
//				output.append(line + System.getProperty("line.separator"));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return output.toString();
//	}
//
//	/***
//	 * returns a new CSVData object for a file ignoring the lines at the top It
//	 * uses the first row as the column names. all other data is stored in 2D
//	 * array
//	 * 
//	 * @param filepath
//	 *            name of the CSV file
//	 * @param numLinesIgnore
//	 *            the number of lines to ignore from the top of the file
//	 * @param columnNames
//	 *            the names/descriptions of each column of values
//	 * @return CSVData
//	 */
//	
//
//	public static CSVData readCSVFile(String filepath, int numLinesIgnore, String[] columnNames) {
//		String dataString = readFileAsString(filepath);
//		int i1 = 0;
//		for(int newLine = 0; newLine < numLinesIgnore; i1++)
//			if(dataString.charAt(i1) == '\n') newLine++;
//		dataString = dataString.substring(i1);
//		String[] lines = dataString.split("\n");
//		
//		return new CSVData(lines, columnNames);
//	}
//
//	/***
//	 * returns a new CSVData object for a file ignoring the lines at the top It
//	 * uses the first row as the column names. all other data is stored in 2D
//	 * array
//	 * 
//	 * @param filepath to the CSV file
//	 * @param numLinesIgnore
//	 *            the number of lines to ignore from the top of the file
//	 * @return CSVDATA
//	 */
//	public static CSVData readCSVFile(String filepath, int numLinesIgnore) {
//		String dataString = readFileAsString(filepath);
//		int i1 = 0, i2;
//		for(int newLine = 0; newLine < numLinesIgnore; i1++)
//			if(dataString.charAt(i1) == '\n') newLine++;
//		for(i2 = i1; dataString.charAt(i2) != '\n'; i2++);
//		String[] columnNames = dataString.substring(i1, i2).split(", ");
//		dataString = dataString.substring(i2+1);
//		String[] lines = dataString.split("\n");
//		//display(lines);
//		return new CSVData(lines, columnNames);
//	}
//	
//	public static void display(String[] arr){
//		for(int i = 0; i < arr.length; i++){
//			System.out.println(arr[i]);
//		}
//		System.out.println("done");
//	}
//
//	/***
//	 * returns the individual row out of the array
//	 * 
//	 * @param rowIndex
//	 *            index of the row
//	 * @return the array of the rows
//	 */
//
//	public double[] getRow(int rowIndex) {
//		return null;
//	}
//
//	/***
//	 * returns the individual col out of the array
//	 * 
//	 * @param columnIndex
//	 *            index of the col
//	 * @return returns the array of the columns
//	 */
//
//	public double[] getColumn(int columnIndex) {
//		return null;
//	}
//
//	/***
//	 * returns the individual col out of the array
//	 * 
//	 * @param name
//	 *            the name of the col
//	 * @return returns the name of the columns
//	 */
//
//	public double[] getColumn(String name) {
//		return null;
//	}
//
//	/***
//	 * returns multiple rows out of the data array
//	 * 
//	 * @param startIndex
//	 *            the index you are starting at
//	 * @param endIndex
//	 *            the index you are ending at
//	 * @return returns the array of the rows
//	 */
//
//	public double[][] getRows(int startIndex, int endIndex) {
//		return null;
//	}
//
//	/***
//	 * returns multiple rows out of the data array
//	 * 
//	 * @param rowIndexes
//	 *            the array of the indexes of the row
//	 * @return returns the array of the rows
//	 */
//
//	public double[][] getRows(int[] rowIndexes) {
//		return null;
//	}
//
//	/***
//	 * returns the multiple columns out of the data array
//	 * 
//	 * @param columnIndexes
//	 *            the array of the indexes of the columns
//	 * @return returns the array of the columns
//	 */
//
//	public double[][] getColumns(int[] columnIndexes) {
//		return null;
//	}
//
//	/***
//	 * returns the multiple rows out of the data array
//	 * 
//	 * @param startIndex
//	 *            the index you are starting at
//	 * @param endIndex
//	 *            the index you are ending at
//	 * @return returns the array of the columns
//	 */
//	public double[][] getColumns(int startIndex, int endIndex) {
//		return null;
//	}
//
//	/***
//	 * returns the multiple columns out of the data array
//	 *
//	 * @param colNames
//	 *            the name of the column
//	 * @return returns the array of the columns
//	 */
//
//	public double[][] getColumns(String colNames) {
//		return null;
//	}
//
//	/***
//	 * sets the value of the the element in the array
//	 * 
//	 * @param rowIndex
//	 *            the index of the row
//	 * @param colIndex
//	 *            the index of the column
//	 * @param newValue
//	 *            the value you want to set the element to
//	 */
//	public void setValue(int rowIndex, int colIndex, double newValue) {
//
//	}
//
//	/***
//	 * sets the values of the rows
//	 * 
//	 * @param values
//	 *            the array of the row values
//	 */
//
//	public void setRow(double[] values) {
//
//	}
//
//	/***
//	 * sets the values of the columns
//	 * 
//	 * @param values
//	 *            the array of the col values
//	 */
//
//	public void setColumn(double[] values) {
//
//	}
//
//	/***
//	 * gets the names of the columns
//	 * 
//	 * @return returns the array of the column names
//	 */
//
//	public static String[] getColumnTitles(String line) {
//		return line.split(",");
//	}
//
//	/***
//	 * saves the current state of the object back into a CSV
//	 * 
//	 * @param filename
//	 *            the name of the file
//	 */
//	public void saveToFile(String filename) {
//
//	}
//	
//	public String toString(){
//		return data.toString();
//	}
//}

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class to read/write numerical CSV files and allow easy access to values
 * 
 * @author 
 *
 */
public class CSVData {
	private double[][] data;
	private String[] columnNames;
	private String filePathToCSV;
	private int numRows;

	public CSVData(String filepath, String[] columnNames, int startRow) {
		this.filePathToCSV = filepath;

		String dataString = readFileAsString(filepath);
		String[] lines = dataString.split("\n");

		// number of data points
		int n = lines.length - startRow;
		this.numRows = n;
		int numColumns = columnNames.length;

		// create storage for column names
		this.columnNames = columnNames;

		// create storage for data
		this.data = new double[n][numColumns];
		for (int i = 0; i < lines.length - startRow; i++) {
			String line = lines[startRow + i];
			String[] coords = line.split(",");
			for (int j = 0; j < numColumns; j++) {
				if (coords[j].endsWith("#"))
					coords[j] = coords[j].substring(0, coords[j].length() - 1);
				double val = Double.parseDouble(coords[j]);
				data[i][j] = val;
			}
		}
	}

	private String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	/***
	 * Returns a new CSV Data object for the file, ignoring lines at the top.
	 * Input String array gives the column names.
	 * 
	 * @param filename
	 *            the file to read
	 * @param numLinesToIgnore
	 *            number of lines at the top to ignore
	 * @param columnNames
	 *            names of the columns
	 * @return a CSVData object for that file
	 */
	public static CSVData readCSVFile(String filename, int numLinesToIgnore, String[] columnNames) {
		return new CSVData(filename, columnNames, numLinesToIgnore);
	}

	/***
	 * Returns a new CSV Data object for the file, ignoring lines at the top. It
	 * uses the first row as the column names. All other data is stored as
	 * doubles.
	 * 
	 * @param filename
	 *            the file to read
	 * @param numLinesToIgnore
	 *            number of lines at the top to ignore
	 * @return a CSVData object for that file
	 */
	public static CSVData readCSVFile(String filename, int numLinesToIgnore) {
		return null;
	}

	/**
	 * Returns a 1d array representing a column of the 2D array arr.
	 * 
	 * @param arr
	 *            the 2D array to extract a column from
	 * @param column
	 *            the index of the column to extract
	 * @return a 1D array that's a copy of the row at index column.
	 */
	public double[] getCol(int column) {
		double[] columnValues = new double[this.data.length];
		System.out.println(this.data.length);
		for (int row = 0; row < this.data.length; row++) {
			columnValues[row] = this.data[row][column];
		}
		return columnValues;
	}

	/**
	 * Returns a 1d array representing a row of the 2D array arr.
	 * 
	 * @param arr
	 *            the 2D array to extract a row from
	 * @param row
	 *            the index of the row to extract
	 * @return a 1D array that's a copy of the row at index row.
	 */
	public double[] getRow(int row) {
		double[] rowValues = new double[data[0].length];
		for (int column = 0; column < data[0].length; column++) {
			rowValues[column] = data[row][column];
		}
		return rowValues;
	}

	public double[] getRow(String columnName) {
		int row = this.getIndex(columnName);
		return this.getRow(row);
	}

	/**
	 * Returns index of word in a input String array
	 * 
	 * @param words
	 *            String array to search word from
	 * @param word
	 *            Word that is being searched for
	 * @return index location of word in the String array
	 */
	public int getIndex(String word) {
		for (int i = 0; i < this.columnNames.length; i++) {
			if (word.equals(this.columnNames[i]))
				return i;
		}
		return -1;
	}

	/***
	 * Returns a 2d array representing the rows of arr starting with startRow up
	 * to but not including endRow
	 * 
	 * @param arr
	 *            the array to extract rows from
	 * @param startRow
	 *            index of starting row
	 * @param endRow
	 *            index of ending row (return value does not include this row)
	 * @return a 2D array whose size is (endRow-startRow) by arr[0].length that
	 *         represents the row of arr starting with startRow up to but not
	 *         including endRow
	 */
	public double[][] getRows(int startRow, int endRow) {
		double[][] rows = new double[endRow - startRow][data.length];
		int rowIndex = 0;
		for (int row = startRow; row < endRow; row++) {
			double[] singleRow = this.getRow(row);
			rows = addRowValue(rows, singleRow, rowIndex);
			rowIndex++;
		}
		return rows;
	}

	/**
	 * Returns a 2d array in String
	 * 
	 * @param arr
	 *            the array to convert to String
	 * @return String array
	 */
	// public static ArrayList<String> toString(double[][] arr){
	// ArrayList<String> values=new ArrayList<String>();
	// for(int rowCounter=0; rowCounter<arr.length; rowCounter++){
	// values.add(Arrays.toString(this.getRow(rowCounter)));
	// }
	// return values;
	// }
	/**
	 * Adds a row of values in a 1d double array to 2d double array
	 * 
	 * @param arr
	 *            2d array to add a row of values to
	 * @param values
	 *            1d double array of values to add to 2d array
	 * @param row
	 *            the row index of the 2d array to add the row values to
	 * @return 2d double array with the added row of values
	 */
	public double[][] addRowValue(double[][] arr, double[] values, int row) {
		for (int i = 0; i < values.length; i++) {
			arr[row][i] = values[i];
		}
		return arr;
	}

	/**
	 * Adds a col of values in a 1d double array to 2d double array
	 * 
	 * @param arr
	 *            2d array to add a row of values to
	 * @param values
	 *            1d double array of values to add to 2d array
	 * @param col
	 *            the col index of the 2d array to add the col values to
	 * @return 2d double array with the added col of values
	 */
	public double[][] addColValue(double[][] arr, double[] values, int col) {
		for (int i = 0; i < values.length; i++) {
			arr[col][i] = values[i];
		}
		return arr;
	}

	/**
	 * Return a 2d array representing the cols of arr starting with startCol up
	 * to but not including endCol.
	 * 
	 * @param arr
	 *            the array to extract columns from
	 * @param startCol
	 *            index of starting column
	 * @param endCol
	 *            index of ending column (return value does not include this
	 *            column)
	 * @return a 2d array whose size is arr.length by (endCol-startCol) that
	 *         represents the columns of arr starting with startCol up to but
	 *         not including endCol
	 */
	public double[][] getCols(int startCol, int endCol) {
		double[][] cols = new double[endCol - startCol][this.data.length];
		int colIndex = 0;
		for (int col = startCol; col < endCol; col++) {
			double[] singleCol = this.getCol(col);
			cols = this.addColValue(cols, singleCol, colIndex);
			colIndex++;
		}
		return cols;
	}

	/**
	 * Return a 2d array representing the cols of arr in the column indexes from
	 * the int input array.
	 * 
	 * @param arr
	 *            the array to extract columns from
	 * @param colIndexes
	 *            the column indexes from the int input
	 * @return a 2d array that represents the columns of arr indexes
	 * 
	 */
	public double[][] getCols(int[] colIndexes) {
		double[][] cols = new double[colIndexes.length][this.data.length];
		int colIndex = 0;
		for (int i = 0; i < colIndexes.length; i++) {
			double[] singleCol = this.getCol(colIndexes[i]);
			cols = this.addColValue(cols, singleCol, colIndex);
			colIndex++;
		}
		return cols;
	}

	/**
	 * Return a 2d array representing the cols of arr in the column indexes from
	 * the int input array.
	 * 
	 * @param colNames
	 *            String of column Names to extract column data from
	 * @return a 2d array that represents the columns of String colNames indexes
	 */
	public double[][] getCols(String[] colNames) {
		double[][] cols = new double[colNames.length][this.data.length];
		int colIndex = 0;
		for (int i = 0; i < colNames.length; i++) {
			int index = getIndex(colNames[i]);
			double[] singleCol = this.getCol(index);
			cols = this.addColValue(cols, singleCol, colIndex);
			colIndex++;
		}
		return cols;
	}

	/**
	 * Sets column values in input columnIndex to values given in array vals
	 * 
	 * @param columnIndex
	 *            index where new values should be set
	 * @param vals
	 *            the new values of that column
	 */
	public void setColumn(int columnIndex, double[] vals) {
		if (vals.length == this.data.length) {
			int rowIndex = 0;
			for (int i = 0; i < vals.length; i++) {
				data[rowIndex][columnIndex] = vals[i];
				rowIndex++;
			}
		}
	}

	/**
	 * Sets row values in input rowIndex to values given in array vals
	 * 
	 * @param rowIndex
	 *            index where new values should be set
	 * @param vals
	 *            the new values of that row
	 */
	public void setRow(int rowIndex, double[] vals) {
		if (vals.length == this.data[0].length) {
			int colIndex = 0;
			for (int i = 0; i < vals.length; i++) {
				this.data[rowIndex][colIndex] = vals[i];
				colIndex++;
			}
		}
	}

	/**
	 * Sets value at input row and column index to input value
	 * 
	 * @param rowIndex
	 *            row index where new value is entered
	 * @param columnIndex
	 *            column index where new value is entered
	 * @param value
	 *            value to be set in the row and column indexes
	 */
	public void setValue(int rowIndex, int columnIndex, int value) {
		this.data[rowIndex][columnIndex] = value;
	}

	public int getNumRows() {
		return numRows;
	}

	public String[] getColumnTitles() {
		return this.columnNames;
	}
	/**
	 * Sets data array to the CSVData data
	 * @param arr 2-D array to set CSVData data to
	 * @return new CSVData data 2-D array
	 */
	public double[][] setCSVData(double[][] arr) {
		this.data=arr;
		return this.data;
	}

	public static void saveToFile(String filename) {
		
	}
	/**Changes time to elapsed time
	 * 
	 * @param values CSVData object with sensor data
	 */
	public static void correctTime(CSVData values) {
		double startTime = values.data[0][0];
		
		for (int row = 0; row < values.data.length; row++)
			values.data[row][0] -= startTime;
	}
}
