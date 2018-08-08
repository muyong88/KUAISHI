package com.poac.quickview.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
			InputStream inputImage=getClass().getResourceAsStream("/imagedata.json");
			 byte b[] = new byte[40960] ; 
			 int len = inputImage.read(b) ;
			JsonArray jsonObjRoot = (JsonArray) parse.parse(new String(b,0,len));
			for(int i=0;i<jsonObjRoot.size();i++) {
				JsonObject parmRoot=jsonObjRoot.get(i).getAsJsonObject();
				JsonObject parm=parmRoot.get("Params").getAsJsonObject();
				String time=parmRoot.get("Time").getAsString();
				String imageName=parm.get("Image").getAsString();
				SubscribeParameters.getSubscribeParameters().page_Container_ImageProperty.get("自定义页面1zwj123").set(
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
