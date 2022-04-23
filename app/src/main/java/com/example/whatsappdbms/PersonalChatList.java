package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonalChatList extends AppCompatActivity {
    ArrayList <String> names ;
    PersonalChatListAdapter personalChatListAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat_list);
        names = new ArrayList<>();
        listView = findViewById(R.id.chatlist);
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
                    String sql = "select name from userlist where userid in ( select recieverid from chats where senderid = 1 union select senderid from chats where recieverid=1);";
            ResultSet output=  stmt.executeQuery(sql);
            while(output.next()){
                Log.e("Output",output.getString(1));
                names.add(output.getString(1));
            }

            connection.close();
            setAdapter();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error",throwables.toString());
        }

    }

    public void setAdapter(){

        personalChatListAdapter = new PersonalChatListAdapter(PersonalChatList.this,names);
        listView.setAdapter(personalChatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersonalChatList.this.startActivity(new Intent(PersonalChatList.this, Personal_Chats.class));

            }
        });

    }



}