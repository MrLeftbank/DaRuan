docker cp ~/upload/dist online-education-system-front:/root/
docker exec -itd online-education-system-front "service" "nginx" "restart"
