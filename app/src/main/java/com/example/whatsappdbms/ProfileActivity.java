package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileActivity extends AppCompatActivity {
    String name;
    String phoneNumber;
    String about,id;
    TextView nameTextView, phoneTextView, aboutTextView;
    String phoneNumberInput, passwordInput;
    ImageView profileImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("userId");
            phoneNumberInput = extras.getString("currUserPhone");
            passwordInput =extras.getString("currUserPassword");
        }
        aboutTextView = findViewById(R.id.aboutTextView);
        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        profileImageView =findViewById(R.id.profileImageView);
        getData();
    }
    public void getData(){

        String username = "root";
        String password = "DBMSProject123";
        Connection connection= null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String ConnectionURL= "jdbc:mysql://103.252.116.130:3306/Whatsapp";
        try {
            connection= DriverManager.getConnection(ConnectionURL,username,password);
            Log.e("Success","Connection Successful");
            Statement stmt = connection.createStatement();
            String sql = "select name, phonenumber, about from userlist where userid = "+id+";";
            ResultSet output=  stmt.executeQuery(sql);
            while(output.next()){
                Log.e("Output",output.getString(1));
                name = output.getString(1);
                phoneNumber = output.getString(2);
                about = output.getString(3);
            }

            connection.close();
            nameTextView.setText(name);
            phoneTextView.setText(phoneNumber);
            aboutTextView.setText(about);
            profileImageView.setImageResource(R.drawable.user);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
        }

    }
}