
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class to read and write numerical CSV files and give access to values
 * 
 * @author shivanighanta
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
		int n = lines.length - startRow;
		this.numRows = n;
		int numColumns = columnNames.length;
		this.columnNames = columnNames;
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
	 * Returns a new CSV Data object for the file and ignores lines at the top.
	 * Input String array gives the column names.
	 * 
	 * @param filename
	 *            the file to read
	 * @param numLinesToIgnore
	 *            number of lines to ignore
	 * @param columnNames
	 *            names of the columns
	 * @return a CSVData object for the file
	 */
	public static CSVData readCSVFile(String filename, int numLinesToIgnore, String[] columnNames) {
		return new CSVData(filename, columnNames, numLinesToIgnore);
	}

	/***
	 * Returns a new CSV Data object for the file and ignores lines at the top.
	 * It uses the first row as the column names.
	 * 
	 * @param filename
	 *            the file to read
	 * @param numLinesToIgnore
	 *            number of lines at the top to ignore
	 * @return a CSVData object
	 */
	public static CSVData readCSVFile(String filename, int numLinesToIgnore) {
		return null;
	}

	/**
	 * Returns an array a column of arr.
	 * 
	 * @param arr
	 *            the 2D array to get a column from
	 * @param column
	 *            the index of the column to get
	 * @return a 1D array that is a copy of the row at index column.
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
	 * Returns an array-a row of the 2D array arr.
	 * 
	 * @param arr
	 *            the 2D array to get a row from
	 * @param row
	 *            the index of the row to get
	 * @return an array that is the same as the row at index row.
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
	 * Returns index of word in input String array
	 * 
	 * @param words
	 *            String array to get word from
	 * @param word
	 *            Word that is being searched for
	 * @return index location of the word in the array
	 */
	public int getIndex(String word) {
		for (int i = 0; i < this.columnNames.length; i++) {
			if (word.equals(this.columnNames[i]))
				return i;
		}
		return -1;
	}

	/***
	 * Returns a 2d array which is the rows of arr starting with startRow up to
	 * endRow
	 * 
	 * @param arr
	 *            the array to get rows from
	 * @param startRow
	 *            index of starting row
	 * @param endRow
	 *            index of ending row
	 * @return a 2D array whose size is endRow-startRow by arr[0].length that is
	 *         the row of arr starting with startRow up to endRow
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
	 * Adds a row of values in a 1D double array to 2D double array
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
	 * @return 2d double array with the col of values
	 */
	public double[][] addColValue(double[][] arr, double[] values, int col) {
		for (int i = 0; i < values.length; i++) {
			arr[col][i] = values[i];
		}
		return arr;
	}

	/**
	 * Return a 2d array which is the cols of arr starting with startCol up
	 * to up to endCol.
	 * 
	 * @param arr
	 *            the array to get columns from
	 * @param startCol
	 *            index of starting column
	 * @param endCol
	 *            index of ending column 
	 * @return a 2d array whose size is endCol-startCol
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
	 * the input array.
	 * 
	 * @param arr
	 *            the array to get columns from
	 * @param colIndexes
	 *            the indexes of the cols from the input
	 * @return a 2d array that is the columns of arr indexes
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
	 * Return a 2d array which is the cols of arr in the column indexes from
	 * the int input array.
	 * 
	 * @param colNames
	 *            String of column Names to get data from
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
	 * Sets col vals in input columnIndex to values given in array vals
	 * 
	 * @param columnIndex
	 *            index where new values are set
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
	 *            index where values should be set
	 * @param vals
	 *            the values of that row
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
	 * 
	 * @param arr
	 *            2-D array to set CSVData data to
	 * @return new CSVData data 2-D array
	 */
	public double[][] setCSVData(double[][] arr) {
		this.data = arr;
		return this.data;
	}

	public static void saveToFile(String filename) {

	}

	/**
	 * Changes time to elapsed time
	 * 
	 * @param values
	 *            CSVData object with sensor data
	 */
	public static void correctTime(CSVData values) {
		double startTime = values.data[0][0];

		for (int row = 0; row < values.data.length; row++)
			values.data[row][0] -= startTime;
	}
}
