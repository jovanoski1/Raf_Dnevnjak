package com.example.rafdnevnjak.view.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.db.DataBaseHelper;
import com.example.rafdnevnjak.db.UserContract;
import com.example.rafdnevnjak.view.activites.LoginActivity;
import com.example.rafdnevnjak.view.activites.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;

    private EditText passwordET;
    private EditText passwordConfirmET;
    private Button logoutBtn;
    private Button changePasswordBtn;

    private boolean changePasswordBtnClick = false;
    private SharedPreferences sharedPreferences;
    //private String userDbContent;


    public ProfileFragment(){
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("USAO PROFILE FRAGMENT");
        init(view);
        initListeners();
        //userDbContent = readFile();
    }
    private void init(View view){
        emailTextView = view.findViewById(R.id.editTextProfileEmail);

        passwordET = view.findViewById(R.id.editTextTextPassword);
        passwordConfirmET = view.findViewById(R.id.editTextTextPasswordConfirm);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        emailTextView.setText(sharedPreferences.getString("email","email@email.com"));
    }
    private void initListeners(){
        logoutBtn.setOnClickListener(e->{
            sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        changePasswordBtn.setOnClickListener(e->{
            if(!changePasswordBtnClick){
                passwordET.setVisibility(View.VISIBLE);
                passwordConfirmET.setVisibility(View.VISIBLE);
            }
            else{
                String password1 = String.valueOf(passwordET.getText());
                String password2 = String.valueOf(passwordConfirmET.getText());
                System.out.println("USAO");

                String oldPassword = sharedPreferences.getString("password", null);
                if(password1.equals(password2) && !password1.isEmpty() && !password1.equals(oldPassword) && checkPassword(password1)){
                    sharedPreferences.edit().putString("password", password1).apply();
                    //                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("user_db.txt", Context.MODE_PRIVATE));
//                        userDbContent = userDbContent.replace(oldPassword, password1);
//                        //outputStreamWriter.append("aaaaaaaaa");
//                        outputStreamWriter.append(userDbContent);
//                        outputStreamWriter.close();
                    System.out.println("USAO CHANGE");

                    DataBaseHelper dbHelper = new DataBaseHelper(getContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(UserContract.UserEntry.COLUMN_PASSWORD, password1);

                    String selection = "email = ?";
                    String[] selectionArgs = {sharedPreferences.getString("email", null)};

                    int count = db.update(UserContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);

                    db.close();

                    passwordET.setVisibility(View.GONE);
                    passwordConfirmET.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Password changed", Toast.LENGTH_SHORT).show();

                }
            }

            changePasswordBtnClick = !changePasswordBtnClick;
        });


    }
    private String readFile(){
        StringBuilder ret = new StringBuilder();

        try {
            InputStream inputStream = getContext().openFileInput("user_db.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while((line=bufferedReader.readLine())!=null){
                System.out.println(line);
                ret.append(line).append("\n");
            }

            inputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(ret);
    }

    private boolean checkPassword(String password){
        int numChars = password.replaceAll("[^a-z]+", "").length();
        int numCapitalChars = password.replaceAll("[^A-Z]+", "").length();
        int numDigits = password.replaceAll("\\D+", "").length();
        int numSpecial = password.replaceAll("[^!@#$%^&*()_+.-]", "").length();


        return numChars >= 1 && numDigits >= 1 && numCapitalChars >= 1 &&
                numSpecial < 1;
    }
}
