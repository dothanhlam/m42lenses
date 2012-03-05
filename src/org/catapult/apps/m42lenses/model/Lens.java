/**
 * 
 */
package org.catapult.apps.m42lenses.model;

import java.io.Serializable;

import android.database.Cursor;

/**
 * @author Lam Do
 * 
 */
public class Lens implements Serializable {
	@SuppressWarnings("unused")
	private static final String TAG = Lens.class.getSimpleName();
	/**
	 * 
	 */
	private static final long serialVersionUID = -1488528560293153110L;

	private String ids;
	private String name;
	private String categoryIds;
	private String year;
	private String fMin;
	private String fMax;
	private String aMin;
	private String aMax;
	private String focusMin;
	private String filter;
	private String vendorIds;
	private String category;
	private String vendor;

	/**
	 * 
	 */
	public Lens() {
		// TODO Auto-generated constructor stub
	}

	public static final Lens fromCursor(Cursor cursor) {
		Lens lens = new Lens();

		lens.setIds(String.valueOf(cursor.getColumnIndex("_id")));
		lens.setName(cursor.getString(0));
		if (cursor.getString(1) != "") {
			lens.setYear(cursor.getString(1));
		}
		lens.setVendorIds(cursor.getString(2));
		lens.setCategoryIds(cursor.getString(3));
		lens.setfMin(cursor.getString(4));
		lens.setfMax(cursor.getString(5));
		lens.setaMax(cursor.getString(6));
		lens.setaMin(cursor.getString(7));
		lens.setFocusMin(cursor.getString(8));
		lens.setFilter(cursor.getString(9));
		lens.setIds(String.valueOf(cursor.getInt(10)));
		return lens;
	}

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

	/**
	 * @return the categoryIds
	 */
	public String getCategoryIds() {
		return categoryIds;
	}

	/**
	 * @param categoryIds
	 *            the categoryIds to set
	 */
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the fMin
	 */
	public String getfMin() {
		return fMin;
	}

	/**
	 * @param fMin
	 *            the fMin to set
	 */
	public void setfMin(String fMin) {
		this.fMin = fMin;
	}

	/**
	 * @return the fMax
	 */
	public String getfMax() {
		return fMax;
	}

	/**
	 * @param fMax
	 *            the fMax to set
	 */
	public void setfMax(String fMax) {
		this.fMax = fMax;
	}

	/**
	 * @return the aMin
	 */
	public String getaMin() {
		return aMin;
	}

	/**
	 * @param aMin
	 *            the aMin to set
	 */
	public void setaMin(String aMin) {
		this.aMin = aMin;
	}

	/**
	 * @return the aMax
	 */
	public String getaMax() {
		return aMax;
	}

	/**
	 * @param aMax
	 *            the aMax to set
	 */
	public void setaMax(String aMax) {
		this.aMax = aMax;
	}

	/**
	 * @return the focusMin
	 */
	public String getFocusMin() {
		return focusMin;
	}

	/**
	 * @param focusMin
	 *            the focusMin to set
	 */
	public void setFocusMin(String focusMin) {
		this.focusMin = focusMin;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the vendorIds
	 */
	public String getVendorIds() {
		return vendorIds;
	}

	/**
	 * @param vendorIds
	 *            the vendorIds to set
	 */
	public void setVendorIds(String vendorIds) {
		this.vendorIds = vendorIds;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor
	 *            the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}
