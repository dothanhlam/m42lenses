package org.catapult.apps.m42lenses.model;

import java.io.Serializable;

import android.database.Cursor;

public class LensCategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695901301087488972L;
	private String ids;
	private String name;

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public static final LensCategory fromCursor(Cursor cursor) {
		LensCategory category = new LensCategory();
		category.ids = String.valueOf(cursor.getColumnIndex("_id"));
		category.name = cursor.getString(0);
		return category;
	}
}
