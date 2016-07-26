package com.example.rat.Launcher;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ListAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    LayoutInflater lInflater;
    volatile ArrayList<ProgrammInstaller> objects;
    Filter filter;

    ListAdapter(Context context, ArrayList<ProgrammInstaller> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getIdForTypeAdapter(int intTypeReturn){
        if (MainActivity.booleanLinearTable){
            if (intTypeReturn==1) {return R.id.tvDescr;}
            else if (intTypeReturn==2) {return R.id.ivImage;}
            else if (intTypeReturn==3) {return R.id.cbBox;}
            else if (intTypeReturn==4) {return R.layout.cheked_list_view_resurce;}
        }
        else {
        if (intTypeReturn==1) {return R.id.tvDescrGrid;}
        else if (intTypeReturn==2) {return R.id.ivImageGrid;}
//        else if (intTypeReturn==3) {return R.id.cbBoxGrid;}
        else if (intTypeReturn==4) {return R.layout.cheked_grid_view_resurce;}
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(getIdForTypeAdapter(4), parent, false);
        }

        ProgrammInstaller p = getProgramm(position);
        TextView textView = ((TextView) view.findViewById(getIdForTypeAdapter(1)));
        textView.setText(p.name);

        if (!MainActivity.booleanLinearTable) {
            ImageButton imageView = (ImageButton) view.findViewById(getIdForTypeAdapter(2));
            imageView.setImageDrawable(p.image);
            imageView.setTag(position);
            imageView.setOnClickListener(myCheckChangGrid);
            if (p.box) {
               imageView.setBackgroundColor(Color.parseColor("#fffac9"));
            }else imageView.setBackgroundColor(Color.parseColor("#FF33B5E5"));

        }else {
            ImageView imageView = (ImageView) view.findViewById(getIdForTypeAdapter(2));
            imageView.setImageDrawable(p.image);
            CheckBox cbBuy = (CheckBox) view.findViewById(getIdForTypeAdapter(3));
            cbBuy.setOnCheckedChangeListener(myCheckChangList);
            cbBuy.setTag(position);
            cbBuy.setChecked(p.box);
        }
        return view;
    }

    ProgrammInstaller getProgramm(int position) {
        return ((ProgrammInstaller) getItem(position));
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            ProgrammInstaller appCurrentLie= getProgramm((Integer) buttonView.getTag());
            int idInstaller = appCurrentLie.idInstaller;
            boolean booleanIscheked = AppListMainActivity.chekListProgramMain(appCurrentLie.idInstaller,isChecked);

            if (!booleanIscheked) {
                boolean booleanIsSet = AppListMainActivity.setListProgramMain(appCurrentLie.idInstaller, isChecked, appCurrentLie.image,appCurrentLie.stringSystemName);

                if (booleanIsSet) {
                    appCurrentLie.box = isChecked;
                } else {//сообщение об неудаче, список полный
                    Toast.makeText(buttonView.getContext(), "Выбрано больше "+MainActivity.maxCountApp+" App", Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }
            }
        }
    };


    View.OnClickListener myCheckChangGrid = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            ProgrammInstaller appCurrentLie= getProgramm((Integer) view.getTag());
            boolean isChecked = !appCurrentLie.box;
            boolean booleanIscheked = AppListMainActivity.chekListProgramMain(appCurrentLie.idInstaller,isChecked);

            if (!booleanIscheked) {
                boolean booleanIsSet = AppListMainActivity.setListProgramMain(appCurrentLie.idInstaller, isChecked, appCurrentLie.image,appCurrentLie.stringSystemName);

                if (booleanIsSet) {
                    appCurrentLie.box = isChecked;
                    if (appCurrentLie.box) {view.setBackgroundColor(Color.parseColor("#fffac9"));}
                    else {view.setBackgroundColor(Color.parseColor("#FF33B5E5"));}
                } else {//сообщение об неудаче, список полный
                    Toast.makeText(view.getContext(), "Выбрано больше "+MainActivity.maxCountApp+" App", Toast.LENGTH_SHORT).show();
                    appCurrentLie.box = false;
                }
            }
        }
    };

 String getNameApp(int position){
     ProgrammInstaller appCurrentLie= getProgramm(position);
        return appCurrentLie.stringSystemName;
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
            filter = new CheeseFilter();
        return filter;
    }

}