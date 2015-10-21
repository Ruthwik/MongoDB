/*
* A Java class for BULK inserting data from JSON file to a MongoDB database given a MongoDB Connection URI
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
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//JSON libraries
import com.mongodb.util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MongoDBBulkInsert {
	public static void main(String[] args) throws ParseException {

		try {

			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			MongoDatabase db = mongo.getDatabase("mydb");

			/**** Get collection / table from 'testdb' ****/
			MongoCollection<BasicDBObject> collection = db.getCollection("user1", BasicDBObject.class);

			/**** Bulk Insert ****/
			JSONParser parser = new JSONParser();

			int i = 1;
			long lStartTime = new Date().getTime();

			BulkWriteOperation bulk = ((DBCollection) collection).initializeUnorderedBulkOperation();
			try {
				JSONArray a = (JSONArray) parser.parse(new FileReader("/home/admin35/Documents/largedata.json"));
				for (Object o : a) {
					JSONObject json = (JSONObject) o;
				
					BasicDBObject dbObject = (BasicDBObject) JSON.parse(json.toString());

					bulk.insert(dbObject);

					i++;
				}

				bulk.execute();

				long lEndTime = new Date().getTime();
				long difference = lEndTime - lStartTime; // check different
				System.out.println(
						"Documents: " + i + "Total Time Elapsed milliseconds using Bulk Update: " + difference);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {

			}

			/**** Close the connection ****/
			mongo.close();

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
