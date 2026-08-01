set -eux

# Preprocess the data and put it into HDFS
java StockPreprocess.java
hdfs dfs -rm -r -f /input || true
hdfs dfs -mkdir /input
hdfs dfs -put sp-500-stock-prices-clean /input

set +eux
