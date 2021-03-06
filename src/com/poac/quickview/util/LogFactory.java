package com.poac.quickview.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class LogFactory {

    // 全局Log的名称
    public static final String LOG_NAME = "Global";

    // 这个文件路径必须存在，不存在会报错，并不会自动创建
    public static  String LOG_FOLDER ;

    // log文件路径
    private static String log_filepath;

    // 静态变量globleLog
    private static Logger globalLog=null;

    static {
    	LOG_FOLDER=(new File("")).getAbsolutePath() + File.separator + "Log";
    	File file=new File(LOG_FOLDER);
    	if(!file.exists())
    		file.mkdir();
        // 加载类的时候初始化log文件全路径，这里的文件名称是JDKLog_+时间戳+.log
        log_filepath = LOG_FOLDER + File.separator + "JDKLog_" + LogUtil.getCurrentDateStr(LogUtil.DATE_PATTERN_NOMARK)
                + ".log";      
    }

    /**
     * 初始化全局Logger
     * 
     * @return
     */
    private static Logger initGlobalLog() {

        // 获取Log
        Logger log = Logger.getLogger(LOG_NAME);

        // 为log设置全局等级
        log.setLevel(Level.ALL);

        // 添加控制台handler
        LogUtil.addConsoleHandler(log, Level.INFO);

        // 添加文件输出handler
        LogUtil.addFileHandler(log, Level.INFO, log_filepath);

        // 设置不适用父类的handlers，这样不会在控制台重复输出信息
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