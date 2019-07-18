package com.example.zingdemoapi.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.ui.activity.MainActivity;


public class ProgramItemCustomView extends View  {

    private Paint bitmapPaint;
    private TextPaint textPaint;

    private RectF rectF = new RectF();

    Context context;

    Bitmap imageBitmap;
    Bitmap nullImageProgram;
    StaticLayout titleStaticLayout;

    private int screenWidth;

    private String title;

    public ProgramItemCustomView(Context context) {
        super(context);
        this.context = context;
        nullImageProgram = BitmapFactory.decodeResource(getResources(), R.drawable.null_user1);
        initBitmapPaint();

    }

    public ProgramItemCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        nullImageProgram = BitmapFactory.decodeResource(getResources(), R.drawable.null_artist);
        initBitmapPaint();
        initTextPaint(attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        this.screenWidth = w;
        Log.d("ZingDemoApi", "onSizeChanged called");


    }
    private void mInvalidate(){
        requestLayout();
        //this.invalidate();
    }
    public void setImageAndTitle(String thumbnailUrl, final String title){

        this.title = title;
        Glide.with(context)
                .asBitmap()
                .load(thumbnailUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.error_load_image);
                        ((MainActivity) context).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mInvalidate();
                            }
                        });
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        imageBitmap = resource;

                        ((MainActivity) context).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mInvalidate();
                            }
                        });
                        return false;
                    }
                }).submit();
        //requestLayout();
    }
    private void initBitmapPaint() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(0XFF000000);

    }

    private void initTextPaint(AttributeSet attributeSet) {
        if (attributeSet == null){
            return;
        }
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProgramItemCustomView);
        int textColor = typeArray.getColor(R.styleable.ProgramItemCustomView_program_title_text_color, Color.parseColor("#483D8B"));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(Constant.NAME_ARTIST_TEXT_SIZE);
        typeArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        rectF.left = 0;
        rectF.top = 0;
        if (titleStaticLayout == null){
            titleStaticLayout
                    = new StaticLayout(
                    title
                    , textPaint
                    , getWidth()
                    , Layout.Alignment.ALIGN_CENTER
                    , 1.0f
                    , 0.0f
                    , false);
        }
        if (imageBitmap != null){

            rectF.right = getWidth();
            rectF.bottom = imageBitmap.getHeight() * rectF.right / imageBitmap.getWidth();

            canvas.drawBitmap(imageBitmap, null, rectF, bitmapPaint);

        } else {

            rectF.right = getWidth();
            rectF.bottom = nullImageProgram.getHeight() * rectF.right / nullImageProgram.getWidth();

            canvas.drawBitmap(nullImageProgram,null, rectF, bitmapPaint);
        }

        canvas.save();
        canvas.translate(rectF.left, rectF.bottom + Constant.NAME_PROGRAM_TEXT_PADDING_HEIGHT);

        titleStaticLayout.draw(canvas);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("ZingDemoApi", "onMeasure called");
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (imageBitmap != null && titleStaticLayout != null){
            setMeasuredDimension(parentWidth, imageBitmap.getHeight() * parentWidth / imageBitmap.getWidth() + Constant.NAME_PROGRAM_TEXT_PADDING_HEIGHT + titleStaticLayout.getHeight());
        } else {
            setMeasuredDimension(parentWidth, nullImageProgram.getHeight() * parentWidth / nullImageProgram.getWidth() + Constant.NAME_PROGRAM_TEXT_PADDING_HEIGHT);
        }
    }

}
