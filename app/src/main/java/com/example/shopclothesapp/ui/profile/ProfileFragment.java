package com.example.shopclothesapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.databinding.FragmentProfileBinding;
import com.example.shopclothesapp.ui.address.AddressActivity;
import com.example.shopclothesapp.ui.payment.PaymentActivity;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding profileBinding;
    ProfileViewModel profileViewModel;
    Users user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        ProfileViewModelFactory factory = new ProfileViewModelFactory(getContext());
        profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        profileBinding.setProfileViewModel(profileViewModel);

        return profileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addEvents();
        setUpObservers();
    }

    private void setUpObservers() {
        profileViewModel.getUsersLiveData().observe(getViewLifecycleOwner(), userObserve -> {
            user = userObserve;
        });
    }

    private void addEvents() {
        profileBinding.btnAddressProfile.setOnClickListener(viewControl -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

        profileBinding.btnPaymentProfile.setOnClickListener(viewControl -> {
            startActivity(new Intent(getActivity(), PaymentActivity.class));
        });

        profileBinding.tvEditProfile.setOnClickListener(viewControl -> {
//            MainScreenFragmentDirections.ActionMainScreenFragmentToEditAccountFragment action = MainScreenFragmentDirections.actionMainScreenFragmentToEditAccountFragment(user);
//            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(action);
            Toast.makeText(requireContext(), "Edit profile", Toast.LENGTH_SHORT).show();
        });

        profileBinding.btnSignOut.setOnClickListener(viewControl -> {
            profileViewModel.signOut();
            Toast.makeText(getContext(), "Sign out", Toast.LENGTH_SHORT).show();

            if (getActivity() != null) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_screenMainFragment_to_loginFragment);
            }
        });

        profileBinding.btnPaymentHistory.setOnClickListener(viewControl -> {
            Toast.makeText(requireContext(), "All orders", Toast.LENGTH_SHORT).show();
        });
    }
}