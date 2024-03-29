package com.example.whatsappdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {
    Intent intent;
    Intent intentFromMain;
    String userId;
    String phoneNumber;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        intentFromMain = getIntent();
        userId = intentFromMain.getStringExtra("userId");
        phoneNumber = intentFromMain.getStringExtra("currUserPhone");
        password = intentFromMain.getStringExtra("currUserPassword");
    }
    public void group (View view){
        intent =new Intent(MainScreenActivity.this, PersonalChatList.class);
        intent.putExtra("type","group");
        intent.putExtra("userId",userId);
        intent.putExtra("currUserPhone",phoneNumber);
        intent.putExtra("currUserPassword",password);
        MainScreenActivity.this.startActivity(intent);

    }
    public void personal (View view){
        intent =new Intent(MainScreenActivity.this, PersonalChatList.class);
        intent.putExtra("type","personal");
        intent.putExtra("userId",userId);
        intent.putExtra("currUserPhone",phoneNumber);
        intent.putExtra("currUserPassword",password);
        MainScreenActivity.this.startActivity(intent);
      }
    public void profile (View view){

        intent =new Intent(MainScreenActivity.this, ProfileActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("currUserPhone",phoneNumber);
        intent.putExtra("currUserPassword",password);
        MainScreenActivity.this.startActivity(intent);
    }
    public void addFriend(View view){
        intent = new Intent(MainScreenActivity.this,AddFriendActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("currUserPhone",phoneNumber);
        intent.putExtra("currUserPassword",password);
        MainScreenActivity.this.startActivity(intent);
    }
}