package com.example.maliciousapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.FileInputStream;
import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.content.ContentResolver;

public class HashFile extends AppCompatActivity {
    public static FileInputStream getFileInputStreamFromContentUri(Context context, Uri contentUri) throws IOException {
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(contentUri);
        return inputStream instanceof FileInputStream ? (FileInputStream) inputStream : null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hash_file);

        final Intent intent = getIntent();

        String hash = null;
        try {
            final FileInputStream fileInputStream = getFileInputStreamFromContentUri(this, intent.getData());
            hash = DigestUtils.sha256Hex(fileInputStream);
        } catch (Exception e) {
            hash = e.getMessage();
        }

        final TextView text = findViewById(R.id.text);
        text.setText("Hash: " + hash);
    }
}

