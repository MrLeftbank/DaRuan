npm run build
scp -r ./dist root@121.37.92.215:/root/upload/
ssh root@121.37.92.215 "bash -s" < deploy.sh
