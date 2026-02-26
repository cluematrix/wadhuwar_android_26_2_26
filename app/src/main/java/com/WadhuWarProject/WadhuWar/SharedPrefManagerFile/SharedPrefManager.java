package com.WadhuWarProject.WadhuWar.SharedPrefManagerFile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;


public class SharedPrefManager {
    public static final String LOGGED_IN_PREF = "logged_in_status";

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String KEY_LAST_API_DATE = "last_api_date";



    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
        pref = mCtx.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

    }

    private static final String PREF_NAME = "testing";

    // All Shared Preferences Keys Declare as #public
    public static final String KEY_SET_APP_RUN_FIRST_TIME  =  "KEY_SET_APP_RUN_FIRST_TIME";

    private static final String IS_HIDDEN = "isHidden";

    public void setApp_runFirst(String App_runFirst)
    {
        editor.remove(KEY_SET_APP_RUN_FIRST_TIME);
        editor.putString(KEY_SET_APP_RUN_FIRST_TIME, App_runFirst);
        editor.apply();
    }
    public FetchProfile getProfileData() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        FetchProfile profile = new FetchProfile();

        profile.setAge(sharedPreferences.getString("age", ""));
        profile.setGender(sharedPreferences.getString("gender", ""));
        profile.setPer_ditrict_name(sharedPreferences.getString("permDistrict", ""));
        profile.setPer_state_name(sharedPreferences.getString("permState", ""));
        profile.setCurrent_address(sharedPreferences.getString("currentAddress", ""));
        profile.setRashi_name(sharedPreferences.getString("rashi", ""));
        profile.setCaste_name(sharedPreferences.getString("cast", ""));
        profile.setCaste_id(sharedPreferences.getString("cast_id", ""));
        profile.setMain_education_name(sharedPreferences.getString("education", ""));
        profile.setMain_occupation_name(sharedPreferences.getString("occupation", ""));
        profile.setOfc_address(sharedPreferences.getString("officeAddress", ""));
        profile.setMothertounge_name(sharedPreferences.getString("motherTongue", ""));
        profile.setSubcaste_name(sharedPreferences.getString("subCast", ""));
        profile.setAcc_type(sharedPreferences.getString("acc_type", ""));
        profile.setPremium(sharedPreferences.getString("premium", ""));

        return profile;
    }
    public String getApp_runFirst()
    {
        String App_runFirst= pref.getString(KEY_SET_APP_RUN_FIRST_TIME, "FIRST");
        return  App_runFirst;
    }
    public void saveLastApiCallDate(String date) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_API_DATE, date);
        editor.apply();
    }
    public String getLastApiCallDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_API_DATE, "");
    }

    public void saveString( String value) {
        editor.putString(IS_HIDDEN, value);
        editor.apply();
    }

    public String getString() {
        return pref.getString(IS_HIDDEN, "");
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }





    public void saveUser(UserData user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("user_id", user.getUser_id());
        editor.putString("name", user.getName());

        editor.putString("mobile", user.getMobile());
        editor.putString("image", user.getImage());
        editor.putString("resid", user.getResid());
        editor.putString("resmsg", user.getResMsg());

//        Log.d("userget123 : ", new Gson().toJson(user));
        editor.apply();
        editor.commit();

    }


    public void saveProfileData(FetchProfile userProfile) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("age", userProfile.getAge());
        editor.putString("gender",userProfile.getGender());

        editor.putString("permDistrict", userProfile.getPer_ditrict_name());
        editor.putString("permState", userProfile.getPer_state_name());
        editor.putString("currentAddress", userProfile.getCurrent_address());
        editor.putString("rashi", userProfile.getRashi_name());
        editor.putString("cast", userProfile.getCaste_name());
        editor.putString("cast_id", userProfile.getCaste_id()); // Added cast_id
        editor.putString("education", userProfile.getMain_education_name());
        editor.putString("occupation", userProfile.getMain_occupation_name());
        editor.putString("officeAddress", userProfile.getOfc_address());
        editor.putString("motherTongue", userProfile.getMothertounge_name());
        editor.putString("subCast", userProfile.getSubcaste_name());
        editor.putString("motherTongue", userProfile.getMothertounge_name());
        editor.putString("motherTongue", userProfile.getMothertounge_name());
        editor.putString("acc_type", userProfile.getAcc_type());
        editor.putString("premium", userProfile.getPremium());

//        Log.d("userget123 : ", new Gson().toJson(user));
        editor.apply();
        editor.commit();
    }


    public String getType() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("acc_type", "");
    }

    public String getGender() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("gender", "");
    }


    public String getCastId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("cast_id", "");
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1) != -1;
    }

    public UserData getUser() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserData(
                sharedPreferences.getInt("user_id", -1),
                sharedPreferences.getString("name", null),

                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("image", null),
                sharedPreferences.getString("resid", null),
                sharedPreferences.getString("resmsg", null)

        );
    }

    public void saveMembershipStatus(boolean hasActivePlan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("HAS_ACTIVE_PLAN", hasActivePlan);
        editor.apply();
    }
    public void saveMobile(String mobile) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saved_mobile", mobile);
        editor.apply();
    }

    public String getSavedMobile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("saved_mobile", "");
    }

    public boolean getMembershipStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("HAS_ACTIVE_PLAN", false);
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}