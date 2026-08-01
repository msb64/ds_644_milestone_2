import kagglehub
import os
import shutil

# Download latest version
if os.path.exists("~/.cache/kagglehub"):
    shutil.remove("~/.cache/kagglehub")
default_path = kagglehub.dataset_download("artemburenok/sp-500-stock-prices")

# Move to current directory
final_path = "./sp-500-stock-prices"
if os.path.exists(final_path):
    shutil.rmtree(final_path)
shutil.move(default_path, final_path)

print("Files saved to: " + final_path)
