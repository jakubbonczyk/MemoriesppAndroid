package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class LookThroughGradesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_look_through_grades, container, false);

        ImageButton groupNameButton = rootView.findViewById(R.id.groupNameButton);
        groupNameButton.setOnClickListener(view -> {
            GroupGradesFragment groupGradesFragment = new GroupGradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, groupGradesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}