package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.google.auto.value.AutoValue;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieCharacter;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.MovieKeyword;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieTitle;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpView;
import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;
import ru.rinekri.udacitypopularmovies.ui.utils.ViewUtils;

class DetailsMvp {
  public interface View extends BaseMvpView<PM> {
  }

  static class Router {
    @NonNull
    private Context context;
    @NonNull
    private android.view.View messageView;

    Router(@NonNull Context context,
           @NonNull android.view.View messageView) {
      this.context = context;
      this.messageView = messageView;
    }

    void showTrailer(@NonNull MovieVideo movieVideo) {
      ContextUtils.openYoutubeVideo(context, movieVideo.id());
    }

    void showReview(@NonNull MovieReview movieReview) {
      ContextUtils.openWeb(context, movieReview.url());
    }

    void showMessage(@NonNull String text) {
      ViewUtils.showSnack(messageView, text);
    }

    void showMessage(@StringRes int textRes, String... options) {
      ViewUtils.showSnack(messageView, context.getString(textRes, options));
    }
  }

  @AutoValue
  abstract public static class PM implements Parcelable {
    abstract MovieShortInfo movieInfo();
    abstract List<MovieVideo> movieVideos();
    abstract List<MovieReview> movieReviews();
    abstract List<MovieTitle> movieTitles();
    abstract List<MovieCharacter> movieCharacters();
    abstract List<MovieInfo> recommendedMovies();
    abstract List<MovieInfo> similarMovies();
    abstract List<MovieKeyword> keywords();
    abstract boolean isInFavorite();

    public abstract PM withInFavorite(boolean isInFavorite);

    public static PM create(@NonNull MovieShortInfo movieShortInfo,
                            @NonNull List<MovieVideo> movieVideos,
                            @NonNull List<MovieReview> movieReviews,
                            @NonNull List<MovieTitle> movieTitles,
                            @NonNull List<MovieCharacter> movieCharacters,
                            @NonNull List<MovieInfo> recommendedMovies,
                            @NonNull List<MovieInfo> similarMovies,
                            @NonNull List<MovieKeyword> keywords,
                            boolean isInFavorites) {
      return new AutoValue_DetailsMvp_PM(
        movieShortInfo,
        movieVideos,
        movieReviews,
        movieTitles,
        movieCharacters,
        recommendedMovies,
        similarMovies,
        keywords,
        isInFavorites);
    }
  }
}