package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ru.rinekri.udacitypopularmovies.BuildConfig;
import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpActivity;
import ru.rinekri.udacitypopularmovies.ui.base.models.ActivityConfig;
import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;

import static android.view.View.GONE;

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
  @BindView(R.id.details_release_date)
  TextView releaseDate;
  @BindView(R.id.details_vote_average)
  TextView voteAverage;
  @BindView(R.id.details_overview)
  TextView overview;
  @BindView(R.id.elce_scroll_container)
  NestedScrollView elceScrollContainer;

  @InjectPresenter
  public DetailsPresenter presenter;

  @ProvidePresenter
  public DetailsPresenter providePresenter() {
    MovieShortInfo movieShortInfo = getIntent().getParcelableExtra(EXTRA_MOVIE_SHORT_INFO);
    MainServiceApi api = ContextUtils.appComponent(this).mainServiceApi();
    DetailsInputInteractor interactor = new DetailsInputInteractor(api);
    return new DetailsPresenter(movieShortInfo, interactor);
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
  public void showViewContent(DetailsMvp.PM data) {
    super.showViewContent(data);
    elceScrollContainer.setVisibility(GONE);
    Picasso
      .with(this)
      .load(data.movieInfo().backDropUrl())
      .into(moviePoster);
    //TODO: Fix bug when view doesn't correctly inflate at second start
    voteAverage.setText(data.movieInfo().voteAverage());
    overview.setText(data.movieInfo().overview());
    releaseDate.setText(data.movieInfo().releaseDate());
  }
}