package ru.rinekri.udacitypopularmovies.ui.base.data_binding;

import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithView;

import java.util.List;

import ru.rinekri.udacitypopularmovies.ui.base.recycler_view.decorations.SpacingDecoration;
import ru.rinekri.udacitypopularmovies.ui.base.views.EpoxyRecyclerView;

@EpoxyModelClass
public abstract class RecyclerModel extends EpoxyModelWithView<EpoxyRecyclerView> {
  @EpoxyAttribute
  List<? extends EpoxyModel<?>> models;

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
    //TODO: Add logic to define padding as attribute
    EpoxyRecyclerView epoxyRecyclerView = new EpoxyRecyclerView(parent.getContext(), null);
    epoxyRecyclerView.addItemDecoration(new SpacingDecoration());
    return epoxyRecyclerView;
  }

  @Override
  public boolean shouldSaveViewState() {
    return true;
  }
}