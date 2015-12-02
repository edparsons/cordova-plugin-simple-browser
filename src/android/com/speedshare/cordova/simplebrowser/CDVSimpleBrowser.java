package com.speedshare.cordova.simplebrowser;

//cordova dependencies and stuff
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

//manipulating webview and stuff
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.ViewGroup;
import android.view.View;

//classes for getting and setting screen size
import android.util.Log;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Rect;

public class CDVSimpleBrowser extends CordovaPlugin {

	private static final String logMsg = "!Debugging Tapcast Plugin - CDVSimpleBrowser!";
	private WebView simplebrowser = null;
	private Context context = null;
	private ViewGroup viewgroup = null;
	private View view = null;
	private JSONArray args;
	private String action;
	private CallbackContext callbackContext;

	private boolean start(String url){
		Log.d(logMsg, url);

		if(simplebrowser != null){
			Log.d(logMsg, "Tried to start multiple simplebrowsers");
			return false;
		}
		if(url != null){
			Log.d(logMsg, "Creating SimpleBrowser Webview");
			//get window dimensions
			Rect window = new Rect();
			view.getWindowVisibleDisplayFrame(window);
			int height = window.bottom - window.top - 44;
			int width = window.right - window.left;

			//create a new view with 44px free on bottom
			LayoutParams params = new ViewGroup.LayoutParams(width, height);
			simplebrowser = new WebView(context);
			simplebrowser.setWebViewClient(new WebViewClient());

			//add webview to viewgroup
			viewgroup.addView(simplebrowser, 1, params);
			simplebrowser.loadUrl(url);
			callbackContext.success();
			return true;
		}else{
			Log.d(logMsg, "Url passed in as null, need value");
			return false;
		}

	}

	private boolean stop(){
		Log.d(logMsg, "Destroying SimpleBrowser Webview");

		if(simplebrowser == null){
			Log.d(logMsg, "Called on webview without calling start first.");
			return false;
		}
		viewgroup.removeView(simplebrowser);
		simplebrowser = null;
		return true;
	}

	private boolean hideView(){
		Log.d(logMsg, "in hideView function");
		if(simplebrowser == null){
			Log.d(logMsg, "Called on webview without calling start first.");
			return false;
		}
		simplebrowser.setVisibility(View.INVISIBLE);
		return true;

	}

	private boolean showView(){
		Log.d(logMsg, "in showView function");
		if(simplebrowser == null){
			Log.d(logMsg, "Called on webview without calling start first.");
			return false;
		}
		simplebrowser.setVisibility(View.VISIBLE);
		return true;

	}

	private boolean forward(){
		Log.d(logMsg, "in forward function");
		if(simplebrowser == null){
			Log.d(logMsg, "Called on webview without calling start first.");
			return false;
		}
		if(simplebrowser.canGoForward()){
			simplebrowser.goForward();
			return true;
		}else{
			Log.d(logMsg, "No forward to be gone to");
			return false;
		}

	}

	private boolean back(){
		Log.d(logMsg, "in back function");
		
		if(simplebrowser == null){
			Log.d(logMsg, "Called on webview without calling start first.");
			return false;
		}
		if(simplebrowser.canGoBack()){
			simplebrowser.goBack();
			return true;
		}else{
			Log.d(logMsg, "No back to be gone to");
			return false;
		}
	}

    public boolean execute(String act, JSONArray arguments, CallbackContext cb) throws JSONException{
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
    		callbackContext = cb;
    		action = act;
    		args = arguments;

		    this.cordova.getActivity().runOnUiThread(new Runnable(){;

       			public void run() {
       				try{
       					Boolean success;
			        	if(action.equals("start")){
						   	Log.d(logMsg, "start called");
							String url = args.getString(0);
							success = start(url);
							if(success){
								callbackContext.success();
							}else{
								callbackContext.error("start function called improperly");
							};

						} else if(action.equals("stop")){
			    		    Log.d(logMsg, "stop called");
			    			success = stop();
			    			if(success){
			    				callbackContext.success();
			    			}else{
			    				callbackContext.error("Stop function called improperly");
			    			};

			        	}else if( action.equals("hideView")){
			    		    Log.d(logMsg, "hideView called");
			    		    success =hideView();
			    		    if(success){
			    		    	callbackContext.success();
			    		    }else{
			    				callbackContext.error("Stop function called improperly");
			    		    }

			    		}else if( action.equals("showView")){
			    		    Log.d(logMsg, "showView called");
			    			success = showView();
			    		
			    			if(success){
			    		    	callbackContext.success();			    				
			    			}else{
			    				callbackContext.error("showview function called improperly");
			    			}

			        	}else if(action.equals("forward")){
			    		    Log.d(logMsg, "forward called");
			    			success = forward();
			    			if(success){
			    		    	callbackContext.success();			    				
			    			}else{
			    				callbackContext.error("forward function called improperly");
			    			}

			        	}else if(action.equals("back")){
			    		    Log.d(logMsg, "back called");
			    			success = back();
			    			if(success){
			    		    	callbackContext.success();			    				
			    			}else{
			    				callbackContext.error("back function called improperly");
			    			}
			        	}
			        }catch(Exception e){
			    		Log.d(logMsg, e.getMessage());
			    		callbackContext.error(e.getMessage());
  				  	}
		        }
		    });
    	return true;
    }
   
}