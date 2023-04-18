package com.example.rafdnevnjak.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.db.DataBaseHelper;
import com.example.rafdnevnjak.db.UserContract;
import com.example.rafdnevnjak.utils.Utils;
import com.example.rafdnevnjak.view.activites.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;

    private EditText editTextEmail;

    private EditText editTextPassword;
    private Button loginButton;

    private TextView emailErrorTv;
    private TextView usernameErrorTv;
    private TextView passwordErrorTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(()->{
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String s = sharedPreferences.getString("username", null);
            if(s!=null){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return false;

        });
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginBtn);
        emailErrorTv = findViewById(R.id.emailErrorTv);
        usernameErrorTv = findViewById(R.id.usernameErrorTv);
        passwordErrorTv = findViewById(R.id.passwordErrorTv);

        boolean mboolean = false;
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if(!mboolean){
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.apply();

            DataBaseHelper dbHelper = new DataBaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("email", "mihailjovanoski14@gmail.com");
            values.put("username", "mjcar");
            values.put("password", "Mihail01");
            long id = db.insert("users", null, values);
            System.out.println("Napravio tabelu");

            Utils.addObligation("2023-04-11 10:30:00","2023-04-11 11:30:00", "Algebra", "Predavanje",
                    2,1,this);

            Utils.addObligation("2023-04-11 17:00:00","2023-04-11 18:00:00", "Nutanix sastanak", "Za praksu razgovor",
                    3,1,this);

            db.close();

            // copyUserDbToDevice();
        }

        initListeners();
    }

    private void copyUserDbToDevice() throws IOException {
        System.out.println("COPPY");
        InputStream inputStream = getAssets().open("user_db.txt");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user_db.txt",MODE_PRIVATE));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            outputStreamWriter.write(line);
            outputStreamWriter.write("\n");
        }
        outputStreamWriter.close();
    }

    private void initListeners(){
        loginButton.setOnClickListener(e-> {

            String regexEmail = "^(.+)@(.+)$";

            Pattern patternEmail = Pattern.compile(regexEmail);
            String email = String.valueOf(editTextEmail.getText());
            String username = String.valueOf(editTextUsername.getText());
            String password = String.valueOf(editTextPassword.getText());

            boolean emptyField = false;
            if (email.isEmpty()){
                emailErrorTv.setVisibility(View.VISIBLE);
                emptyField = true;
            }
            if (username.isEmpty()){
                usernameErrorTv.setVisibility(View.VISIBLE);
                emptyField = true;
            }
            if (password.isEmpty()){
                passwordErrorTv.setVisibility(View.VISIBLE);
                emptyField = true;
            }
            if (emptyField)return;

            if(patternEmail.matcher(email).matches()){
                //System.out.println("Match");
            }else{
                //System.out.println("NO match");
                Toast.makeText(this, "Wrong email pattern!", Toast.LENGTH_SHORT).show();
                return;
            }

            int numChars = password.replaceAll("[^a-z]+", "").length();
            int numCapitalChars = password.replaceAll("[^A-Z]+", "").length();
            int numDigits = password.replaceAll("\\D+", "").length();
            int numSpecial = password.replaceAll("[^!@#$%^&*()_+.-]", "").length();
            System.out.println(numChars + " "+numCapitalChars);
            if (numChars >=1 && numDigits >= 1 && numCapitalChars >=1 &&
                    numSpecial < 1 && password.length()>=5) {
                //System.out.println("password is valid");
            }
            else {
                //System.out.println("password is invalid");
                Toast.makeText(this, "Password has to contain at least: 5 characters, 1 upper, 1 lower" +
                        ", 1 digit cant contain special characters", Toast.LENGTH_LONG).show();
                return;
            }

            DataBaseHelper dbHelper = new DataBaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {UserContract.UserEntry._ID,UserContract.UserEntry.COLUMN_EMAIL, UserContract.UserEntry.COLUMN_USERNAME, UserContract.UserEntry.COLUMN_PASSWORD};


            Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,  projection, null, null, null, null, null);
            //System.out.println(cursor.getCount() + "prokic");
            while (cursor.moveToNext()) {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                String emailDB = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String usernameDB = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String passwordDB = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                System.out.println("DB : " + ", "+ userId + ", "+ username + ", "+ password);
                // Do something with the data
                if(email.equals(emailDB) && username.equals(usernameDB) && password.equals(passwordDB)){
                    SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("id", userId);
                    editor.putString("email", email);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();

//            try {
//                InputStream inputStream = openFileInput("user_db.txt");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                System.out.println("FILE user_db.txt");
//                while ((line = reader.readLine()) != null){
//                    System.out.println(line);
//                    String[] split = line.split(",");
//                    String emailGuessed = split[0];
//                    String usernameGuessed = split[1];
//                    String passwordGuessed = split[2];
//
//                    if(email.equals(emailGuessed) && username.equals(usernameGuessed) && password.equals(passwordGuessed)){
//                        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("email", email);
//                        editor.putString("username", username);
//                        editor.putString("password", password);
//                        editor.apply();
//                        Intent intent = new Intent(this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailErrorTv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameErrorTv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordErrorTv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}