# Background

Stock market data can be interesting to analyze; strong predictive models can have large financial payoffs. The amount of financial data on the web is seemingly endless. A large and well-structured dataset on a wide array of companies can be hard to come by. This is a dataset with historical stock prices for all companies on the S&P 500 index.

There is a separate csv file for each of the 500 stocks with ticker as the file name. The fields in each one are:
  - Date - in format: yy-mm-dd
  - Open - price of stock at market open (this is NYSE data so all in USD)
  - High - highest price reached in the day
  - Low - lowest price reached in the day
  - Close - close price
  - Volume - number of shares traded

# Output
Compute these quantities for each stock is saved to: https://github.com/msb64/ds_644_milestone_2/blob/main/results.csv
  - growth (average growth of Close price from start date to end date)
  - volatility (average absolute change of Close price from one business day to the next)

# How to download the dataset

## Install pip packages
```bash
sudo apt update
sudo apt upgrade -y
sudo apt install python3-venv -y   # make sure venv is available
python3 -m venv venv               # create a virtual environment
source venv/bin/activate           # activate it
pip install --upgrade pip          # upgrade pip inside venv
pip install "kagglehub[pandas-datasets]"
```

## Download the dataset
```bash
python3 download_data.py
```

# How to run the MapReduce job
./preprocess.sh
./mapreduce.sh 2>&1 | tee mapreduce.log
