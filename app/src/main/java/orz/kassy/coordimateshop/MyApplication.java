package orz.kassy.coordimateshop;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by kashimoto on 2015/12/05.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "nG1gXZThYiQreyZatVoizB0KaPvHUhxzAJ12hnfy", "dkxvpmmSyqNUpzD7bujfMa7ebOWn3AD27DXKCDU4");
    }
}
