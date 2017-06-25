package ru.rinekri.udacitypopularmovies.ui.base.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AsyncRequestWrapper<D> {
  @Nullable
  public abstract D data();
  @Nullable
  public abstract Throwable error();

  private static <D> AsyncRequestWrapper create(@Nullable D data,
                                                @Nullable Throwable error) {
    return new AutoValue_AsyncRequestWrapper<D>(data, error);
  }

  public static <D> AsyncRequestWrapper error(@NonNull Throwable error) {
    return create(null, error);
  }

  public static <D> AsyncRequestWrapper content(@NonNull D data) {
    return create(data, null);
  }
}