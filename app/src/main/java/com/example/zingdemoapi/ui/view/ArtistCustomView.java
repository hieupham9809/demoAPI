package com.example.zingdemoapi.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;

import java.util.List;

public class ArtistCustomView extends View {
    private List<Artist> artistList;
    private Bitmap[] bitmaps;
    private StaticLayout[] staticLayouts;

    private Bitmap nullArtist;
    private Paint bitmapPaint;
    private TextPaint textPaint;
    private final int NUM_COLUMN = 3;

    private int maxHeightOfStaticLayout;

    private Context context;
    private final float X_INIT = 0.0f;
    private final float Y_INIT = 0.0f;

    RectF rectF = new RectF();

    public void mInvalidate() {
        this.invalidate();
    }

    public void setArtistList(final List<Artist> artistList) {
        this.artistList = artistList;
        bitmaps = new Bitmap[artistList.size()];
        nullArtist = getRoundedBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.null_artist)
                , 1000);
        staticLayouts = new StaticLayout[artistList.size()];
        for (int i = 0; i < artistList.size(); i++) {
            final int j = i;
            staticLayouts[j] = new StaticLayout(artistList.get(j).getName()
                    , textPaint
                    , Math.round(getWidth() * 1.0f / NUM_COLUMN)
                    , Layout.Alignment.ALIGN_CENTER
                    , 1.0f
                    , 0.0f
                    , false);
            Glide.with(context)
                    .asBitmap()
                    .load(artistList.get(i).getAvatar())

                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                      //Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                                      bitmaps[j] = getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.error_load_image), 1000);
                                      ((ProgramInfoActivity) context).runOnUiThread(new Runnable() {

                                          @Override
                                          public void run() {
                                              mInvalidate();
                                          }
                                      });
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                      bitmaps[j] = getRoundedBitmap(resource, 1000);
                                      ((ProgramInfoActivity) context).runOnUiThread(new Runnable() {

                                          @Override
                                          public void run() {
                                              mInvalidate();
                                          }
                                      });
                                      //Log.d("ZingDemoApi", "put image bitmap to list");

                                      return false;
                                  }
                              }
                    ).submit();

        }
        maxHeightOfStaticLayout = findMaxHeightOfStaticLayout(staticLayouts);
        requestLayout();

    }
    private int findMaxHeightOfStaticLayout(StaticLayout[] staticLayouts){
        int height = 0;
        for (int i = 0; i < staticLayouts.length; i++){
            if (staticLayouts[i].getHeight() > height){
                height = staticLayouts[i].getHeight();
            }
        }
        return height;
    }
    public static Bitmap getRoundedBitmap(Bitmap bitmap, int cornerRadius) {

        if (bitmap == null) {
            return null;
        }
        if (cornerRadius < 0) {
            cornerRadius = 0;
        }
        // Create plain bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return canvasBitmap;
    }

    public ArtistCustomView(Context context) {
        super(context);
        this.context = context;

    }

    public ArtistCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initBitmapPaint();
        initTextPaint(attrs);
//        Intent intent = ((ProgramInfoActivity) context).getIntent();
//        if (intent.hasExtra(Constant.PROGRAMID)) {
//            id = intent.getIntExtra(Constant.PROGRAMID, -1);
//        }
    }


    private void initBitmapPaint() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(0XFF000000);

    }

    private void initTextPaint(AttributeSet attributeSet) {
        if (attributeSet == null){
            return;
        }
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ArtistCustomView);
        int textColor = typeArray.getColor(R.styleable.ArtistCustomView_artist_name_text_color, Color.parseColor("#483D8B"));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(Constant.NAME_ARTIST_TEXT_SIZE);
        typeArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (artistList != null) {
            float distance = getWidth() * 1.0f / NUM_COLUMN;
            int rows = (artistList.size() % NUM_COLUMN == 0) ? (artistList.size() / NUM_COLUMN) : (artistList.size() / NUM_COLUMN + 1);
            setMeasuredDimension(getWidth(), rows * (Math.round(distance) + maxHeightOfStaticLayout));
            //super.onMeasure(getWidth(), rows * Math.round(distance));
            //Log.d("ZingDemoApi", "distance " + distance);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float distance = getWidth() * 1.0f / NUM_COLUMN;
        int padding = 50;

        if (bitmaps != null) {

            int artistListLength = artistList.size();

            for (int i = 0; i < artistListLength; i++) {
                int xStep = ((i + 1) % NUM_COLUMN == 0) ? (NUM_COLUMN - 1) : ((i + 1) % NUM_COLUMN - 1);
                int yStep = ((i + 1) % NUM_COLUMN == 0) ? ((i + 1) / NUM_COLUMN - 1) : ((i + 1) / NUM_COLUMN);

                rectF.left = X_INIT + xStep * distance + padding;
                rectF.top = Y_INIT + yStep * distance + padding;
                rectF.right = X_INIT + xStep * distance + distance - padding;
                rectF.bottom = Y_INIT + yStep * distance + distance - padding;

//                Log.d("ZingDemoApi", "left " + rectF.left + "top " + rectF.top
//                        + "right " + rectF.right + "bottom " + rectF.bottom);
                if (bitmaps[i] != null) {
                    canvas.drawBitmap(bitmaps[i], null, rectF, bitmapPaint);
                } else {
                    canvas.drawBitmap(
                            nullArtist
                            , null
                            , rectF
                            , bitmapPaint);
                }
                //canvas.drawBitmap(bitmapList.get(i), null, rectF, paint);

                StaticLayout mArtistNameStaticLayout = staticLayouts[i];
                canvas.save();

                // calculate x and y position where your text will be placed
                canvas.translate(rectF.left - padding , rectF.bottom + Constant.NAME_ARTIST_TEXT_SIZE);
                mArtistNameStaticLayout.draw(canvas);
                canvas.restore();

//                canvas.drawText(name
//                        //, 0
//                        //, (name.length() < Constant.MAX_LENGTH_FOR_TEXT) ? name.length() : Constant.MAX_LENGTH_FOR_TEXT
//                        , rectF.left - padding + distance / 2 - textPaint.measureText(name) * 1.0f / 2, rectF.bottom + Constant.NAME_ARTIST_TEXT_SIZE, textPaint);
            }

            //bitmapList.clear();
        } else {
            Log.d("ZingDemoApi", "has not set");
        }
    }
}
