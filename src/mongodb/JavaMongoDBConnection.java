/*
* A Java class for inserting data from JSON file to a MongoDB database given a MongoDB Connection URI
* Mongo Version : 3.0
* Documents in JSON file are separated by comma 
* Dataset used :largedata.json
*  
*
* @author  Ruthwik
* @version 1.0
* @since   2015-9-29 
*/
package mongodb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

//mongo libraries
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

//JSON libraries
import com.mongodb.util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JavaMongoDBConnection {

	public static void main(String[] args) throws ParseException, IOException {

		try {

			/**** Connect to MongoDB ****/
			//MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://172.50.88.12:27018"));
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			MongoDatabase db = mongo.getDatabase("mydb");

			/**** Get collection / table from 'testdb' ****/
			MongoCollection<BasicDBObject> collection = db.getCollection("userdata", BasicDBObject.class);

			/**** Insert ****/
			JSONParser parser = new JSONParser();

			int i = 0;
			// start time for performance calculation
			long lStartTime = new Date().getTime();

			try {
				JSONArray a = (JSONArray) parser.parse(new FileReader("/home/mongo/Documents/largedata.json"));
				for (Object o : a) {

					JSONObject json = (JSONObject) o;

					// json.toString() doesn't change json object to string,it
					// must be assigned to something
					// String abc=json.toString() works fine or pass
					// json.toString() as a parameter

					BasicDBObject dbObject = (BasicDBObject) JSON.parse(json.toString());

					(collection).insertOne(dbObject);

					// Counting no of documents inserted
					i++;
					if(i==10)
						break;

				}

				// End time for performance calculation
				long lEndTime = new Date().getTime();

				// Difference gives the time taken to insert all documents
				long difference = lEndTime - lStartTime;

				System.out.println(
						"Documents: " + i + " Total Time Elapsed milliseconds using normal insert: " + difference);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			/**** Find and display ****/

			 MongoCursor<BasicDBObject> cursor = collection.find().iterator();
			 try {
			 while (cursor.hasNext()) {
			 System.out.println(cursor.next());
			 }
			 } finally {
			 cursor.close();
			 }

			/**** Close the connection ****/
			mongo.close();
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}

