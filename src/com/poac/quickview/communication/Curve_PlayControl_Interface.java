package com.poac.quickview.communication;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.WebSocket.READYSTATE;

import com.poac.quickview.util.LogFactory;

public class Curve_PlayControl_Interface {
	private String url;
	private WebSocketClientCustom client;
	public Curve_PlayControl_Interface(String url) {
		this.url=url;
	}
	public  String initConnection(String username,String page,String container) {
		try {
			client = new WebSocketClientCustom(new URI(url));
			client.connect();
			if (!client.getReadyState().equals(READYSTATE.OPEN)) {
				LogFactory.getGlobalLog().warning("WebSocket ReadyState is not open, MSG cannot send!");
				return null;
			}
			client.send("Open;"+username+";"+page+";"+container);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client.getReceiveMsg();
	}
	public String dataRequest(String startime,String endtime) {
		client.send("Request;"+startime+";"+endtime);
		return client.getReceiveMsg();
	}
}
