package ru.rinekri.udacitypopularmovies.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;
import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.ui.base.models.ActivityConfig;
import ru.rinekri.udacitypopularmovies.ui.base.models.ErrorConfig;
import ru.rinekri.udacitypopularmovies.ui.utils.ViewUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

abstract public class BaseMvpActivity<D> extends MvpAppCompatActivity implements BaseMvpView<D> {

  protected abstract ActivityConfig provideActivityConfig();

  protected void initView() {
  }

  private TextView errorView;
  private TextView emptyView;
  private ProgressBar progressView;
  private View contentView;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityConfig config = provideActivityConfig();

    setContentView(config.contentViewRes());
    ButterKnife.bind(this);
    initActionBar(config);
    initElceViews(config);
    switchToInitState();
    initView();
  }

  @SuppressWarnings("ConstantConditions")
  private void initActionBar(ActivityConfig config) {
    //TODO: Transfer Toolbar and AppBar from each layout to shell
    toolbar = ButterKnife.findById(this, R.id.toolbar);
    setSupportActionBar(toolbar);

    final ActionBar ab = getSupportActionBar();
    if (config.titleRes() != 0) {
      ab.setTitle(config.titleRes());
    } else {
      ab.setTitle(config.titleText());
    }
    ab.setDisplayHomeAsUpEnabled(config.useBackButton());
    if (config.useBackButton()) {
      toolbar.setNavigationOnClickListener(view -> NavUtils.navigateUpFromSameTask(this));
    } else {
      toolbar.setNavigationOnClickListener(null);
    }
  }

  private void initElceViews(ActivityConfig config) {
    emptyView = ButterKnife.findById(this, config.elceEmptyViewId());
    errorView = ButterKnife.findById(this, config.elceErrorViewId());
    progressView = ButterKnife.findById(this, config.elceProgressViewId());
    contentView = ButterKnife.findById(this, config.contentContainerId());
    Integer gravity;
    if (config.alignElceCenter()) {
      gravity = Gravity.CENTER;
    } else {
      gravity = Gravity.CENTER_HORIZONTAL;
    }
    emptyView.setGravity(gravity);
    errorView.setGravity(gravity);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    params.gravity = gravity;
    progressView.setLayoutParams(params);
  }

  @Override
  public void showViewContent(D data) {
    switchToInitState();
  }

  @Override
  public void showEmpty(@Nullable @StringRes Integer messageRes) {
    ViewUtils.setVisibility(false, errorView, progressView, contentView);
    ViewUtils.setVisibility(true, emptyView);
    if (messageRes != null) {
      emptyView.setText(getString(messageRes));
    } else {
      emptyView.setText(getString(R.string.empty_default));
    }
  }

  @Override
  public void showError(ErrorConfig errorConfig) {
    ViewUtils.setVisibility(false, emptyView, progressView, contentView);
    ViewUtils.setVisibility(true, errorView);
    errorView.setText(errorConfig.resolveTitle(this));
  }

  @Override
  public void showLoading() {
    ViewUtils.setVisibility(false, errorView, emptyView, contentView);
    ViewUtils.setVisibility(true, progressView);
  }

  private void switchToInitState() {
    ViewUtils.setVisibility(false, errorView, emptyView, progressView);
    ViewUtils.setVisibility(true, contentView);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @NonNull
  protected Toolbar getToolbar() {
    return toolbar;
  }
}