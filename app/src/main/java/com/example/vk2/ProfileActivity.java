package com.example.vk2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vk2.ResponseClasses.AuthResponse;

public class ProfileActivity extends SingleFragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragment();
    }

    @Override
    protected Fragment createFragment() {
        String token = getIntent().getStringExtra(VKFetchr.ACCESS_TOKEN);
        int expiresIn = getIntent().getIntExtra(VKFetchr.EXPIRES_IN, 0);
        int userId = getIntent().getIntExtra(VKFetchr.USER_ID, 0);

        return ProfileFragment.newInstance(token, expiresIn, userId);
    }
/*
    public static Intent newIntent(Context packageContext, String token, int expiresIn, int userId){
        Intent intent = new Intent(packageContext, ProfileActivity.class);
        intent.putExtra(VKFetchr.ACCESS_TOKEN, token);
        intent.putExtra(VKFetchr.EXPIRES_IN, expiresIn);
        intent.putExtra(VKFetchr.USER_ID, userId);
        return intent;
    }*/
}
