package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Personal_Chats extends AppCompatActivity {
    ArrayList<String> message;
    ArrayList<String> message_sender;
   HashMap<String,String> userid_name;
   Chat_Adapter chat_adapter;
   ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chats);
        message=new ArrayList<>();
        message_sender=new ArrayList<>();
        userid_name = new HashMap<>();
        listView=findViewById(R.id.chatlist);
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
            String sql="select name from userlist where userid = 1;";
            ResultSet output=  stmt.executeQuery(sql);
            while(output.next()){
                userid_name.put("1",output.getString(1));

            }
             sql="select name from userlist where userid = 2;";
             output=  stmt.executeQuery(sql);
            while(output.next()){
                userid_name.put("2",output.getString(1));

            }

            sql = "Select * from chats where (senderid=1 and recieverid=2) or (senderid=2 and recieverid=1) order by senttime;";
             output=  stmt.executeQuery(sql);

            while(output.next()){
                Log.e("Output",output.getString(1));
                Log.e("Output",output.getString(2));
                message.add(output.getString(2));
                Log.e("Output",output.getString(3));
                Log.e("Output",output.getString(4));
                Log.e("Output",output.getString(5));
                message_sender.add(userid_name.get(output.getString(5)));
                Log.e("Output",output.getString(6));

               // names.add(output.getString(1));
            }
            setAdapter();

            connection.close();
            //setAdapter();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error",throwables.toString());
        }

    }
    public void setAdapter(){

        chat_adapter = new Chat_Adapter(Personal_Chats.this,message_sender,message);
        listView.setAdapter(chat_adapter);

    }
}