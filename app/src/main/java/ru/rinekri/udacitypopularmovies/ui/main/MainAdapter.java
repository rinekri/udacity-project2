package ru.rinekri.udacitypopularmovies.ui.main;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import java8.util.function.Consumer;
import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.ui.base.recycler_view.BaseSimpleAdapter;
import ru.rinekri.udacitypopularmovies.ui.base.recycler_view.BaseViewHolder;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

class MainAdapter extends BaseSimpleAdapter<MovieInfo, MainAdapter.MainViewHolder> {
  @Nullable
  private Consumer<MovieInfo> onPosterClickAction;
  @Nullable
  private Consumer<MovieInfo> onPosterLongClickAction;

  MainAdapter(@LayoutRes Integer itemLayoutRes,
              @NonNull Consumer<MovieInfo> onPosterClickAction,
              @NonNull Consumer<MovieInfo> onPosterLongClickAction) {
    super(itemLayoutRes);
    this.onPosterClickAction = onPosterClickAction;
    this.onPosterLongClickAction = onPosterLongClickAction;
  }

  @Override
  protected MainViewHolder createViewHolder(View itemView) {
    return new MainViewHolder(itemView);
  }

  class MainViewHolder extends BaseViewHolder<MovieInfo> {
    @BindView(R.id.movie_poster)
    ImageView poster;

    MainViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void fill(MovieInfo item) {
      Picasso
        .with(poster.getContext())
        .load(item.posterUrl())
        .placeholder(R.drawable.ic_main_placeholder)
        .into(poster);

      itemView.setOnClickListener((view) -> {
        LangUtils.safeInvoke(onPosterClickAction,
          action -> action.accept(item));
      });
      itemView.setOnLongClickListener(view -> {
        if (onPosterLongClickAction != null) {
          onPosterLongClickAction.accept(item);
          return true;
        }
        return false;
      });
    }
  }
}