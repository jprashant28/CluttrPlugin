package com.cluttr.post;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import com.cluttr.post.LoginHttpUnit;

public class CLPostPlugin extends CordovaPlugin {

	@Override
    public boolean execute( String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		try
		{
			if (action.equals("POST2CL"))
			{
				LoginHttpUnit.post();
				callbackContext.success();
				return true;
			}
			return true;
		}
		catch(Exception e)
		{
			callbackContext.error(e.getMessage());
			return false;
		}
	}
}
