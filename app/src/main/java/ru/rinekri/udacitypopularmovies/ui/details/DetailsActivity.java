package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

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
import ru.rinekri.udacitypopularmovies.ui.utils.ViewUtils;

public class DetailsActivity extends BaseMvpActivity<DetailsMvp.PM> implements DetailsMvp.View, DetailsContentController.Actions {
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

  private DetailsContentController contentController = new DetailsContentController(this);

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
  protected void initView() {
    content.setLayoutManager(new LinearLayoutManager(this));
    content.setAdapter(contentController.getAdapter());
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

  @Override
  public void onTitleClickedAction(String fullTitle) {
    ViewUtils.showSnack(content, fullTitle);
  }
}