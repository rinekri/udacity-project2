package ru.rinekri.udacitypopularmovies.ui.utils;

import android.support.annotation.Nullable;

import java8.util.function.Consumer;


public final class LangUtils {
  public LangUtils() {
    throw new RuntimeException("static class");
  }

  public static <T> void safeInvokeOrThrow(@Nullable T target, Consumer<T> safeFunc) {
    if (target != null) {
      safeFunc.accept(target);
    } else {
      throw new RuntimeException("target shouldn't be null");
    }
  }

  public static <T> void safeInvoke(@Nullable T target, Consumer<T> safeFunc) {
    if (target != null) {
      safeFunc.accept(target);
    }
  }

  public static void check(boolean condition) {
    if (!condition) {
      throw new RuntimeException("Unsupported operation exception");
    }
  }
}
