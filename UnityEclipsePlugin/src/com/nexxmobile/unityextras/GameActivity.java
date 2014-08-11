package com.nexxmobile.unityextras;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class GameActivity extends UnityPlayerActivity {
	private Activity _activity;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		_activity = UnityPlayer.currentActivity;
	}
	public void shareOnFacebook() {
		if (checkConnection()) {
			try {
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				String shareText = "Insert some link to share";
				shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
				String appName = "facebook";
				final PackageManager pm = _activity.getPackageManager();
				final List<?> activityList = pm.queryIntentActivities(
						shareIntent, 0);
				int len = activityList.size();
				for (int i = 0; i < len; i++) {
					final ResolveInfo app = (ResolveInfo) activityList.get(i);
					if ((app.activityInfo.name.contains(appName))) {
						final ActivityInfo activity = app.activityInfo;
						final ComponentName name = new ComponentName(
								activity.applicationInfo.packageName,
								activity.name);
						shareIntent.setComponent(name);
						_activity.startActivity(shareIntent);
						return;
					}
				}
			} catch (Exception e) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.facebook.com/"
								+ "Your fb page id")));
				return;
			}
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/"
							+ "Your fb page id")));
		}
	}
	public void shareOnTwitter() {
		if (checkConnection()) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			String shareText = "Awesome tutorial @nexxmobile";
			shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
			String appName = "twitter";
			final PackageManager pm = _activity.getPackageManager();
			final List<?> activityList = pm.queryIntentActivities(shareIntent,
					0);
			int len = activityList.size();
			for (int i = 0; i < len; i++) {
				final ResolveInfo app = (ResolveInfo) activityList.get(i);
				if ((app.activityInfo.name.contains(appName))) {
					final ActivityInfo activity = app.activityInfo;
					final ComponentName name = new ComponentName(
							activity.applicationInfo.packageName, activity.name);
					shareIntent.setComponent(name);
					_activity.startActivity(shareIntent);
					return;
				}
			}
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.twitter.com/" + "nexxmobile")));
		} else {
			makeToast("No connection");
		}
	}
	public void makeToast(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();
			}
		});

	}
	public void alert(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder bld = new AlertDialog.Builder(_activity);
				bld.setMessage(message);
				bld.setNeutralButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				bld.create().show();
			}
		});

	}
	private Boolean checkConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null) {
			return true;
		}
		return false;
	}
}
