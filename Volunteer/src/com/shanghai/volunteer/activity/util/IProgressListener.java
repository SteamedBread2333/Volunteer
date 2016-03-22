/*
 * @(#)IProgressListener.java 2011-11-11
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

/**
 *
 * @author wang
 */
public interface IProgressListener {
	// ===========================================================
	// Constants
	// ===========================================================
	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * @param pProgress between 0 and 100.
	 */
	public void onProgressChanged(final int pProgress);
}