package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Personal_Chats extends AppCompatActivity {
    ArrayList<String> message;
    ArrayList<String> message_sender;
    HashMap<String, String> userid_name;
    Chat_Adapter chat_adapter;
    ListView listView;
    String id;
    String type, cur_user;
    ProgressBar progressBar;
    String phoneNumber, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chats);
        progressBar=findViewById(R.id.progressBar1);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            type = extras.getString("type");
            cur_user = extras.getString("cur_user");
            phoneNumber = extras.getString("currUserPhone");
            passwordInput = extras.getString("currUserPassword");
        }
        message = new ArrayList<>();
        message_sender = new ArrayList<>();
        userid_name = new HashMap<>();
        listView = findViewById(R.id.chatlist);

        if (type.equals("group")) {
            CompletableFuture.runAsync(() -> {
                // method call or code to be asynch.
                get_group_data();

            });
            setAdapter();

        } else {
            CompletableFuture.runAsync(() -> {
                // method call or code to be asynch.
                getData();

            });
            setAdapter();

        }


    }

    public void getData() {

        String username = phoneNumber;
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
            String sql = "select name from userlist where userid ="+cur_user+";";
            ResultSet output = stmt.executeQuery(sql);
            output.beforeFirst();
            while (output.next()) {
                userid_name.put(cur_user, output.getString(1));

            }
            sql = "select name from userlist where userid =" + id + ";";
            output = stmt.executeQuery(sql);
            while (output.next()) {
                userid_name.put(id, output.getString(1));

            }

            sql = "Select * from chats where (senderid="+cur_user+" and recieverid=" + id + ") or (senderid=" + id + " and recieverid="+cur_user+") order by senttime;";
            output = stmt.executeQuery(sql);
            output.beforeFirst();
            while (output.next()) {
                Log.e("Output", output.getString(1));
                Log.e("Output", output.getString(2));
                message.add(output.getString(2));
                Log.e("Output", output.getString(3));
               // Log.e("Output", output.getString(4));
                // Log.e("Output",output.getString(5));
                message_sender.add(userid_name.get(output.getString(5)));
                //  Log.e("Output",output.getString(6));
                // names.add(output.getString(1));
            }
            this.runOnUiThread(new Runnable() {
                public void run() {
                    chat_adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error", throwables.toString());
        }

    }

    public void get_group_data() {
        String username = phoneNumber;
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
            String sql = "Select senderid from groupchats where groupmessageid in ( select groupmessageid from groupmessageid where grouplistid=" + id + ");";
            ResultSet output = stmt.executeQuery(sql);
            output.beforeFirst();
            while (output.next()) {
                Statement stmt1 = connection.createStatement();
                sql = "select name from userlist where userid =" + output.getString(1) + ";";
                ResultSet output1 = stmt1.executeQuery(sql);
                output.beforeFirst();
                while (output1.next()) {
                    userid_name.put(output.getString(1), output1.getString(1));
                }
            }

            sql = "Select * from groupchats where groupmessageid in ( select groupmessageid from groupmessageid where grouplistid=" + id + ");";
            output = stmt.executeQuery(sql);
            output.beforeFirst();
            while (output.next()) {
                Log.e("Output", output.getString(1));
                Log.e("Output", output.getString(2));
                message.add(output.getString(3));
                Log.e("Output", output.getString(3));
                Log.e("Output", output.getString(4));
                // Log.e("Output",output.getString(5));
                message_sender.add(userid_name.get(output.getString(2)));
//                Log.e("Output",output.getString(6));
                // names.add(output.getString(1));

            }

            this.runOnUiThread(new Runnable() {
                public void run() {
                    chat_adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error", throwables.toString());
        }


    }


    public void setAdapter() {

        chat_adapter = new Chat_Adapter(Personal_Chats.this, message_sender, message);
        listView.setAdapter(chat_adapter);

    }

    public void send(View view) {
        EditText editText = findViewById(R.id.send_message);

        if (type.equals("group")) {

            String username = phoneNumber;
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
                PreparedStatement p = connection.prepareStatement("Insert Into groupchats(senderid,message) value(?,?);");
                p.setInt(1, Integer.parseInt(cur_user));
                p.setString(2,editText.getText().toString());
                p.executeUpdate();
                p = connection.prepareStatement("Insert into groupmessageid(grouplistid) value(?);");
                p.setInt(1, Integer.parseInt(id));
                p.executeUpdate();
                message.add(editText.getText().toString());
                message_sender.add(userid_name.get(cur_user));
                chat_adapter.notifyDataSetChanged();
                editText.setText("");
                connection.close();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }





        } else {
            String username = phoneNumber;
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
                PreparedStatement p = connection.prepareStatement("Insert Into chats(message,seentime,senderid,recieverid) value(?,?,?,?);");
                p.setString(1, editText.getText().toString());
                p.setString(2,null);
                p.setInt(3, Integer.parseInt(cur_user));
                p.setInt(4, Integer.parseInt(id));
                p.executeUpdate();
              //  Statement stmt = connection.createStatement();
               // String sql = "Insert Into chats(message,seentime,senderid,recieverid) value ("+editText.getText().toString()+", ,"+"1,"+id+");";
               // stmt.execute(sql);
                message.add(editText.getText().toString());
                message_sender.add(userid_name.get(cur_user));
                chat_adapter.notifyDataSetChanged();
                editText.setText("");
                connection.close();




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


    }
}