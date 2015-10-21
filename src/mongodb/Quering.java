/*
* A Java class to retrieve data from MongoDB database given a MongoDB Connection URI
* Mongo Version : 3.0
* 
*
* @author  Ruthwik
* @version 1.0
* @since   2015-9-29 
*/

package mongodb;

import static java.util.Arrays.asList;

import java.util.Date;

//bson libraries
import org.bson.Document;
import org.json.simple.parser.ParseException;

//mongo libraries
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;

public class Quering {

	public static void main(String[] args) throws ParseException {

		try {

			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://172.50.88.48:27017"));


			/**** Get database ****/
			MongoDatabase db = mongo.getDatabase("ruthwikdb");

			/**** Get collection / table from 'testdb' ****/
			MongoCollection<Document> collection = db.getCollection("userdata");
			int i = 1;
			long lStartTime = new Date().getTime();
			// "AnonID":"1327669"
			FindIterable<Document> iterable = collection.find((new Document("Query", "happyall")));

			 /*
			 FindIterable<Document> iterable = collection.find(
			 new Document("$and", asList(new Document("gender", "male"),
			 new Document("age", new Document("$lte", 38)), new
			 Document("age", new Document("$gte", 28)))));
			 */

			// Iterate the results and apply a block to each resulting document.
			iterable.forEach(new Block<Document>() {
				@Override
				public void apply(final Document document) {
					// System.out.println(document);
				}
			});

			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime; // check different

			System.out.println("Total Time Elapsed milliseconds : " + difference);
			

			/**** Find and display ****/
			/*
			 * MongoCursor<Document> cursor = collection.find().iterator(); try
			 * { while (cursor.hasNext()) { System.out.println(cursor.next()); }
			 * } finally { cursor.close(); }
			 */
			
			/**** Close the connection ****/
			mongo.close();

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
