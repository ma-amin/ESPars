package radioestekhdam.com.espars;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class EsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
