package com.poac.quickview.global;

import java.util.ArrayList;
import java.util.HashMap;

import com.poac.quickview.controller.IController;
import com.poac.quickview.model.DataParameter;

public class SubscribeParameters {
	private static SubscribeParameters subParameters=null;
	public HashMap<String,DataParameter> subParameterMap=
			new HashMap<String,DataParameter>();  //��¼�������Ŷ�Ӧ��������,��Ҫȷ��ͬһ��������ֻ��ʵ����һ�����󣨲�ͬ����ʹ��ͬһ��������
	public static SubscribeParameters getSubscribeParameters() {
		if(subParameters==null) {
			subParameters=new SubscribeParameters();
		}
		return subParameters;
	}	
}
