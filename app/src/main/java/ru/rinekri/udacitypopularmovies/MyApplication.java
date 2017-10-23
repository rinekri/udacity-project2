package ru.rinekri.udacitypopularmovies;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {
  public ApplicationComponent appComponent;

  @Override
  public void onCreate() {
    setupAppComponent();
    setupLogs();
    setupCalligraphy();
    setupLeakCanary();
    super.onCreate();
  }

  private void setupLeakCanary() {
    LeakCanary.install(this);
  }

  private void setupAppComponent() {
    appComponent = DaggerApplicationComponent.builder()
      .applicationModule(new ApplicationModule(this))
      .build();
  }

  private void setupCalligraphy() {
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
      .setDefaultFontPath("fonts/Roboto-Regular.ttf")
      .setFontAttrId(R.attr.fontPath)
      .build());
  }

  private void setupLogs() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      Stetho.initializeWithDefaults(this);
    }
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}