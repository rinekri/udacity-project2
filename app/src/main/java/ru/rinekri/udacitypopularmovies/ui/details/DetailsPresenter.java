package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;

import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

@InjectViewState
public class DetailsPresenter extends BaseMvpPresenter<DetailsMvp.PM, DetailsMvp.View> {
  @Nullable
  private DetailsMvp.Router router;
  private SyncInteractor<MovieShortInfo, DetailsMvp.PM> inputInteractor;
  @NonNull
  private MovieShortInfo movieShortInfo;

  public DetailsPresenter(@NonNull MovieShortInfo movieShortInfo,
                          @NonNull SyncInteractor<MovieShortInfo, DetailsMvp.PM> inputInteractor) {
    this.movieShortInfo = movieShortInfo;
    this.inputInteractor = inputInteractor;
  }

  public void setRouter(@Nullable DetailsMvp.Router router) {
    this.router = router;
  }

  @Override
  public void destroyView(DetailsMvp.View view) {
    router = null;
    super.destroyView(view);
  }

  @Override
  protected void onFirstViewAttach() {
    super.onFirstViewAttach();
    elceAsyncRequestL(() -> inputInteractor.getData(movieShortInfo));
  }
}