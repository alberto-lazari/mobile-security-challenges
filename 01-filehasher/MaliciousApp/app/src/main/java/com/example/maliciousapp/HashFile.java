package com.example.maliciousapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.FileInputStream;

public class HashFile extends AppCompatActivity {
    private static String calcHash(final String filePath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            return DigestUtils.sha256Hex(fileInputStream);
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hash_file);

        final TextView text = findViewById(R.id.text);

        final Intent intent = getIntent();
        final String filePath = intent.getData()
                                      .toString()
                                      .replaceAll("^content:/", "/data");
        final String hash = calcHash(filePath);

        text.setText("File: " + filePath + "\nHash: " + hash);
    }
}

