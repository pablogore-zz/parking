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