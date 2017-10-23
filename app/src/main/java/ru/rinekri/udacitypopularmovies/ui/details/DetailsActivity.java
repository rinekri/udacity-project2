package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ru.rinekri.udacitypopularmovies.BuildConfig;
import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpActivity;
import ru.rinekri.udacitypopularmovies.ui.base.models.ActivityConfig;
import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;

public class DetailsActivity extends BaseMvpActivity<DetailsMvp.PM> implements DetailsMvp.View {
  private static final String EXTRA_MOVIE_SHORT_INFO = BuildConfig.APPLICATION_ID + ".extra_short_info";
  private static final String EXTRA_TITLE = BuildConfig.APPLICATION_ID + ".extra_title";

  public static void start(Context context,
                           @NonNull MovieShortInfo movieShortInfo) {
    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra(EXTRA_MOVIE_SHORT_INFO, movieShortInfo);
    intent.putExtra(EXTRA_TITLE, movieShortInfo.title());
    context.startActivity(intent);
  }

  @NonNull
  private String getStartTitle() {
    return getIntent().getStringExtra(EXTRA_TITLE);
  }

  @BindView(R.id.backdrop)
  ImageView moviePoster;
  @BindView(R.id.content_container)
  RecyclerView content;
  @BindView(R.id.favorites_button)
  FloatingActionButton favoritesButton;

  private DetailsContentController contentController;

  @InjectPresenter
  public DetailsPresenter presenter;

  @ProvidePresenter
  public DetailsPresenter providePresenter() {
    MovieShortInfo movieShortInfo = getIntent().getParcelableExtra(EXTRA_MOVIE_SHORT_INFO);
    MainServiceApi api = ContextUtils.appComponent(this).mainServiceApi();
    DetailsInputInteractor interactor = new DetailsInputInteractor(api);
    SQLiteOpenHelper dbHelper = ContextUtils.appComponent(this).databaseHelper();
    DetailsSaveFavoriteInteractor saveFavoriteInteractor = new DetailsSaveFavoriteInteractor(dbHelper);
    return new DetailsPresenter(movieShortInfo, saveFavoriteInteractor, interactor);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.setRouter(new DetailsMvp.Router(this, content));
  }

  @Override
  protected ActivityConfig provideActivityConfig() {
    return ActivityConfig.builder()
      .contentViewRes(R.layout.content_details)
      .titleText(getStartTitle())
      .useBackButton(true)
      .alignElceCenter(false)
      .build();
  }

  @Override
  protected void initView() {
    content.setLayoutManager(new LinearLayoutManager(this));
    contentController = new DetailsContentController(new DetailsContentController.Actions() {

      @Override
      public void onTitleClicked(String fullTitle) {
        presenter.onMovieTitleClicked(fullTitle);
      }

      @Override
      public void onVideoClicked(MovieVideo movieVideo) {
        presenter.onMovieVideoClicked(movieVideo);
      }

      @Override
      public void onOverviewAuthorClicked(MovieReview movieReview) {
        presenter.onMovieOverviewClicked(movieReview);
      }
    });
    content.setAdapter(contentController.getAdapter());
    favoritesButton.setOnClickListener(v ->
      presenter.onAddToFavoritesClicked());
  }

  @Override
  public void showViewContent(DetailsMvp.PM data) {
    super.showViewContent(data);
    Picasso.with(this)
      .load(data.movieInfo().backDropUrl())
      .into(moviePoster);
    contentController.setData(data);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    contentController.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    contentController.onRestoreInstanceState(savedInstanceState);
  }
}