package com.mongodb.demo;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class JavaMongoDBConnect {

	public static void main(String[] args) {
		try {
		// connect with server
		MongoClient mongoclient = new MongoClient("localhost", 27017);
		System.out.println("server connection established");

		// connect with database
		DB db = mongoclient.getDB("testdb");
		System.out.println("connected to db successfully");
		System.out.println("db name : " + db.getName());

		// Display all databases
		List<String> dbs = mongoclient.getDatabaseNames();
		for (String database : dbs) {
			System.out.println(database);
		}

		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		DBCollection table = db.getCollection("user");

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("name", "anitha");
		document.put("age", 23);
		document.put("createdDate", new Date());
		table.insert(document);

		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "anitha");

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		
		/**** Update ****/
		// search document where name="anitha" and update it with new values
		BasicDBObject query = new BasicDBObject();
		query.put("name", "anitha");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "Anitha.K");

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);

		/**** Find and display ****/
		BasicDBObject searchQuery2
		    = new BasicDBObject().append("name", "Anitha.K");

		DBCursor cursor2 = table.find(searchQuery2);

		while (cursor2.hasNext()) {
			System.out.println(cursor2.next());
		}

		/**** Done ****/
		System.out.println("Done");
		
		//remove Anitha.k user
		DBCollection table1 = db.getCollection("user");

		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("name", "Anitha.k");
		
		table1.remove(searchQuery1);
		
	}catch (MongoException e) {
	e.printStackTrace();
    }
	}
}
