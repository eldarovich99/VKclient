package com.example.vk2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

public class ProfileFragment extends Fragment {
    private TextView mUsername;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle args = getArguments();
        mUsername = v.findViewById(R.id.user_name_tv);
        String access_token = args.getString(VKFetchr.ACCESS_TOKEN);
        int expiresIn = args.getInt(VKFetchr.EXPIRES_IN);
        int userId = args.getInt(VKFetchr.USER_ID);
        mUsername.setText(String.valueOf(userId));
        return v;
    }

    public static ProfileFragment newInstance(String token, int expiresIn, int userId) {
        Bundle args = new Bundle();
        args.putSerializable(VKFetchr.ACCESS_TOKEN, token);
        args.putSerializable(VKFetchr.EXPIRES_IN, expiresIn);
        args.putSerializable(VKFetchr.USER_ID, userId);

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
