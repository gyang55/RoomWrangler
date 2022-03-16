package com.example.room_wrangler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToMainPage();//Test main_page!!!
    }

    //Test main_page!!!
    private void goToMainPage() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }
}