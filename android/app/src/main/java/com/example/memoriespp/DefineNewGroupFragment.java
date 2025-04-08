package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class DefineNewGroupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_define_new_group, container, false);
        AppCompatButton addNewGroupButton = rootView.findViewById(R.id.addNewGroupButton);

        addNewGroupButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Dodano grupę", Toast.LENGTH_SHORT).show();

            AdminGroupsFragment adminGroupsFragment = new AdminGroupsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminGroupsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
