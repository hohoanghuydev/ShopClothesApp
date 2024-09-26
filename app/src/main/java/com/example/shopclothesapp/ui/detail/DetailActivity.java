package com.example.shopclothesapp.ui.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.SlidesAdapter;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.models.Slides;
import com.example.shopclothesapp.data.models.Images;
import com.example.shopclothesapp.data.models.Sizes;
import com.example.shopclothesapp.data.repositories.UserRepository;
import com.example.shopclothesapp.databinding.ActivityDetailBinding;
import com.example.shopclothesapp.factories.ProductViewModelFactory;
import com.example.shopclothesapp.utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding detailBinding;
    SlidesAdapter slidesAdapter;
    ProductViewModel detailViewModel;
    List<Slides> slides = new ArrayList<>();
    Products product;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        product = getIntent().getParcelableExtra("product");

        if(product == null) {
            Log.e("Detail", "Product data is null");
            finish();
            return;
        }

        for(Images image : product.getImages()) {
            if(image == null || image.getUrl() == null || image.getUrl().isEmpty()) {
                continue;
            }
            slides.add(new Slides(image.getUrl()));
        }

        setupDataBinding();
        setupViewPager();
        observeViewModel();
        addEvents();

        if(product.getSizes() != null) {
            for(Sizes size : product.getSizes()) {
                if(size == null) {
                    continue;
                }

                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(View.generateViewId());
                radioButton.setText(size.getSize());
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                radioButton.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setBackgroundResource(R.drawable.bg_rectangle_radius);
                radioButton.setButtonDrawable(android.R.color.transparent);
                radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                radioButton.setPadding(16, 16, 16, 16);

                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) radioButton.getLayoutParams();
                marginLayoutParams.setMarginEnd((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                marginLayoutParams.setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

                detailBinding.radioGroupSize.addView(radioButton);
            }
        }
    }

    private void addEvents() {
        detailBinding.btnBackDetail.setOnClickListener(view -> finish());

        detailBinding.btnAddToFavorite.setOnClickListener(view -> detailViewModel.setFlagFavorite(!detailViewModel.getFlagFavorite()));

        detailBinding.btnAddToCart.setOnClickListener(view -> {
            int idChecked = detailBinding.radioGroupSize.getCheckedRadioButtonId();

            if (idChecked == -1) {
                Toast.makeText(this, "Please select size", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioButtonChecked = findViewById(idChecked);
            detailViewModel.addToCart(radioButtonChecked.getText().toString());
        });
    }

    private void observeViewModel() {
        detailViewModel.getProductLiveData().observe(this, itemProduct -> {
            slides.clear();
            for(Images image : product.getImages()) {
                if(image == null || image.getUrl() == null || image.getUrl().isEmpty()) {
                    continue;
                }
                slides.add(new Slides(image.getUrl()));
            }

            slidesAdapter.setSliders(slides);
        });

        detailViewModel.getFlagFavoriteLiveData().observe(this, flag -> {
            if(flag) {
                detailBinding.btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_24);
                detailViewModel.addToFavorite();
            } else {
                detailBinding.btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
                detailViewModel.removeFromFavorite();
            }
        });

        detailViewModel.getStateOrder().observe(this, stateOrder -> {
            if(stateOrder) {
                Toast.makeText(this, "Add to cart success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Add to cart failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager() {
        ViewPager2 viewPagerImagesProduct = detailBinding.viewPagerSlideProduct;
        CircleIndicator3 circleIndicatorSlide = detailBinding.circleIndicatorSlide;

        slidesAdapter = new SlidesAdapter(slides);
        viewPagerImagesProduct.setAdapter(slidesAdapter);
        viewPagerImagesProduct.setPageTransformer(new ZoomOutPageTransformer());

        circleIndicatorSlide.setViewPager(viewPagerImagesProduct);
        slidesAdapter.registerAdapterDataObserver(circleIndicatorSlide.getAdapterDataObserver());

        setupIndicatorClickListener(circleIndicatorSlide, viewPagerImagesProduct);
    }

    private void setupDataBinding() {
        userRepository = new UserRepository(this);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ProductViewModelFactory productFactory = new ProductViewModelFactory(product, this);
        detailViewModel = new ViewModelProvider(this, productFactory).get(ProductViewModel.class);
        detailBinding.setProductViewModel(detailViewModel);
        detailBinding.setLifecycleOwner(this);
        detailBinding.executePendingBindings();
    }

    private void setupIndicatorClickListener(CircleIndicator3 circleIndicatorSlide, ViewPager2 viewPagerImagesProduct) {
        for(int indexOfSlide = 0; indexOfSlide < circleIndicatorSlide.getChildCount(); indexOfSlide++) {
            final int position = indexOfSlide;
            View viewIndicator = circleIndicatorSlide.getChildAt(position);
            viewIndicator.setOnClickListener(view -> viewPagerImagesProduct.setCurrentItem(position));
        }
    }
}