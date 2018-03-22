# Parking


edit  to parking/grpc/src/main/resources/config.properties

Change path to where you will put your json files , in this case is 
~/parking/files

# Build 
mvn package

#Run GRPC Server
cd grpc && mvn exec:java in one terminal

#Run API
cd api && mvn exec:java in one terminal

#Integration test
At this point , we could run our integration test , the only requirment is
the grpc server must be up and running.

cd api && mvn verify in one terminal

#Documentation 
I've provided a wsdl document where I exposed the interfaces of the API

#Curl
When api and grpc server  are up and running , we could test our service
just using curl command.

### xml
curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:9000/spothero/rate/2015-07-04T07:00:00Z/2015-07-04T12:00:00Z

### json
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9000/spothero/rate/2015-07-04T07:00:00Z/2015-07-04T12:00:00Z