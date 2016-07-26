package com.example.rat.Launcher;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityTwo  extends Activity implements PopupMenu.OnMenuItemClickListener {
    EditText filterEditText;
    SetListAdapter setListadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getIdForTypeAdapter(4));
        setListadapter= new SetListAdapter();
        filterEditText = (EditText)findViewById(R.id.editTextFind);
        filterEditText.addTextChangedListener(setListadapter.filterTextWatcher);
        if (!MainActivity.booleanAppIsLoad){
            Toast.makeText(this, "Ждем загрузки App", Toast.LENGTH_SHORT).show();

            while (!MainActivity.booleanAppIsLoad){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //ждем пока загрузится данные
            }
        }

        setListadapter.SetListAdapterStart(ActivityTwo.this, MainActivity.programmInstallers);
    }

    public int getIdForTypeAdapter(int intTypeReturn){
        if (MainActivity.booleanLinearTable){
            if (intTypeReturn==3) {return R.id.list;}
            else if (intTypeReturn==4) {return R.layout.application_list_activity_two;}
        }
        else {
            if (intTypeReturn==3) {return R.id.grid;}
            else if (intTypeReturn==4) {return R.layout.application_grid_activity_two;}
        }
        return 0;
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_deleted:
                new SendAction().sendQuestionDeleted(this,setListadapter.nameOfAppName);
                return true;
            case R.id.item_info:
                Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_start:
                new SendAction().sendActivityAction(ActivityTwo.this,setListadapter.nameOfAppName , "startAppIsNme");
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        MainActivity.loadSaveData.saveDataPreferences();

    }
}
