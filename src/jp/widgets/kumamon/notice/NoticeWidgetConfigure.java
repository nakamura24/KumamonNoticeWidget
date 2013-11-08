/*
 * Copyright (C) 2012 M.Nakamura
 *
 * This software is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 2.1 Japan License.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://creativecommons.org/licenses/by-nc-sa/2.1/jp/legalcode
 */
package jp.widgets.kumamon.notice;

import jp.widgets.kumamon.lib.*;
import static jp.widgets.kumamon.notice.NoticeWidgetConstant.*;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class NoticeWidgetConfigure extends Activity {
	private static final String TAG = "NoticeWidgetConfigure";
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		try {
			// AppWidgetID の取得
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mAppWidgetId = extras.getInt(
						AppWidgetManager.EXTRA_APPWIDGET_ID,
						AppWidgetManager.INVALID_APPWIDGET_ID);
				Log.d(TAG, "mAppWidgetId=" + String.valueOf(mAppWidgetId));
				setContentView(R.layout.widget_notice_configure);

				StaticHash hash = new StaticHash(this);
				boolean allday = hash.get(ConfigureAllDay,
						false);
				int period = hash.get(ConfigurePeriod, 14);

				CheckBox notice_allday_CheckBox = (CheckBox) findViewById(R.id.notice_allday_CheckBox);
				notice_allday_CheckBox.setChecked(allday);

				EditText notice_priod_EditText = (EditText) findViewById(R.id.notice_priod_EditText);
				notice_priod_EditText.setText(String.valueOf(period));
			}

			Log.i(TAG, "onCreate end");
		} catch (Exception e) {
			ExceptionLog.Log(TAG, e);
		}
	}

	// Button の onClick で実装
	public void onOKButtonClick(View v) {
		try {
			Log.i(TAG, "onOKButtonClick");
			CheckBox notice_allday_CheckBox = (CheckBox) findViewById(R.id.notice_allday_CheckBox);
			EditText notice_priod_EditText = (EditText) findViewById(R.id.notice_priod_EditText);
			StaticHash hash = new StaticHash(this);
			hash.put(ConfigureAllDay, notice_allday_CheckBox.isChecked());
			hash.put(ConfigurePeriod,
					Integer.parseInt(notice_priod_EditText.getText().toString()));
			Intent intent = new Intent(this, KumamonNoticeWidget.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			intent.setAction(CONFIG_DONE);
			sendBroadcast(intent);
			finish();
			Log.i(TAG, "onOKButtonClick End");
		} catch (Exception e) {
			ExceptionLog.Log(TAG, e);
		}
	}
}
