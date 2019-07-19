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
import android.os.Handler;
import android.os.Looper;
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


public class ProgramItemCustomView extends View  {

    private Paint bitmapPaint;
    private TextPaint textPaint;

    private RectF rectF = new RectF();

    Context context;

    Bitmap imageBitmap;
    Bitmap nullImageProgram;
    StaticLayout titleStaticLayout;

    private String title;

    public ProgramItemCustomView(Context context) {
        super(context);
        this.context = context;
        nullImageProgram = BitmapFactory.decodeResource(getResources(), R.drawable.default_program);
        initBitmapPaint();

    }

    public ProgramItemCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        nullImageProgram = BitmapFactory.decodeResource(getResources(), R.drawable.default_program);
        initBitmapPaint();
        initTextPaint(attrs);


    }
    private void invalidateAndRequestLayout(){
        requestLayout();
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
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                invalidateAndRequestLayout();
                            }
                        });

                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        imageBitmap = resource;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                invalidateAndRequestLayout();
                            }
                        });

                        return false;
                    }
                }).submit();
        //requestLayout();
    }
    private void initBitmapPaint() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(getResources().getColor(R.color.color_bitmap_paint));

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
        canvas.translate(rectF.left, rectF.bottom + getResources().getDimension(R.dimen.name_program_text_padding_height));
        titleStaticLayout.draw(canvas);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (imageBitmap != null && titleStaticLayout != null){
            setMeasuredDimension(parentWidth, imageBitmap.getHeight() * parentWidth / imageBitmap.getWidth() + Math.round(getResources().getDimension(R.dimen.name_program_text_padding_height))+ titleStaticLayout.getHeight());
        } else {
            setMeasuredDimension(parentWidth, nullImageProgram.getHeight() * parentWidth / nullImageProgram.getWidth() + Math.round(getResources().getDimension(R.dimen.name_program_text_padding_height)));
        }
    }

}
