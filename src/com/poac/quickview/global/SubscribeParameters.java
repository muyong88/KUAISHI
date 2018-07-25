package com.poac.quickview.global;

import java.util.HashMap;

import com.poac.quickview.model.DataParameter;

public class SubscribeParameters {
	private static SubscribeParameters subParameters=null;
	public HashMap<String,DataParameter> subParameterMap=new HashMap<String,DataParameter>();
	public static SubscribeParameters getSubscribeParameters() {
		if(subParameters==null) {
			subParameters=new SubscribeParameters();
		}
		return subParameters;
	}	
}
