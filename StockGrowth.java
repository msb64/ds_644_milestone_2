import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class StockGrowth {

	/*
	 * Main entrypoint to the MapReduce job, which includes the StockGrowthMapper and StockGrowthReducer classes.
	 * Input and output directory must be provided as parameters.
	 * Computes these quantities for each stock:
	 *   - growth (average growth of Close price from start date to end date)
	 *   - volatility (average absolute change of Close price from one business day to the next)
	 * Returns:
	 *   - symbol	growth,volatility
	 */

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.println("Two parameters required (input dir and output dir");
			System.exit(1);
		}

		Job job = Job.getInstance(new Configuration(), "Stock Growth");

		job.setJarByClass(StockGrowth.class);
		job.setMapperClass(StockGrowthMapper.class);
		job.setReducerClass(StockGrowthReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		Path input_path = new Path(args[0]);
		Path output_path = new Path(args[1]);

		FileInputFormat.setInputPaths(job, input_path);
		FileOutputFormat.setOutputPath(job, output_path);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
