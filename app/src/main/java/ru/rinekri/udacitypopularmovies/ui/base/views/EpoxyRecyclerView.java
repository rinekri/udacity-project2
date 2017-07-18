package ru.rinekri.udacitypopularmovies.ui.base.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.SimpleEpoxyController;

import java.util.List;

public class EpoxyRecyclerView extends RecyclerView {
  private static final int SPAN_COUNT = 1;

  private SimpleEpoxyController controller;
  GridLayoutManager layoutManager;

  public EpoxyRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setHasFixedSize(true);
    layoutManager = new GridLayoutManager(context, SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
    setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    setLayoutManager(layoutManager);
  }

  public void setModels(List<? extends EpoxyModel<?>> models) {
    if (controller == null) {
      controller = new SimpleEpoxyController();
      controller.setSpanCount(SPAN_COUNT);
      layoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
      setAdapter(controller.getAdapter());
    }
    controller.setModels(models);
    setNestedScrollingEnabled(false);
  }

  public void clearModels() {
    controller.cancelPendingModelBuild();
    controller = null;
  }
}