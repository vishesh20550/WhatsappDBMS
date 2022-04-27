package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText editTextPhone;
    EditText editTextTextPassword;
    String phoneNumberInput;
    String passwordInput;
    String userId;
    Intent intent;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPhone =findViewById(R.id.editTextPhone);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.loginButton);
    }
    public void loginClicked(View view) {
        closeKeyBoard();
        login.setEnabled(false);
        phoneNumberInput = editTextPhone.getText().toString();
        passwordInput = editTextTextPassword.getText().toString();
        if(phoneNumberInput.equals("") || passwordInput.equals(""))
            Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show();
        else{
            String username = phoneNumberInput;
            String password = passwordInput;
            Connection connection = null;

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String ConnectionURL = "jdbc:mysql://103.252.116.130:3306/Whatsapp";
            try {
                connection = DriverManager.getConnection(ConnectionURL, username, password);
                Log.e("Success", "Connection Successful");
                Statement stmt = connection.createStatement();
                String sql = "select user_id, password from auth_table where user_id =(select userid from userlist where phonenumber ="+phoneNumberInput+");";
                ResultSet output = stmt.executeQuery(sql);
                output.beforeFirst();
                if(output.next()){
                    if(!passwordInput.equals(output.getString(2))){
                        Log.e("Password",output.getString(2));
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        userId = output.getString(1);
                        intent = new Intent(this, MainScreenActivity.class);
                        intent.putExtra("userId",userId);
                        this.startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
                }
                connection.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                Log.e("Error", throwable.toString());
                Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        login.setEnabled(true);
    }
    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}