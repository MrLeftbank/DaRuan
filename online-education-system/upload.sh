mvn package
scp ./target/oesb-0.0.1-SNAPSHOT.jar root@121.37.92.215:/root/oesb-0.0.1-SNAPSHOT.jar
ssh root@121.37.92.215 "bash -s" < deploy.sh
