package com.example.maliciousapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import org.apache.commons.codec.digest.DigestUtils
import java.io.InputStream
import java.io.FileInputStream
import java.io.IOException
import android.content.Context
import android.net.Uri
import android.app.Activity

class HashFile : AppCompatActivity() {
    // public static FileInputStream getFileInputStream(
    //     final Context context,
    //     final Uri contentUri
    // ) throws IOException {
    //     final InputStream inputStream = context.getContentResolver()
    //                                            .openInputStream(contentUri);
    //     return inputStream instanceof FileInputStream ? (FileInputStream) inputStream : null;
    // }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hash_file)

        // final Intent intent = getIntent();

        // String hash = null;
        // try {
        //     final FileInputStream fileInputStream = getFileInputStream(this, intent.getData());
        //     hash = DigestUtils.sha256Hex(fileInputStream);
        // } catch (Exception e) {
        //     hash = e.getMessage();
        // }

        // // Print hash on the app for debug
        // final TextView text = findViewById(R.id.text);
        // text.setText("Hash: " + hash);

        // setResult(Activity.RESULT_OK, new Intent().putExtra("hash", hash));
        // finish();
    }
}
