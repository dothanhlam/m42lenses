package org.catapult.apps.m42lenses.model;

import java.io.Serializable;

import android.database.Cursor;

public class Vendor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -554813553975587901L;

	private String ids;
	private String name;
	
	public Vendor() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public static final Vendor fromCursor(Cursor cursor) {
		Vendor vendor = new Vendor();
		vendor.ids = String.valueOf(cursor.getColumnIndex("_id"));
		vendor.name = cursor.getString(0);
		return vendor;
	}

}
