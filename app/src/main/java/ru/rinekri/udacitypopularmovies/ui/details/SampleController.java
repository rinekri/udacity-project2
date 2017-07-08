package ru.rinekri.udacitypopularmovies.ui.details;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import java.util.List;

import ru.rinekri.udacitypopularmovies.ItemDetailsMovieInfoBindingModel_;

public class SampleController extends TypedEpoxyController<List<DetailsMvp.PM>> {
  @AutoModel
  ItemDetailsMovieInfoBindingModel_ model;

  @Override
  protected void onExceptionSwallowed(RuntimeException exception) {
    // Best practice is to throw in debug so you are aware of any issues that Epoxy notices.
    // Otherwise Epoxy does its best to swallow these exceptions and continue gracefully
    throw exception;
  }

  @Override
  protected void buildModels(List<DetailsMvp.PM> data) {

  }
}