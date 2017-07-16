package ru.rinekri.udacitypopularmovies.ui.details;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import ru.rinekri.udacitypopularmovies.ItemDetailsMovieInfoBindingModel_;

class DetailsContentController extends TypedEpoxyController<DetailsMvp.PM> {
  @AutoModel
  ItemDetailsMovieInfoBindingModel_ model;

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
  }
}