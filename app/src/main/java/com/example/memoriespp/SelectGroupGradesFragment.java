package com.example.memoriespp;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class SelectGroupGradesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_group_grades, container, false);

        ImageButton groupNameButton = rootView.findViewById(R.id.groupNameButton);

        groupNameButton.setOnClickListener(view -> {
            AddGradesFragment addGradesFragment = new AddGradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, addGradesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView; // Poprawione - Zwracamy `rootView`, a nie ponowne `inflater.inflate`
    }

}