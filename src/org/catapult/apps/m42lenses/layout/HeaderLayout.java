package org.catapult.apps.m42lenses.layout;

import org.catapult.apps.m42lenses.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class HeaderLayout extends LinearLayout {

	public Button vendorButton;
	public Button categoryButton;
	
	public HeaderLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.initComponents();
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initComponents();
	}

	private void initComponents() {
		setOrientation(VERTICAL);
		Context context = this.getContext();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);
		
		vendorButton = (Button) findViewById(R.id.vendorButton);
		categoryButton = (Button) findViewById(R.id.categoryButton);
		
	}
}
