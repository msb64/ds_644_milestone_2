set -eux

# Compile the StockGrowth MapReduce
javac -classpath $(hadoop classpath) -d . StockGrowthMapper.java StockGrowthReducer.java StockGrowth.java && jar -cvf StockGrowth.jar *.class
RC=$?
if [ $RC -ne 0 ]; then
	echo "Compilation failed"
	exit $RC
fi

# Run the StockGrowth MapReduce
hdfs dfs -rm -r -f /output_stock_growth
hadoop jar StockGrowth.jar StockGrowth /input/sp-500-stock-prices-clean /output_stock_growth

# View and save the output
OUT_FILE=results.csv
echo "symbol,growth,volatility" > $OUT_FILE
hdfs dfs -cat /output_stock_growth/part-r-00000 | sed -E "s/\s+/,/g" > $OUT_FILE
set +x
OUT_LINES=$( wc -l $OUT_FILE | awk -F' ' '{print $1}' )
echo "Wrote $OUT_LINES lines to $OUT_FILE"
echo "Except of data:"
head -6 $OUT_FILE

set +eux
