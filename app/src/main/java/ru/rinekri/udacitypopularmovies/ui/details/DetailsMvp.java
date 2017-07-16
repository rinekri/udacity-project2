package ru.rinekri.udacitypopularmovies.ui.details;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieCharacter;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieTitle;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpView;

class DetailsMvp {
  public interface View extends BaseMvpView<PM> {
  }

  static class Router {
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

    public static PM create(@NonNull MovieShortInfo movieShortInfo,
                            @NonNull List<MovieVideo> movieVideos,
                            @NonNull List<MovieReview> movieReviews,
                            @NonNull List<MovieTitle> movieTitles,
                            @NonNull List<MovieCharacter> movieCharacters,
                            @NonNull List<MovieInfo> recommendedMovies,
                            @NonNull List<MovieInfo> similarMovies) {
      return new AutoValue_DetailsMvp_PM(
        movieShortInfo,
        movieVideos,
        movieReviews,
        movieTitles,
        movieCharacters,
        recommendedMovies,
        similarMovies);
    }
  }
}