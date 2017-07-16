package ru.rinekri.udacitypopularmovies.ui.base.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.google.auto.value.AutoValue;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ru.rinekri.udacitypopularmovies.BuildConfig;
import ru.rinekri.udacitypopularmovies.R;

@AutoValue
public abstract class ErrorConfig {
  @StringRes
  @NonNull
  public abstract Integer errorMessageRes();
  @Nullable
  public abstract String errorMessageText();

  public String resolveTitle(Context context) {
    String error;
    if (errorMessageText() != null) {
      error = context.getString(R.string.error_default);
      if (BuildConfig.DEBUG) {
        error += " (" + errorMessageText() + ")";
      }
    } else if (errorMessageRes() != 0) {
      error = context.getString(errorMessageRes());
    } else {
      error = context.getString(R.string.error_default);
    }
    return error;
  }

  public static Builder builder() {
    return new AutoValue_ErrorConfig.Builder()
      .errorMessageRes(0)
      .errorMessageText(null);
  }

  //TODO: Add CallAdapter to extract server errors
  public static ErrorConfig createFrom(Throwable error) {
    if (error instanceof UnknownHostException || error instanceof ConnectException) {
      return builder().errorMessageRes(R.string.error_no_network).build();
    } else if (error instanceof SocketTimeoutException) {
      return builder().errorMessageRes(R.string.error_network_timeout).build();
    }
    return builder().errorMessageText(error.getMessage()).build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder errorMessageRes(@StringRes @NonNull Integer value);
    public abstract Builder errorMessageText(@Nullable String value);
    public abstract ErrorConfig build();
  }
}