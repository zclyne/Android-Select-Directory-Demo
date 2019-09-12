package com.example.testselectdirectory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mSelectDirectoryButton;
    private TextView mSelectedDirectoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectDirectoryButton = (Button) findViewById(R.id.button);
        mSelectedDirectoryTextView = (TextView) findViewById(R.id.selectedDir);

    }

    public void onButtonClick(View view) {

        getPermission();

        Intent intent = new Intent(this, SelectDirectoryActivity.class);
        startActivityForResult(intent, 2333);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2333) {
            String result = data.getStringExtra("selectedDirectory");
            Log.i("t-yifz", "result is " + result);
            mSelectedDirectoryTextView.setText(result);
        }
    }

    private void getPermission()
    {
        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    124);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 124) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
            {
                Log.d("heihei","获取到权限了！");
            } else {
                Log.d("heihei","搞不定啊！");
            }
        }
    }

}
