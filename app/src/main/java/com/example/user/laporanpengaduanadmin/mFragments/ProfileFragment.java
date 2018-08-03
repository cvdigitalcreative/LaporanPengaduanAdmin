package com.example.user.laporanpengaduanadmin.mFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.laporanpengaduanadmin.LoginAdminActivity;
import com.example.user.laporanpengaduanadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private TextView profileEmail;
    private Button btnLogout;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileEmail = (TextView)view.findViewById(R.id.p_Email);
        btnLogout = (Button)view.findViewById(R.id.p_Logout);
        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = user.getEmail();

        profileEmail.setText(email);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
