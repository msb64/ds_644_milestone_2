
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class StockGrowthMapper extends Mapper<LongWritable, Text, Text, Text> {

	/*
	 * Map symbol to date:close
	 *
	 * Sample input data format:
	 * Symbol,Date,Open,High,Low,Close,Adj Close,Volume
	 * RMD,1995-06-02,0.75,0.75,0.65625,0.671875,0.5741074085235596,14915200.0
	 */

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// Exclude headers
		if (value.toString().startsWith("Symbol")) {
			return;
		}

		// Store date + close for each stock symbol, so that average change per day and volatility can be computed
		String[] fields = value.toString().split(",");
		String symbol = fields[0].trim();
		String date = fields[1].trim();
		String close = fields[5].trim();
		context.write(new Text(symbol), new Text(date + ":" + close));
	}
}
