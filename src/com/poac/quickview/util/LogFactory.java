package com.poac.quickview.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class LogFactory {

    // ȫ��Log������
    public static final String LOG_NAME = "Global";

    // ����ļ�·��������ڣ������ڻᱨ���������Զ�����
    public static  String LOG_FOLDER ;

    // log�ļ�·��
    private static String log_filepath;

    // ��̬����globleLog
    private static Logger globalLog=null;

    static {
    	LOG_FOLDER=(new File("")).getAbsolutePath() + File.separator + "Log";
    	File file=new File(LOG_FOLDER);
    	if(!file.exists())
    		file.mkdir();
        // �������ʱ���ʼ��log�ļ�ȫ·����������ļ�������JDKLog_+ʱ���+.log
        log_filepath = LOG_FOLDER + File.separator + "JDKLog_" + LogUtil.getCurrentDateStr(LogUtil.DATE_PATTERN_NOMARK)
                + ".log";      
    }

    /**
     * ��ʼ��ȫ��Logger
     * 
     * @return
     */
    private static Logger initGlobalLog() {

        // ��ȡLog
        Logger log = Logger.getLogger(LOG_NAME);

        // Ϊlog����ȫ�ֵȼ�
        log.setLevel(Level.ALL);

        // ��ӿ���̨handler
        LogUtil.addConsoleHandler(log, Level.INFO);

        // ����ļ����handler
        LogUtil.addFileHandler(log, Level.INFO, log_filepath);

        // ���ò����ø����handlers�����������ڿ���̨�ظ������Ϣ
        log.setUseParentHandlers(false);

        return log;
    }

    public static Logger getGlobalLog() {
        if(globalLog==null) {
        	globalLog=initGlobalLog();
        }
    	return globalLog;
    }

}