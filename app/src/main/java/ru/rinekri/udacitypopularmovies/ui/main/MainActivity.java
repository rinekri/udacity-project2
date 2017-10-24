package ru.rinekri.udacitypopularmovies.ui.main;

import android.content.ContentResolver;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Arrays;

import butterknife.BindView;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpActivity;
import ru.rinekri.udacitypopularmovies.ui.base.models.ActivityConfig;
import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;
import ru.rinekri.udacitypopularmovies.ui.utils.DialogUtils;

import static ru.rinekri.udacitypopularmovies.ui.UiConstants.GRID_COLUMNS;

public class MainActivity extends BaseMvpActivity<MainMvp.PM> implements MainMvp.View {

  @BindView(R.id.content_container)
  RecyclerView contentView;

  TextView toolbarTitle;
  MainAdapter contentAdapter;
  ListPopupWindow sortTypesSelector;

  @InjectPresenter
  public MainPresenter presenter;

  @ProvidePresenter
  public MainPresenter providePresenter() {
    MainServiceApi api = ContextUtils.appComponent(this).mainServiceApi();
    ContentResolver contentResolver = ContextUtils.appComponent(this).contentResolver();
    MainInputInteractor interactor = new MainInputInteractor(api, contentResolver);
    return new MainPresenter(interactor, contentResolver);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.setRouter(new MainMvp.Router(this, contentView));
  }

  @Override
  protected ActivityConfig provideActivityConfig() {
    return ActivityConfig.builder()
      .contentViewRes(R.layout.content_main)
      .build();
  }

  @Override
  protected void initView() {
    contentAdapter = new MainAdapter(
      R.layout.item_main,
      movieInfo -> presenter.onMoviePosterClicked(movieInfo),
      movieInfo -> presenter.onMoviePosterLongClicked(movieInfo)
    );
    contentView.setAdapter(contentAdapter);
    contentView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
  }

  @Override
  public void showInitContent(MainMvp.IM data) {
    toolbarTitle = (TextView) getLayoutInflater().inflate(R.layout.view_spinner, null);
    getToolbar().removeAllViews();
    getToolbar().addView(toolbarTitle);

    String[] sortNames = StreamSupport
      .stream(data.sortTypes())
      .map(sortType -> sortType.resolveName(this))
      .toArray(String[]::new);

    String initSortName = sortNames[data.sortTypes().indexOf(data.initSortType())];

    //TODO: Add logic to restore dialog after rotate. For example, with Runnable?
    toolbarTitle.setText(initSortName);
    toolbarTitle.setOnClickListener(view -> {
      sortTypesSelector = DialogUtils.makePopupWindow(this, Arrays.asList(sortNames), toolbarTitle, (position) -> {
        toolbarTitle.setText(sortNames[position]);
        presenter.onMovieSortChanged(data.sortTypes().get(position));
      });
      sortTypesSelector.show();
    });
  }

  @Override
  protected void onDestroy() {
    hideSortTypesDialog();
    sortTypesSelector = null;
    super.onDestroy();
  }

  @Override
  public void showViewContent(MainMvp.PM data) {
    super.showViewContent(data);
    contentAdapter.swapContent(data.movies());
  }

  private void hideSortTypesDialog() {
    if (sortTypesSelector != null) {
      sortTypesSelector.dismiss();
    }
  }
}