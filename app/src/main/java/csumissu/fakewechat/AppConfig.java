package csumissu.fakewechat;

import android.content.Context;
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
    private Context mContext;
    private static AppConfig sInstance;
    private static final String APP_CONFIG = "config";
    private ReadWriteLock mLock = new ReentrantReadWriteLock();

    private AppConfig(Context context) {
        mContext = context.getApplicationContext();
    }

    public synchronized static AppConfig getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppConfig(context);
        }
        return sInstance;
    }

    public String get(String key) {
        Properties props = getProps();
        return props.getProperty(key);
    }

    public void set(Properties ps) {
        Properties props = getProps();
        props.putAll(ps);
        setProps(props);
    }

    public void set(String key, String value) {
        Properties props = getProps();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String... key) {
        Properties props = getProps();
        for (String k : key) {
            props.remove(k);
        }
        setProps(props);
    }

    private Properties getProps() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            fis = mContext.openFileInput(APP_CONFIG);
            props.load(fis);
        } catch (IOException e) {
            Log.w(TAG, "get props failed", e);
        } finally {
            IoUtils.close(fis);
        }
        return props;
    }

    private void setProps(Properties props) {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);
            props.store(fos, null);
        } catch (IOException e) {
            Log.w(TAG, "set props failed");
        } finally {
            IoUtils.close(fos);
        }
    }

}
