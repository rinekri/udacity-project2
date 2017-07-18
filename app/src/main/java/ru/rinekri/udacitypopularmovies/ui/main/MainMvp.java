package ru.rinekri.udacitypopularmovies.ui.main;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpView;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.details.DetailsActivity;
import ru.rinekri.udacitypopularmovies.ui.details.MovieShortInfo;
import ru.rinekri.udacitypopularmovies.ui.utils.ViewUtils;

class MainMvp {
  public interface View extends BaseMvpView<PM> {
    void showInitContent(IM data);
  }

  @AutoValue
  abstract public static class PM implements Parcelable {
    @NonNull
    abstract List<MovieInfo> movies();

    public static PM create(@NonNull List<MovieInfo> movies) {
      return new AutoValue_MainMvp_PM(movies);
    }
  }

  @AutoValue
  abstract public static class IM implements Parcelable {
    @NonNull
    abstract List<MovieSortType> sortTypes();
    @NonNull
    abstract MovieSortType initSortType();

    public static IM create(@NonNull List<MovieSortType> sortTypes,
                            MovieSortType initSortType) {
      return new AutoValue_MainMvp_IM(sortTypes, initSortType);
    }
  }

  static class Router {
    private Context context;
    private android.view.View messageView;

    Router(Context context, android.view.View messageView) {
      this.context = context;
      this.messageView = messageView;
    }

    void showDetailInfo(MovieShortInfo movieInfo) {
      DetailsActivity.start(context, movieInfo);
    }

    void showMessage(String text) {
      ViewUtils.showSnack(messageView, text);
    }
  }
}