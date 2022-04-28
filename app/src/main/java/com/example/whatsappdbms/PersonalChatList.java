package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PersonalChatList extends AppCompatActivity {
    ArrayList <String> names ;
    PersonalChatListAdapter personalChatListAdapter;
    ListView listView;
    String value;
    ArrayList<String> id_sequence;
    String cur_user;
    ProgressBar progressBar;
    String phoneNumber, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat_list);
        progressBar=findViewById(R.id.progressBar1);
        id_sequence=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("type");
            cur_user=extras.getString("userId");
            phoneNumber = extras.getString("currUserPhone");
            passwordInput = extras.getString("currUserPassword");
        }
        names = new ArrayList<>();
        listView = findViewById(R.id.chatlist);
        if(value.equals("group")){

            CompletableFuture.runAsync(() -> {
                // method call or code to be asynch.
                get_group_data();

            });
            setAdapter();


        }else{
            CompletableFuture.runAsync(() -> {
                // method call or code to be asynch.
                getData_personal();

            });
            setAdapter();

        }



    }

    public void getData_personal(){

        String sql = "select * from userlist where userid in ( select user2id from chatlist where userid ="+cur_user+" union select userid from chatlist where user2id="+cur_user+");";
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
            ResultSet output=  stmt.executeQuery(sql);
            output.beforeFirst();
            while(output.next()){
//                Log.e("Output",output.getString(1));
//                Log.e("Output",output.getString(2));
//                Log.e("Output",output.getString(3));
               // Log.e("Output",output.getString(4));

                names.add(output.getString(3));
                id_sequence.add(output.getString(1));
            }

            connection.close();
            this.runOnUiThread(new Runnable() {
                public void run() {
                    personalChatListAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });



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
                intent.putExtra("cur_user",cur_user);
                intent.putExtra("currUserPhone",phoneNumber);
                intent.putExtra("currUserPassword",passwordInput);
                PersonalChatList.this.startActivity(intent);
            }
        });

    }

    public void get_group_data(){
        String sql = "Select * from grouplist where grouplistid in  (Select grouplistid from hasgrouprelation where userid="+cur_user+");";
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
            ResultSet output=  stmt.executeQuery(sql);
            output.beforeFirst();
            while(output.next()){
//                Log.e("Output",output.getString(1));
//                Log.e("Output",output.getString(2));
//                Log.e("Output",output.getString(3));
//                Log.e("Output",output.getString(4));
                names.add(output.getString(2));
                id_sequence.add(output.getString(1));

               // names.add(output.getString(1));
            }

            connection.close();
            this.runOnUiThread(new Runnable() {
                public void run() {
                    personalChatListAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e("Error",throwable.toString());
        }

    }



}