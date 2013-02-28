package fr.vhat.keydyn.client;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;

import fr.vhat.keydyn.shared.TimeSequence;

/**
 * Charts management class.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class MemberAreaCharts {

	/**
	 * Create a line chart to display.
	 * @param type Define the type of the chart which set options values.
	 * @param length Size of the data to display.
	 * @param data Data table.
	 * @return A line chart representing the data.
	 */
	public static LineChart getChart(String type, TimeSequence[] times) {
		LineChart chart = new LineChart(createTable(times),
				createOptions(type));
		return chart;
	}

	/**
	 * Define the characteristics of the chart.
	 * @param type Define the type of the chart.
	 * @return A line chart options object.
	 */
	public static Options createOptions(String type) {
		Options options = Options.create();
		options.setWidth(700);
		options.setHeight(300);
		if (type.equals("released"))
			options.setTitle("Time between two key release");
		else if (type.equals("pressed"))
			options.setTitle("Time between two key press");
		else if (type.equals("pressedToReleased"))
			options.setTitle("Time between a key press and release");
		else if (type.equals("releasedToPressed"))
			options.setTitle("Time between a release and the next key press");
			// This value could be negative if an user press a key before
			// releasing the precedent one
		else
			options.setTitle("Unknown type of chart");
		return options;
	}

	/**
	 * Create the needed data table.
	 * @param passwordLength Length of the data to display on the chart.
	 * @param pressedTimes Data to display.
	 * @return The data table.
	 */
	public static AbstractDataTable createTable(TimeSequence[] times) {

		int KDDataNumber = times.length;
		if (KDDataNumber != 0) {
			DataTable data = DataTable.create();
			for (int i = 1 ; i <= KDDataNumber ; ++i) {
				data.addColumn(
					ColumnType.NUMBER, new String(
							"Data " + i + " (id) : date"));
			// TODO: add ID and date information
			}
			data.addRows(times[0].length());
			// Each column represents a KDData
			for (int column = 0 ; column < KDDataNumber ; ++column) {
				for (int row = 0 ; row < times[column].length() ; ++row)
					data.setValue(row, column,
							times[column].getTimeTable()[row]);
			}
			return data;
		} else {
			return null;
		}
	}
}
