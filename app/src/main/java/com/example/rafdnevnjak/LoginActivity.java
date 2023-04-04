package com.example.rafdnevnjak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.rafdnevnjak.view.activites.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;

    private EditText editTextEmail;

    private EditText editTextPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginBtn);

        initListeners();
    }

    private void initListeners(){
        loginButton.setOnClickListener(e-> {

            String regexEmail = "^(.+)@(.+)$";

            Pattern patternEmail = Pattern.compile(regexEmail);
            String email = String.valueOf(editTextEmail.getText());

            if(patternEmail.matcher(email).matches()){
                //System.out.println("Match");
            }else{
                //System.out.println("NO match");
                return;
            }
            String username = String.valueOf(editTextUsername.getText());
            String password = String.valueOf(editTextPassword.getText());

            int numChars = password.replaceAll("[^a-z]+", "").length();
            int numCapitalChars = password.replaceAll("[^A-Z]+", "").length();
            int numDigits = password.replaceAll("\\D+", "").length();
            int numSpecial = password.replaceAll("[^!@#$%^&*()_+.-]", "").length();
            System.out.println(numChars + " "+numCapitalChars);
            if (numChars >=1 && numDigits >= 1 && numCapitalChars >=1 &&
                    numSpecial < 1) {
                //System.out.println("password is valid");
            }
            else {
                //System.out.println("password is invalid");
                return;
            }

            try {
                InputStream inputStream = getAssets().open("user_db.txt");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null){
                    String emailGuessed = line;
                    String usernameGuessed = reader.readLine();
                    String passwordGuessed = reader.readLine();
//                    System.out.println(email+" "+username+" "+password);
//                    System.out.println(emailGuessed+" "+usernameGuessed+" "+passwordGuessed);
//                    System.out.println("--------------");
                    if(email.equals(emailGuessed) && username.equals(usernameGuessed) && password.equals(passwordGuessed)){
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}