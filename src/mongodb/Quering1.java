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

import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class Quering1 {

	public static void main(String[] args) throws ParseException {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			
			DB db = mongo.getDB("mydb");

			/**** Get collection / table from 'testdb' ****/
			
			DBCollection table = db.getCollection("user1");

			

			BasicDBObject andQuery = new BasicDBObject();

			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			
			//NO of employees working for the company TALAE or gender is male
			obj.add(new BasicDBObject("company", "TALAE"));
			obj.add(new BasicDBObject("gender", "male"));
			//obj.add(new BasicDBObject("gender", "female"));
			andQuery.put("$or", obj);
			
			
			System.out.println(andQuery.toString());
			long lStartTime = new Date().getTime();
			DBCursor cursor7 = table.find(andQuery);

			while (cursor7.hasNext()) {
				System.out.println(cursor7.next());
			}
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime; // check different
			System.out.println("Total Time Elapsed milliseconds " + difference);
			
			/**** Find and display ****/
			/*
			 BasicDBObject neQuery = new BasicDBObject();
			 neQuery.put("isActive", new BasicDBObject("$ne", "false"));
			 DBCursor cursor6 = table.find(neQuery);
			 while (cursor6.hasNext()) {
			 System.out.println(cursor6.next());
			 } */

			/**** Done ****/
			System.out.println("Done");
			mongo.close();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}

}
