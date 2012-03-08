package org.catapult.apps.m42lenses;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.catapult.apps.m42lenses.activities.ViewLensActivity;
import org.catapult.apps.m42lenses.helper.DatabaseHelper;
import org.catapult.apps.m42lenses.layout.HeaderLayout;
import org.catapult.apps.m42lenses.model.Lens;
import org.catapult.apps.m42lenses.model.LensCategory;
import org.catapult.apps.m42lenses.model.Vendor;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	@SuppressWarnings("unused")
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	private SQLiteDatabase database;

	private ArrayAdapter<Vendor> vendorAdapter;
	private ArrayList<Vendor> vendorsArrayList;

	private ArrayAdapter<LensCategory> categoryAdapter;
	private ArrayList<LensCategory> categoryArrayList;

	private int byVendor = 0;
	private int byCategory = 0;

	private HeaderLayout header;

	private Runnable getListOfLensesRunnable;
	private ArrayList<Lens> lensesArraylist;
	private AdvancedAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		header = (HeaderLayout) findViewById(R.id.headerBar);

		lensesArraylist = new ArrayList<Lens>();
		vendorsArrayList = new ArrayList<Vendor>();
		categoryArrayList = new ArrayList<LensCategory>();

		adapter = new AdvancedAdapter(this, R.layout.row_layout,
				lensesArraylist);
		setListAdapter(adapter);

		vendorAdapter = new ArrayAdapter<Vendor>(this,
				android.R.layout.simple_spinner_dropdown_item, vendorsArrayList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView,
						parent);
				view.setText(vendorsArrayList.get(position).getName());
				return view;
			}
		};

		categoryAdapter = new ArrayAdapter<LensCategory>(this,
				android.R.layout.simple_spinner_dropdown_item,
				categoryArrayList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView,
						parent);
				view.setText(categoryArrayList.get(position).getName());
				return view;
			}
		};

		getListOfLensesRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fillData();
			}
		};

		Thread thread = new Thread(null, getListOfLensesRunnable,
				"MagentoBackground");
		thread.start();

		header.vendorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showVendorDialog();
			}
		});

		header.categoryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showCategoryDialog();
			}
		});

		ListView listView = this.getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(onItemClickListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.filterByCategoryMenuItem:
			showCategoryDialog();
			return true;

		case R.id.filterByVendorMenuItem:
			showVendorDialog();
			return true;

		case R.id.searchMenuItem:
			return true;

		default:
			break;
		}
		return false;
	}

	public OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Lens lens = (Lens) parent.getItemAtPosition(position);
			if (lens.getCategoryIds() != "") {
				LensCategory category = (LensCategory) categoryAdapter
						.getItem(Integer.valueOf(lens.getCategoryIds()));
				lens.setCategory(category.getName());
			}
			Intent intent = new Intent(MainActivity.this,
					ViewLensActivity.class);
			intent.putExtra("lens", lens);
			startActivity(intent);
		}
	};

	private void fillData() {
		DatabaseHelper helper = new DatabaseHelper(this);
		try {
			helper.createDataBase();

		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {

			helper.openDataBase();

		} catch (SQLException sqle) {
			throw sqle;
		}

		String fields[] = { "name", "year", "vendor_ids", "category_ids",
				"fmin", "fmax", "amax", "amin", "focusmin", "filter", "_id" };
		database = helper.getReadableDatabase();
		Cursor lenses = database.query("lens", fields, null, null, null, null,
				null);

		int size = lenses.getCount();
		lensesArraylist = new ArrayList<Lens>();
		for (int i = 0; i < size; i++) {
			// Position the cursor
			lenses.moveToNext();
			lensesArraylist.add(Lens.fromCursor(lenses));
		}

		Cursor vendors = database.query("vendor",
				new String[] { "name", "_id" }, null, null, null, null, "_id");

		size = vendors.getCount();
		vendorsArrayList = new ArrayList<Vendor>();
		for (int i = 0; i < size; i++) {
			// Position the cursor
			vendors.moveToNext();
			vendorsArrayList.add(Vendor.fromCursor(vendors));
		}

		Cursor categories = database.query("category", new String[] { "name",
				"_id" }, null, null, null, null, "_id");

		size = categories.getCount();
		categoryArrayList = new ArrayList<LensCategory>();
		for (int i = 0; i < size; i++) {
			// Position the cursor
			categories.moveToNext();
			categoryArrayList.add(LensCategory.fromCursor(categories));
		}

		runOnUiThread(returnRes);
	}

	private Runnable returnRes = new Runnable() {

		@Override
		public void run() {
			if (lensesArraylist != null && lensesArraylist.size() > 0) {
				adapter.notifyDataSetChanged();
				for (int i = 0; i < lensesArraylist.size(); i++)
					adapter.add(lensesArraylist.get(i));
			}

			if (vendorsArrayList != null && vendorsArrayList.size() > 0) {
				vendorAdapter.notifyDataSetChanged();
				for (int i = 0; i < vendorsArrayList.size(); i++)
					vendorAdapter.add(vendorsArrayList.get(i));
			}

			if (categoryArrayList != null && categoryArrayList.size() > 0) {
				categoryAdapter.notifyDataSetChanged();
				for (int i = 0; i < categoryArrayList.size(); i++)
					categoryAdapter.add(categoryArrayList.get(i));
			}
		}
	};

	private void showVendorDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(vendorAdapter, byVendor,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						byVendor = item;
						Vendor vendor = (Vendor) vendorAdapter.getItem(item);
						header.vendorButton.setText(vendor.getName());
						adapter.filterBySettings();
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showCategoryDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(categoryAdapter, byCategory,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						byCategory = item;
						LensCategory category = (LensCategory) categoryAdapter
								.getItem(item);
						header.categoryButton.setText(category.getName());
						adapter.filterBySettings();
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private class AdvancedAdapter extends ArrayAdapter<Lens> {
		private ArrayList<Lens> items;

		public AdvancedAdapter(Context context, int textViewResourceId,
				ArrayList<Lens> items) {
			super(context, textViewResourceId, items);
			// TODO Auto-generated constructor stub
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row_layout, null);
			}

			Lens lens = null;
			if (position < items.size()) {
				lens = items.get(position);
			}

			if (lens != null) {
				TextView nameTextView = (TextView) v.findViewById(R.id.label);
				nameTextView.setText(lens.getName());
				// displayLensImage(lens.getIds());
			}
			return v;
		}

		private void displayLensImage(String lensId) {
			final AssetManager assetManager = getAssets();
			try {
				ImageView lensImageView = (ImageView) findViewById(R.id.icon);
				InputStream ims = assetManager
						.open("lenses/" + lensId + ".jpg");
				Drawable d = Drawable.createFromStream(ims, null);
				lensImageView.setImageDrawable(d);

			} catch (Exception e) {
				Log.d(TAG, e.getMessage());
			}
		}

		public void filterBySettings() {
			items.clear();
			for (int i = 0; i < lensesArraylist.size(); i++) {
				if ((byVendor > 0) && (byCategory == 0)) {
					if (lensesArraylist.get(i).getVendorIds()
							.compareTo(String.valueOf(byVendor)) == 0) {
						items.add(lensesArraylist.get(i));
					}
				} else if ((byCategory > 0) && (byVendor == 0)) {
					if (lensesArraylist.get(i).getCategoryIds()
							.compareTo(String.valueOf(byCategory)) == 0) {
						items.add(lensesArraylist.get(i));
					}

				} else if ((byCategory > 0) && (byVendor > 0)) {
					if ((lensesArraylist.get(i).getVendorIds()
							.compareTo(String.valueOf(byVendor)) == 0)
							&& (lensesArraylist.get(i).getCategoryIds()
									.compareTo(String.valueOf(byCategory)) == 0)) {
						items.add(lensesArraylist.get(i));

					}
				} else {
					items.add(lensesArraylist.get(i));
				}
			}
			notifyDataSetChanged();
		}
	}
}