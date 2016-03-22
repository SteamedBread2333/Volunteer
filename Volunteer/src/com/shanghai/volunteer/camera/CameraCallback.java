package com.shanghai.volunteer.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.format.DateFormat;

public class CameraCallback {
	/** CAMERA_RESULT*/
	public final static int CAMERA_RESULT = 0;
	/** VIDEO_RESULT*/
	public final static int VIDEO_RESULT = 1;
	/** SELECT_PICTURE_RESULT*/
	public final static int SELECT_PICTURE = 2;
	/** SELECT_VIDEO_RESULT*/
	public final static int SELECT_VIDEO = 3;
	/** default picture width */
	public final static int DISPLAYWIDTH = 300;
	/** default picture height */
	public final static int DISPLAYHEIGHT = 300;

	private Activity context;
	/** 请求码 */
	private int requestCode;
	/** 响应码 */
	private int resultCode;
	/** intent */
	private Intent intent;

	/**操作参数 */
	private int operate = 0;
	/**文件路径 */
	private String filePath = "";
	/**文件名 */
	private String fileName = "";
	/**文件类型 */
	private String fileType = "";
	/**thumb路径 */
	private String thumb = "";

	/**拍照URI*/
	private Uri imageFileUri;

	/**
	 * 
	 * @param context
	 * 
	 */
	public CameraCallback(Activity context) {
		this.context = context;
	}

	/**
	 * 设置请求参数
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	public void setCallBackParameter(int requestCode, int resultCode,
			Intent intent) {
		this.requestCode = requestCode;
		this.resultCode = resultCode;
		this.intent = intent;
		operate = 0;
		filePath = "";
		fileName = "";
		fileType = "";
		thumb = "";
		processCallBack();
	}

	/**
	 * 处理CameraCallback的操作结果给调用activity
	 * */
	private void processCallBack() {
		// 处理不同的操作结果
		if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE) {
			operate = SELECT_PICTURE;
			Uri uri = intent.getData();
			ContentResolver cr = context.getContentResolver();
			fileType = cr.getType(uri);
			Cursor cursor = getPicCursor(cr, uri);
			if (cursor != null) {
				int fileColumn = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				int displayColumn = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
				if (cursor.moveToFirst()) {
					filePath = cursor.getString(fileColumn);
					fileName = cursor.getString(displayColumn);
				}
				// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
				if (android.os.Build.VERSION.SDK_INT < 14) {
					cursor.close();
				}
				return;
			}
		} else if (resultCode == Activity.RESULT_OK
				&& requestCode == SELECT_VIDEO) {
			operate = SELECT_VIDEO;
			Uri uri = intent.getData();
			ContentResolver cr = context.getContentResolver();
			fileType = cr.getType(uri);
			Cursor cursor = getVideoCursor(cr, uri);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					filePath = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
					fileName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
					thumb = getThumbPath(cursor);
				}
				if (android.os.Build.VERSION.SDK_INT < 14) {
					cursor.close();
				}
				return;
			}
		} else if (resultCode == Activity.RESULT_OK
				&& requestCode == CAMERA_RESULT) { // 照片保存
			operate = CAMERA_RESULT;
			String save = DateFormat.format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			ContentValues contentValues = new ContentValues(2);
			contentValues.put(Media.DISPLAY_NAME, save);
			contentValues.put(Media.DESCRIPTION, save);
			// 保存成功
			if (context.getContentResolver().update(imageFileUri,
					contentValues, null, null) == 1) {
				// 刷新库
				try {
					Cursor cursor = getPicCursor(null, null);
					if (cursor != null) {
						if (cursor.moveToLast()) {
							int fileColumn = cursor
									.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
							int displayColumn = cursor
									.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
							filePath = cursor.getString(fileColumn);
							fileName = cursor.getString(displayColumn);
						}
						if (android.os.Build.VERSION.SDK_INT < 14) {
							cursor.close();
						}
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		} else if (resultCode == Activity.RESULT_OK
				&& requestCode == VIDEO_RESULT) { // 视频保存
			operate = VIDEO_RESULT;
			String save = DateFormat.format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".3gp";
			Uri videoFileUri = intent.getData();
			ContentValues contentValues = new ContentValues(1);
			contentValues.put(MediaStore.MediaColumns.TITLE, save);
			if (context.getContentResolver().update(videoFileUri,
					contentValues, null, null) == 1) {
				// 刷新库
				Cursor cursor = getVideoCursor(null, null);
				if (cursor != null) {
					if (cursor.moveToLast()) {
						filePath = cursor
								.getString(cursor
										.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
						fileName = cursor
								.getString(cursor
										.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
						thumb = getThumbPath(cursor);
					}
					cursor.close();
					return;
				}
			}
		}
	}

	/**
	 * 获得DCIM照片库
	 * 
	 * @param cr
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Cursor getPicCursor(ContentResolver cr, Uri uri) {
		Cursor cursor = null;
		String[] columns = { Media.DATA, Media._ID, Media.TITLE,
				Media.DISPLAY_NAME };
		if (uri != null) {
			cursor = cr.query(uri, columns, null, null, null);
		} else {
			cursor = context.managedQuery(Media.EXTERNAL_CONTENT_URI, columns,
					null, null, null);
		}
		return cursor;
	}

	/**
	 * 获得DCIM视频库
	 * 
	 * @param cr
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Cursor getVideoCursor(ContentResolver cr, Uri uri) {
		Cursor cursor = null;
		String[] mediaColumns = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE };
		if (uri != null) {
			cursor = context.managedQuery(uri, mediaColumns, null, null, null);
		} else {
			cursor = context.managedQuery(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
					null, null, null);
		}
		return cursor;
	}

	/**
	 * 获得视频缩略图路径
	 * 
	 * @param cursor
	 * @return
	 */
	private String getThumbPath(Cursor cursor) {
		String thumb = "";
		String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
				MediaStore.Video.Thumbnails.VIDEO_ID };
		int id = cursor.getInt(cursor
				.getColumnIndex(MediaStore.Video.Media._ID));
		@SuppressWarnings("deprecation")
		Cursor thumbCursor = context.managedQuery(
				MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns,
				MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null);
		if (thumbCursor.moveToFirst()) {
			thumb = thumbCursor.getString(thumbCursor
					.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
		}
		thumbCursor.close();
		return thumb;
	}

	public Uri getImageFileUri() {
		return imageFileUri;
	}

	public void setImageFileUri(Uri imageFileUri) {
		this.imageFileUri = imageFileUri;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	/**
	 * 跳转到图片图库
	 */
	public void tuku2Pictrue() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		context.startActivityForResult(i, SELECT_PICTURE);
	}

	/**
	 * 跳转到视频图库
	 */
	public void tuku2Video() {
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		innerIntent.setType("video/*");
		Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		context.startActivityForResult(wrapperIntent, SELECT_VIDEO);
	}

	/**
	 * 调用摄像头拍照
	 */
	public void camera2Pic() {

		this.setImageFileUri(context.getContentResolver().insert(
				Media.EXTERNAL_CONTENT_URI, new ContentValues()));
		Intent capturePicIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		capturePicIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				getImageFileUri());
		context.startActivityForResult(capturePicIntent, CAMERA_RESULT);
	}

	/**
	 * 调用摄像头录像
	 */
	public void camera2Video() {
		Intent captureVideoIntent = new Intent(
				android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
		context.startActivityForResult(captureVideoIntent, VIDEO_RESULT);
	}

	/**
	 * 计算图片压缩后比例
	 * @param options BitmapFactory.Options对象
	 * @param minSideLength 
	 * @param maxNumOfPixels 
	 * @return 位图对象
	 * */
	public int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * 计算图片压缩后比例
	 * @param options BitmapFactory.Options对象
	 * @param minSideLength 
	 * @param maxNumOfPixels 
	 * @return 位图对象
	 * */
	private int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 获取压缩后的图片
	 * @param imageFilePath 图像的路径
	 * @param w 指定图片的宽度
	 * @return 位图对象
	 * */
	public Bitmap getCompressPicture(String imageFilePath, int w) {
		if(! new File(imageFilePath).exists()){
			return null;
		}
		BitmapFactory.Options opts = null;
		Bitmap obmp = null;
		int h = w * 2 / 3;
		
		opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;  
		// 获取这个图片的宽和高，注意此处的bitmap为null 
		BitmapFactory.decodeFile(imageFilePath, opts);
		h = opts.outHeight * w / opts.outWidth;
		System.out.println("w:"+w+",h:"+h);
		// 计算图片缩放比例
		final int minSideLength = Math.min(w, h);
		opts.inSampleSize = computeSampleSize(opts, minSideLength, w * h);
		opts.inJustDecodeBounds = false;
		opts.inInputShareable = true;
		opts.inPurgeable = true;

		try {
			obmp = BitmapFactory.decodeFile(imageFilePath, opts);
		
			if (obmp != null) {
				obmp = zoomImage(obmp, w, h, opts.inSampleSize);  

			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return obmp;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
    public int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
    /**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
		Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        		bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	/**
	 * 将图片压缩放入到SDCARD中
	 * */
	public void copySmallImage(String imageFilePath) {
		
		BitmapFactory.Options opts = null;
		Bitmap obmp = null;
		int w = 900;
		int h = 640;
		
		//according to width compute height 1920,2560
		opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;  
		// 获取这个图片的宽和高，注意此处的bitmap为null 
		BitmapFactory.decodeFile(imageFilePath, opts);
		h = (opts.outHeight * w) / opts.outWidth;
	
		// 计算图片缩放比例
		final int minSideLength = Math.min(w, h);
		opts.inSampleSize = computeSampleSize(opts, minSideLength, w * h);
		opts.inJustDecodeBounds = false;
		opts.inInputShareable = true;
		opts.inPurgeable = true;
		try {
			obmp = BitmapFactory.decodeFile(imageFilePath, opts);
			Bitmap nbmp = zoomImage(obmp, w, h, imageFilePath,
					opts.inSampleSize);
			saveBmpFile2SD(imageFilePath, nbmp);
			System.gc();
			obmp.recycle();
			nbmp.recycle();
			System.gc();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将图片缩放到指定的w和h
	 * 
	 * @param bgimage
	 *            位图对象
	 * @param newWidth
	 *            图片宽
	 * @param newHeight
	 *            图片高
	 * */
	public Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight,
			String imageFilePath, int sampleSize) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth;
		float scaleHeight;

		scaleWidth = ((float) newWidth) / width;
		scaleHeight = ((float) newHeight) / height;
		//获取图片角度
		int degree = readPictureDegree(imageFilePath);
		// 缩放图片动作
		matrix.postRotate(degree);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
				matrix, true);

		return bitmap;
	}

	/**
	 * 将图片缩放到指定的w和h
	 * 
	 * @param bgimage
	 *            位图对象
	 * @param newWidth
	 *            图片宽
	 * @param newHeight
	 *            图片高
	 * */
	public Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight,
			int sampleSize) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth;
		float scaleHeight;

		scaleWidth = ((float) newWidth) / width;
		scaleHeight = ((float) newHeight) / height;

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
				matrix, true);

		return bitmap;
	}

	/**
	 * 保存图片到SD卡上
	 * @param fileName 文件名称
	 * @param bitmap 位图资源
	 * @return 保存位图是否成功
	 */
	@SuppressWarnings("finally")
	public static boolean saveBmpFile2SD(String fileName, Bitmap bitmap) {
		File bmp = null;
		FileOutputStream fos = null;
		try {
			bmp = new File(fileName);
			if (bmp.exists()) {
				bmp.delete();
			}
			fos = new FileOutputStream(bmp);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			bitmap.recycle();
			fos.flush();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
	}

}
