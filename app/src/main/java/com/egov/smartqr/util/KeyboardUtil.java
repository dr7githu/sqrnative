package com.egov.smartqr.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
	public static void hide(Context context, View view) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
