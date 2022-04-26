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
    String value;
    ArrayList<String> id_sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat_list);
        id_sequence=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("type");
        }
        names = new ArrayList<>();
        listView = findViewById(R.id.chatlist);
        if(value.equals("group")){
            get_group_data();

        }else{
            getData_personal();
        }



    }

    public void getData_personal(){

        String sql = "select * from userlist where userid in ( select recieverid from chats where senderid = 1 union select senderid from chats where recieverid=1);";
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
            ResultSet output=  stmt.executeQuery(sql);
            while(output.next()){
                Log.e("Output",output.getString(1));
                Log.e("Output",output.getString(2));
                Log.e("Output",output.getString(3));
               // Log.e("Output",output.getString(4));

                names.add(output.getString(3));
                id_sequence.add(output.getString(1));
            }

            connection.close();
            setAdapter();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
        }

    }

    public void setAdapter(){

        personalChatListAdapter = new PersonalChatListAdapter(PersonalChatList.this,names);
        listView.setAdapter(personalChatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent =new Intent(PersonalChatList.this, Personal_Chats.class);
                intent.putExtra("type",value);
                intent.putExtra("id",id_sequence.get(i));
                PersonalChatList.this.startActivity(intent);
            }
        });

    }

    public void get_group_data(){
        String sql = "Select * from grouplist where grouplistid in  (Select grouplistid from hasgrouprelation where userid=1)";
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
            ResultSet output=  stmt.executeQuery(sql);
            while(output.next()){
                Log.e("Output",output.getString(1));
                Log.e("Output",output.getString(2));
                Log.e("Output",output.getString(3));
                Log.e("Output",output.getString(4));
                names.add(output.getString(2));
                id_sequence.add(output.getString(1));

               // names.add(output.getString(1));
            }

            connection.close();
            setAdapter();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
        }

    }



}