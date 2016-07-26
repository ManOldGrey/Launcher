package com.example.rat.Launcher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

class AppListMainActivity extends AppCompatActivity {
    static Integer[] listProgramMain=new Integer[MainActivity.maxCountApp];
    static Bitmap[] listProgramMainBitMap=new Bitmap[MainActivity.maxCountApp];
    static String[] listProgramMainName=new String[MainActivity.maxCountApp];

    static Integer getProgramForNumber(int number){
        return listProgramMain[number];
    }
    static String getNameProgramForNumber(int number){
        return listProgramMainName[number];
    }

    public static void refreshData(){
        listProgramMain=new Integer[MainActivity.maxCountApp];
        listProgramMainBitMap=new Bitmap[MainActivity.maxCountApp];
        listProgramMainName=new String[MainActivity.maxCountApp];
        for (int i=0;i<MainActivity.maxCountApp;i++){
            if(listProgramMain[i]==null){
            listProgramMain[i]=0;listProgramMainBitMap[i]=null;listProgramMainName[i]="";}
        }
    }

    public static String getNameForProgramm(int idProgramm){
        for (int i=0;i<MainActivity.maxCountApp;i++){
            if (getProgramForNumber(i)==idProgramm){
                return listProgramMainName[i];
            }
        }
        return null;
    }

    static boolean setListProgramMain(int idInstallProgramm,boolean typeAddRemove,Drawable bitmap, String strName){
        boolean setOk=false;

        for (int i=0;i<listProgramMain.length;i++){
            if (listProgramMain[i]==0 & typeAddRemove & listProgramMainName[i].isEmpty()){
                listProgramMain[i]=idInstallProgramm;
                listProgramMainBitMap[i]=drawableToBitmap(bitmap);
                listProgramMainName[i]=strName;
                setOk=true;
                break;
            }
            else if (listProgramMain[i]==idInstallProgramm& !typeAddRemove){
                listProgramMainBitMap[i]=null;
                listProgramMain[i]=0;
                listProgramMainName[i]="";
                setOk=true;}

            else if (listProgramMain[i]==idInstallProgramm& typeAddRemove & listProgramMainName[i].isEmpty()){
                setOk=true;//происходит события во время прокрутки
            }
        }
        return setOk;
    }

    static boolean chekListProgramMain(int idInstallProgramm,boolean typeCheked){
        boolean setOk=false;
        for (int i=0;i<listProgramMain.length;i++){
            if (listProgramMain[i]==idInstallProgramm& typeCheked & !listProgramMainName[i].isEmpty()){
                setOk=true;}
        }
        Boolean itsFind=false;
        for (int i=0;i<listProgramMain.length;i++){
            if ((listProgramMain[i] == idInstallProgramm & !listProgramMainName[i].isEmpty())){
                itsFind=true;}
        }
            if ((!itsFind) & (!typeCheked)) {return true;}
        return setOk;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
