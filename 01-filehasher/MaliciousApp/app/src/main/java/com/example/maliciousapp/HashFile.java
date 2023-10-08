package com.example.maliciousapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class HashFile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hash_file);

        TextView text = findViewById(R.id.text);

        Intent intent = getIntent();
        text.setText("I received: " + intent.getData());
    }
}

