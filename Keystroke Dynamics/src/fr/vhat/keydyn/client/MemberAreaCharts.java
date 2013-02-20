package fr.vhat.keydyn.client;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;

public class MemberAreaCharts {
		// Create a line chart (constructor)
		// Member function addData to add a KDData

	public static LineChart getChart(String type, int length, int[][] data) {
		LineChart chart = new LineChart(createTable(length, data),
				createOptions(type));
		return chart;
	}

	public static Options createOptions(String type) {
		Options options = Options.create();
		options.setWidth(700);
		options.setHeight(300);
		if (type.equals("released"))
			options.setTitle("Time between two key press");
		else if (type.equals("pressed"))
			options.setTitle("Time between two key release");
		else
			options.setTitle("Unknown type of chart");
		return options;
	}

	public static AbstractDataTable createTable(int passwordLength,
			int[][] pressedTimes) {

		int KDDataNumber = pressedTimes.length;
		DataTable data = DataTable.create();

		for (int i = 1 ; i <= KDDataNumber ; ++i) {
			data.addColumn(
					ColumnType.NUMBER, new String("Data " + i + " (id) : date"));
		}
		data.addRows(passwordLength - 1);
		// Each column represents a KDData
		for (int column = 0 ; column < KDDataNumber ; ++column) {
			for (int row = 0 ; row < passwordLength - 1 ; ++row)
				data.setValue(row, column,
						pressedTimes[column][row+1] - pressedTimes[column][row]);
		}

		return data;
	}
}
