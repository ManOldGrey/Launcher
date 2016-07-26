package com.example.rat.Launcher;


import android.widget.Filter;

import java.util.ArrayList;

public class CheeseFilter extends Filter {

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        constraint = constraint.toString().toLowerCase();

        FilterResults newFilterResults = new FilterResults();

        if (constraint != null && constraint.length() > 0) {

            ArrayList<ProgrammInstaller> auxData = new ArrayList<ProgrammInstaller>();

            for (int i = 0; i < MainActivity.programmInstallers.size(); i++) {
                if (MainActivity.programmInstallers.get(i).name.toLowerCase().contains(constraint))
                    auxData.add(MainActivity.programmInstallers.get(i));
            }

            newFilterResults.count = auxData.size();
            newFilterResults.values = auxData;
        } else {

            newFilterResults.count = MainActivity.programmInstallers.size();
            newFilterResults.values = MainActivity.programmInstallers;
        }
        return newFilterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        ArrayList<ProgrammInstaller> resultData = (ArrayList<ProgrammInstaller>) results.values;
        SetListAdapter.SetListAdapterFilter(resultData);
    }

}