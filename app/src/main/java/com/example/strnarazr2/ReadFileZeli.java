package com.example.strnarazr2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFileZeli {
    final String nZeli;
    final String nameFIle = "Zeli";
    Context mainActivity;

    public ReadFileZeli(String nZeli, Context mainActivity) {
        this.nZeli = nZeli;
        this.mainActivity = mainActivity;
    }

    @SuppressLint("SuspiciousIndentation")
    public String[] getXYZeli() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(mainActivity.getAssets().open(nameFIle)))) {
            String line;
            String[] masStr;
            while ((line = reader.readLine()) != null) {
                masStr = line.split(",");
                if (nZeli.equals(masStr[0])) {
                    reader.close();
                    return new String[]{masStr[1], masStr[2]};
                }
            }
        } catch (IOException ex) {
            Log.e("Шляпа: ", "class=ReadFileZeli;method=getXYZeli :" + ex.getMessage());
        }
        toast();
        return new String[]{"0", "0"};
    }

    public void toast() {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(mainActivity
                , "Цель под указанным номером не неайдена, возможно введен кирилический символ!!!"
                , Toast.LENGTH_LONG);
        toast.show();
    }

}
