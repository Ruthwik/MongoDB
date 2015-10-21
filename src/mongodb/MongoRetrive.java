/*
* A Java class to retrieve data from MongoDB database given a MongoDB Connection URI
* Mongo Version : 2.0
* 
*
* @author  Ruthwik
* @version 1.0
* @since   2015-9-29 
*/

package mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//JSON libraries
import org.json.simple.parser.ParseException;

//mongo libraries
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoRetrive {

	public static void main(String[] args) throws ParseException {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			DB db = mongo.getDB("mydb");

			/**** Get collection / table from 'testdb' ****/
			DBCollection table = db.getCollection("userdata");



			BasicDBObject andQuery = new BasicDBObject();

			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			
			 obj.add(new BasicDBObject("Query","bits.ac.in"));
			
			 /*
			obj.add(new BasicDBObject("age", new BasicDBObject("$lte", 38).append("$gte", 28)));
			andQuery.put("$and", obj);
            db.userdata.find( {Query:"bits.ac.in" } ).count()
			System.out.println(andQuery.toString());
			*/
			
			long lStartTime = new Date().getTime();
			
			DBCursor cursor7 = table.find(andQuery);
		
            int i=0;
			while (cursor7.hasNext()) {
				System.out.println(cursor7.next());
				i++;
			}
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime; // check different

			System.out.println(
					"Documents: " + i + " Total Time Elapsed milliseconds using normal insert: " + difference);


			/**** Close the connection ****/
			mongo.close();
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}

}
