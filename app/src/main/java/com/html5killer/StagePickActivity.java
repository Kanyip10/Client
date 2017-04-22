package com.html5killer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.html5killer.db.StatusInfoDB;
import com.html5killer.utility.Common;
import com.html5killer.utility.CoverFlow;
import com.html5killer.utility.DifferencesInfo;
import com.html5killer.utility.DifferencesXMLHandler;
import com.html5killer.utility.Prefs;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.maps.model.GroundOverlayOptions;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParserFactory;

public class StagePickActivity extends Activity {
    private ArrayList<DifferencesInfo> diffList;
    private TextView mDatePlayed;
    private TextView mDuration;
    private TextView mErrorsMade;
    private TextView mLevel;
    private TextView mNumDiff;
    private TextView mStatus;

    /* renamed from: net.androidiconpacks.findmulti.StagePickActivity.1 */
    class C06651 implements OnItemClickListener {
        C06651() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            Prefs.clearPref(StagePickActivity.this.getApplicationContext());
            Prefs.setStagePref(StagePickActivity.this.getApplicationContext(), arg2 + 1);
            StagePickActivity.this.startActivity(new Intent(StagePickActivity.this, PlayActivity.class));
            StagePickActivity.this.finish();
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.StagePickActivity.2 */
    class C06662 implements OnItemSelectedListener {
        C06662() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            StagePickActivity.this.mLevel.setText("Stage " + (arg2 + 1));
            StagePickActivity.this.mNumDiff.setText("Differences: " + ((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).getPointsCount());
            if (((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).isUnlocked()) {
                StagePickActivity.this.mStatus.setText("UNLOCKED");
            } else {
                StagePickActivity.this.mStatus.setText("LOCKED");
            }
            if (((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).isCompleted()) {
                StagePickActivity.this.mDuration.setText("Time record: " + ((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).getDuration() + " seconds ");
                StagePickActivity.this.mErrorsMade.setText("Errors made: " + ((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).getErrors());
                StagePickActivity.this.mDatePlayed.setText("Last played: " + ((DifferencesInfo) StagePickActivity.this.diffList.get(arg2)).getDatePlayed());
                StagePickActivity.this.mDuration.setVisibility(View.VISIBLE);
                StagePickActivity.this.mErrorsMade.setVisibility(View.VISIBLE);
                StagePickActivity.this.mDuration.setVisibility(View.VISIBLE);
                return;
            }
            StagePickActivity.this.mDuration.setVisibility(View.INVISIBLE);
            StagePickActivity.this.mErrorsMade.setVisibility(View.INVISIBLE);
            StagePickActivity.this.mDuration.setVisibility(View.INVISIBLE);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        int mGalleryItemBackground;
        private ImageView[] mImages;

        public ImageAdapter(Context c) {
            this.mContext = c;
            this.mImages = new ImageView[StagePickActivity.this.diffList.size()];
        }

        public boolean createReflectedImages() {
            int index = 0;
            Iterator it = StagePickActivity.this.diffList.iterator();
            while (it.hasNext()) {
                Bitmap originalImage = Common.loadImageFromAsset(StagePickActivity.this.getApplicationContext(), ((DifferencesInfo) it.next()).getImageLocation1());
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();
                Matrix matrix = new Matrix();
                matrix.preScale(TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION);
                Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
                Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height / 2) + height, Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapWithReflection);
                canvas.drawBitmap(originalImage, 0.0f, 0.0f, null);
                canvas.drawRect(0.0f, (float) height, (float) width, (float) (height + 4), new Paint());
                canvas.drawBitmap(reflectionImage, 0.0f, (float) (height + 4), null);
                Paint paint = new Paint();
                Paint paint2 = paint;
                paint2.setShader(new LinearGradient(0.0f, (float) originalImage.getHeight(), 0.0f, (float) (bitmapWithReflection.getHeight() + 4), 1895825407, 16777215, TileMode.CLAMP));
                paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
                Canvas canvas2 = canvas;
                canvas2.drawRect(0.0f, (float) height, (float) width, (float) (bitmapWithReflection.getHeight() + 4), paint);
                ImageView imageView = new ImageView(this.mContext);
                imageView.setImageBitmap(bitmapWithReflection);
                imageView.setLayoutParams(new LayoutParams(320, 480));
                imageView.setPadding(30, 100, 20, 20);
                int index2 = index + 1;
                this.mImages[index] = imageView;
                index = index2;
            }
            return true;
        }

        public int getCount() {
            return StagePickActivity.this.diffList.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(this.mContext);
            i.setImageBitmap(Common.loadImageFromAsset(StagePickActivity.this.getApplicationContext(), ((DifferencesInfo) StagePickActivity.this.diffList.get(position)).getImageLocation1()));
            i.setLayoutParams(new LayoutParams(380, 450));
            i.setScaleType(ScaleType.FIT_XY);
            ((BitmapDrawable) i.getDrawable()).setAntiAlias(true);
            return i;
        }

        public float getScale(boolean focused, int offset) {
            return Math.max(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE / ((float) Math.pow(2.0d, (double) Math.abs(offset))));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stagepick);
        parseXML(getResources().getString(R.string.imageLocation));
        readDBData();
        this.mLevel = (TextView) findViewById(R.id.txtLevel);
        this.mNumDiff = (TextView) findViewById(R.id.txtNumDiff);
        this.mDuration = (TextView) findViewById(R.id.txtDuration);
        this.mStatus = (TextView) findViewById(R.id.txtStatus);
        this.mErrorsMade = (TextView) findViewById(R.id.txtErrorsMade);
        this.mDatePlayed = (TextView) findViewById(R.id.txtDatePlayed);
        CoverFlow coverFlow = (CoverFlow) findViewById(R.id.coverflow);
        ImageAdapter coverImageAdapter = new ImageAdapter(this);
        coverImageAdapter.createReflectedImages();
        coverFlow.setSpacing(-25);
        coverFlow.setSelection(0, true);
        coverFlow.setAnimationDuration(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
        coverFlow.setOnItemClickListener(new C06651());
        coverFlow.setOnItemSelectedListener(new C06662());
        coverFlow.setBackgroundColor(getResources().getColor(R.color.transparent));
        coverFlow.setAdapter(coverImageAdapter);
    }

    private void readDBData() {
        try {
            StatusInfoDB siDB = new StatusInfoDB(getApplicationContext());
            siDB.open();
            int i = 0;
            while (i < this.diffList.size()) {
                Cursor dbScoreCursor = siDB.getScore(i);
                if (dbScoreCursor != null) {
                    if (dbScoreCursor.getCount() > 0) {
                        ((DifferencesInfo) this.diffList.get(i)).setDatePlayed(dbScoreCursor.getString(dbScoreCursor.getColumnIndex(StatusInfoDB.LAST_UPDATE)));
                        ((DifferencesInfo) this.diffList.get(i)).setDuration(dbScoreCursor.getInt(dbScoreCursor.getColumnIndex(StatusInfoDB.MIN_DURATION)));
                        ((DifferencesInfo) this.diffList.get(i)).setErrors(dbScoreCursor.getInt(dbScoreCursor.getColumnIndex(StatusInfoDB.NUM_ERRORS)));
                        ((DifferencesInfo) this.diffList.get(i)).setCompleted(true);
                        ((DifferencesInfo) this.diffList.get(i)).setUnlocked(true);
                        dbScoreCursor.close();
                    } else {
                        ((DifferencesInfo) this.diffList.get(i)).setUnlocked(true);
                        i = this.diffList.size();
                    }
                }
                i++;
            }
            siDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXML(String xmlFile) {
        try {
            InputStream is = getBaseContext().getAssets().open(xmlFile);
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            DifferencesXMLHandler myXMLHandler = new DifferencesXMLHandler();
            xr.setContentHandler(myXMLHandler);
            xr.parse(new InputSource(is));
            this.diffList = myXMLHandler.getDiffList();
            is.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
