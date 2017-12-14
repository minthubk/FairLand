package com.example.guillaume.fairland;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.params.Face;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Guillaume on 11/12/2017.
 */

public class FaceOverlayView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setBitmap( Bitmap bitmap ) {
        mBitmap = bitmap;
    }
}