package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.Nullable;
import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ItemDetailsMovieInfoBindingModel_;
import ru.rinekri.udacitypopularmovies.ItemDetailsTitleBindingModel_;
import ru.rinekri.udacitypopularmovies.ui.base.data_binding.RecyclerModel_;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

class DetailsContentController extends TypedEpoxyController<DetailsMvp.PM> {

  public interface Actions {
    void onTitleClickedAction(String fullTitle);
  }

  @AutoModel
  ItemDetailsMovieInfoBindingModel_ model;
  @Nullable
  Actions actions;

  public DetailsContentController() {
    this(null);
  }

  public DetailsContentController(@Nullable Actions actions) {
    this.actions = actions;
  }

  @Override
  protected void onExceptionSwallowed(RuntimeException exception) {
    throw exception;
  }

  @Override
  protected void buildModels(DetailsMvp.PM data) {
    model
      .voteAverage(data.movieInfo().voteAverage())
      .overview(data.movieInfo().overview())
      .releaseDate(data.movieInfo().releaseDate())
      .addTo(this);

    List<ItemDetailsTitleBindingModel_> titleModels = StreamSupport
      .stream(data.movieTitles())
      .map(titleModel -> {
        ItemDetailsTitleBindingModel_ model = new ItemDetailsTitleBindingModel_()
          .id(View.generateViewId())
          .title(titleModel.getShortName());
        model.clickListener(v -> LangUtils.safeInvoke(actions, a -> a.onTitleClickedAction(titleModel.name())));
        return model;
      })
      .collect(Collectors.toList());

    add(new RecyclerModel_().id(View.generateViewId()).models(titleModels));
  }
}