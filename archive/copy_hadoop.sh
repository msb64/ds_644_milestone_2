# This script has been helpful for me to configure my Hadoop environment, ONLY AT INITIAL SETUP

set -eux

for _host in slave1 slave2 slave3; do

	rsync -ltr /etc/hosts $_host:/home/ubuntu/etc_hosts
	ssh $_host "
		sudo mkdir -p /usr/local/hadoop && \
		sudo mkdir -p /usr/local/hadoop/data/dn && \
		sudo chown -R ubuntu:ubuntu /usr/local/hadoop && \
		sudo cp /home/ubuntu/etc_hosts /etc/hosts
	"
	rsync -lrt /usr/local/hadoop/ $_host:/usr/local/hadoop
	rsync -ltr ~/.bashrc $_host:~/.bashrc
done

set +eux
