package com.poac.quickview.communication;

import com.poac.quickview.util.LogFactory;
/**
 * ������ṩ���沼�����ýӿ�
 *
 */
public class Laylout_Setting_Interface {
	/**
	 *  get method
	 */
	private static String getMethod(
			String url,
			String username,
			String password) {
		HTTPSConnection conn=new HTTPSConnection();
		conn.authorizeByPassword(username, password);
		conn.setURL(url);
		conn.GET();
		int statusCode = conn.getStatusCode();
		if (statusCode/100 != 2) {
			LogFactory.getGlobalLog().severe("Error response when getting status");
			return null;
		}else  return conn.getBodyAsString();
	}
	/**
	 *  post method
	 */
	private static String postMethod(
			String url,
			String username,
			String password) {
		HTTPSConnection conn=new HTTPSConnection();
		conn.authorizeByPassword(username, password);
		conn.setURL(url);
		conn.POST();
		int statusCode = conn.getStatusCode();
		if (statusCode/100 != 2) {
			LogFactory.getGlobalLog().severe("Error response when getting status");
			return null;
		}else  return conn.getBodyAsString();
	}
	/**
	 *  delete method
	 */
	private static String deleteMethod(
			String url,
			String username,
			String password) {
		HTTPSConnection conn=new HTTPSConnection();
		conn.authorizeByPassword(username, password);
		conn.setURL(url);
		conn.DELETE();
		int statusCode = conn.getStatusCode();
		if (statusCode/100 != 2) {
			LogFactory.getGlobalLog().severe("Error response when getting status");
			return null;
		}else  return conn.getBodyAsString();
	}
	/**
	 *  put method
	 */
	private static String putMethod(
			String url,
			String username,
			String password) {
		HTTPSConnection conn=new HTTPSConnection();
		conn.authorizeByPassword(username, password);
		conn.setURL(url);
		conn.PUT();
		int statusCode = conn.getStatusCode();
		if (statusCode/100 != 2) {
			LogFactory.getGlobalLog().severe("Error response when getting status");
			return null;
		}else  return conn.getBodyAsString();
	}
	/**
	 *  ��ȡĿ¼
	 */
	public static String getCatalogue(
			String domain,
			String username,
			String password) {
		String url=domain+"/v1/page?username="+username+"&page=Ŀ¼";
		return getMethod(url,username,password);
	}
	/**
	 *  ��ȡҳ��
	 */
	public static String getPage(
			String domain,
			String username,
			String password,
			String pageName) {
		String url=domain+"/v1/page?username="+username+"&page="+pageName;
		return getMethod(url,username,password);
	}
	/**
	 *  ���ҳ��
	 */
	public static String addPage (
			String domain,
			String username,
			String password,
			String capsule,
			String cabinet,
			String payload,
			String pageName){
		String url=domain+"/v1/page?username="+username+"&capsule="+capsule+"&cabinet="+cabinet+"&payload="+payload+"&page="+pageName;
		return postMethod(url,username,password);
	}
	/**
	 *  ɾ��Ŀ¼
	 */
	public static String deletePage(
			String domain,
			String username,
			String password,
			String pageName) {
		String url=domain+"/v1/page?username="+username+"&page="+pageName;
		return deleteMethod(url,username,password);
	}
	/**
	 *  �������
	 */
	public static String addContainer (
			String domain,
			String username,
			String password,
			String pageName,
			String containerName,
			String width,
			String height,
			String type){
		String url=domain+"/v1/container?username="+username+"&page="+pageName+"&container="+containerName+"&width="+width+"&height="+height+"&type="+type;
		return postMethod(url,username,password);
	}
	/**
	 *  ɾ������
	 */
	public static String deleteContainer(
			String domain,
			String username,
			String password,
			String pageName,
			String container) {
		String url=domain+"/v1/container?username="+username+"&page="+pageName+"&container="+container;
		return deleteMethod(url,username,password);
	}
	/**
	 *  ����������С
	 */
	public static String changeContainerSize(
			String domain,
			String username,
			String password,
			String pageName,
			String container,			
			String width,
			String height) {
		String url=domain+"/v1/containersize?username="+username+"&page="+pageName+"&container="+container+"&width="+width+"&height="+height;
		return putMethod(url,username,password);
	}
	/**
	 *  ��ȡ������ʵ������б�
	 */
	public static String getTopicList(
			String domain,
			String username,
			String password,
			String group,
			String type) {
		String url=domain+"/v1/topicindex?group="+group+"&type="+type;
		return getMethod(url,username,password);
	}
	/**
	 *  ��ȡĳ�������µ����в������Ƽ����ű�
	 */
	public static String getParametersByTopic(
			String domain,
			String username,
			String password,
			String topic) {
		String url=domain+"/v1/topic?topic="+topic;
		return getMethod(url,username,password);
	}
	/**
	 *  ��������˳��
	 */
	public static String changeParametersOrder(
			String domain,
			String username,
			String password,
			String page,
			String container,
			String codename,
			String rank) {
		String url=domain+"/v1/param?username="+username+"&page="+page+"&container="+container+"&codename="+codename+"&rank="+rank;
		return putMethod(url,username,password);
	}
	/**
	 *  ɾ������
	 */
	public static String deleteParameter(
			String domain,
			String username,
			String password,
			String page,
			String container,
			String param) {
		String url=domain+"/v1/container?username="+username+"&page="+page+"&container="+container+"&param="+param;
		return deleteMethod(url,username,password);
	}
	/**
	 *  �������
	 */
	public static String addCurve(
			String domain,
			String username,
			String password,
			String page,
			String container,
			String axisname) {
		String url=domain+"/v1/curve?username="+username+"&page="+page+"&container="+container+"&axisname="+axisname;
		return postMethod(url,username,password);
	}
}
