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

public class SignUpPage extends AppCompatActivity {
    EditText password, phone, bio, name;
    String pname, pphone, pbio, ppassword,created_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        name=findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phoneNumber);
        bio=findViewById(R.id.Bio);

    }
    public void signup (View view){
        pname=name.getText().toString();
        ppassword=password.getText().toString();
        pphone=phone.getText().toString();
        pbio=bio.getText().toString();
        String username = "root";
        String password = "DBMSProject123";
        Connection connection = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String ConnectionURL = "jdbc:mysql://103.252.116.130:3306/";
        try {
            connection = DriverManager.getConnection(ConnectionURL, username, password);
            Log.e("Success", "Connection Successful");
            Statement stmt = connection.createStatement();
            String sql = "create user '"+pphone+"' identified by '"+ppassword+"';";
            stmt.execute(sql);
            sql="Grant insert,select on Whatsapp.chats to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);
            sql="Grant select,insert,update,delete on Whatsapp.chatlist to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);
            sql="Grant insert,select on Whatsapp.groupchats to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);
            sql="Grant insert,update,delete,select on Whatsapp.grouplist to '"+pphone+"' WITH GRANT OPTION;;";
            stmt.execute(sql);
            sql="Grant insert,update,delete, select on Whatsapp.hasgrouprelation to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);
            sql="Grant insert,select on Whatsapp.groupmessageid to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);
            sql="Grant insert,select on Whatsapp.userlist to '"+pphone+"' WITH GRANT OPTION;";
            stmt.execute(sql);

            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error", throwable.toString());
            Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
        }

         username = "root";
         password = "DBMSProject123";
         connection = null;

        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         ConnectionURL = "jdbc:mysql://103.252.116.130:3306/Whatsapp";
        try {
            connection = DriverManager.getConnection(ConnectionURL, username, password);
            Log.e("Success", "Connection Successful");
            Statement stmt = connection.createStatement();
            PreparedStatement p = connection.prepareStatement("Insert into userlist(phonenumber,name,profilephoto,about) Values(?,?,?,?);");
            p.setString(1, pphone);
            p.setString(2,pname);
            p.setString(3,null);
            p.setString(4,pbio);
            p.executeUpdate();
            String sql = "select userid from userlist where phonenumber="+pphone+";";
            ResultSet output = stmt.executeQuery(sql);
            while (output.next()) {
                created_id= output.getString(1);
            }
            Intent intent = new Intent(this, MainScreenActivity.class);
            intent.putExtra("userId",created_id);
            this.startActivity(intent);

            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error", throwable.toString());
            Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
        }



    }



}