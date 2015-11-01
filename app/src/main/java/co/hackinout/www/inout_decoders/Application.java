package co.hackinout.www.inout_decoders;

import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseObject;
import io.fabric.sdk.android.Fabric;

/**
 * Created by meet on 31/10/15.
 */
public class Application extends android.app.Application {

    //Debug Switch

    public static final boolean APPDEBUG = true;

    //Debug Tag

    public static final String APPTAG = "InOutDecoders";

    private static SharedPreferences preferences;

    private static ConfigHelper configHelper;

    public Application(){}

    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        //ParseObject.registerSubclass(CasePost.class);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "3mpbjonGIhuMvrW4IfynrMEBgIqUKpAnXX51DhlV", "AHVG3dSzWjzD1oFbPqfeXq46xIqCucC8UmKYUMGR");

        preferences = getSharedPreferences("com.parse.anywall", Context.MODE_PRIVATE);


        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }
}
