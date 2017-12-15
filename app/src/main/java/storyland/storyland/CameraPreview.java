package storyland.storyland;


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

import static android.content.Context.WINDOW_SERVICE;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "Video";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Context mContext;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

            //mCamera.startFaceDetection(); // start face detection feature

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            Log.d(TAG, "mHolder.getSurface() == null");
            return;
        }

        try {
            mCamera.stopPreview();

        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
            Log.d(TAG, "Error stopping camera preview: " + e.getMessage());
        }

        Camera.Parameters parameters = mCamera.getParameters();
        Display display = ((WindowManager)mContext.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0) {
            parameters.setPreviewSize(h, w);
            mCamera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90) {
            parameters.setPreviewSize(w, h);
        }

        if(display.getRotation() == Surface.ROTATION_180) {
            parameters.setPreviewSize(h, w);
        }

        if(display.getRotation() == Surface.ROTATION_270) {
            parameters.setPreviewSize(w, h);
            mCamera.setDisplayOrientation(180);
        }

        //mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

            mCamera.startFaceDetection(); // re-start face detection feature

        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}