/*
* A Java class to update the data from MongoDB database
* Mongo Version : 3.0
* 
*
* @author  Ruthwik
* @version 1.0
* @since   2015-9-29 
*/

package mongodb;

import java.util.Date;

import org.bson.Document;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UpdateQuery {

	public static void main(String[] args) throws ParseException {

		try {

			/**** Connect to MongoDB ****/
			// MongoClient mongo = new MongoClient(new
			// MongoClientURI("mongodb://172.50.88.12:27018"));
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			MongoDatabase db = mongo.getDatabase("mydb");

			/**** Get collection / table from 'testdb' ****/
			MongoCollection<Document> collection = db.getCollection("userdata");
			int i = 1;
			long lStartTime = new Date().getTime();


			// "company","ZORROMOP" "isActive": true, "balance","2,000.00"
            
		
			/**** update one document ****/
			//update one updates the first match only
			
		  	collection.updateOne(new Document("company", "ZORROMOP"),
			        new Document("$set", new Document("company", "Apple Inc")));

			
			/**** update few documents ****/
						
			collection.updateMany(new Document("company","INCUBUS"),
					new Document("$set", new Document("balance", "$500.00")));
			
			
			/**** update all documents ****/
		
			collection.updateMany(new Document("company", "ZORROMOP"),
					new Document("$set", new Document("balance", "2,000.00")));
			

			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime; // check different

			System.out.println(" Total Time Elapsed milliseconds " + difference);
			

			/**** Find and display ****/

			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("company","INCUBUS");

			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toJson());
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
/*
 * 
 * 
 */