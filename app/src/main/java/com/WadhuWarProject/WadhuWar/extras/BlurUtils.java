package com.WadhuWarProject.WadhuWar.extras;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurUtils {

//    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
//        Bitmap blurredBitmap;
//        blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        RenderScript rs = RenderScript.create(context);
//        Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SCRIPT);
//        Allocation output = Allocation.createTyped(rs, input.getType());
//        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        script.setInput(input);
//        script.setRadius(radius);
//        script.forEach(output);
//        output.copyTo(blurredBitmap);
//        rs.destroy();
//        return blurredBitmap;
//    }

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false); // Adjust the scale factor as needed
        Bitmap blurredBitmap = Bitmap.createBitmap(scaledBitmap.getWidth(), scaledBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, scaledBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);
        script.setRadius(radius);
        script.forEach(output);
        output.copyTo(blurredBitmap);
        rs.destroy();
        return blurredBitmap;
    }
}
