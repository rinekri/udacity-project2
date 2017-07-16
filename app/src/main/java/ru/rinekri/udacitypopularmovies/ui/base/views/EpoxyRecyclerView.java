package ru.rinekri.udacitypopularmovies.ui.base.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.SimpleEpoxyController;

import java.util.List;

public class EpoxyRecyclerView extends RecyclerView {
  private static final int SPAN_COUNT = 2;

  private SimpleEpoxyController controller;

  public EpoxyRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setHasFixedSize(true);
    setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
  }

  public void setModels(List<? extends EpoxyModel<?>> models) {
    if (controller == null) {
      controller = new SimpleEpoxyController();
      controller.setSpanCount(SPAN_COUNT);
      setAdapter(controller.getAdapter());
    }
    controller.setModels(models);
  }

  public void clearModels() {
    controller.cancelPendingModelBuild();
    controller = null;
  }
}