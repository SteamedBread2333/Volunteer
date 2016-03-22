/*
 * @(#)Callback.java 2011-11-11
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

/**
 *
 * @author wang
 */
public interface Callback<T> {
	// ===========================================================
	// Final Fields
	// ===========================================================
	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * 
	 */
	public void onCallback(final T pCallbackValue);
}
