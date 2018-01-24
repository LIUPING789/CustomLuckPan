package com.knight.customluckpan.util;

import android.util.Log;

/**
 * description: ${TODO}
 * autour: Knight
 * new date: 24/01/2018 on 15:40
 * e-mail: 37442216knight@gmail.com
 * update: 24/01/2018 on 15:40
 * version: v 1.0
 */
public class Logger {
    /**
     * log tag
     */
    private static String TAG = "MyApplication";// application name

    //TODO 配置Log打开或关闭
    /**
     * debug or not
     */
    private static boolean debug = true;

    private static Logger instance = new Logger();

    private Logger() {

    }

    public static Logger getLogger() {
        return instance;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + st.getFileName() + ":" + st.getLineNumber() + "]";
        }

        return null;
    }

    private String createMessage(String msg) {
        String functionName = getFunctionName();
        String message = (functionName == null ? msg : (functionName + " - " + msg));
        return message;
    }

    /**
     * log.i
     */
    public void i(String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.i(TAG, message);
        }
    }

    /**
     * log.v
     */
    public void v(String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.v(TAG, message);
        }
    }

    /**
     * log.d
     */
    public void d(String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.d(TAG, message);
        }
    }

    /**
     * log.e
     */
    public void e(String msg) {
//		if (debug) {
        String message = createMessage(msg);
        Log.e(TAG, message);
//		}
    }

    /**
     * log.error
     */
    public void error(Exception e) {
        if (debug) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = e.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + e + "\r\n");
            } else {
                sb.append(e + "\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":" + st.getLineNumber() + " ]\r\n");
                    }
                }
            }

            Log.e(TAG, sb.toString());
        }
    }

    /**
     * log.d
     */
    public void w(String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.w(TAG, message);
        }
    }

    public void setTag(String tag) {
        this.TAG = tag;
    }

    /**
     * set debug
     */
    public static void setDebug(boolean d) {
        debug = d;
    }

    public static boolean isDebug() {
        return debug;
    }
}
