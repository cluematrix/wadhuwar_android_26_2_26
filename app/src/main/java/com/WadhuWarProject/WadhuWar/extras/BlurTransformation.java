package com.WadhuWarProject.WadhuWar.extras;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

public class BlurTransformation implements Transformation<Bitmap> {
    private static final int VERSION = 1;
    private static final String ID = "BlurTransformation." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private Context context;
    private int blurRadius;

    public BlurTransformation(Context context, int blurRadius) {
        this.context = context;
        this.blurRadius = blurRadius;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        Bitmap sourceBitmap = resource.get();
        Bitmap blurredBitmap = BlurUtils.blurBitmap(context, sourceBitmap, blurRadius);
        return BitmapResource.obtain(blurredBitmap, bitmapPool);

    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlurTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
