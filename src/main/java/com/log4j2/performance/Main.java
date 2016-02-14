package com.log4j2.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static {
        System.setProperty("AsyncLogger.RingBufferSize", String.valueOf(1024 * 1024));
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        System.setProperty("AsyncLogger.ThreadNameStrategy", "CACHED");// 如果在线程池中通过Thread.setName()，这里需要修改为UNCACHED
        System.setProperty("log4j.Clock", "CachedClock");
    }
    private static ThreadLocal<Logger> threadLocalLogger = new ThreadLocal<Logger>() {
        protected Logger initialValue() {
            return LogManager.getLogger(Main.class.toString() + Thread.currentThread().getId());
        }
    };

    public static void main(String[] args) {
        Logger logger = threadLocalLogger.get();
        logger.info("Start");
    }

}
