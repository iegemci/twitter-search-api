package vngrs.enesgemci.tweetsearch.util;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

/**
 * Created by enesgemci on 5/21/13
 */
public final class L {

    private static boolean debuggable = false;

    private static final String TAG = "[Mateli]";
    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int ASSERT = Log.ASSERT;
    private static final int DISABLE = 1024;
    //    private static int _logLevel;
    private static String _tag;

    private L() {
    }

    public static void init(boolean debug) {
        debuggable = debug;
    }

    private static boolean shouldLog() {
//        boolean debug = BuildConfig.DEVELOPMENT_MODE;
//        boolean log = debug || (!debug && priority >= getLogLevel());
//        getLogLevel();
        return debuggable;
    }

    private static void log(int priority, Object obj) {
        String msg;

        if (obj instanceof Throwable) {
            StringWriter sw = new StringWriter();
            ((Throwable) obj).printStackTrace(new PrintWriter(sw));
            msg = sw.toString();
        } else {
            msg = String.valueOf(obj);

            if (isEmpty(msg)) {
                msg = "\"\"";
            }
        }

        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";

        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();

            if (!clazz.equals(L.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }

        try {
            String totalMsg = String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
            Log.println(priority, getTag(), totalMsg);
        } catch (Exception e) {
            e(e);
        }
    }

    private static void log(int priority, String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";

        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();

            if (!clazz.equals(L.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }

        try {
            String totalMsg = String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
            Log.println(priority, getTag(), totalMsg);
        } catch (Exception e) {
            e(e);
        }
    }

//    private static int getLogLevel() {
//        if (_logLevel == 0) {
//            Context ctx = MInjector.getApplicationContext();
//
//            if (ctx != null) {
//                String logLevelStr = null;
//
//                try {
//                    Bundle metaData = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA).metaData;
//                    logLevelStr = metaData.getString(ManifestMeta.LOG_LEVEL).toLowerCase().trim();
//                } catch (Exception e) {
//                }
//
//                if (ManifestMeta.VERBOSE.equals(logLevelStr)) {
//                    _logLevel = VERBOSE;
//                } else if (ManifestMeta.DEBUG.equals(logLevelStr)) {
//                    _logLevel = DEBUG;
//                } else if (ManifestMeta.INFO.equals(logLevelStr)) {
//                    _logLevel = INFO;
//                } else if (ManifestMeta.WARN.equals(logLevelStr)) {
//                    _logLevel = WARN;
//                } else if (ManifestMeta.ERROR.equals(logLevelStr)) {
//                    _logLevel = ERROR;
//                } else if (ManifestMeta.ASSERT.equals(logLevelStr)) {
//                    _logLevel = ASSERT;
//                } else if (ManifestMeta.DISABLE.equals(logLevelStr)) {
//                    _logLevel = DISABLE;
//                } else {
//                    _logLevel = DISABLE;
//                    Log.i(TAG, "No valid <meta-data android:name=\""
//                            + ManifestMeta.LOG_LEVEL
//                            + "\" android:value=\"...\"/> in AndroidManifest.xml. Logging disabled.");
//                }
//            }
//        }
//
//        return (_logLevel != 0) ? _logLevel : DISABLE;
//    }

    private static String getTag() {
        if (shouldLog()) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[5];
            String c = caller.getClassName();
            String className = c.substring(c.lastIndexOf(".") + 1, c.length());
            StringBuilder sb = new StringBuilder(5);
            sb.append(className);
            sb.append(".");
            sb.append(caller.getMethodName());
            sb.append("():");
            sb.append(caller.getLineNumber());

            return sb.toString();
        } else {
            if (_tag == null) {
                Context ctx = MInjector.getApplicationContext();

                if (ctx != null) {
                    _tag = ctx.getPackageName();
                }
            }

            return (_tag != null) ? _tag : TAG;
        }
    }

    public static void v(Object obj) {
        if (shouldLog()) {
            log(VERBOSE, obj);
        }
    }

    public static void v(String format, Object... args) {
        if (shouldLog()) {
            log(VERBOSE, format, args);
        }
    }

    public static void d(Object obj) {
        if (shouldLog()) {
            log(DEBUG, obj);
        }
    }

    public static void d(String format, Object... args) {
        if (shouldLog()) {
            log(DEBUG, format, args);
        }
    }

    public static void i(Object obj) {
        if (shouldLog()) {
            log(INFO, obj);
        }
    }

    public static void i(String format, Object... args) {
        if (shouldLog()) {
            log(INFO, format, args);
        }
    }

    public static void w(Object obj) {
        if (shouldLog()) {
            log(WARN, obj);
        }
    }

    public static void w(String format, Object... args) {
        if (shouldLog()) {
            log(WARN, format, args);
        }
    }

    public static void e(Object obj) {
        if (shouldLog()) {
            log(ERROR, obj);
        }
    }

    public static void e(String format, Object... args) {
        if (shouldLog()) {
            log(ERROR, format, args);
        }
    }

    public static void wtf(Object obj) {
        if (shouldLog()) {
            log(ASSERT, obj);
        }
    }

    public static void wtf(String format, Object... args) {
        if (shouldLog()) {
            log(ASSERT, format, args);
        }
    }

    public static void wtf() {
        if (shouldLog()) {
            log(ASSERT, "WTF");
        }
    }
}