# Introduction
This project deals with the performance analysis of MongoDB in a single node and in a cluster 

# MongoDB
[MongoDB](https://en.wikipedia.org/wiki/MongoDB) is a document oriented and NoSQL database.


# Running the Project

### Importing the project into eclipse.
1. In Eclipse go to File -> Import.
2. Select Existing Projects into Workspace.
3. Select the project location on the hardrive.
4. Click Finish to perform the import.

### Installing MongoDB on a single Node
To install follow the steps mentioned [here](https://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

### Creating a cluster

##### Steps in creating a cluster
1.Create config server

2.Connect master to config server

3.Create Shards

4.Add shards in master



 


#### 1  Creating a config server

1.Install mongodb

2.sudo mkdir /data/configdb

3.sudo chown mongo  /data/configdb

4.mongod --configsvr --dbpath /data/configdb --port 25020


If port is in use
sudo service mongod stop sudo mongod



#### 2  Connecting Master to Config servers

Open a terminal (This is the first terminal in Master)
     
mongos --configdb 172.50.88.60:25020,172.50.88.12:25020,172.50.57:25020 --port 27020
mongos --configdb 172.50.88.60:25020 --port 27020

* I was able to add only one config server due to time sync problem in the systems.
* 25020 is the port in the config servers that will be waiting for the connection (remember we used  mongod --configsvr --dbpath /data/configdb --port 25020 to start config server)
--port 27020 is the port through which you want your master to connect through.


#### 3  Creating Shards

Create /data/db 
Give permissions:  sudo chown mongo /data/db 
mongod -shardsvr

Shard server waits at port 27018 by default
      Created 3 shards and same steps must be repeated in each shard
    http://docs.mongodb.org/manual/tutorial/deploy-config-servers/

#### 4  Adding shards in master

1. Open the terminal in the master (This is the second terminal in the master) and type mongo 172.50.88.46:27020/admin

2. To see all the databases:

             
             mongos> show dbs 
             admin   (empty)
             config  0.016GB
              
3. In order to see status (to know what are the shards,balancers etc) type: sh.status()
             
              
             mongos>sh.status()
            --- Sharding Status --- 
            sharding version: {
	          "_id" : 1,
	          "minCompatibleVersion" : 5,
           	"currentVersion" : 6,
          	"clusterId" : ObjectId("5608748c72b2a03e98f126e2")
            }
            shards:
            balancer:
          	Currently enabled:  yes
           	Currently running:  no
           	Failed balancer rounds in last 5 attempts:  0
           	Migration Results for the last 24 hours: 
         		No recent migrations
            databases:
	          {  "_id" : "admin",  "partitioned" : false,  "primary" : "config" }
	          
4. Intially	there will be no shards. So, to add shards          


           
            mongos> sh.addShard("172.50.88.12:27018")
           { "shardAdded" : "shard0000", "ok" : 1 }

           mongos> sh.addShard("172.50.88.57:27018")
           { "shardAdded" : "shard0001", "ok" : 1 }

           mongos> sh.addShard("172.50.88.48:27018")
           { "shardAdded" : "shard0002", "ok" : 1 }
          

