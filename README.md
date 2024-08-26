**Criar imagens**

cd activemq
docker build -t activemq .

docker build -t java-maven .


**Rodar em Containers isolados**

**activemq**

docker network create mensageria

docker run --rm -d -p 8161:8161 -p 61641:61641 -p 61642:61642 --name activemq --network mensageria activemq

http://localhost:8161/admin/

admin/admin

docker exec -it activemq /bin/bash

**Producer**

docker run --rm -it -v $(pwd):/app  --name message-producer --network mensageria java-maven /bin/bash

mvn clean package
java -jar target/message-producer-1.0-SNAPSHOT.jar

**Consumer**

docker run --rm -it -v $(pwd):/app  --name message-consumer --network mensageria java-maven /bin/bash

mvn clean package
java -jar target/message-consumer-1.0-SNAPSHOT.jar
