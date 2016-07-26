package com.example.rat.Launcher;

import android.graphics.drawable.Drawable;

public class ProgrammInstaller {

    volatile int idInstaller;
    volatile String name;
    volatile Drawable image;
    volatile boolean box;
    volatile String stringSystemName;


    ProgrammInstaller(String _describe, int _idInstaller, Drawable _image, boolean _box, String _stringSystemName) {
            name = _describe;
            idInstaller = _idInstaller;
            image = _image;
            box = _box;
            stringSystemName = _stringSystemName;
        }
    }
