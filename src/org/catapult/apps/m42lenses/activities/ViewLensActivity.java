/**
 * 
 */
package org.catapult.apps.m42lenses.activities;

import java.io.InputStream;

import org.catapult.apps.m42lenses.R;
import org.catapult.apps.m42lenses.model.Lens;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Lam Do
 * 
 */
public class ViewLensActivity extends Activity {
	private static final String TAG = ViewLensActivity.class.getSimpleName();

	/**
	 * 
	 */
	public ViewLensActivity() {
		// TODO Auto-generated constructor stub

	}

	private void displayLensImage(String lensId) {
		final AssetManager assetManager = getAssets();
		try {
			ImageView lensImageView = (ImageView) findViewById(R.id.lensImageView);
			InputStream ims = assetManager.open("lenses/" + lensId + ".jpg");
			Drawable d = Drawable.createFromStream(ims, null);
			lensImageView.setImageDrawable(d);

		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_lens_layout);

		try {
			Lens lens = (Lens) getIntent().getSerializableExtra("lens");
			displayLensImage(lens.getIds());
			
			TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
			nameTextView.setText(lens.getName());

			TextView categoryTextView = (TextView) findViewById(R.id.categoryTextView);
			if (lens.getCategory() != "") {
				categoryTextView.setText(lens.getCategory());
			}

			TextView yearTextView = (TextView) findViewById(R.id.yearTextView);
			yearTextView.setText(lens.getYear());

			TextView fminTextView = (TextView) findViewById(R.id.fminTextView);
			fminTextView.setText(lens.getfMin());

			TextView fmaxTextView = (TextView) findViewById(R.id.fmaxTextView);
			fmaxTextView.setText(lens.getfMax());

			TextView aminTextView = (TextView) findViewById(R.id.aminTextView);
			aminTextView.setText(lens.getaMin());

			TextView amaxTextView = (TextView) findViewById(R.id.amaxTextView);
			amaxTextView.setText(lens.getaMax());

			TextView minFocusTextView = (TextView) findViewById(R.id.minFocusTextView);
			minFocusTextView.setText(lens.getFocusMin());

			TextView filterTextView = (TextView) findViewById(R.id.filterTextView);
			filterTextView.setText(lens.getFilter());

		} catch (Exception e) {
			// TODO: handle exception

		}
	}
}
