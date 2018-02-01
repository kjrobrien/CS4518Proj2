package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GalleryViewActivity extends AppCompatActivity {

    FaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        UUID uuid = UUID.fromString(getIntent().getStringExtra("uuid"));
        Log.d("ciminal_intent", uuid.toString());
        ImageView iv1 = (ImageView) findViewById(R.id.GalleryImage1);
        ImageView iv2 = (ImageView) findViewById(R.id.GalleryImage2);
        ImageView iv3 = (ImageView) findViewById(R.id.GalleryImage3);
        ImageView iv4 = (ImageView) findViewById(R.id.GalleryImage4);
        ImageView iv5 = (ImageView) findViewById(R.id.GalleryImage5);
        ImageView iv6 = (ImageView) findViewById(R.id.GalleryImage6);
        ImageView iv7 = (ImageView) findViewById(R.id.GalleryImage7);
        ImageView iv8 = (ImageView) findViewById(R.id.GalleryImage8);
        ImageView iv9 = (ImageView) findViewById(R.id.GalleryImage9);
        ImageView iv10 = (ImageView) findViewById(R.id.GalleryImage10);
        ImageView iv11 = (ImageView) findViewById(R.id.GalleryImage11);
        ImageView iv12 = (ImageView) findViewById(R.id.GalleryImage12);
        ImageView iv13 = (ImageView) findViewById(R.id.GalleryImage13);
        ImageView iv14 = (ImageView) findViewById(R.id.GalleryImage14);
        ImageView iv15 = (ImageView) findViewById(R.id.GalleryImage15);
        ImageView iv16 = (ImageView) findViewById(R.id.GalleryImage16);
        ImageView[] imageViews = {iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16};
/*
        Crime mCrime = CrimeLab.get(this).getCrime(uuid);
        File mPhoto = CrimeLab.get(this).getPrimaryPhotoFile(mCrime);


        Bitmap bitmap = PictureUtils.getScaledBitmap(
                mPhoto.getPath(), this);
        iv1.setImageBitmap(bitmap);

*/
        List<String> photos = CrimeLab.get(this).getPhotos(uuid);

        for(String photo : photos) {
            Log.d("criminal_photos", photo);
        }

        for (int i = 0; i < photos.size(); i++){
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    photos.get(i), this);
            imageViews[i].setImageBitmap(bitmap);
        }

        if (getIntent().getStringExtra("checkBox").equals("true")){
            for (int i = 0; i < photos.size(); i++){
                BitmapDrawable drawable = (BitmapDrawable) imageViews[i].getDrawable();
                Bitmap myBitmap = drawable.getBitmap();

                //Create a Paint object for drawing on
                Paint myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.RED);
                myRectPaint.setStyle(Paint.Style.STROKE);

                //Create the canvas to draw on
                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                //Detect the faces
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> faces = detector.detect(frame);

                //Draw rectangles on the faces
                for(int j=0; j<faces.size(); j++) {
                    Face thisFace = faces.valueAt(j);
                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();
                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                }
                imageViews[i].setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
            }

        }

    }

}
