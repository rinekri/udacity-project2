package ru.rinekri.udacitypopularmovies.ui.base.data_binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUrlAdapter {

  @BindingAdapter(value = {"imageUrl", "imagePlaceholder"}, requireAll = false)
  public static void setImageUrl(ImageView view, String imageUrl, int imagePlaceholder) {
    if (imageUrl != null) {
      Picasso.with(view.getContext())
        .load(imageUrl)
        .fit()
        .centerInside()
        .placeholder(imagePlaceholder)
        .into(view);
    } else {
      view.setImageResource(imagePlaceholder);
    }
  }
}