package com.example.quizz_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private String mUserName = "";
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            mUserName = this.getArguments().getString(com.example.quizapp.Constants.USER_NAME);
        }

        binding.textView3.setText("Hi" + " " + mUserName);

        binding.cvMath.setOnClickListener(this);
        binding.cvGeography.setOnClickListener(this);
        binding.cvHistory.setOnClickListener(this);
        binding.cvScience.setOnClickListener(this);

        return binding.getRoot();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cvMath:
                sendCategoryData("MATH");
                break;

            case R.id.cvGeography:
                sendCategoryData("GEOGRAPHY");
                break;

            case R.id.cvHistory:
                sendCategoryData("HISTORY");
                break;

            case R.id.cvScience:
                sendCategoryData("SCIENCE");
                break;
        }
    }

    private void sendCategoryData(String data) {
        // Go to DifficultyFragment
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Use bundle to save the data
        Bundle result = new Bundle();
        result.putString(com.example.quizapp.Constants.CATEGORY, data);
        result.putString(com.example.quizapp.Constants.USER_NAME, mUserName);

        DifficultyFragment difficultyFragment = new DifficultyFragment();
        difficultyFragment.setArguments(result);

        fragmentTransaction.replace(R.id.frame_layout, difficultyFragment).commit();
    }
}