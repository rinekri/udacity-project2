package ru.rinekri.udacitypopularmovies.ui.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public final class ViewUtils {
  public ViewUtils() {
    throw new RuntimeException("Static class");
  }

  public static void showSnack(View targetView, String message) {
    Snackbar.make(targetView, message, Snackbar.LENGTH_LONG).show();
  }

  public static void setVisibility(boolean visible, View... views) {
    for (View view : views) {
      view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
  }
}