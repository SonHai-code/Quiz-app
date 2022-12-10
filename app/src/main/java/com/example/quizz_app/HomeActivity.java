package com.example.quizz_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private String mUserName = "";
    private int mCorrectAnswers;
    private int mTotalQuestions;
    private String mCategory = "";
    private String mDifficulty = "";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get username from MainActivity
        mUserName = getIntent().getStringExtra(Constants.USER_NAME);

        // Get data from ResultActivity after pressing FINISH button
        mCorrectAnswers = getIntent().getIntExtra(Constants.CORRECT_ANSWER, 0);
        mTotalQuestions = getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 10);
        mCategory = getIntent().getStringExtra(Constants.CATEGORY);
        mDifficulty = getIntent().getStringExtra(Constants.DIFFICULTY);


        // Set HomeFragment as a default
        replaceFragment(new HomeFragment());

        // Set bottom navigation bar selections
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.history:
                    replaceFragment(new HistoryFragment());
                    break;
            }
            return true;
        });

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mUserName = savedInstanceState.getString(Constants.USER_NAME);
        }
    }

    private void replaceFragment(Fragment fragment) {
        // Send username data to HomeFragment and set HomeFragment as Default


            Bundle bundle = new Bundle();
            bundle.putString(Constants.USER_NAME, mUserName);
            bundle.putInt(Constants.CORRECT_ANSWER, mCorrectAnswers);
            bundle.putInt(Constants.TOTAL_QUESTIONS, mTotalQuestions);
            bundle.putString(Constants.DIFFICULTY, mDifficulty);
            bundle.putString(Constants.CATEGORY, mCategory);
            fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
    }


    // Handle saving data
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Constants.USER_NAME, mUserName);
        super.onSaveInstanceState(outState);
    }


    // Top navigation bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information:
                Intent intent = new Intent(this, InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"20021524@vnu.edu.vn"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback " + Build.VERSION.RELEASE);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}