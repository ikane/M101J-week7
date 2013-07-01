package photosharing;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Question7 {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client=new MongoClient();
		DB db = client.getDB("photosharing");
		
		DBCollection albumsCollection = db.getCollection("albums");
		DBCollection imagessCollection = db.getCollection("images");
		
		DBCursor cursor = imagessCollection.find();
		List<Integer> orphans = new ArrayList<Integer>();
		while(cursor.hasNext()) {
			DBObject image = cursor.next();
			Integer imgId = (Integer)image.get("_id");
			if(albumsCollection.findOne(new BasicDBObject("images", imgId)) == null) {
				orphans.add(imgId);
			}
		}
		
		for (Iterator iterator = orphans.iterator(); iterator.hasNext();) {
			Integer imgId = (Integer) iterator.next();
			imagessCollection.remove(new BasicDBObject("_id", imgId));
		}
		System.out.println(orphans.size() + " removed");
	}

}
