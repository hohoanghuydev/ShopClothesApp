package com.example.shopclothesapp.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    LoginViewModel loginViewModel;
    FragmentLoginBinding loginBinding;
    Dialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        LoginViewModelFactory factory = new LoginViewModelFactory(getContext());
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        loginBinding.setLoginViewModel(loginViewModel);

        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addEvents();
        setUpObservers();
    }

    private void setUpObservers() {
        loginViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                StringBuilder message = new StringBuilder();
                message.append("Hello ");
                if (user.getFullname() != null) {
                    message.append(user.getFullname());
                } else {
                    message.append(user.getEmail());
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                loginViewModel.saveDataLogin(user);

                if (getActivity() != null) {
                    Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_screenMainFragment);
                }
            } else {
                Toast.makeText(requireContext(), "Email or password incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        loginViewModel.getStateSendEmail().observe(getViewLifecycleOwner(), stateSendEmail -> {
            if (stateSendEmail) {
                Toast.makeText(requireContext(), "Check your email", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Email is not exists", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvents() {
        loginBinding.btnLoginAccount.setOnClickListener(viewControl -> {
            String email = loginBinding.edtLoginEmail.getText().toString();
            String password = loginBinding.edtLoginPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Invalid field", Toast.LENGTH_SHORT).show();
                loginBinding.edtLoginEmail.setFocusable(true);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Invalid field", Toast.LENGTH_SHORT).show();
                loginBinding.edtLoginPassword.setFocusable(true);
                return;
            }

            loginViewModel.loginAccount(email, password);
        });

        loginBinding.tvRegisterAccount.setOnClickListener(viewControl -> {
            if (getActivity() != null) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

        loginBinding.tvForgotPassword.setOnClickListener(viewControl -> {
            loadDialogForgotPassword();

            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                dialog.show();
            }
        });
    }

    private void loadDialogForgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_forgotpassword, null);
        builder.setView(viewDialog);
        dialog = builder.create();

        Button btnCancel = viewDialog.findViewById(R.id.btnCancelDialog);
        Button btnReset = viewDialog.findViewById(R.id.btnResetPassword);
        EditText edtEmailResetPassword = viewDialog.findViewById(R.id.edtEmailResetPassword);

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnReset.setOnClickListener(viewButtonReset -> {
            String email = edtEmailResetPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Email can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Email is not incorrect format", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.getPasswordForgot(email);
        });
    }
}