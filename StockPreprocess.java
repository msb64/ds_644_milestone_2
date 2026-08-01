import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Stock {

	public static String raw_data_dir = "sp-500-stock-prices"; // Raw data directory, as downloaded from kaggle
	public static String data_dir = "sp-500-stock-prices-clean"; // Data where symbol column has been added

	public static void main(String[] args) throws Exception {

		preprocess();
	}

	public static void preprocess() {

		/*
		 * Add symbol column to each CSV (extracted from file name) and put this modified data into a new directory.
		 * This allows the MapReduce job to be able to extract the symbol, since it has access to individual lines rather than file names.
		 */

		// Create data_dir
		try {
			Path data_path = Paths.get(data_dir);
			Files.createDirectories(data_path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add symbol column for each file in raw_data_dir, and write the result to data_dir
		Path dir = Paths.get(raw_data_dir);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			Path outFile = Paths.get(data_dir, "all.csv");
			try (BufferedWriter writer = Files.newBufferedWriter(outFile)) {
				writer.write("Symbol,Date,Open,High,Low,Close,Adj Close,Volume");
				writer.newLine();
				for (Path entry : stream) {
					try (BufferedReader reader = Files.newBufferedReader(entry)) {
						String line;
						boolean isHeader = true;
						while ((line = reader.readLine()) != null) {
							if (isHeader) {
								isHeader = false;
								continue;
							}
							String symbol = entry.getFileName().toString().split("\\.")[0];
							writer.write(symbol + "," + line);
							writer.newLine();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
}
