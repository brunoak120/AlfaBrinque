package com.book.app.util;


import android.os.Build;

public class UtilitarioVersao {

    public static int currentVersion() {
        return Build.VERSION.SDK_INT;
    }

}
