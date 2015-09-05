package ru.gid4u.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
import android.widget.Toast;
import android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import ru.ulogin.sdk.UloginAuthActivity;

public class uLogin extends CordovaPlugin {

    private static final String LOG_TAG      = "ulogin";
    private static final String ACTION_AUTH  = "auth";
    private static final String RESULT       = "result";
    private static final int REQUEST_ULOGIN  = 1;

    private CallbackContext callbackContext;
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    	   this.callbackContext = callbackContext;

           if (action.equals(ACTION_AUTH)) {

               Login(callbackContext);

           } else {

               callbackContext.error("Invalid action");
               return false;

           }	 
           return true;
	}
	
    public void Login(CallbackContext callbackContext) throws JSONException {

    	try {

           Intent intent = new Intent(this.cordova.getActivity(), UloginAuthActivity.class);

           String[] providers = { "vkontakte", "odnoklassniki", "google", "mailru", "facebook", "twitter", "webmoney", "openid", "uid" };
           //String[] providers = this.cordova.getActivity().getResources().getStringArray(ru.ulogin.sdk.R.array.ulogin_providers);

           intent.putExtra(UloginAuthActivity.PROVIDERS, new ArrayList<String>(Arrays.asList(providers)));


//		String[] fields = getResources().getStringArray(ru.ulogin.sdk.R.array.ulogin_fields);
//		intent.putExtra(UloginAuthActivity.OPTIONAL, new ArrayList<String>(Arrays.asList(fields)));
           intent.putExtra(UloginAuthActivity.FIELDS, new ArrayList<String>(Arrays.asList(new String[]{ "email" })));

           intent.putExtra(UloginAuthActivity.OPTIONAL, new ArrayList<String>(Arrays.asList(new String[]{"first_name", "last_name", "nickname", "bdate", "sex", "phone", "photo", "photo_big", "city", "country"})));

           intent.putExtra(UloginAuthActivity.APPLICATION_ID, "b9abaff0");
           intent.putExtra(UloginAuthActivity.SECRET_KEY,     "0Pl7fMAibeEz8pGDq4cwg");

//      intent.putExtra(UloginAuthActivity.CLEAN_COOKIES_AFTER_AUTH,true);

           this.cordova.startActivityForResult((CordovaPlugin) this, intent, REQUEST_ULOGIN);

    	}  catch (Exception e) {
           callbackContext.error(e.getMessage());
           Log.d(LOG_TAG, e.getMessage());
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_ULOGIN) {

            HashMap<String, String> userdata = (HashMap<String, String>) intent.getSerializableExtra(UloginAuthActivity.USERDATA);

            if (resultCode == Activity.RESULT_OK) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(RESULT, 200);
                    obj.put("token", userdata.get("token"));
                    String s = "";
                    Iterator it = userdata.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it.next();
                        s += (pairs.getKey() + " = " + pairs.getValue()) + "\n";
                        it.remove();
                    }
                    obj.put("all", s);
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "RESULT_OK, but exception");
                    Log.d(LOG_TAG, e.getMessage());
                }
                this.callbackContext.success(obj);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(RESULT, 404);
                    obj.put("token", "");
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "RESULT_CANCELED and exception");
                    Log.d(LOG_TAG, e.getMessage());
                }
                this.callbackContext.success(obj);
            } else {
                this.callbackContext.error("Unexpected error (return not OK or CANCELED)");
                Log.d(LOG_TAG, "Unexpected error (return not OK or CANCELED)");
            }
        } else {
           Log.d(LOG_TAG, "Unexpected requestCode");
        }
    }
}
