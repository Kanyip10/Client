package com.html5killer.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.html5killer.R;
import com.html5killer.db.StatusInfoDB;
import com.google.android.gms.location.GeofenceStatusCodes;

import java.util.ArrayList;

public class Prefs {
    private static String MY_ERRORS_MULTI_PREF;
    private static String MY_ERRORS_PREF;
    private static String MY_HINTS_PREF;
    private static String MY_ISRESUME_PREF;
    private static String MY_POINTS_MULTI_PREF;
    private static String MY_POINTS_PREF;
    private static String MY_RESUME_MULTI_PREF;
    private static String MY_RESUME_PREF;
    private static String MY_SCALEH_PREF;
    private static String MY_SCALEW_PREF;
    private static String MY_SOUND_PREF;
    private static String MY_STAGE_MULTI_PREF;
    private static String MY_STAGE_PREF;
    private static String MY_TOTALS_PLAYER1_PREF;
    private static String MY_TOTALS_PLAYER2_PREF;
    private static String MY_TOTALS_PREF;


    static {
        MY_STAGE_PREF = "stage";
        MY_STAGE_MULTI_PREF = "stageMulti";
        MY_POINTS_PREF = "points";
        MY_POINTS_MULTI_PREF = "pointsMulti";
        MY_HINTS_PREF = "hints";
        MY_ERRORS_PREF = StatusInfoDB.NUM_ERRORS;
        MY_ERRORS_MULTI_PREF = "errorsMulti";
        MY_SOUND_PREF = "sound";
        MY_RESUME_PREF = "resume";
        MY_RESUME_MULTI_PREF = "resumeMulti";
        MY_SCALEW_PREF = "scaleW";
        MY_SCALEH_PREF = "scaleH";
        MY_TOTALS_PREF = "totals";
        MY_TOTALS_PLAYER1_PREF = "totalsPlayer1";
        MY_TOTALS_PLAYER2_PREF = "totalsPlayer2";
        MY_ISRESUME_PREF = "isresumed";
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("net.androidiconpacks.findmulti", 0);
    }

    public static void clearPref(Context context) {
        setPointsPref(context, null);
        setResumePref(context, context.getResources().getInteger(R.integer.levelDuration) * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
        setStagePref(context, 1);
        setScaleWPref(context, 0);
        setScaleHPref(context, 0);
        setHintsPref(context, context.getResources().getInteger(R.integer.hintsAllowed));
        setErrorsPref(context, context.getResources().getInteger(R.integer.errorsAllowed));
        setTotalsPref(context, 0);
        setIsResumedPref(context, false);
    }

    public static void clearMultiPref(Context context) {
        setScaleWPref(context, 0);
        setScaleHPref(context, 0);
        setTotalsPlayer1Pref(context, 0);
        setTotalsPlayer2Pref(context, 0);
    }

    public static boolean isResumed(Context context) {
        return getPrefs(context).getBoolean(MY_ISRESUME_PREF, false);
    }

    public static int getTotalsPref(Context context) {
        return getPrefs(context).getInt(MY_TOTALS_PREF, 0);
    }

    public static int getTotalsPlayer1Pref(Context context) {
        return getPrefs(context).getInt(MY_TOTALS_PLAYER1_PREF, 0);
    }

    public static int getTotalsPlayer2Pref(Context context) {
        return getPrefs(context).getInt(MY_TOTALS_PLAYER2_PREF, 0);
    }

    public static int getStagePref(Context context) {
        return getPrefs(context).getInt(MY_STAGE_PREF, 1);
    }

    public static int getStageMultiPref(Context context) {
        return getPrefs(context).getInt(MY_STAGE_MULTI_PREF, 1);
    }

    public static int getResumePref(Context context) {
        return getPrefs(context).getInt(MY_RESUME_PREF, context.getResources().getInteger(R.integer.levelDuration) * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
    }

    public static int getResumeMultiPref(Context context) {
        return getPrefs(context).getInt(MY_RESUME_MULTI_PREF, context.getResources().getInteger(R.integer.levelDuration) * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
    }

    public static ArrayList<Integer> getPointsPref(Context context) {
        ArrayList<Integer> myList = new ArrayList();
        for (String str : getPrefs(context).getString(MY_POINTS_PREF, "").split(",")) {
            if (!str.equalsIgnoreCase("")) {
                myList.add(Integer.valueOf(str));
            }
        }
        return myList;
    }

    public static ArrayList<Integer> getPointsMultiPref(Context context) {
        ArrayList<Integer> myList = new ArrayList();
        for (String str : getPrefs(context).getString(MY_POINTS_MULTI_PREF, "").split(",")) {
            if (!str.equalsIgnoreCase("")) {
                myList.add(Integer.valueOf(str));
            }
        }
        return myList;
    }

    public static boolean getSoundPref(Context context) {
        return getPrefs(context).getBoolean(MY_SOUND_PREF, true);
    }

    public static int getErrorsPref(Context context) {
        return getPrefs(context).getInt(MY_ERRORS_PREF, context.getResources().getInteger(R.integer.errorsAllowed));
    }

    public static int getErrorsMultiPref(Context context) {
        return getPrefs(context).getInt(MY_ERRORS_MULTI_PREF, context.getResources().getInteger(R.integer.errorsAllowed));
    }

    public static int getHintsPref(Context context) {
        return getPrefs(context).getInt(MY_HINTS_PREF, context.getResources().getInteger(R.integer.hintsAllowed));
    }

    public static int getScaleWPref(Context context) {
        return getPrefs(context).getInt(MY_SCALEW_PREF, 1);
    }

    public static int getScaleHPref(Context context) {
        return getPrefs(context).getInt(MY_SCALEH_PREF, 1);
    }

    public static void setStagePref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_STAGE_PREF, value).commit();
    }

    public static void setStageMultiPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_STAGE_MULTI_PREF, value).commit();
    }

    public static void setIsResumedPref(Context context, boolean value) {
        getPrefs(context).edit().putBoolean(MY_ISRESUME_PREF, value).commit();
    }

    public static void setTotalsPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_TOTALS_PREF, value).commit();
    }

    public static void setTotalsPlayer1Pref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_TOTALS_PLAYER1_PREF, value).commit();
    }

    public static void setTotalsPlayer2Pref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_TOTALS_PLAYER2_PREF, value).commit();
    }

    public static void setResumePref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_RESUME_PREF, value).commit();
    }

    public static void setMultiResumePref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_RESUME_MULTI_PREF, value).commit();
    }

    public static void setPointsPref(Context context, ArrayList<Integer> points) {
        String value = "";
        if (points != null) {
            for (int i = 0; i < points.size(); i++) {
                if (value.equalsIgnoreCase("")) {
                    value = Integer.toString(((Integer) points.get(i)).intValue());
                } else {
                    value = new StringBuilder(String.valueOf(value)).append(",").append(Integer.toString(((Integer) points.get(i)).intValue())).toString();
                }
            }
        }
        getPrefs(context).edit().putString(MY_POINTS_PREF, value).commit();
    }

    public static void setPointsMultiPref(Context context, ArrayList<Integer> points) {
        String value = "";
        if (points != null) {
            for (int i = 0; i < points.size(); i++) {
                if (value.equalsIgnoreCase("")) {
                    value = Integer.toString(((Integer) points.get(i)).intValue());
                } else {
                    value = new StringBuilder(String.valueOf(value)).append(",").append(Integer.toString(((Integer) points.get(i)).intValue())).toString();
                }
            }
        }
        getPrefs(context).edit().putString(MY_POINTS_MULTI_PREF, value).commit();
    }

    public static void setSoundPref(Context context, boolean value) {
        getPrefs(context).edit().putBoolean(MY_SOUND_PREF, value).commit();
    }

    public static void setErrorsPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_ERRORS_PREF, value).commit();
    }

    public static void setErrorsMultiPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_ERRORS_MULTI_PREF, value).commit();
    }

    public static void setHintsPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_HINTS_PREF, value).commit();
    }

    public static void setScaleWPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_SCALEW_PREF, value).commit();
    }

    public static void setScaleHPref(Context context, int value) {
        getPrefs(context).edit().putInt(MY_SCALEH_PREF, value).commit();
    }
}
