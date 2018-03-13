package org.ECS193.TripleDataProcessor;
import java.util.ArrayList;

import org.ECS193.TripleDataProcessor.TripleElement.TYPE;
import org.json.JSONArray;
import org.json.JSONObject;

public class EndPoint {
	private ArrayList<Triple> triples;
	
	public EndPoint(String data) {
		this.setTriples(new ArrayList<Triple>());
		try {
			this.parser(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Triple> getTriples() {
		return triples;
	}

	public void setTriples(ArrayList<Triple> triples) {
		this.triples = triples;
	}
	
	public void addTriple(Triple triple) {
		triples.add(triple);
	}
	
	public void parser(String data) throws Exception {
		JSONObject jsonObject = new JSONObject(data);
        
//        System.out.println("object: " + jsonObject);
//        System.out.println();

        jsonObject = jsonObject.getJSONObject("results");
//        System.out.println(jsonObject);
//        System.out.println();

        JSONArray triples = jsonObject.getJSONArray("bindings");
       
//        System.out.println(triples);
//        System.out.println(triples.length());
        
        JSONObject temp;
        String subject = "";
        String predicate = ""; 
        String object = "";
        TYPE subjectType = null;
        TYPE predicateType = null;
        TYPE objectType = null;
        
        for(int i = 0; i < triples.length(); i++) {
        		temp = (JSONObject) triples.get(i);
        		
        		subject = temp.getJSONObject("subject").get("value").toString();
        		predicate = temp.getJSONObject("predicate").get("value").toString();
        		object = temp.getJSONObject("object").get("value").toString();
        		
        		if(temp.getJSONObject("subject").get("type").toString().equals("literal")) {
        			subjectType = TYPE.literal;
        		}
        		else {
        			subjectType = TYPE.uri;
        		}
        		
        		if(temp.getJSONObject("predicate").get("type").toString().equals("literal")) {
        			predicateType = TYPE.literal;
        		}
        		else {
        			predicateType = TYPE.uri;
        		}
        		
        		if(temp.getJSONObject("object").get("type").toString().equals("literal")) {
        			objectType = TYPE.literal;
        		}
        		else {
        			objectType = TYPE.uri;
        		}
        		
        		System.out.println("Triple " + (i+1) + ": ");
        		System.out.println(subject);
        	    System.out.println(predicate);
        	    System.out.println(object);
        		System.out.println();
        		
        		this.addTriple(new Triple(subject, subjectType, predicate, predicateType, object, objectType));
        }
    	}


}