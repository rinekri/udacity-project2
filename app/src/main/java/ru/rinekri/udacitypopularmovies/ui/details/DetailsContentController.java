package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.google.common.collect.Lists;

import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ItemDetailsChipBindingModel_;
import ru.rinekri.udacitypopularmovies.ItemDetailsMovieInfoBindingModel_;
import ru.rinekri.udacitypopularmovies.ItemDetailsReviewBindingModel_;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.data_binding.RecyclerModel_;
import ru.rinekri.udacitypopularmovies.ui.base.recycler_view.decorations.SpacingDecoration;

import static ru.rinekri.udacitypopularmovies.ui.UiConstants.VIDEO_YOUTUBE;
import static ru.rinekri.udacitypopularmovies.ui.utils.LangUtils.safeInvoke;

class DetailsContentController extends TypedEpoxyController<DetailsMvp.PM> {

  interface Actions {
    void onTitleClicked(String fullTitle);
    void onVideoClicked(MovieVideo movieVideo);
    void onOverviewAuthorClicked(MovieReview movieReview);
  }

  @AutoModel
  ItemDetailsMovieInfoBindingModel_ model;
  @Nullable
  private Actions actions;
  @NonNull
  private List<RecyclerView.ItemDecoration> defaultItemDecorations
    = Lists.newArrayList(new SpacingDecoration());

  DetailsContentController(@Nullable Actions actions) {
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
        model.clickListener(v -> safeInvoke(actions, a -> a.onTitleClicked(titleModel.name())));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(titleModels)
      .itemDecorations(defaultItemDecorations)
      .addIf(!titleModels.isEmpty(), this);

    model
      .voteAverage(String.format("%.1f", Float.valueOf(data.movieInfo().voteAverage())))
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
        model.clickListener(v -> safeInvoke(actions, a -> a.onVideoClicked(videoModel)));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(videoModels)
      .itemDecorations(defaultItemDecorations)
      .addIf(!videoModels.isEmpty(), this);

    List<ItemDetailsReviewBindingModel_> reviewModels = StreamSupport
      .stream(data.movieReviews())
      .map(reviewModel -> {
        ItemDetailsReviewBindingModel_ model = new ItemDetailsReviewBindingModel_()
          .author(reviewModel.author())
          .overview(reviewModel.content())
          .id(View.generateViewId());
        model.clickListener(v -> safeInvoke(actions, a -> a.onOverviewAuthorClicked(reviewModel)));
        return model;
      })
      .collect(Collectors.toList());

    new RecyclerModel_()
      .id(View.generateViewId())
      .models(reviewModels)
      .shouldSnapItems(true)
      .itemDecorations(defaultItemDecorations)
      .addIf(!reviewModels.isEmpty(), this);
  }
}