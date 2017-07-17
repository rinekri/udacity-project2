package ru.rinekri.udacitypopularmovies.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.util.List;

import ru.rinekri.udacitypopularmovies.ApplicationComponent;
import ru.rinekri.udacitypopularmovies.MyApplication;
import ru.rinekri.udacitypopularmovies.R;

public final class ContextUtils {
  public ContextUtils() {
    throw new RuntimeException("static class");
  }

  public static ApplicationComponent appComponent(Context context) {
    return ((MyApplication) context.getApplicationContext()).appComponent;
  }

  public static void showToast(@NonNull Context context, @NonNull String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public static void showToast(@NonNull Context context, @StringRes int messageRes) {
    showToast(context, context.getString(messageRes));
  }

  public static void openYoutubeVideo(Context context, @NonNull String videoId) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
    intent.putExtra("VIDEO_ID", videoId);
    context.startActivity(intent);
  }

  public static void openWeb(@NonNull Context context, @NonNull String url) {
    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    maybeStartActivity(context, sendIntent, true);
  }

  private static boolean maybeStartActivity(@NonNull Context context,
                                            @NonNull Intent intent,
                                            boolean chooser) {
    Intent resultIntent = intent;
    if (hasHandler(context, intent)) {
      if (chooser) {
        resultIntent = Intent.createChooser(intent, null);
      }

      context.startActivity(resultIntent);
      return true;
    } else {
      showToast(context, R.string.share_error);
      return false;
    }
  }

  private static boolean hasHandler(@NonNull Context context,
                                    Intent intent) {
    List handlers = context.getPackageManager().queryIntentActivities(intent, 0);
    return !handlers.isEmpty();
  }
}