package csumissu.fakewechat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import csumissu.fakewechat.util.IoUtils;

/**
 * @author sunyaxi
 * @date 2016/6/22
 */
public class AppConfig {

    private static final String TAG = AppConfig.class.getSimpleName();

    public static final String KEY_USER = "KEY_USER";
    public static final String KEY_CHECK_UPDATE = "KEY_CHECK_UPDATE";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";

    private static Context sContext;
    private static AppConfig sInstance;
    private static final String APP_CONFIG = "config";
    private static ReadWriteLock mLock = new ReentrantReadWriteLock();

    private AppConfig() {
    }

    public synchronized static AppConfig init(Context context) {
        if (sInstance == null) {
            sInstance = new AppConfig();
            sContext = context.getApplicationContext();
        }
        return sInstance;
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String defaultValue) {
        Properties props = getProps();
        return props.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ignore) {
            }
        }
        return defaultValue;
    }

    public static long getLong(String key, long defaultValue) {
        String value = get(key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException ignore) {
            }
        }
        return defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (!TextUtils.isEmpty(value)) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    public static void set(Properties ps) {
        Properties props = getProps();
        props.putAll(ps);
        setProps(props);
    }

    public static void set(String key, String value) {
        Properties props = getProps();
        props.setProperty(key, value);
        setProps(props);
    }

    public static void remove(String... key) {
        Properties props = getProps();
        for (String k : key) {
            props.remove(k);
        }
        setProps(props);
    }

    private static Properties getProps() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            mLock.readLock().lock();
            fis = sContext.openFileInput(APP_CONFIG);
            props.load(fis);
        } catch (IOException e) {
            Log.w(TAG, "get props failed", e);
        } finally {
            mLock.readLock().unlock();
            IoUtils.close(fis);
        }
        return props;
    }

    private static void setProps(Properties props) {
        FileOutputStream fos = null;
        try {
            mLock.writeLock().lock();
            fos = sContext.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);
            props.store(fos, null);
        } catch (IOException e) {
            Log.w(TAG, "set props failed");
        } finally {
            mLock.writeLock().unlock();
            IoUtils.close(fos);
        }
    }

}
