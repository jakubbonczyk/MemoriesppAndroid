package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

public class LookThroughGroupsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_look_through_groups, container, false);

        ImageButton groupButton = rootView.findViewById(R.id.groupButton);


        groupButton.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.getMenu().add("Usuń");
            popupMenu.getMenu().add("Edycja");

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getTitle().toString()) {
                    case "Usuń":
//                        Implementacja usunięcia użytkownika
                        break;
                    case "Edycja":
                            EditGroupFragment editGroupFragment = new EditGroupFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, editGroupFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
                return true;
            });

            popupMenu.show();
            return true;
        });

        return rootView;
    }
}
