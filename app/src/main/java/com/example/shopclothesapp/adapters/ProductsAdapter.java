package com.example.shopclothesapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.databinding.ItemProductBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductsAdapter extends ListAdapter<Products, ProductsAdapter.ItemProductViewHolder> implements Filterable {
    List<Products> originalProducts = new ArrayList<>();
    ItemProductClickListener itemListener;
    FilterCallback filterCallback;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<Products> filterProducts;

                if(!query.isEmpty()) {
                    filterProducts = originalProducts.stream().filter(r -> r.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
                } else {
                    filterProducts = new ArrayList<>(originalProducts);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterProducts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<Products> products = (List<Products>) filterResults.values;
                submitList(products);
                filterCallback.checkEmptyProduct(products);
            }
        };
    }

    public interface ItemProductClickListener {
        void sendDataClick(Products product);
    }

    public interface FilterCallback {
        void checkEmptyProduct(List<Products> products);
    }

    public ProductsAdapter(ItemProductClickListener itemListener) {
        super(DIFF_CALLBACK);
        this.itemListener = itemListener;
    }

    public void setFilterCallback(FilterCallback filterCallback) {
        this.filterCallback = filterCallback;
    }

    public void submitProducts(List<Products> originalProducts) {
        this.originalProducts = originalProducts;
        submitList(originalProducts);
    }

    @NonNull
    @Override
    public ItemProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding itemProductBinding = DataBindingUtil.inflate(inflater, R.layout.item_product, parent, false);
        return new ItemProductViewHolder(itemProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductViewHolder holder, int position) {
        Products product = getItem(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(view -> itemListener.sendDataClick(product));
    }

    static class ItemProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding itemProductBinding;

        public ItemProductViewHolder(@NonNull ItemProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemProductBinding = itemBinding;
        }

        public void bind(Products product) {
            itemProductBinding.setProduct(product);
            itemProductBinding.executePendingBindings();
        }
    }

    private static final DiffUtil.ItemCallback<Products> DIFF_CALLBACK = new DiffUtil.ItemCallback<Products>() {
        @Override
        public boolean areItemsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return Objects.equals(oldItem.getProductId(), newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return oldItem.equals(newItem);
        }
    };
}
