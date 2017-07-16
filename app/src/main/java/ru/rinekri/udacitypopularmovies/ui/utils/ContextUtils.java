package ru.rinekri.udacitypopularmovies.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import ru.rinekri.udacitypopularmovies.ApplicationComponent;
import ru.rinekri.udacitypopularmovies.MyApplication;

public final class ContextUtils {
  public ContextUtils() {
    throw new RuntimeException("static class");
  }

  public static ApplicationComponent appComponent(Context context) {
    return ((MyApplication) context.getApplicationContext()).appComponent;
  }

  public static void showToast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public static void openYoutubeVideo(Context context, @NonNull String videoId) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
    intent.putExtra("VIDEO_ID", videoId);
    context.startActivity(intent);
  }
}