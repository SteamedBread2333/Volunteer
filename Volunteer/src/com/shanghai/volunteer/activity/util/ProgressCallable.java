/*
 * @(#)ProgressCallable.java 2011-11-11
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

/**
 *
 * @author wang
 */
public interface ProgressCallable<T> {

	T call(IProgressListener iProgressListener);

}
