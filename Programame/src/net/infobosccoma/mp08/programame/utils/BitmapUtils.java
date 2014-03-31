package net.infobosccoma.mp08.programame.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

public class BitmapUtils {

	/**
	 * Mètode que fixa la orientació de la imatge capturada en cas que s'hagi
	 * llençat la fotografia amb portrait
	 * 
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap fixOrientation(Bitmap mBitmap) {
		if (mBitmap.getWidth() > mBitmap.getHeight()) {
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
					mBitmap.getHeight(), matrix, true);
		}
 
		return mBitmap;
	}

	/**
	 * Redondejar les vores d'una imatge
	 * 
	 * @param bitmap
	 * @param cornerRadius
	 * @return
	 */
	public static Bitmap getRoundCorneredBitmapFrom(Bitmap bitmap,
			int cornerRadius) {
		if (bitmap == null) {
			return null;
		}
		if (cornerRadius < 0) {
			cornerRadius = 0;
		}
		// Create plain bitmap
		Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = getCanvas(canvasBitmap);

		Paint paint = getPaint();

		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);

		canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return canvasBitmap;
	}

	private static Canvas getCanvas(Bitmap bitmap) {
		Canvas canvas = new Canvas(bitmap);
		canvas.drawARGB(0, 0, 0, 0);
		return canvas;
	}

	private static Paint getPaint() {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		return paint;
	}

	public static void changeOrientation(Bitmap bitmap, String nom_arxiu) {
		ExifInterface exif;
		try {
			exif = new ExifInterface(nom_arxiu);

			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;

			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;

			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			}

			if (rotate != 0) {
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();

				// Setting pre rotate
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);

				// Rotating Bitmap & convert to ARGB_8888, required by tess
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
				bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
