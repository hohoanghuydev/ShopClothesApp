package com.example.shopclothesapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.databinding.ItemCategoryBinding;
import com.example.shopclothesapp.databinding.ItemCategoryHomeBinding;
import com.example.shopclothesapp.utils.TypeViewHolder;

public class CategoryAdapter extends ListAdapter<Category, RecyclerView.ViewHolder> {
    private ItemCategoryImp itemClickListener;
    private TypeViewHolder typeViewHolder;

    public CategoryAdapter(TypeViewHolder typeViewHolder) {
        super(COMPARATOR);
        this.typeViewHolder = typeViewHolder;
    }

    public interface ItemCategoryImp {
        void onClickItemCategory(Category category);
    }

    public void setItemClickListener(ItemCategoryImp itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (typeViewHolder == TypeViewHolder.SMALL_HORIZONTAL) {
            ItemCategoryHomeBinding itemCategoryHomeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_category_home, parent, false);
            return new ItemCategoryHomeViewHolder(itemCategoryHomeBinding);
        } else {
            ItemCategoryBinding itemCategoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false);
            return new ItemCategoryViewHolder(itemCategoryBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = getItem(position);

        if (holder instanceof ItemCategoryViewHolder) {
            ((ItemCategoryViewHolder) holder).bind(category);
            holder.itemView.setOnClickListener(view -> itemClickListener.onClickItemCategory(category));
        } else {
            ((ItemCategoryHomeViewHolder) holder).bind(category);
            holder.itemView.setOnClickListener(view -> itemClickListener.onClickItemCategory(category));
        }
    }

    static class ItemCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding itemCategoryBinding;

        public ItemCategoryViewHolder(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.itemCategoryBinding = itemCategoryBinding;
        }

        public void bind(Category category) {
            itemCategoryBinding.setCategory(category);
            itemCategoryBinding.executePendingBindings();
        }
    }

    static class ItemCategoryHomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryHomeBinding itemCategoryHomeBinding;

        public ItemCategoryHomeViewHolder(ItemCategoryHomeBinding itemCategoryHomeBinding) {
            super(itemCategoryHomeBinding.getRoot());
            this.itemCategoryHomeBinding = itemCategoryHomeBinding;
        }

        public void bind(Category category) {
            itemCategoryHomeBinding.setCategory(category);
            itemCategoryHomeBinding.executePendingBindings();
        }
    }

    static final DiffUtil.ItemCallback<Category> COMPARATOR = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getCategoryId().equals(newItem.getCategoryId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.equals(newItem);
        }
    };
}
