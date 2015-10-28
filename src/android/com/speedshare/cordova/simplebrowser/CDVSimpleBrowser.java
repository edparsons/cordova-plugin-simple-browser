package com.speedshare.cordova.simplebrowser;

//cordova dependencies and stuff
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

//dependencies for accessing android view/context/viewgroup 
import android.util.Log;
import android.content.Context;
import android.webkit.WebView;
import android.view.ViewGroup;
import android.view.View;

//classes for getting and setting screen size
import android.view.ViewGroup.LayoutParams;
import android.graphics.Rect;



public class CDVSimpleBrowser extends CordovaPlugin {

	private static final String logHead = "!CDVSimpleBrowser Debug!";
	private WebView simplebrowser;
	private Context context;
	private ViewGroup viewgroup;
	private View view;

	private boolean start(String url, CallbackContext callbackContext){

		Log.d(logHead, "Creating SimpleBrowser Webview");
		//get window dimensions
		Rect window = new Rect();
		view.getWindowVisibleDisplayFrame(window);
		int height = window.bottom - window.top - 44;
		int width = window.right - window.left;

		//create a new view with 44px free on bottom
		LayoutParams params = new ViewGroup.LayoutParams(width, height);
		simplebrowser = new WebView(context);

		//add webview to viewgroup
		viewgroup.addView(simplebrowser, 0, params);
		simplebrowser.loadUrl(url);
		return true;

	}

	private boolean stop(CallbackContext callbackContext){
		Log.d(logHead, "Destroying SimpleBrowser Webview");

		if(simplebrowser == null){
			Log.d(logHead, "Called on webview without calling start first.");
			return false;
		}
		viewgroup.removeView(simplebrowser);
		simplebrowser = null;
		return true;
	}

	private boolean hideView(CallbackContext callbackContext){
		Log.d(logHead, "in hideView function");
		if(simplebrowser == null){
			Log.d(logHead, "Called on webview without calling start first.");
			return false;
		}
		simplebrowser.setVisibility(View.INVISIBLE);
		return true;

	}

	private boolean showView(CallbackContext callbackContext){
		Log.d(logHead, "in showView function");
		if(simplebrowser == null){
			Log.d(logHead, "Called on webview without calling start first.");
			return false;
		}
		simplebrowser.setVisibility(View.VISIBLE);
		return true;

	}

	private boolean forward(CallbackContext callbackContext){
		Log.d(logHead, "in forward function");
		if(simplebrowser == null){
			Log.d(logHead, "Called on webview without calling start first.");
			return false;
		}
		if(simplebrowser.canGoForward()){
			simplebrowser.goForward();
			return true;
		}else{
			Log.d(logHead, "No forward to be gone to");
			return false;
		}

	}

	private boolean back(CallbackContext callbackContext){
		Log.d(logHead, "in back function");
		
		if(simplebrowser == null){
			Log.d(logHead, "Called on webview without calling start first.");
			return false;
		}
		if(simplebrowser.canGoBack()){
			simplebrowser.goBack();
			return true;
		}else{
			Log.d(logHead, "No back to be gone to");
			return false;
		}
	}

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException{
    	try{
    		Log.d(logHead, "Entering Plugin");

    		//access applications activity context, view, and viewgroup
    		if(context == null){
    			context = this.cordova.getActivity().getApplicationContext();
    		}
    		if(view == null){
    			view = this.cordova.getActivity().getCurrentFocus();
    		}
    		if(viewgroup == null){
    			viewgroup = (ViewGroup) this.cordova.getActivity().getCurrentFocus().getParent();
    		}

        	
        	if(action.equals("start")){
			   	Log.d(logHead, "start called");
				String url = args.getString(0);
				return start(url, callbackContext);

			} else if(action.equals("stop")){
    		    Log.d(logHead, "stop called");
    			return stop(callbackContext);

        	}else if( action.equals("hideView")){
    		    Log.d(logHead, "hideView called");
    			return hideView(callbackContext);

    		}else if( action.equals("showView")){
    		    Log.d(logHead, "showView called");
    			return showView(callbackContext);

        	}else if(action.equals("forward")){
    		    Log.d(logHead, "forward called");
    			return forward(callbackContext);

        	}else if(action.equals("back")){
    		    Log.d(logHead, "back called");
    			return back(callbackContext);
        	}
    	}catch(Exception e){
    		Log.d(logHead, e.getMessage());
    		callbackContext.error(e.getMessage());
    		return false;
    	}
    	return false;

    }
   
}