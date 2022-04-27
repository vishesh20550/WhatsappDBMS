package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddFriendActivity extends AppCompatActivity {
    String userId;
    EditText editTextPhoneUser2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        editTextPhoneUser2 = findViewById(R.id.editTextPhoneUser2);
    }
    public void onAddClick(View view){
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
            PreparedStatement p = connection.prepareStatement("Insert into chatlist(userid,userid2) values(?,(select userid from userlist where phonenumber= ?;));");
            p.setInt(1, Integer.parseInt(userId));
            p.setString(2,editTextPhoneUser2.getText().toString());
            p.executeUpdate();
            Toast.makeText(this, "Friend Added", Toast.LENGTH_SHORT).show();
            connection.close();
            AddFriendActivity.this.startActivity(new Intent(this,MainScreenActivity.class));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
        }
    }
}
