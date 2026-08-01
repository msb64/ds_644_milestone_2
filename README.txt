# How to download the dataset:

## Install pip packages
sudo apt update
sudo apt upgrade -y
sudo apt install python3-venv -y   # make sure venv is available
python3 -m venv venv               # create a virtual environment
source venv/bin/activate           # activate it
pip install --upgrade pip          # upgrade pip inside venv
pip install "kagglehub[pandas-datasets]"

## Download the dataset
python3 download_data.py
