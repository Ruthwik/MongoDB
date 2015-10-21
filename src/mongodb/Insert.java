/*
* A Java class for inserting data from JSON file to a MongoDB database given a MongoDB Connection URI 
* Mongo Version : 3.0
* Documents in JSON file are separated by new line 
* Dataset used :largedata.json
*
* @author  Ruthwik
* @version 1.0
* @since   2015-9-29
*  
*/
package mongodb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

//mongo libraries
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//JSON libraries
import com.mongodb.util.JSON;
import org.json.simple.parser.ParseException;

public class Insert {

	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {

		try {

			/**** Connect to MongoDB ****/
			// MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://172.50.88.48:27017"));

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create
			MongoDatabase db = mongo.getDatabase("ruthwikdb");

			/**** Get collection ****/
			// if collection doesn't exists, MongoDB will create it for you
			MongoCollection<DBObject> collection = db.getCollection("userdata", DBObject.class);

			/**** Insert ****/
			int i = 0;
			// start time for performance calculation
			long lStartTime = new Date().getTime();

			try (BufferedReader br = new BufferedReader(new FileReader("/home/mongo/Documents/largedata.json"))) {
				String line;
				while ((line = br.readLine()) != null) {

					DBObject dbObject = (DBObject) JSON.parse(line);

					collection.insertOne(dbObject);
					i++;
					if (i == 1000000) //No of documents to be inserted
						break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {

			}

			// End time for performance calculation
			long lEndTime = new Date().getTime();

			// Difference gives the time taken to insert all documents
			long difference = lEndTime - lStartTime;

			System.out
					.println("Documents: " + i + " Total Time Elapsed milliseconds using normal insert: " + difference);

			/**** Find and display ****/

			/*
			 * MongoCursor<DBObject> cursor = collection.find().iterator(); 
			 * try
			 * {
			 *  while (cursor.hasNext())
			 *   {
			 *    System.out.println((cursor.next()));
			 *   } 
			 * }
			 *  finally
			 *   { 
			 *   cursor.close();
			 *   }
			 */
			
			/**** Close the connection ****/
			mongo.close();
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
