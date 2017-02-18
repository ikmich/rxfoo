package org.ikmich.rxfoo;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getContext() {
        return app.getApplicationContext();
    }

    public static void toast(Object o) {
        Toast.makeText(app.getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Object o) {
        Toast.makeText(app.getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
    }
}
