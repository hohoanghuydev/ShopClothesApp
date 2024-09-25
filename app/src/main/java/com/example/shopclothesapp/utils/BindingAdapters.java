package com.example.shopclothesapp.utils;

import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

public class BindingAdapters {
    @BindingAdapter({"imageUrl", "imageError"})
    public static void setImageUrl(ImageView imageView, String urlImage, Drawable errorImage) {
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(16));

        Glide.with(imageView.getContext())
                .load(urlImage)
                .error(errorImage)
                .apply(requestOptions)
                .into(imageView);
    }

    @BindingAdapter("formatedPrice")
    public static void setFormatedPrice(TextView textView, int price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormat format = new DecimalFormat("#,### đ");
        textView.setText(format.format(price));
    }

    @BindingAdapter("formatedPrice")
    public static void setFormatedPrice(TextView textView, long price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormat format = new DecimalFormat("#,### đ");
        textView.setText(format.format(price));
    }
}
