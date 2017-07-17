package ru.rinekri.udacitypopularmovies.ui.base.data_binding;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithView;

import java.util.Collections;
import java.util.List;

import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ui.base.views.EpoxyRecyclerView;

@EpoxyModelClass
public abstract class RecyclerModel extends EpoxyModelWithView<EpoxyRecyclerView> {
  @EpoxyAttribute
  List<? extends EpoxyModel<?>> models;
  @EpoxyAttribute
  boolean shouldSnapItems;
  @EpoxyAttribute
  List<RecyclerView.ItemDecoration> itemDecorations = Collections.emptyList();

  @Override
  public void bind(EpoxyRecyclerView epoxyRecyclerView) {
    epoxyRecyclerView.setModels(models);
  }

  @Override
  public void unbind(EpoxyRecyclerView epoxyRecyclerView) {
    epoxyRecyclerView.clearModels();
  }

  @Override
  protected EpoxyRecyclerView buildView(ViewGroup parent) {
    EpoxyRecyclerView epoxyRecyclerView = new EpoxyRecyclerView(parent.getContext(), null);
    StreamSupport
      .stream(itemDecorations)
      .forEach(epoxyRecyclerView::addItemDecoration);
    if (shouldSnapItems) {
      new LinearSnapHelper().attachToRecyclerView(epoxyRecyclerView);
    }
    return epoxyRecyclerView;
  }

  @Override
  public boolean shouldSaveViewState() {
    return true;
  }
}