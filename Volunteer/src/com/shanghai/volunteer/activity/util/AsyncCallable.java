/*
 * @(#)AsyncCallable.java 2011-11-11
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

/**
 *
 * @author wang
 */
public interface AsyncCallable<T> {
	// ===========================================================
	// Final Fields
	// ===========================================================
	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Computes a result asynchronously, return values and exceptions are to be handled through the callbacks.
	 * This method is expected to return almost immediately, after starting a {@link Thread} or similar.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	public void call(final Callback<T> pCallback, final Callback<Exception> pExceptionCallback);
}