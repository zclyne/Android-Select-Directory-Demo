package com.example.testselectdirectory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectDirectoryActivity extends AppCompatActivity {

    private RecyclerView filesRecyclerView;
    private Button okButton;
    private Button cancelButton;
    private Button backButton;
    private TextView selectedDirectoryTextView;

    private LinearLayoutManager layoutManager;
    private FilesAdapter adapter;

    private ArrayList<File> subDirectories;
    private ArrayList<String> subDirectoryPaths;
    private File rootDirectory;
    private File currentDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_directory);

        initializeViews();
        initializeDirectories();
        configureRecyclerView();
        setButtonClickListeners();
    }

    private void initializeViews() {
        selectedDirectoryTextView = (TextView) findViewById(R.id.selected_directory_text_view);
        filesRecyclerView = (RecyclerView) findViewById(R.id.files_recycler_view);
        okButton = (Button) findViewById(R.id.ok_button);
        backButton = (Button) findViewById(R.id.back_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
    }

    private void initializeDirectories() {
        subDirectoryPaths = new ArrayList<>();
        // update current directory
        updateCurrentDirectory(rootDirectory = Environment.getExternalStorageDirectory());
        selectedDirectoryTextView.setText(currentDirectory.getPath());
    }

    private void configureRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        filesRecyclerView.setLayoutManager(layoutManager);
        adapter = new FilesAdapter(subDirectoryPaths);
        // when the item is clicked, get the path and go into this directory
        adapter.setOnIemClickListener(new FilesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String clickedItemPath = ((TextView) view).getText().toString();
                File newDirectory = new File(clickedItemPath);
                updateCurrentDirectory(newDirectory);
            }
        });
        filesRecyclerView.setAdapter(adapter);
    }

    private void setButtonClickListeners() {
        // set button click listeners
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("selectedDirectory", currentDirectory.getPath());
                setResult(2333, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToParentDirectory();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBackToParentDirectory();
    }

    private void updateCurrentDirectory(File newDirectory) {
        currentDirectory = newDirectory;
        selectedDirectoryTextView.setText(currentDirectory.getPath());
        subDirectories = new ArrayList<>(Arrays.asList(currentDirectory.listFiles()));
        subDirectoryPaths.clear();
        for (File file : subDirectories) {
            // because the user should select a directory, files are not displayed here
            if (file.isDirectory()) {
//                String[] tmp = file.getPath().split("/");
//                String directoryName = tmp[tmp.length - 1];
//                subDirectoryPaths.add(directoryName);
                subDirectoryPaths.add(file.getPath());
            }
        }
    }

    // when the back button is pressed, go to parent directory
    private void goBackToParentDirectory() {
        if (currentDirectory.getPath().equals(rootDirectory.getPath())) { // already reached the root directory
            super.onBackPressed();
            return;
        }
        // go to parent directory
        updateCurrentDirectory(currentDirectory.getParentFile());
        // refresh recycler view
        filesRecyclerView.getAdapter().notifyDataSetChanged();
    }

}
