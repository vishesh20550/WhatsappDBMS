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
        intent.putExtra("type","group");
        MainScreenActivity.this.startActivity(intent);

    }
    public void personal (View view){
        intent =new Intent(MainScreenActivity.this, PersonalChatList.class);
        intent.putExtra("type","personal");
        MainScreenActivity.this.startActivity(intent);
      }
    public void profile (View view){

        MainScreenActivity.this.startActivity(new Intent(MainScreenActivity.this, ProfileActivity.class));

    }
}