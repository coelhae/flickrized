package pt.alex.flickrized.utils;

import java.util.Calendar;

/**
 * Created by nb19875 on 18/06/16.
 */
public class NetUtils {

    public static long getTimeStamp(){
        return Calendar.getInstance().getTimeInMillis() / 1000l; // devide milisecs to seconds
    }
}
