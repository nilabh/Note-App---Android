package noteapp.nilabh.com.noteapp.customWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Custom ImageView class to render gray scale image on user touch action
 * Created by nilabh on 12-07-2017.
 */
public class GrayScaleImageView extends AppCompatImageView implements View.OnTouchListener {

    /**
     * The Bmp original.
     */
    Bitmap bmpOriginal;


    /**
     * Instantiates a new Gray scale image view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public GrayScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            makeGreyScale();
            return true;
        }
        renderOldBitmap();
        return false;
    }

    private void makeGreyScale() {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if(drawable == null)
            return;
        bmpOriginal = drawable.getBitmap();
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayScale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        setImageBitmap(bmpGrayScale);
    }

    private void renderOldBitmap() {
        if(bmpOriginal != null)
            setImageBitmap(bmpOriginal);
    }
}
