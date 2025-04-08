package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class DefineNewClassFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_define_new_class, container, false);
        AppCompatButton addNewClassButton = rootView.findViewById(R.id.addNewClassButton);

        addNewClassButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Dodano przedmiot", Toast.LENGTH_SHORT).show();

            AdminClassesFragment adminClassesFragment = new AdminClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminClassesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
