package com.poac.quickview.global;

import java.util.HashMap;
import com.poac.quickview.model.DataParameter;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

public class SubscribeParameters {
	private static SubscribeParameters subParameters=null;
	public HashMap<String,DataParameter> subParameterMap=
			new HashMap<String,DataParameter>();  //��¼�������Ŷ�Ӧ��������,��Ҫȷ��ͬһ��������ֻ��ʵ����һ�����󣨲�ͬ����ʹ��ͬһ��������
	public HashMap<String,ObjectProperty<Image>> page_Container_ImageProperty
	                                             =new HashMap<String,ObjectProperty<Image>>();
	public HashMap<String,ObjectProperty<MediaPlayer>> page_Container_MediaPlayerProperty
                                                 =new HashMap<String,ObjectProperty<MediaPlayer>>();
	public static SubscribeParameters getSubscribeParameters() {
		if(subParameters==null) {
			subParameters=new SubscribeParameters();
		}
		return subParameters;
	}	
}
