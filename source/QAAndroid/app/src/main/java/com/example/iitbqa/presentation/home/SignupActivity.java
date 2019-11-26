package com.example.iitbqa.presentation.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.ApiService;
import com.example.iitbqa.data.models.DegreeChoice;
import com.example.iitbqa.data.models.User;
import com.example.iitbqa.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.et_ldap)
    EditText etLdap;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_bio)
    EditText etBio;

    @BindView(R.id.et_spec)
    EditText etSpec;

    @BindView(R.id.atvSearch)
    AutoCompleteTextView atvSearch;

    @BindView(R.id.male)
    RadioButton rbMale;

    @BindView(R.id.female)
    RadioButton rbFemale;

    @BindView(R.id.student)
    RadioButton rbStudent;

    @BindView(R.id.prof)
    RadioButton rbProf;

    @BindView(R.id.ll_spec)
    LinearLayout llSpec;

    @BindView(R.id.ll_student)
    LinearLayout llStudent;

    @BindView(R.id.btn_topics)
    Button btnTopics;

    @BindView(R.id.btn_signup)
    Button btnSignup;

    @BindView(R.id.atv_dept)
    AutoCompleteTextView atvDept;


    @Inject
    Retrofit retrofit;

    @Inject
    @Named("NetworkThread")
    Scheduler networkScheduler;

    @Inject
    @Named("MainThread")
    Scheduler mainScheduler;


    List<String> degreeList = new ArrayList<>();
    Set<String> degreeCode = new HashSet<>();
    Set<String> departmentCode = new HashSet<>();
    List<String> departmentList = new ArrayList<>();
    private String bio = "";
    private String ldap = "";
    private String name = "";
    private String password = "";
    private String specialization = "";

    String[] topicList;
    boolean[] checkedItems;
    List<Integer> checkedTopics = new ArrayList<>();

    @Inject
    AuthManager authManager;

    @Inject
    UserRepository userRepository;
    private String department = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        ((IITBQA) getApplication()).getAppComponent().inject(this);

        topicList = authManager.getTopicList();
        checkedItems = new boolean[topicList.length];

        Map<String, String> degreeMap = authManager.getDegreeChoice();

        degreeCode = degreeMap.keySet();

        for (String s: degreeCode) {
            degreeList.add(s);
        }
//        degreeList.addAll(degreeMap.values());
//        departmentList.addAll(authManager.getDepartmentChoices().values());
        departmentCode = authManager.getDepartmentChoices().keySet();
        for (String s: departmentCode) {
            departmentList.add(s);
        }

        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bio = s.toString();
                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etLdap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ldap = s.toString();
                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
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
                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        etDepartment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                department = s.toString();
//                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, department, atvSearch.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        etSpec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                specialization = s.toString();
                monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rbStudent.setOnClickListener(v -> {
            llStudent.setVisibility(View.VISIBLE);
            llSpec.setVisibility(View.GONE);
        });

        rbProf.setOnClickListener(v -> {
            llStudent.setVisibility(View.GONE);
            llSpec.setVisibility(View.VISIBLE);
        });

        btnTopics.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Topics");
            builder.setMultiChoiceItems(topicList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        if (!checkedTopics.contains(which)) {
                            checkedTopics.add(which);
                        }
                    } else {
                        int j = 0;
                        for (int i : checkedTopics) {
                            if (i == which) {
                                checkedTopics.remove(j);
                                break;
                            }
                            j++;
                        }
                    }
                }
            });

            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    for (int i: checkedTopics) {
//                        selectedTopics.add(topicList[i]);
//                    }

                    monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());


                }
            });
            builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        btnSignup.setOnClickListener(v -> {
            List<Integer> finalTopics = new ArrayList<>();
            for (int i: checkedTopics) {
                Log.d("topics", String.valueOf(i));
                finalTopics.add(i + 1);
            }

            retrofit.create(ApiService.class).addUser(new User(
                    0,ldap, name, atvDept.getText().toString(), password, finalTopics, bio, atvSearch.getText().toString(), "NA", 0, specialization
            ))
                    .subscribeOn(networkScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(
                            user -> {
                                authManager.saveUser(user);
                                authManager.setUserLoggedIn(true);
                                Intent intent = new Intent(this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            },
                            error -> {

                            }
                    );
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, degreeList);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.atvSearch);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, departmentList);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv1 = (AutoCompleteTextView) findViewById(R.id.atv_dept);
        actv1.setThreshold(1);//will start working from first character
        actv1.setAdapter(adapter1);//setting the adapter data into the AutoCompleteTextView

    }

    @Override
    protected void onStart() {
        super.onStart();
        monitorPostButtonStatus(bio, password, ldap, name, specialization, checkedTopics, atvDept.getText().toString(), atvSearch.getText().toString());
    }

    private void monitorPostButtonStatus(String bio, String password, String ldap, String name, String specialization, List<Integer> checkedTopics, String department, String degree) {
        if (!ldap.isEmpty() && !password.isEmpty() && !bio.isEmpty() && !name.isEmpty() && !checkedTopics.isEmpty() && (rbFemale.isChecked() || rbMale.isChecked()) && ((rbStudent.isChecked() && !degree.isEmpty()) || (rbProf.isChecked() && !specialization.isEmpty()))) {
            btnSignup.setBackgroundResource(R.drawable.rounded_button_red);
            btnSignup.setEnabled(true);
        } else {
            btnSignup.setBackgroundResource(R.drawable.rounded_button_red_disable);
            btnSignup.setEnabled(false);
        }
    }

}
