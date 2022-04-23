package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }
    public void group (View view){
        intent =new Intent(MainScreenActivity.this, PersonalChatList.class);
        intent.putExtra("query","select name from userlist where userid in ( select recieverid from chats where senderid = 1 union select senderid from chats where recieverid=1);");
        MainScreenActivity.this.startActivity(intent);

    }
    public void personal (View view){
        intent =new Intent(MainScreenActivity.this, PersonalChatList.class);
        intent.putExtra("query","select name from userlist where userid in ( select recieverid from chats where senderid = 1 union select senderid from chats where recieverid=1);");
        MainScreenActivity.this.startActivity(intent);
      }
    public void profile (View view){

        MainScreenActivity.this.startActivity(new Intent(MainScreenActivity.this, ProfileActivity.class));

    }
}