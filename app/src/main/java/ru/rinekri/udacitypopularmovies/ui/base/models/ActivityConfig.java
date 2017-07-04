package ru.rinekri.udacitypopularmovies.ui.base.models;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.google.auto.value.AutoValue;

import ru.rinekri.udacitypopularmovies.R;

@AutoValue
public abstract class ActivityConfig {
  //TODO: Add logic to inflate shell
  @NonNull
  public abstract Integer contentViewRes();
  @NonNull
  @IdRes
  public abstract Integer contentContainerId();
  @StringRes
  @NonNull
  public abstract Integer titleRes();
  @NonNull
  public abstract String titleText();
  @NonNull
  public abstract Boolean useBackButton();
  @NonNull
  public abstract Integer elceContainerId();
  @NonNull
  public abstract Integer elceErrorViewId();
  @NonNull
  public abstract Integer elceEmptyViewId();
  @NonNull
  public abstract Integer elceProgressViewId();
  @NonNull
  public abstract Boolean alignElceCenter();

  public static Builder builder() {
    return new AutoValue_ActivityConfig.Builder()
      .titleRes(0)
      .titleText("")
      .elceContainerId(R.id.elce_container)
      .elceEmptyViewId(R.id.elce_empty_view)
      .elceErrorViewId(R.id.elce_error_view)
      .elceProgressViewId(R.id.elce_progress_view)
      .contentContainerId(R.id.content_container)
      .alignElceCenter(true)
      .useBackButton(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder contentViewRes(@LayoutRes @NonNull Integer value);
    public abstract Builder contentContainerId(@IdRes @NonNull Integer value);
    public abstract Builder titleRes(@StringRes @NonNull Integer value);
    public abstract Builder titleText(@NonNull String value);
    public abstract Builder useBackButton(@NonNull Boolean use);
    public abstract Builder elceErrorViewId(@NonNull @IdRes Integer id);
    public abstract Builder elceEmptyViewId(@NonNull @IdRes Integer id);
    public abstract Builder elceProgressViewId(@NonNull @IdRes Integer id);
    public abstract Builder elceContainerId(@NonNull @IdRes Integer id);
    public abstract Builder alignElceCenter(@NonNull Boolean shouldAlign);
    public abstract ActivityConfig build();
  }
}