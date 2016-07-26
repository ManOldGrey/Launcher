package com.example.rat.Launcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SendAction {

    void sendActivityClass(Context context, Class classToSend){
        Intent launchNewIntent = new Intent(context,classToSend);
        context.startActivity(launchNewIntent);
    }
    void sendActivityAction(Context context, String actoin, String type){
        Intent intent;
        if (type.equals("fone")) {
            String number = context.getString(R.string.number_set_default);
            Uri call = Uri.parse("tel:" + number);
            intent = new Intent(actoin, call);
        }else if (type.equals("sms")) {
            intent = new Intent(actoin);
            intent.setType("vnd.android-dir/mms-sms");
        }else if (type.equals("startApp")) {
            intent = context.getPackageManager().getLaunchIntentForPackage(AppListMainActivity.getNameForProgramm(Integer.valueOf(actoin)));
        }else if (type.equals("startAppIsNme")) {
            intent = context.getPackageManager().getLaunchIntentForPackage(actoin);
        }else {return;}
        context.startActivity(intent);
    }
    void sendDialogSetting(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Setting");
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.activity_menu_setting, null, false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.idCountCollum);
        input.setText(String.valueOf(MainActivity.countColumsApp));

        int countRows = MainActivity.maxCountApp / MainActivity.countColumsApp;
        final EditText input1 = (EditText) viewInflated.findViewById(R.id.idCountRows);
        input1.setText(String.valueOf(countRows));

        boolean linearTable = MainActivity.booleanLinearTable;
        final CheckBox input2 = (CheckBox) viewInflated.findViewById(R.id.idcheckBoxSettings);
        input2.setChecked(linearTable);


        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.booleanLinearTable = input2.isChecked();
                MainActivity.countColumsApp = Integer.valueOf(input.getText().toString());
                MainActivity.maxCountApp = Integer.valueOf(input1.getText().toString()) * MainActivity.countColumsApp;
                MainActivity.loadSaveData.saveDataPreferences();
                context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                System.exit(0);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    void uninstallPacked(Context context, String app_pkg_name){
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + app_pkg_name));
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        context.startActivity(intent);
    }

    void sendQuestionDeleted(final Context context,final String app_pkg_name) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("You are Reality Deleted App?");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
                uninstallPacked(context,app_pkg_name);
//                dialog.dismiss();
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.cancel();
//            }
//        });
//        builder.show();
    }


}
