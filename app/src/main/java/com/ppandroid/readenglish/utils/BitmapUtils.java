/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class BitmapUtils {

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromByte(byte[] resource,
                                                     int resId, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(resource, 0, resource.length, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(resource, 0, resource.length,
				options);
	}

	public static Bitmap decodeSampledBitmapFromResource(Context context,
                                                         int resId, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		if (reqWidth != 0 && reqHeight != 0) {
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
		}
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(context.getResources(), resId,
				options);
	}

	public static Bitmap decodeSampledBitmapFromFile(Context context,
                                                     String path, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		if (reqWidth != 0 && reqHeight != 0) {
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
		}
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static String saveImage(Bitmap photo, String fileName) {
		File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "qzapp/images/");
		if (!root.exists()) {
			root.mkdirs();
		}
		File out = new File(root, fileName);

		String absolutePath = out.getAbsolutePath();
		System.out.println("absolutePath is " + absolutePath);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(absolutePath, false));
			
			photo.compress(Bitmap.CompressFormat.PNG, 100, bos);//????
			bos.flush();
			System.out.println("size is " + out.length());
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return absolutePath;
	}

	public static Bitmap getBitmapByFileName(String fileName, int reqWidth,
                                             int reqHeight) {
		File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "qzapp/images/");
		if (!root.exists()) {
			root.mkdirs();
		}
		File out = new File(root, fileName);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(out.getAbsolutePath(), options);

		if (reqWidth != 0) {
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
		}
		options.inSampleSize = 1;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(out.getAbsolutePath(), options);
	}

	public static synchronized String queryImageContentFromSdCard(
			String fileName) {
		File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "qzapp/images/");
		if (!root.exists()) {
			root.mkdirs();
		}
		File out = new File(root, fileName);
		try {
			FileInputStream inputStream = new FileInputStream(out);
			byte[] buffer = new byte[(int) out.length()];
			inputStream.read(buffer);
			inputStream.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
			// return new String(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int width) {
		float scale = (float) width / bitmap.getWidth();
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postScale(scale, scale);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		// bitmap.recycle();
		System.gc();
		return resizeBmp;
	}

	public static Bitmap zoomBitmapByHeight(Bitmap bitmap, int height) {
		float scale = (float) height / bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postScale(scale, scale);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		// bitmap.recycle();
		System.gc();
		return resizeBmp;
	}




	public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
		int bmpWidth = bmp.getWidth();
		int bmpHeght = bmp.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);
		return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
	}


	/**
	 * ???
	 * @param bitmap
	 * @param px
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int px) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = px;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * ???
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float pxPrecent) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth()*pxPrecent;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}


	public static Bitmap createBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = false;//inJustDecodeBounds??true???????????
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;// ?????????
			int srcHeight = opts.outHeight;// ????????
			int destWidth = 0;
			int destHeight = 0;
			// ?????
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// ??????????????maxLength???????????
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// ?????????????????????????????????inSampleSize????????????????SDK??????2????
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds??false??????????
			newOpts.inJustDecodeBounds = false;
			// ?????????????????inSampleSize????????????????
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// ???????
			return BitmapFactory.decodeFile(path, newOpts);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ???????
	 * @param path
	 * @return
	 */
	public static int getPictureDegree(String path){

	    int degree = 0;
	    try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch(orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 9270;
                break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

	    return degree;
	}



	/**
	 * ????????
	 * @param bitmap
	 * @param path
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, String path){

	    if(bitmap != null){
	        Matrix m = new Matrix();

	        int degress = getPictureDegree(path);
	        m.postRotate(degress);
	        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(),
	                m, true);
	        return rotateBitmap;
	    }
	    return null;
	}

	 public static String getPathCut(String fileName){
	        File root = new File(Environment.getExternalStorageDirectory()
	                + File.separator + "qzapp/images/");
	        if (!root.exists()) {
	            root.mkdirs();
	        }
	        File out = new File(root, fileName);
	        String absolutePath = out.getAbsolutePath();

	        return absolutePath;
	    }

	 /**
	     * ???????:?????????????????
	     * @param srcPath
	     * @return
	     */
	    public static Bitmap getimage(String srcPath) {
	    	System.out.println("000file is " + srcPath);
	    	/*String fileName = srcPath.split("qzapp/images/")[1];
	    	File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
	    			+File.separator+"qzapp/images/"+fileName);*/

	    	//System.out.println("000file is " + file.getAbsolutePath());

	    	/*InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/

	        BitmapFactory.Options newOpts = new BitmapFactory.Options();
	        //??????????options.inJustDecodeBounds ??true?
	        newOpts.inJustDecodeBounds = true;
	        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//????bm??

	       // Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, newOpts);

	        newOpts.inJustDecodeBounds = false;
	        int w = newOpts.outWidth;
	        int h = newOpts.outHeight;
	        //??????????800*480??????????????
	        float hh = 800f;//???????800f
	        float ww = 480f;//???????480f
	        //????????????????????????????????
	        int be = 1;//be=1?????
	        if (w > h && w > ww) {//?????????????????
	            be = (int) (newOpts.outWidth / ww);
	        } else if (w < h && h > hh) {//?????????????????
	            be = (int) (newOpts.outHeight / hh);
	        }
	        if (be <= 0)
	            be = 1;
	        newOpts.inSampleSize = be;//??????
	        //??????????????options.inJustDecodeBounds ??false?
	        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
	        //bitmap = BitmapFactory.decodeStream(inputStream, null, newOpts);
	        return compressImage(bitmap);//???????????????
	    }

	    /**
	     * ????????100k??
	     * @param image
	     * @return
	     */
	    public static Bitmap compressImage(Bitmap image) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     // ?????????100????????????????baos?
	        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
	        int options = 80;
	        // ???????????????100kb,??????

	        while ( baos.toByteArray().length / 1024 > 100 && options >10) {
	            baos.reset();// ??baos???baos
	            options -= 10;// ?????10
	         // ????options%???????????baos?
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
	            Log.i("", "--------------???"+options);
	            Log.i("", "--------------???"+baos.toByteArray().length / 1024);
	        }
	     // ???????baos???ByteArrayInputStream?
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
	     // ?ByteArrayInputStream??????
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

	        recycleBitmap(image);
	        return bitmap;
	    }

	    /**
	     * ??Bitmap
	     * @param bitmap
	     */
	    public static void recycleBitmap(Bitmap bitmap){
	    	if(null != bitmap && !bitmap.isRecycled()){
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}
	    }

	/**
	 * ??????????????????????????????????????????????
	 * @param bitmap
	 * @param context
	 */
	public static void zoomBitmapByWidthAndHeight(Bitmap bitmap, Context context){
		int screenWidth = Utils_Screen.getScreenWidth(context);
		int screenHeigth = Utils_Screen.getScreenHeight(context);
		Boolean iswidth = (float)bitmap.getWidth()/bitmap.getHeight() >= (float)screenWidth/screenHeigth ;
		if(iswidth){
			zoomBitmapByHeight(bitmap, screenHeigth);
		}else{
			zoomBitmap(bitmap, screenWidth);
		}
	}
}
