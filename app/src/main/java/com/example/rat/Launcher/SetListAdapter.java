package com.example.rat.Launcher;

import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;

public class SetListAdapter {
    public static ListAdapter boxAdapter;
    public static String nameOfAppName;
    public static ActivityTwo context;

public void SetListAdapterInit(){

}

    public static void SetListAdapterStart(final ActivityTwo cont, ArrayList<ProgrammInstaller> products) {
        context = cont;
        boxAdapter = new ListAdapter(context, products);
        AbsListView lvMain = (AbsListView) context.findViewById(context.getIdForTypeAdapter(3));
        lvMain.setFocusable(true);

        lvMain.setTextFilterEnabled(true);
        lvMain.setAdapter(boxAdapter);

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                nameOfAppName = boxAdapter.getNameApp(arg2);

                PopupMenu popupMenu = new PopupMenu(context, arg1);
                popupMenu.setOnMenuItemClickListener(context);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                return true;
            }
        });
    }
    public static void SetListAdapterFilter(ArrayList<ProgrammInstaller> products) {
        SetListAdapterStart(context,products);
    }
    TextWatcher filterTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boxAdapter.getFilter().filter(s);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };


}
