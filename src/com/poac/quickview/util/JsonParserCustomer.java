package com.poac.quickview.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.poac.quickview.model.Cabinet;
import com.poac.quickview.model.Capsule;
import com.poac.quickview.model.Group;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Parameter;
import com.poac.quickview.model.Payload;
import com.poac.quickview.model.Topic;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.model.Type;

import javafx.scene.control.TreeItem;

public  class JsonParserCustomer {
	/**
	 * 解析PYTHON，获取导航列表
	 */	
	public  TreeDataModel getNavationData(ArrayList<String> pageList) {
		JsonParser parse = new JsonParser();
		TreeDataModel itemRoot=null;		
		try {
			String nav_URL = getClass().getResource("/navigation.json").getPath();
			JsonObject jsonObjRoot = (JsonObject) parse.parse(new FileReader(nav_URL));
			JsonObject jsonObjData = jsonObjRoot.get("data").getAsJsonObject(); 
			JsonObject jsonObjCapsules=jsonObjData.get("Capsules").getAsJsonObject();
			JsonArray jsonArrayCapsule=jsonObjCapsules.get("Capsule").getAsJsonArray();			
			for(int i=0;i<jsonArrayCapsule.size();i++) {
				JsonObject jsonObjCapsule=jsonArrayCapsule.get(i).getAsJsonObject();
				itemRoot = new TreeDataModel(new Capsule(jsonObjCapsule.get("Name").getAsString()));
				if (jsonObjCapsule.has("Page")) {
					JsonArray jsonArrayPage = jsonObjCapsule.get("Page").getAsJsonArray();
					for (int j = 0; j < jsonArrayPage.size(); j++) {
						JsonObject jsonObjPage = jsonArrayPage.get(j).getAsJsonObject();
						itemRoot.add(new Page(jsonObjPage.get("Name").getAsString()));
						pageList.add(jsonObjPage.get("Name").getAsString());
					}
				}
				JsonArray jsonArrayCabinet=jsonObjCapsule.get("Cabinet").getAsJsonArray();
				for(int j=0;j<jsonArrayCabinet.size();j++) {
					JsonObject jsonObjCabinet=jsonArrayCabinet.get(j).getAsJsonObject();
					TreeDataModel item1 =new TreeDataModel(new Cabinet(jsonObjCabinet.get("Name").getAsString()));
					itemRoot.add(item1);
					JsonArray jsonArrayPayload=jsonObjCabinet.get("Payload").getAsJsonArray();
					for(int k=0;k<jsonArrayPayload.size();k++) {
						JsonObject jsonObjPayload=jsonArrayPayload.get(k).getAsJsonObject();
						TreeDataModel item2 =new  TreeDataModel(new Payload(jsonObjPayload.get("Name").getAsString()));
						item1.add(item2);
						if (jsonObjPayload.has("Page")) {
							JsonArray jsonArrayPayloadPage = jsonObjPayload.get("Page").getAsJsonArray();
							for (int m = 0; m < jsonArrayPayloadPage.size(); m++) {
								JsonObject jsonObjPayloadPage = jsonArrayPayloadPage.get(m).getAsJsonObject();
								item2.add(new Page(jsonObjPayloadPage.get("Name").getAsString()));
								pageList.add(jsonObjPayloadPage.get("Name").getAsString());
							}
						}
					}
				}
			}

		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return itemRoot;
	}
	
	public TreeDataModel getSubscribeData() {
		
    	TreeDataModel rootNode=new TreeDataModel(new Group("力学所"));
    	TreeDataModel dataType=new TreeDataModel(new Type("data"));
    	rootNode.add(dataType);
    	TreeDataModel imageType=new TreeDataModel(new Type("image"));
    	rootNode.add(imageType);
    	TreeDataModel videoType=new TreeDataModel(new Type("video"));
    	rootNode.add(videoType);
    	TreeDataModel curveType=new TreeDataModel(new Type("curve"));
    	rootNode.add(curveType);
    	TreeDataModel topic_data_1=new TreeDataModel(new Topic("流体实验柜-遥测数据-空间三相多液滴迁移"));
    	TreeDataModel topic_data_2=new TreeDataModel(new Topic("流体实验柜-工程数据-实验柜控制器A1"));
    	TreeDataModel topic_data_3=new TreeDataModel(new Topic("天和-工程数据"));
    	dataType.add(topic_data_1);
    	dataType.add(topic_data_2);
    	dataType.add(topic_data_3);
    	TreeDataModel topic_image=new TreeDataModel(new Topic("高等植物-应用数据JPEG"));
    	imageType.add(topic_image);
    	TreeDataModel topic_curve=new TreeDataModel(new Topic("流体实验柜-工程数据-实验柜控制器A1"));
    	curveType.add(topic_curve);
    	topic_data_1.add(new Parameter("液滴温度1"));
    	topic_data_1.add(new Parameter("液滴温度2"));
    	topic_data_2.add(new Parameter("A1温度1"));
    	topic_data_2.add(new Parameter("A1温度2"));
    	topic_data_3.add(new Parameter("俯仰姿态角度估值"));
    	topic_data_3.add(new Parameter("偏航姿态角度估值"));
    	topic_data_3.add(new Parameter("滚动姿态角度估值"));
    	topic_data_3.add(new Parameter("俯仰角速度预估"));
    	topic_image.add(new Parameter("温度1"));
    	topic_image.add(new Parameter("温度2"));
    	topic_image.add(new Parameter("高等植物图像"));
    	topic_curve.add(new Parameter("#0000ff"));
    	topic_curve.add(new Parameter("#ff8040"));
    	return rootNode;
	}
}
