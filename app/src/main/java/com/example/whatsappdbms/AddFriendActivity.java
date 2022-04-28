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
    String phoneNumber, passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        phoneNumber = intent.getStringExtra("currUserPhone");
        passwordInput = intent.getStringExtra("currUserPassword");
        editTextPhoneUser2 = findViewById(R.id.editTextPhoneUser2);
    }
    public void onAddClick(View view){
        String username = phoneNumber;
        String password = passwordInput;
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
            String sql = "select userid from userlist where phonenumber= "+ editTextPhoneUser2.getText().toString()+";";
            ResultSet output = stmt.executeQuery(sql);
            output.beforeFirst();
            String user2id ="";
            if(output.next()){
                user2id = output.getString(1);
                PreparedStatement p = connection.prepareStatement("Insert into chatlist(userid,user2id) values(?,?);");
                p.setInt(1, Integer.parseInt(userId));
                p.setInt(2,Integer.parseInt(user2id));
                p.executeUpdate();
                Toast.makeText(this, "Friend Added", Toast.LENGTH_SHORT).show();
            }
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
            Toast.makeText(this, "No User Found with this phone number", Toast.LENGTH_SHORT).show();
        }
//        AddFriendActivity.this.startActivity(new Intent(this,MainScreenActivity.class));
    }
}