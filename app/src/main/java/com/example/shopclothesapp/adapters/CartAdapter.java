package com.example.shopclothesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.CartItem;
import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.repositories.UserRepository;
import com.example.shopclothesapp.databinding.ItemCartBinding;

public class CartAdapter extends ListAdapter<CartItem, CartAdapter.ItemCartViewHolder> {
    UserRepository userRepository;

    public CartAdapter(Context context) {
        super(DIFF_CALLBACK);
        userRepository = new UserRepository(context.getApplicationContext());
    }

    @NonNull
    @Override
    public ItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding itemCartBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);
        return new ItemCartViewHolder(itemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartViewHolder holder, int position) {
        CartItem cartItem = getItem(position);

        String userId = userRepository.getUserLogin("uid");
        holder.bind(cartItem, userId);
    }

    static class ItemCartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding itemCartBinding;

        public ItemCartViewHolder(@NonNull ItemCartBinding itemCartBinding) {
            super(itemCartBinding.getRoot());
            this.itemCartBinding = itemCartBinding;
        }

        public void bind(CartItem cartItem, String userId) {
            itemCartBinding.setCartItem(cartItem);

            ProductCart cart = cartItem.getProductCart();

            itemCartBinding.btnMinusQuantity.setOnClickListener(view -> {
                int quantity = Integer.parseInt(itemCartBinding.edtQuantityItemCart.getText().toString());
                if(quantity == 1) {
                    cartItem.removeProductCart(userId, cart);
                } else {
                    cartItem.subtractQuantityCart(userId, cart);
                }
            });

            itemCartBinding.btnPlusQuantity.setOnClickListener(view -> {
                int quantity = Integer.parseInt(itemCartBinding.edtQuantityItemCart.getText().toString());
                cartItem.addQuantityCart(userId, cart);
            });

            itemCartBinding.btnRemoveProductFromCart.setOnClickListener(view -> cartItem.removeProductCart(userId, cart));
        }
    }

    public static DiffUtil.ItemCallback<CartItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CartItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getProductCart().getProductId().equals(newItem.getProductCart().getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.equals(newItem);
        }
    };
}
