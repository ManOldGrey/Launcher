package com.example.rat.Launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadSaveDate extends Activity {
    public SharedPreferences preferences;
    public static final String APP_PREFERENCES = "settings";

    public void init(Context context){
        preferences = context.getSharedPreferences(APP_PREFERENCES, context.MODE_PRIVATE);
        loadDataPreferences();
        loadListPackageManager(context);
    }

    public void saveDataPreferences() {

        SharedPreferences.Editor ed = preferences.edit();

        for (int i = 0; i< AppListMainActivity.listProgramMain.length; i++){
            ed.putInt("ListApp"+Integer.toString(i), AppListMainActivity.listProgramMain[i]);
            ed.commit();
        }

        for (int i = 0; i< AppListMainActivity.listProgramMainBitMap.length; i++){
            Bitmap bitmap = AppListMainActivity.listProgramMainBitMap[i];
            if (bitmap==null){
                ed.putString("BitMap"+Integer.toString(i),"");
            }
            else {
                ed.putString("BitMap"+Integer.toString(i), BitMapToString(AppListMainActivity.listProgramMainBitMap[i]));
            }
            ed.commit();
        }

        for (int i = 0; i< AppListMainActivity.listProgramMainName.length; i++){
            ed.putString("strName"+Integer.toString(i), AppListMainActivity.listProgramMainName[i]);
            ed.commit();
        }

        ed.putInt("maxCountApp", MainActivity.maxCountApp);
        ed.commit();

        ed.putInt("countColumsApp", MainActivity.countColumsApp);
        ed.commit();

        ed.putBoolean("booleanLinearTable", MainActivity.booleanLinearTable);
        ed.commit();


    }

    void loadDataPreferences() {

        if (preferences == null){
            for (int i = 0; i< AppListMainActivity.listProgramMain.length; i++) {
                AppListMainActivity.listProgramMain[i]=0;
            }
            MainActivity.maxCountApp = 4;
            MainActivity.countColumsApp = 2;
            AppListMainActivity.refreshData();
        }
        else {
            MainActivity.maxCountApp = preferences.getInt("maxCountApp",4);
            MainActivity.countColumsApp = preferences.getInt("countColumsApp",2);
            MainActivity.booleanLinearTable = preferences.getBoolean("booleanLinearTable",false);

            AppListMainActivity.refreshData();
            for (int i = 0; i< AppListMainActivity.listProgramMain.length; i++) {
                int idProgramm = preferences.getInt("ListApp"+Integer.toString(i),0);
                AppListMainActivity.listProgramMain[i]=idProgramm;

                String strBitmap = preferences.getString("BitMap"+Integer.toString(i),"");
                if (strBitmap==""){}
                else AppListMainActivity.listProgramMainBitMap[i]=StringToBitMap(strBitmap);

                String strName = preferences.getString("strName"+Integer.toString(i),"");
                AppListMainActivity.listProgramMainName[i]=strName;
            }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static void loadAppLAUNCHER(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        MainActivity.programmInstallers.clear();
        List<ResolveInfo> list = pm.queryIntentActivities(intent,0);
        for (ResolveInfo rInfo : list) {
            String nameStringProgramm = rInfo.activityInfo.applicationInfo.loadLabel(pm).toString();
            Drawable r = rInfo.activityInfo.applicationInfo.loadIcon(context.getPackageManager());
            int labelRes = rInfo.activityInfo.applicationInfo.labelRes;
            String stringSystemName = rInfo.activityInfo.packageName;
            boolean booleanChB = false;
            for (int i=0;i<MainActivity.maxCountApp;i++){
                if (AppListMainActivity.getProgramForNumber(i)==labelRes & !AppListMainActivity.getNameProgramForNumber(i).isEmpty() ){
                    booleanChB = true;
                    break;
                }
            }
            MainActivity.programmInstallers.add(new ProgrammInstaller(nameStringProgramm, labelRes,r, booleanChB, stringSystemName));
        }
        MainActivity.booleanAppIsLoad=true;

    }
    public void loadListPackageManager(final Context context){
        new Thread(new Runnable() {
            public void run() {
                loadAppLAUNCHER(context);
                //do time consuming operations
            }
        }).start();
    }
}
