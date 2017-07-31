package test;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cloudant.http.Http;
import com.cloudant.http.HttpConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DBConnectionTest {

	private static final String URL = "https://9d81ef69-b254-4693-a607-b3275d8ca9f0-bluemix:27ff564dc10ffde8f69f5042d7ed5427030c76275839fd5401ad326b09d1c8bc@9d81ef69-b254-4693-a607-b3275d8ca9f0-bluemix.cloudant.com";
	private static final String USER_NAME = "9d81ef69-b254-4693-a607-b3275d8ca9f0-bluemix";
	private static final String PASSWORD = "27ff564dc10ffde8f69f5042d7ed5427030c76275839fd5401ad326b09d1c8bc";

	public static void main(String[] args) throws Exception {
		// Create a new CloudantClient instance for account endpoint
		// example.cloudant.com
		// CloudantClient client = ClientBuilder.account("example")
		// .username("exampleUser")
		// .password("examplePassword")
		// .build();

		// Note: for Cloudant Local or Apache CouchDB use:
		CloudantClient client = ClientBuilder.url(new URL(URL)).username(USER_NAME).password(PASSWORD).build();

		// Show the server version
		System.out.println("Server Version: " + client.serverVersion());

		// Get a List of all the databases this Cloudant account
		List<String> databases = client.getAllDbs();

		System.out.println("All my databases : ");

		String dbname = "training";
		boolean isCreate = false;

		for (String db : databases) {
			System.out.println(db);

			if (db.equals(dbname) == true) {
				isCreate = true;
			}
		}

		if (isCreate == false) {
			client.createDB(dbname);
		}

		DBConnectionTest test = new DBConnectionTest();

		JsonObject result = test.createData(client, dbname);

		System.out.println("insert result : " + result);

		result = test.getAll(client, dbname);

		System.out.println("get all : " + result);
		
		result = test.getDocument(client, dbname, "085f230d2bdd41b58e0d96fa0214814d");
		
		System.out.println("get document : " + result);

		// Working with data
		//
		// // Delete a database we created previously.
		// client.deleteDB("example_db");
		//
		// // Create a new database.
		// client.createDB("example_db");
		//
		// // Get a Database instance to interact with, but don't create it if it
		// doesn't already exist
		// Database db = client.database("example_db", false);
		//
		//
		// // Create an ExampleDocument and save it in the database
		// db.save(new ExampleDocument(true));
		// System.out.println("You have inserted the document");
		//
		// // Get an ExampleDocument out of the database and deserialize the JSON into a
		// Java type
		// ExampleDocument doc = db.find(ExampleDocument.class,"example_id");
		// System.out.println(doc);

	}

	public JsonObject createData(CloudantClient client, String dbname) {

		// Create a dummy json document
		JsonObject studentJson = new JsonObject();
		studentJson.addProperty("firstname", "Joe");
		studentJson.addProperty("lastname", "Doe");
		studentJson.addProperty("studentId", generateStudentId());

		Database db = client.database(dbname, false);
		Response dbResponse = db.save(studentJson);

		JsonObject output = new JsonObject();
		// for success insertion
		if (dbResponse.getStatusCode() < 400) {
			output.add("doc", studentJson);

			// dbResponse json data
			JsonObject dbResponseJson = new JsonObject();
			dbResponseJson.addProperty("status", dbResponse.getStatusCode() + " - " + dbResponse.getReason());
			dbResponseJson.addProperty("id", dbResponse.getId());
			dbResponseJson.addProperty("rev", dbResponse.getRev());

			output.add("data", dbResponseJson);
		} else {
			output.addProperty("err", dbResponse.getStatusCode() + " - " + dbResponse.getReason());
		}

		return output;
	}

	private String generateStudentId() {
		return "ID#" + new Double(Math.floor(Math.random() * 10000)).intValue();
	}

	public JsonObject getAll(CloudantClient client, String dbname) throws Exception {
		JsonObject output = new JsonObject();

		try {
			Database db = client.database(dbname, false);

			String url = "https://" + USER_NAME + ".cloudant.com/" + db.info().getDbName() + "/_all_docs";

			System.out.println("url : " + url);

			HttpConnection httpResponse = client.executeRequest(Http.GET(new URL(url)));

			JsonParser parser = new JsonParser();
			output = parser.parse(httpResponse.responseAsString()).getAsJsonObject();

		} catch (NoDocumentException ex) {
			output.addProperty("err", "No Database/Document found");
		}

		return output;
	}

	public JsonObject getDocument(CloudantClient client, String dbname, String id) throws Exception {
		JsonObject output = new JsonObject();

		try {
			Database db = client.database(dbname, false);

			db.find(id);
			InputStream is = db.find(id);
			int i;
			char c;
			String doc = "";
			
			while ((i = is.read()) != -1) {
				c = (char) i;
				doc += c;
			}
			JsonParser parser = new JsonParser();
			output = parser.parse(doc).getAsJsonObject();

		} catch (NoDocumentException ex) {
			output.addProperty("err", "No Database/Document found");
		}

		return output;
	}

	public JsonObject updateDocument(CloudantClient client, String dbname, String id, String firstName, String lastName)
			throws Exception {
		JsonObject output = new JsonObject();

		if (id == null || id.isEmpty()) {
			output.addProperty("err", "Please specify valid Doc ID");
		} else {
			try {
				Database db = client.database(dbname, false);

				db.find(id);
				InputStream is = db.find(id);
				
				int i;
				char c;
				String doc = "";
				
				while ((i = is.read()) != -1) {
					c = (char) i;
					doc += c;
				}
				
				JsonParser parser = new JsonParser();
				JsonObject docJson = parser.parse(doc).getAsJsonObject();

				if (!(firstName == null || firstName.isEmpty())) {
					docJson.addProperty("firstname", firstName);
				}
				
				if (!(lastName == null || lastName.isEmpty())) {
					docJson.addProperty("lastname", lastName);
				}
				
				db.update(docJson);

				output.addProperty("result", "Success update document");

			} catch (NoDocumentException ex) {
				output.addProperty("err", ex.getReason());
			} catch (DocumentConflictException ex) {
				output.addProperty("err", ex.getReason());
			}
		}
		
		return output;
	}
	
	public JsonObject deleteDocument(CloudantClient client, String dbname, String id) throws Exception {
		JsonObject output = new JsonObject();
		
		try {
	    	Database db = client.database(dbname, false);
	
	    	db.find(id);
	    	InputStream is = db.find(id);
	    	
			int i;
			char c;
			String doc = "";
			
			while((i=is.read())!=-1) {
	            c=(char)i;
	            doc += c;
	        }
			
			JsonParser parser = new JsonParser();
			JsonObject docJson = parser.parse(doc).getAsJsonObject();
			
			db.remove(docJson);
			
			output.addProperty("result", "Document deleted");
	    	
    	} catch(NoDocumentException ex) {
    		output.addProperty("err", ex.getReason());
    	} 
		
		return output;
	}
}
