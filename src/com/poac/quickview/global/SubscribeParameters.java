package com.poac.quickview.global;

import java.util.ArrayList;
import java.util.HashMap;

import com.poac.quickview.controller.IController;
import com.poac.quickview.model.DataParameter;

public class SubscribeParameters {
	private static SubscribeParameters subParameters=null;
	public HashMap<String,DataParameter> subParameterMap=
			new HashMap<String,DataParameter>();  //记录参数代号对应参数对象,主要确保同一个参数名只能实例化一个对象（不同容器使用同一参数对象）
	public static SubscribeParameters getSubscribeParameters() {
		if(subParameters==null) {
			subParameters=new SubscribeParameters();
		}
		return subParameters;
	}	
}
