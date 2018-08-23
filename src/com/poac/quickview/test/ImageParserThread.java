package com.poac.quickview.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.poac.quickview.global.SubscribeParameters;

import javafx.scene.image.Image;

public class ImageParserThread extends Thread  {

	public ImageParserThread() {
		super(); 
	}  
	public void run() {  
		JsonParser parse = new JsonParser();
		try {
			InputStream input=getClass().getResourceAsStream("/imagedata.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
	        String line;
	        String allText="";
	        do {
	            line = br.readLine();
	            if (line != null) allText+=line;
	        } while (line != null);
	        JsonArray jsonObjRoot = (JsonArray) parse.parse(allText);
			for(int i=0;i<jsonObjRoot.size();i++) {
				JsonObject parmRoot=jsonObjRoot.get(i).getAsJsonObject();
				JsonObject parm=parmRoot.get("Params").getAsJsonObject();
				String time=parmRoot.get("Time").getAsString();
				String imageName=parm.get("Image").getAsString();
				SubscribeParameters.getSubscribeParameters().page_Container_ImageProperty.get("自定义页面1:zwj123").set(
						new Image(getClass().getResourceAsStream("/"+imageName)));
				Iterator<String> iterator = parm.keySet().iterator();
				while(iterator.hasNext()) {
					String codeName=iterator.next();
					if(codeName.equals("Image"))
						continue;
					SubscribeParameters.getSubscribeParameters().subParameterMap.get(codeName).setValue(parm.get(codeName).getAsString());		
					SubscribeParameters.getSubscribeParameters().subParameterMap.get(codeName).setTime(time);
					try { 
						sleep(1000); 
						} catch (InterruptedException e) { 
						e.printStackTrace(); 
						}
				}
			}
			 
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
