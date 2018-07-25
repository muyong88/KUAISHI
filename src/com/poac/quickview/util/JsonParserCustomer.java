package com.poac.quickview.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Cabinet;
import com.poac.quickview.model.Capsule;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.CurveParameter;
import com.poac.quickview.model.Group;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.ImageParameter;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.Payload;
import com.poac.quickview.model.Topic;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.model.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;

public  class JsonParserCustomer {
	/**
	 * 解析PYTHON，获取导航列表
	 */	
	public  TreeDataModel getNavationData(ArrayList<String> pageList) {
		JsonParser parse = new JsonParser();
		TreeDataModel itemRoot=null;	 	
		try {
			InputStream input=getClass().getResourceAsStream("/navigation.json");
			 byte b[] = new byte[4096] ; 
			 int len = input.read(b) ;
			JsonObject jsonObjRoot = (JsonObject) parse.parse(new String(b,0,len));
			JsonObject jsonObjData = jsonObjRoot.get("data").getAsJsonObject();
			JsonObject jsonObjCapsules = jsonObjData.get("Capsules").getAsJsonObject();
			JsonArray jsonArrayCapsule = jsonObjCapsules.get("Capsule").getAsJsonArray();
			for (int i = 0; i < jsonArrayCapsule.size(); i++) {
				JsonObject jsonObjCapsule = jsonArrayCapsule.get(i).getAsJsonObject();
				itemRoot = new TreeDataModel(new Capsule(jsonObjCapsule.get("Name").getAsString()));
				if (jsonObjCapsule.has("Page")) {
					JsonArray jsonArrayPage = jsonObjCapsule.get("Page").getAsJsonArray();
					for (int j = 0; j < jsonArrayPage.size(); j++) {
						JsonObject jsonObjPage = jsonArrayPage.get(j).getAsJsonObject();
						itemRoot.add(new Page(jsonObjPage.get("Name").getAsString()));
						pageList.add(jsonObjPage.get("Name").getAsString());
					}
				}
				JsonArray jsonArrayCabinet = jsonObjCapsule.get("Cabinet").getAsJsonArray();
				for (int j = 0; j < jsonArrayCabinet.size(); j++) {
					JsonObject jsonObjCabinet = jsonArrayCabinet.get(j).getAsJsonObject();
					TreeDataModel item1 = new TreeDataModel(new Cabinet(jsonObjCabinet.get("Name").getAsString()));
					itemRoot.add(item1);
					JsonArray jsonArrayPayload = jsonObjCabinet.get("Payload").getAsJsonArray();
					for (int k = 0; k < jsonArrayPayload.size(); k++) {
						JsonObject jsonObjPayload = jsonArrayPayload.get(k).getAsJsonObject();
						TreeDataModel item2 = new TreeDataModel(new Payload(jsonObjPayload.get("Name").getAsString()));
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemRoot;
	}
	/**
	 * 解析PYTHON（尚未完成），获取订阅数据
	 */	 
	public TreeDataModel getSubscribeData(String type) {
		TreeDataModel rootNode=new TreeDataModel(new Type(type));
		if(!type.equals("data"))
			return rootNode;
		JsonParser parse = new JsonParser();
		try {
			InputStream inputTheme=getClass().getResourceAsStream("/theme.json");
			 byte b[] = new byte[4096] ; 
			 int len = inputTheme.read(b) ;
			JsonObject jsonObjRoot = (JsonObject) parse.parse(new String(b,0,len));
			JsonObject jsonObjData = jsonObjRoot.get("data").getAsJsonObject();
			JsonObject jsonObjCapsules = jsonObjData.get("Capsules").getAsJsonObject();
			JsonArray jsonArrayCapsule = jsonObjCapsules.get("Capsule").getAsJsonArray();
			for (int i = 0; i < jsonArrayCapsule.size(); i++) {
				JsonObject jsonObjCapsule = jsonArrayCapsule.get(i).getAsJsonObject();
				if (jsonObjCapsule.has("Topic")) {
					JsonObject jsonObjTopic = jsonObjCapsule.get("Topic").getAsJsonArray().get(0).getAsJsonObject();
					TreeDataModel topiNode = new TreeDataModel(new Topic(jsonObjTopic.get("Name").getAsString()));
					rootNode.add(topiNode);
					createTopicParms(topiNode); // 创建Topic对应的参数
				}
				JsonArray jsonArrayCabinet = jsonObjCapsule.get("Cabinet").getAsJsonArray();
				for (int j = 0; j < jsonArrayCabinet.size(); j++) {
					JsonObject jsonObjCabinet = jsonArrayCabinet.get(j).getAsJsonObject();
					if (jsonObjCabinet.has("Topic")) {
						JsonObject jsonObjTopic = jsonObjCabinet.get("Topic").getAsJsonArray().get(0).getAsJsonObject();
						TreeDataModel topiNode = new TreeDataModel(new Topic(jsonObjTopic.get("Name").getAsString()));
						rootNode.add(topiNode);
						createTopicParms(topiNode); // 创建Topic对应的参数
					}
					JsonArray jsonArrayPayload = jsonObjCabinet.get("Payload").getAsJsonArray();
					for (int k = 0; k < jsonArrayPayload.size(); k++) {
						JsonObject jsonObjPayload = jsonArrayPayload.get(k).getAsJsonObject();
						if (jsonObjCabinet.has("Topic")) {
							JsonObject jsonObjTopic = jsonObjPayload.get("Topic").getAsJsonArray().get(0).getAsJsonObject();
							TreeDataModel topiNode = new TreeDataModel(new Topic(jsonObjTopic.get("Name").getAsString()));
							rootNode.add(topiNode);
							createTopicParms(topiNode); // 创建Topic对应的参数
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    	return rootNode;
	}
	private void createTopicParms(TreeDataModel topiNode) {
		if(!topiNode.getName().equals("天和-工程数据-"))    //目前只有一个主题
			return;
		JsonParser parse = new JsonParser();
		try {
			InputStream inputTheme=getClass().getResourceAsStream("/topicparms.json");
			 byte b[] = new byte[40960] ; 
			 int len = inputTheme.read(b) ;
			JsonObject jsonObjRoot = (JsonObject) parse.parse(new String(b,0,len));
			JsonObject jsonObjData = jsonObjRoot.get("data").getAsJsonObject();
			JsonObject jsonObjTopic = jsonObjData.get("Topic").getAsJsonObject();
			JsonArray jsonArrayTopicData = jsonObjTopic.get("Data").getAsJsonArray();
			for (int i = 0; i < jsonArrayTopicData.size(); i++) {
				JsonObject jsonObjParm = jsonArrayTopicData.get(i).getAsJsonObject();
				String paraCodeName=jsonObjParm.get("Codename").getAsJsonArray()
						.get(0).getAsJsonObject().get("Content").getAsString();
                if(SubscribeParameters.getSubscribeParameters().subParameterMap.containsKey(paraCodeName)) {
                	topiNode.add(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCodeName));
                }else {
                	DataParameter parameter = new DataParameter(jsonObjParm.getAsJsonArray()
    						.get(0).getAsJsonObject().get("Content").getAsString());
                	parameter.setCodeName(paraCodeName);
                	parameter.setIsSubscribe(false);
                	parameter.setRange(jsonObjParm.get("Range").getAsJsonArray()
    						.get(0).getAsJsonObject().get("Content").getAsString());
                	parameter.setUnit(jsonObjParm.get("Unit").getAsJsonArray()
    						.get(0).getAsJsonObject().get("Content").getAsString());
                	topiNode.add(parameter);
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
	public TreeDataModel getPageData() {
		JsonParser parse = new JsonParser();
		TreeDataModel itemRoot=null;
		try {
			
			InputStream input=getClass().getResourceAsStream("/page.json");
			 byte b[] = new byte[40960] ; 
			 int len = input.read(b) ;
			JsonObject jsonObjRoot = (JsonObject) parse.parse(new String(b,0,len));
			JsonObject jsonObjData = jsonObjRoot.get("data").getAsJsonObject(); 
			JsonObject jsonObjPage=jsonObjData.get("Page").getAsJsonObject();
			itemRoot=new TreeDataModel(new Page(jsonObjPage.get("Name").getAsString()));
			JsonArray jsonArrayContainer=jsonObjPage.get("Container").getAsJsonArray();
			for(int i=0;i<jsonArrayContainer.size();i++) {
				JsonObject jsonObjContainer=jsonArrayContainer.get(i).getAsJsonObject();
				Container container=new Container(jsonObjContainer.get("Name").getAsString());
				String containerType=jsonObjContainer.get("Type").getAsString();
				container.setType(containerType);
				container.setHeight(jsonObjContainer.get("Height").getAsString());
				container.setWidth(jsonObjContainer.get("Width").getAsString());
				TreeDataModel containerModel=new TreeDataModel(container);
				itemRoot.add(containerModel);
				JsonArray jsonArrayTopic=jsonObjContainer.get("Topic").getAsJsonArray();
				for(int j=0;j<jsonArrayTopic.size();j++) {
					JsonObject jsonObjTopic=jsonArrayTopic.get(j).getAsJsonObject();
					JsonArray jsonArrayTopicData=jsonObjTopic.get("Data").getAsJsonArray();
					for (int k = 0; k < jsonArrayTopicData.size(); k++) {
						JsonObject jsonObjTopicData=jsonArrayTopicData.get(k).getAsJsonObject();
						if (containerType.equals("data")) {
							DataParameter parameter = new DataParameter(jsonObjTopicData.get("Name").getAsJsonArray()
									.get(0).getAsJsonObject().get("Content").getAsString());
							String codeName=jsonObjTopicData.get("Codename").getAsJsonArray().get(0)
									.getAsJsonObject().get("Content").getAsString();
							parameter.setCodeName(codeName);
							parameter.setUnit(jsonObjTopicData.get("Unit").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
							parameter.setRange(jsonObjTopicData.get("Range").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
							parameter.setIsSubscribe(true);
							SubscribeParameters.getSubscribeParameters().subParameterMap.put(codeName, parameter);
							containerModel.add(parameter);
						}else if(containerType.equals("image")) {
							ImageParameter parameter = new ImageParameter(jsonObjTopicData.get("Name").getAsJsonArray()
									.get(0).getAsJsonObject().get("Content").getAsString());
							parameter.setCodeName(jsonObjTopicData.get("Codename").getAsJsonArray().get(0)
									.getAsJsonObject().get("Content").getAsString());
							parameter.setUnit(jsonObjTopicData.get("Unit").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
							containerModel.add(parameter);
							parameter.setRange(jsonObjTopicData.get("Range").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
						}else if(containerType.equals("curve")) {
							CurveParameter parameter = new CurveParameter(jsonObjTopicData.get("Codename").getAsJsonArray()
									.get(0).getAsJsonObject().get("Content").getAsString());
							parameter.setDashstyle(jsonObjTopicData.get("Dashstyle").getAsJsonArray().get(0)
									.getAsJsonObject().get("Content").getAsString());
							parameter.setLinewidth(jsonObjTopicData.get("Linewidth").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
							parameter.setColor(jsonObjTopicData.get("Color").getAsJsonArray().get(0).getAsJsonObject()
									.get("Content").getAsString());
							containerModel.add(parameter);
						}
					}
				}
			}
			
		}catch (JsonIOException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Look, an Information Dialog");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemRoot;
	}
}
