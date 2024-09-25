package com.example.shopclothesapp.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopclothesapp.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addActions(view);
    }

    private void addActions(View view) {
        view.findViewById(R.id.btnLoginAccount).setOnClickListener(viewControl -> {
            if (getActivity() != null) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_screenMainFragment);
            }
        });

        view.findViewById(R.id.tvRegisterAccount).setOnClickListener(viewControl -> {
            if (getActivity() != null) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }
}