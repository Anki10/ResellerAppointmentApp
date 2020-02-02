package com.anova.reseller_appointment.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.anova.reseller_appointment.R;


public class CustomEditText extends EditText {

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	//	Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro_Regular.otf");
	//	this.setTypeface(face);
		this.setHintTextColor(ContextCompat.getColor(context, R.color.edittextColor));
//		init(context, attrs);
	}
	
	public void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.customfont);
		String fontFamily = null;
		final int n = a.getIndexCount();
		for (int i = 0; i < n; ++i) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.customfont_android_fontFamily) {
				fontFamily = a.getString(attr);
			}
			a.recycle();
		}
		if (!isInEditMode()) {
			try {
				Typeface tf = Typeface.createFromAsset(
						getContext().getAssets(), fontFamily);
				setTypeface(tf);
			} catch (Exception e) {
			}
		}
	}

}
