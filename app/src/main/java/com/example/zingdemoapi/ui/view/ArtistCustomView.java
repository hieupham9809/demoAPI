package com.example.zingdemoapi.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class ArtistCustomView extends View {
    private List<Artist> artistList;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private int id;
    private Paint paint;
    private TextPaint textPaint;
    private final int NUM_COLUMN = 3;

    private Context context;
    private final float X_INIT = 0.0f;
    private final float Y_INIT = 0.0f;


    RectF rectF = new RectF();

    public void mInvalidate() {
        while (true) {
            Log.d("ZingDemoApi", "bitmaplist: " + bitmapList.size() + "artistlist: " + artistList.size());
            if (bitmapList.size() >= artistList.size()) {
                break;
            }
        }
        this.invalidate();
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
        for (int i = 0; i < artistList.size(); i++) {
            Glide.with(context)
                    .asBitmap()
                    .load(artistList.get(i).getAvatar())

                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                      //Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                                      bitmapList.add(getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_program), 1000));
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                      bitmapList.add(getRoundedBitmap(resource, 1000));
                                      Log.d("ZingDemoApi", "put image bitmap to list");

                                      return false;
                                  }
                              }
                    ).submit();

        }

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
        initPaint();
        initTextPaint();

    }

    public ArtistCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
        initTextPaint();
//        Intent intent = ((ProgramInfoActivity) context).getIntent();
//        if (intent.hasExtra(Constant.PROGRAMID)) {
//            id = intent.getIntExtra(Constant.PROGRAMID, -1);
//        }
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0XFF000000);

    }

    private void initTextPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#483D8B"));
        textPaint.setTextSize(Constant.NAME_ARTIST_TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float distance = getWidth() * 1.0f / NUM_COLUMN;
        int padding = 50;
        //canvas = new Canvas(output);

        if (bitmapList.size() != 0) {

            //float distance = getWidth() * 1.0f / NUM_COLUMN;
            int artistListLength = artistList.size();

            for (int i = 0; i < artistListLength; i++) {
                int xStep = ((i + 1) % NUM_COLUMN == 0) ? (NUM_COLUMN - 1) : ((i + 1) % NUM_COLUMN - 1);
                int yStep = ((i + 1) % NUM_COLUMN == 0) ? ((i + 1) / NUM_COLUMN - 1) : ((i + 1) / NUM_COLUMN);

                rectF.left = X_INIT + xStep * distance + padding;
                rectF.top = Y_INIT + yStep * distance + padding;
                rectF.right = X_INIT + xStep * distance + distance - padding;
                rectF.bottom = Y_INIT + yStep * distance + distance - padding;

                Log.d("ZingDemoApi", "left " + rectF.left + "top " + rectF.top
                        + "right " + rectF.right + "bottom " + rectF.bottom);

                canvas.drawBitmap(bitmapList.get(i), null, rectF, paint);
                String name = artistList.get(i).getName();
                canvas.drawText(artistList.get(i).getName()
                        , 0
                        , (name.length() < 15) ? name.length() : 15
                        , rectF.left, rectF.bottom + Constant.NAME_ARTIST_TEXT_SIZE, textPaint);

            }

           //bitmapList.clear();
        } else {
            Log.d("ZingDemoApi", "has not set");
        }
//        canvas.drawPoint(100, 100, paint);
    }
}
