package ru.rinekri.udacitypopularmovies.ui.details;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpView;

public class DetailsMvp {
  public interface View extends BaseMvpView<PM> {
  }

  @AutoValue
  abstract public static class PM implements Parcelable {
    abstract MovieShortInfo movieInfo();

    public static PM create(@NonNull MovieShortInfo movieShortInfo) {
      return new AutoValue_DetailsMvp_PM(movieShortInfo);
    }
  }
}