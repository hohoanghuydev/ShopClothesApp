package com.example.shopclothesapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.Slides;
import com.example.shopclothesapp.databinding.ItemSlideBinding;

import java.util.List;

public class SlidesAdapter extends RecyclerView.Adapter<SlidesAdapter.SliderViewHolder> {
    List<Slides> slides;

    public SlidesAdapter(List<Slides> slides) {
        this.slides = slides;
    }

    public void setSliders(List<Slides> sliders) {
        this.slides = sliders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSlideBinding slideBinding = DataBindingUtil.inflate(inflater, R.layout.item_slide, parent, false);
        return new SliderViewHolder(slideBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Slides slide = slides.get(position);
        holder.bind(slide);
    }

    @Override
    public int getItemCount() {
        return slides != null ? slides.size() : 0;
    }


    public class SliderViewHolder extends RecyclerView.ViewHolder {
        ItemSlideBinding itemSlideBinding;

        public SliderViewHolder(@NonNull ItemSlideBinding itemSlideBinding) {
            super(itemSlideBinding.getRoot());
            this.itemSlideBinding = itemSlideBinding;
        }

        public void bind(Slides slide) {
            itemSlideBinding.setSlide(slide);
            itemSlideBinding.executePendingBindings();
        }
    }
}
