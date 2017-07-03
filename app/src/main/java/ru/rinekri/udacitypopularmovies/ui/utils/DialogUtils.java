package ru.rinekri.udacitypopularmovies.ui.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import java8.util.function.Consumer;
import ru.rinekri.udacitypopularmovies.R;

public final class DialogUtils {
  public DialogUtils() {
    throw new RuntimeException("static class");
  }

  @SuppressWarnings("unchecked")
  public static ListPopupWindow makePopupWindow(Context context,
                                                List<String> data,
                                                View anchorView,
                                                @LayoutRes Integer itemLayout,
                                                Consumer<Integer> onItemClickAction) {
    ArrayAdapter adapter = new ArrayAdapter<String>(context, itemLayout);
    adapter.addAll(data);
    ListPopupWindow window = new ListPopupWindow(context);
    window.setAdapter(adapter);
    window.setOnItemClickListener((parent, spinnerItem, position, id) -> {
      window.dismiss();
      onItemClickAction.accept(position);
    });
    window.setAnchorView(anchorView);
    window.setVerticalOffset(-(anchorView.getHeight() / 2));
    return window;
  }

  public static ListPopupWindow makePopupWindow(Context context,
                                                List<String> data,
                                                View anchorView,
                                                Consumer<Integer> onItemClickAction) {
    return makePopupWindow(context, data, anchorView, R.layout.item_spinner, onItemClickAction);
  }
}