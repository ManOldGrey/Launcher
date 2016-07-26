package com.example.rat.Launcher;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean booleanAppIsLoad = false;
    public static LoadSaveDate loadSaveData;
    public static SendAction sendAction = new SendAction();
    public static volatile int maxCountApp;
    public static volatile int countColumsApp;
    public static volatile boolean booleanLinearTable;
    public static ArrayList<ProgrammInstaller> programmInstallers = new ArrayList<ProgrammInstaller>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        loadSaveData = new LoadSaveDate();//инициализируем и загружаем сохраненные данные
        loadSaveData.init(this);
        setScrreenActivity();
    }

    void setScrreenActivity(){
//        AppListMainActivity.refreshData();
        drawMainActiv();//заполним первый экран елементами
        updateActivityForm();//прорисуем и отобразим выбранные программы
        btnSetCall();//назначаем обработчик
    }

    public void drawMainActiv(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.id_gridLayout);
        gridLayout.setColumnCount(countColumsApp);

        for (int i=0;i<maxCountApp;i++) {
            LayoutInflater.from(this).inflate(R.layout.view_button, gridLayout);
            gridLayout.getChildAt(i).setId(100+i);
            gridLayout.getChildAt(i).setOnClickListener(this);
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.idButtonLiner);
        LinearLayout.LayoutParams layParButtonLinear = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        if (this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            layParButtonLinear.weight = 20;}
        else {layParButtonLinear.weight = 10;}
        linearLayout.setLayoutParams(layParButtonLinear);

        LayoutInflater.from(this).inflate(R.layout.view_button_call, linearLayout);
        LayoutInflater.from(this).inflate(R.layout.view_button_programm, linearLayout);
        LayoutInflater.from(this).inflate(R.layout.view_button_send, linearLayout);
    }

    public void btnSetCall(){

        ImageButton btnCall = (ImageButton) findViewById(R.id.id_buttonCall);
        btnCall.setOnClickListener(this);
        Button btnProgramm = (Button) findViewById(R.id.id_buttonProgram);
        btnProgramm.setOnClickListener(this);
        ImageButton btnSms = (ImageButton) findViewById(R.id.id_buttonSMS);
        btnSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_buttonProgram:
                sendAction.sendActivityClass(this, ActivityTwo.class);
                break;
            case R.id.id_buttonCall:
                sendAction.sendActivityAction(this, Intent.ACTION_DIAL,"fone");
                break;
            case R.id.id_buttonSMS:
                sendAction.sendActivityAction(this, Intent.ACTION_VIEW,"sms");
                break;
            default :
                if (v.getId()>200) {sendAction.sendActivityAction(this, String.valueOf(v.getId()), "startApp");}
                break;
        }
    }

    public void updateActivityForm(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.id_gridLayout);
        int countChildren = gridLayout.getChildCount();
        for (int i=0;i<countChildren;i++){
            View view = gridLayout.getChildAt(i);
            int idViewButton = view.getId();
            ImageButton viewButton = (ImageButton) findViewById(idViewButton);
            int idProgramm = AppListMainActivity.getProgramForNumber(i);
            if (idProgramm==0){view.setId(100+i);}
            else {view.setId(idProgramm);}
            if (idProgramm>200) {viewButton.setImageBitmap(AppListMainActivity.listProgramMainBitMap[i]);}
            else {viewButton.setImageBitmap(null);}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void onSettingsMenuClickExit(MenuItem item) {
        finish();
    }

    public void onSettingsMenuClick(MenuItem item) {
        sendAction.sendDialogSetting(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateActivityForm();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadSaveData.saveDataPreferences();
    }


    @Override
    protected void onStop() {
        super.onStop();
        loadSaveData.saveDataPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadSaveData.saveDataPreferences();
    }

}
