package com.s4.wyaw.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.Toast;

//
// http://colintmiller.com/2010/10/how-to-add-text-over-a-progress-bar-on-android/
// 

public class TextProgressBar extends ProgressBar implements OnClickListener {
	private String text;
	private Paint textPaint;

	public TextProgressBar(Context context) {
		super(context);
		text = "10 points";
		textPaint = new Paint();
		setOnClickListener((OnClickListener) this);
		
	}

	public TextProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = "0 points";
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
	}

	public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		text = "0 Points";
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// First draw the regular progress bar, then custom draw our text
		super.onDraw(canvas);
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		int x = getWidth() / 2 - bounds.centerX();
		int y = getHeight() / 2 - bounds.centerY();
		canvas.drawText(text, x, y, textPaint);
	}

	public synchronized void setText(String text) {
		this.text = text;
		drawableStateChanged();
	}

	public void setTextColor(int color) {
		textPaint.setColor(color);
		drawableStateChanged();
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(this.getContext(), "YOU HAVE CLICKED!", Toast.LENGTH_LONG).show();
		
	}
}

