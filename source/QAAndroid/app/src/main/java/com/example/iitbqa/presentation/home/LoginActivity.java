package com.example.iitbqa.presentation.home;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.repository.UserRepository;
import com.example.iitbqa.exception.CreatedException;

import javax.inject.Inject;
import javax.inject.Named;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_ldap)
    EditText etLdap;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.btn_signup)
    Button btnSignup;
    private String ldap = "";
    private String password = "";

    @Inject
    AuthManager authManager;

    @Inject
    UserRepository userRepository;


    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((IITBQA)getApplication()).getAppComponent().inject(this);

        monitorPostButtonStatus(ldap, password);

        etLdap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ldap =  s.toString();
                monitorPostButtonStatus(ldap, password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
                monitorPostButtonStatus(ldap, password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener( v -> {
            userRepository.loginUser(ldap, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(user -> {
//                authManager.saveUser(user);
//                authManager.setUserLoggedIn(true);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            },
                    error -> {
                        if (error instanceof CreatedException) {
                            etLdap.setText("");
                            etPassword.setText("");
                            Toast.makeText(context, "Password and ldap don't match", Toast.LENGTH_LONG).show();
                        }
                    });
        });

        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });


    }

    private void monitorPostButtonStatus(String ldap, String password) {
        if (!ldap.isEmpty() && !password.isEmpty()) {
            btnLogin.setBackgroundResource(R.drawable.rounded_button_red);
            btnLogin.setEnabled(true);
        }
        else {
            btnLogin.setBackgroundResource(R.drawable.rounded_button_red_disable);
            btnLogin.setEnabled(false);
        }
    }
}
