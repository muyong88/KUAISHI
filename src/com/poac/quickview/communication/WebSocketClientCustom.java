package com.poac.quickview.communication;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.poac.quickview.util.LogFactory;
/**
 * This class aimed to supply some methods to easily build a WebSocket connection
 *
 */
public class WebSocketClientCustom extends WebSocketClient{
	private String receiveMsg;
	public WebSocketClientCustom(URI serverURI){
		super(serverURI,new Draft_17());
	}
	
	public WebSocketClientCustom(URI serverURI, Draft draft) {
		super( serverURI, draft );
	}
 

	@Override
	public void onClose( int code, String reason, boolean remote ) {
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame
		LogFactory.getGlobalLog().info( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
	}
 
	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		LogFactory.getGlobalLog().warning("make mistakes");
	}
 
	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		receiveMsg=message;
	}
 
	@Override
	public void onOpen(ServerHandshake arg0) {
		// TODO Auto-generated method stub
		LogFactory.getGlobalLog().info( "opened connection" );
	}	
	public String getReceiveMsg() {
		return receiveMsg;
	}

}