package ru.rinekri.udacitypopularmovies.ui.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

public class DrawableUtils {

  public static void tintBackgroundRes(@Nullable View view, @ColorRes int colorRes) {
    if (view == null) return;
    tintBackgroundInt(view, ContextCompat.getColor(view.getContext(), colorRes));
  }

  public static void tintBackgroundInt(@Nullable View view, @ColorInt int colorInt) {
    if (view == null) return;
    view.setBackground(withTint(view.getBackground(), colorInt));
  }

  @NonNull
  public static Drawable withTint(@NonNull Drawable drawable, @ColorInt int colorInt) {
    Drawable mutateDrawable = drawable.mutate();
    DrawableCompat.setTint(DrawableCompat.wrap(mutateDrawable), colorInt);
    return mutateDrawable;
  }
}