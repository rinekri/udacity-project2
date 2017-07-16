package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.Nullable;
import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ItemDetailsChipBindingModel_;
import ru.rinekri.udacitypopularmovies.ItemDetailsMovieInfoBindingModel_;
import ru.rinekri.udacitypopularmovies.ItemDetailsReviewBindingModel_;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.data_binding.RecyclerModel_;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

import static ru.rinekri.udacitypopularmovies.ui.UiConstants.VIDEO_YOUTUBE;

class DetailsContentController extends TypedEpoxyController<DetailsMvp.PM> {

  public interface Actions {
    void onTitleClicked(String fullTitle);
    void onVideoClicked(MovieVideo movieVideo);
    void onOverviewAuthorClicked(MovieReview movieReview);
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
    List<ItemDetailsChipBindingModel_> titleModels = StreamSupport
      .stream(data.movieTitles())
      .map(titleModel -> {
        ItemDetailsChipBindingModel_ model = new ItemDetailsChipBindingModel_()
          .id(View.generateViewId())
          .title(titleModel.getShortName());
        model.clickListener(v -> LangUtils.safeInvoke(actions, a -> a.onTitleClicked(titleModel.name())));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(titleModels)
      .addIf(!titleModels.isEmpty(), this);

    model
      .voteAverage(data.movieInfo().voteAverage())
      .overview(data.movieInfo().overview())
      .releaseDate(data.movieInfo().releaseDate())
      .addTo(this);

    List<ItemDetailsChipBindingModel_> videoModels = StreamSupport
      .stream(data.movieVideos())
      .filter(videoModel -> videoModel.hostingUrl().equals(VIDEO_YOUTUBE))
      .map(videoModel -> {
        ItemDetailsChipBindingModel_ model = new ItemDetailsChipBindingModel_()
          .title(videoModel.name())
          .id(View.generateViewId());
        model.clickListener(v -> LangUtils.safeInvoke(actions, a -> a.onVideoClicked(videoModel)));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(videoModels)
      .addIf(!videoModels.isEmpty(), this);

    List<ItemDetailsReviewBindingModel_> reviewModels = StreamSupport
      .stream(data.movieReviews())
      .map(reviewModel -> {
        ItemDetailsReviewBindingModel_ model = new ItemDetailsReviewBindingModel_()
          .author(reviewModel.author())
          .overview(reviewModel.content())
          .id(View.generateViewId());
        model.clickListener(v -> LangUtils.safeInvoke(actions, a -> a.onOverviewAuthorClicked(reviewModel)));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(reviewModels)
      .addIf(!reviewModels.isEmpty(), this);
  }
}