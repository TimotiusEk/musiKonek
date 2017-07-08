package com.example.timotiusek.musikonek.Helper;

import android.util.Log;

/**
 * Created by wilbe on 06/07/2017.
 */

public class DateFormatter {

    public static String monthYear(String date){
        String result = "UNKNOWN";

        int holder=0;

        String year = "";
        for(int i=0; i<date.length();i++){
            if(date.charAt(i)=='-'){
                year = date.substring(0,i);
                holder = i;
                break;
            }
        }

        String numMonth = "12";

        for(int i=holder+1; i<date.length();i++){
            if(date.charAt(i)=='-'){

                    Log.d("this",holder+" // " + i );
                    numMonth = date.substring(holder+1,i);
                    break;

            }
        }

        Log.d("asdf", "this "+numMonth);
        int intMonth = Integer.valueOf(numMonth);


        String month = "";

        if(intMonth  == 1 ){
            month = "Januari";
        }
        if(intMonth  == 2 ){
            month = "Februari";
        }
        if(intMonth  == 3 ){
            month = "Maret";
        }if(intMonth  == 4 ){
            month = "April";
        }
        if(intMonth  == 5 ){
            month = "Mei";
        }if(intMonth  == 6 ){
            month = "Juni";
        }
        if(intMonth  == 7 ){
            month = "Juli";
        }
        if(intMonth  == 8 ){
            month = "Agustus";
        }
        if(intMonth  == 9 ){
            month = "September";
        }
        if(intMonth  == 10 ){
            month = "Oktober";
        }
        if(intMonth  == 11 ){
            month = "November";
        }
        if(intMonth  == 12 ) {
            month = "Desember";
        }

        return month + " "+ year;
    }

}
