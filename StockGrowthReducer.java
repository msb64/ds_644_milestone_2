import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class StockGrowthReducer extends Reducer<Text, Text, Text, Text> {

	/*
	 * Map symbol to growth,volatility
	 *   - growth (average growth of Close price from start date to end date)
	 *   - volatility (average absolute change of Close price from one business day to the next)
	 */

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		// Sort the rows by date
		List<String> rows = new ArrayList<>();
		for (Text v : values) {
			rows.add(v.toString());
		}
		Collections.sort(rows);

		// Compute growth
		double firstClose = Double.parseDouble(rows.get(0).split(":")[1]);
		double lastClose = Double.parseDouble(rows.get(rows.size() - 1).split(":")[1]);
		LocalDate firstDate = LocalDate.parse(rows.get(0).split(":")[0], DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate lastDate = LocalDate.parse(rows.get(rows.size() - 1).split(":")[0], DateTimeFormatter.ISO_LOCAL_DATE);
		long daysDiff = ChronoUnit.DAYS.between(firstDate, lastDate);
		double growth = (lastClose - firstClose) / daysDiff;

		// Compute volatility
		double changes = 0;
		for (int i = 1; i < rows.size(); i++) {
			changes += Math.abs(Double.parseDouble(rows.get(i).split(":")[1]) - Double.parseDouble(rows.get(i-1).split(":")[1]));
		}
		double volatility = changes / (rows.size() - 1);

		// Write output
		context.write(key, new Text(String.format("%.6f", growth) + "," + String.format("%.6f", volatility)));
	}
}
