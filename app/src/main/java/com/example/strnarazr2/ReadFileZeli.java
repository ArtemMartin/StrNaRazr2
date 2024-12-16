package com.example.strnarazr2;

import android.annotation.SuppressLint;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileZeli {
    final String nZeli;
    final String nameFIle = "D:/YO_NA/Zeli";
    MainActivity mainActivity;

    public ReadFileZeli(MainActivity mainActivity, String nZeli) {
        this.nZeli = nZeli;
        this.mainActivity = mainActivity;
    }

    public String[] getXYZeli() {
        File file = new File(nameFIle);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String[] masStr;
            while ((line = reader.readLine()) != null) {
                masStr = line.split(",");
                if (nZeli.equals(masStr[0]))
                    reader.close();
                    return new String[]{masStr[1], masStr[2]};
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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
