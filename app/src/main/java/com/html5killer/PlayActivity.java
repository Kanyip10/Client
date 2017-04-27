package com.html5killer;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.db.DBHelper;
import com.html5killer.db.StatusInfoDB;
import com.html5killer.model.Response;
import com.html5killer.model.User;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utility.Common;
import com.html5killer.utility.DifferencePoint;
import com.html5killer.utility.DifferencesInfo;
import com.html5killer.utility.DifferencesXMLHandler;
import com.html5killer.utility.Prefs;
import com.html5killer.utility.SaundProgressBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.html5killer.utils.Constants;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.SAXParserFactory;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PlayActivity extends Activity {
    private float ROTATE_FROM;
    private float ROTATE_TO;
    private RotateAnimation mAnimateError;
    private Animation mAnimateHit;
    private ImageView mBtnHint;
    private ImageView mBtnSound;
    private int mCurStage;
    private ArrayList<DifferencesInfo> mDiffList;
    private DifferencePoint mDiffPoint;
    private DifferencesInfo mDiffinfo;
    private LayoutParams mErrorLParams;
    private ImageView mErrorV1;
    private ImageView mErrorV2;
    private CountDownTimer mGameTimer;
    private int mHitHeight;
    private ArrayList<Integer> mHitPoints;

    private int mHitWidth;
    private Bitmap mImageB1;
    private Bitmap mImageB2;
    private TouchImage mImageV1;
    private TouchImage mImageV2;
    private int mLevelDuration;
    private int mNumOfDifferencs;
    private int mNumOfErrors;
    private int mNumOfHints;
    private boolean mPauseFlag;
    private boolean mPlaySound;
    private SaundProgressBar mProgressBar;
    private int mResumeAt;
    private int mScoreIncrement;
    private String mSourceXML;
    private int mTotalScore;
    private TextView mTxtHintCount;
    private TextView mTxtHitCount;
    private TextView mTxtScoreCount;
    private TextView mTxtErrorCount;
    private RelativeLayout rlImage1;
    private RelativeLayout rlImage2;
    private int scaledHeight;
    private int scaledWidth;

    private CompositeSubscription mSubscriptions;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.1 */
    class C06441 implements AnimationListener {

        /* renamed from: net.androidiconpacks.findmulti.PlayActivity.1.1 */
        class C06431 implements OnCompletionListener {
            C06431() {
            }

            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        }

        C06441() {
        }

        public void onAnimationStart(Animation animation) {
            if (PlayActivity.this.mPlaySound) {
                MediaPlayer mp = MediaPlayer.create(PlayActivity.this, R.raw.error);
                mp.setOnCompletionListener(new C06431());
                mp.start();
            }
            PlayActivity.this.mErrorV1.setVisibility(View.VISIBLE);
           // PlayActivity.this.mErrorV2.setVisibility(View.VISIBLE);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            PlayActivity.this.mErrorV1.setVisibility(View.INVISIBLE);
            //PlayActivity.this.mErrorV2.setVisibility(View.INVISIBLE);
            if (PlayActivity.this.mNumOfErrors < 1) {
                Prefs.clearPref(PlayActivity.this.getApplicationContext());
                Prefs.setStagePref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mCurStage);
                PlayActivity.this.LoadSharedPreferences();
                PlayActivity.this.mGameTimer.cancel();
                PlayActivity.this.startActivity(new Intent(PlayActivity.this, LostActivity.class));
                PlayActivity.this.finish();
            }
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.2 */
    class C06462 implements AnimationListener {

        /* renamed from: net.androidiconpacks.findmulti.PlayActivity.2.1 */
        class C06451 implements OnClickListener {
            C06451() {
            }

            public void onClick(DialogInterface dialog, int which) {

               // PlayActivity.this.startActivity(new Intent(PlayActivity.this, HomeActivity.class));
                PlayActivity.this.finish();
            }
        }

        C06462() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (PlayActivity.this.mHitPoints.size() >= PlayActivity.this.mNumOfDifferencs) {
                int duration = (PlayActivity.this.mLevelDuration - PlayActivity.this.mResumeAt) / GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;
                if (!((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).isCompleted() || ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).getDuration() >= duration) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).setCompleted(true);
                    ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).setDatePlayed(sdf.format(new Date()));
                    ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).setDuration(duration);
                    ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).setErrors(PlayActivity.this.getResources().getInteger(R.integer.errorsAllowed) - PlayActivity.this.mNumOfErrors);
                    DBHelper.setScore(PlayActivity.this.getApplicationContext(), PlayActivity.this.mCurStage - 1, ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).getDatePlayed(), ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).getDuration(), ((DifferencesInfo) PlayActivity.this.mDiffList.get(PlayActivity.this.mCurStage - 1)).getErrors());
                }
                PlayActivity.this.rlImage1.removeViewsInLayout(PlayActivity.this.rlImage1.getChildCount() - PlayActivity.this.mHitPoints.size(), PlayActivity.this.mHitPoints.size());
               //PlayActivity.this.rlImage2.removeViewsInLayout(PlayActivity.this.rlImage2.getChildCount() - PlayActivity.this.mHitPoints.size(), PlayActivity.this.mHitPoints.size());
                PlayActivity.this.mGameTimer.cancel();
                PlayActivity.this.mHitPoints.clear();
                PlayActivity playActivity = PlayActivity.this;
                playActivity.mCurStage = playActivity.mCurStage + 1;
                if (PlayActivity.this.mCurStage > PlayActivity.this.mDiffList.size()) {
                    int nHighScore = PlayActivity.this.getSharedPreferences("FIND_DIFF", 0).getInt("HIGHSCORE", 0);
                    String title = PlayActivity.this.getString(R.string.success_title);
                    Object[] args = new Object[]{Integer.valueOf(PlayActivity.this.mTotalScore), Integer.valueOf(nHighScore)};
                    String message = MessageFormat.format(PlayActivity.this.getString(R.string.success), args);
                    updateExp();
                    int icon = R.drawable.win;
                    if (PlayActivity.this.mTotalScore > nHighScore) {
                        title = PlayActivity.this.getString(R.string.hiscore_title);
                        message = MessageFormat.format(PlayActivity.this.getString(R.string.hiscore), args);
                        icon = R.drawable.hiscore;
                        Editor editor = PlayActivity.this.getSharedPreferences("FIND_DIFF", 0).edit();
                        editor.putInt("HIGHSCORE", PlayActivity.this.mTotalScore);
                        editor.commit();
                    }
                    Builder altDialog = new Builder(PlayActivity.this);
                    altDialog.setMessage(message);
                    altDialog.setTitle(title);
                    altDialog.setIcon(icon);
                    altDialog.setNeutralButton("OK", new C06451());
                    altDialog.show();
                    Prefs.clearPref(PlayActivity.this.getApplicationContext());
                    PlayActivity.this.LoadSharedPreferences();
                    return;
                }
                Intent NextGame = new Intent(PlayActivity.this, NextGameActivity.class);
                playActivity = PlayActivity.this;
                playActivity.mTotalScore = playActivity.mTotalScore + (PlayActivity.this.mResumeAt / GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
                Prefs.clearPref(PlayActivity.this.getApplicationContext());
                Prefs.setStagePref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mCurStage);
                Prefs.setHintsPref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mNumOfHints);
                Prefs.setTotalsPref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mTotalScore);
                PlayActivity.this.LoadSharedPreferences();
                PlayActivity.this.mPauseFlag = true;
                PlayActivity.this.startActivityForResult(NextGame, 0);
            }
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.3 */
    class C06473 implements OnTouchListener {
        C06473() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case DetectedActivity.IN_VEHICLE /*0*/:
                    PlayActivity.this.mDiffPoint = PlayActivity.this.CheckForDetection(PlayActivity.this.mImageB1,(int) event.getX(), (int) event.getY());

                    PlayActivity playActivity;
                    if (PlayActivity.this.mDiffPoint != null) {
                        PlayActivity.this.mHitPoints.add(Integer.valueOf(PlayActivity.this.mDiffPoint.getID()));
                        playActivity = PlayActivity.this;
                        playActivity.mTotalScore = playActivity.mTotalScore + PlayActivity.this.mScoreIncrement;
                        PlayActivity.this.AnimateHit(PlayActivity.this.mDiffPoint, true);
                    } else {
                        playActivity = PlayActivity.this;
                        playActivity.mNumOfErrors = playActivity.mNumOfErrors - 1;
                        PlayActivity.this.AnimateError((int) event.getX(), (int) event.getY());
                    }
                    v.onTouchEvent(event);
                    break;
            }
            return true;
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.4 */
    class C06484 implements OnTouchListener {
        C06484() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case DetectedActivity.IN_VEHICLE /*0*/:
                    PlayActivity.this.mDiffPoint = PlayActivity.this.CheckForDetection(PlayActivity.this.mImageB1, (int) event.getX(), (int) event.getY());
                    PlayActivity playActivity;
                    if (PlayActivity.this.mDiffPoint != null) {
                        PlayActivity.this.mHitPoints.add(Integer.valueOf(PlayActivity.this.mDiffPoint.getID()));
                        playActivity = PlayActivity.this;
                        playActivity.mTotalScore = playActivity.mTotalScore + PlayActivity.this.mScoreIncrement;
                        PlayActivity.this.AnimateHit(PlayActivity.this.mDiffPoint, true);
                    } else {
                        playActivity = PlayActivity.this;
                        playActivity.mNumOfErrors = playActivity.mNumOfErrors - 1;
                        PlayActivity.this.AnimateError((int) event.getX(), (int) event.getY());
                    }
                    v.onTouchEvent(event);
                    break;
            }
            return true;
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.5 */
    class C06495 implements View.OnClickListener {
        C06495() {
        }

        public void onClick(View v) {
            PlayActivity.this.mPlaySound = !PlayActivity.this.mPlaySound;
            if (PlayActivity.this.mPlaySound) {
                PlayActivity.this.mBtnSound.setImageResource(R.drawable.sound);
            } else {
                PlayActivity.this.mBtnSound.setImageResource(R.drawable.mute);
            }
            Prefs.setSoundPref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mPlaySound);
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.6 */
    class C06506 implements View.OnClickListener {
        C06506() {
        }

        public void onClick(View v) {
            if (PlayActivity.this.mNumOfHints > 0) {
                try {
                    PlayActivity playActivity = PlayActivity.this;
                    playActivity.mNumOfHints = playActivity.mNumOfHints - 1;
                    PlayActivity.this.mDiffPoint = PlayActivity.this.mDiffinfo.getNonDetectedPoint(true);
                    PlayActivity.this.mHitPoints.add(Integer.valueOf(PlayActivity.this.mDiffPoint.getID()));
                    PlayActivity.this.SaveSharedPreferences();
                    PlayActivity.this.AnimateHit(PlayActivity.this.mDiffPoint, true);
                    PlayActivity.this.SetGameStatusOnScreen();
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.7 */
    class C06517 implements OnCompletionListener {
        C06517() {
        }

        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.8 */
    class C06528 extends CountDownTimer {
        C06528(long $anonymous0, long $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onTick(long millisUntilFinished) {
            PlayActivity.this.mProgressBar.setProgress((int) millisUntilFinished);
            PlayActivity.this.mResumeAt = (int) millisUntilFinished;
        }

        public void onFinish() {
            if (!PlayActivity.this.mPauseFlag) {
                Prefs.clearPref(PlayActivity.this.getApplicationContext());
                Prefs.setStagePref(PlayActivity.this.getApplicationContext(), PlayActivity.this.mCurStage);
                PlayActivity.this.LoadSharedPreferences();
                PlayActivity.this.startActivity(new Intent(PlayActivity.this, LostActivity.class));
                PlayActivity.this.finish();
            }
        }
    }

    /* renamed from: net.androidiconpacks.findmulti.PlayActivity.9 */
    class C06539 implements OnPreDrawListener {
        private final /* synthetic */ TouchImage val$touchView;

        C06539(TouchImage touchImage) {
            this.val$touchView = touchImage;
        }

        public boolean onPreDraw() {
            Rect rect = this.val$touchView.getDrawable().getBounds();
            PlayActivity.this.scaledHeight = rect.height();
            PlayActivity.this.scaledWidth = rect.width();
            this.val$touchView.getViewTreeObserver().removeOnPreDrawListener(this);
            return true;
        }
    }

    public PlayActivity() {
        this.mCurStage = 1;
        this.mNumOfDifferencs = 0;
        this.mNumOfErrors = 0;
        this.mNumOfHints = 0;
        this.mTotalScore = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mSubscriptions = new CompositeSubscription();
        LoadConfigParams();
        LoadSharedPreferences();
        initSharedPreferences();
        Log.i("TAG", mEmail+ "    " + mToken);
        LoadResources();
        LoadListeners();
        LoadStage(this.mCurStage);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutAdmob);
    }

    public void onPause() {
        super.onPause();
        this.mGameTimer.cancel();
        SaveSharedPreferences();
        if (!this.mPauseFlag) {
            this.mPauseFlag = !this.mPauseFlag;
            finish();
        }
    }

    protected void onRestart() {
        super.onRestart();
        LoadConfigParams();
        LoadSharedPreferences();
        LoadResources();
        LoadListeners();
        LoadStage(this.mCurStage);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            LoadStage(this.mCurStage);
            this.mPauseFlag = false;
        }
    }

    private void LoadStage(int stage) {
        if (this.mResumeAt == this.mLevelDuration) {
            this.rlImage1.removeViewsInLayout(this.rlImage1.getChildCount() - this.mHitPoints.size(), this.mHitPoints.size());
           // this.rlImage2.removeViewsInLayout(this.rlImage2.getChildCount() - this.mHitPoints.size(), this.mHitPoints.size());
        }
        this.mDiffinfo = (DifferencesInfo) this.mDiffList.get(stage - 1);
        this.mNumOfDifferencs = this.mDiffinfo.getPointsCount();
        this.mImageB1 = Common.loadImageFromAsset(this, this.mDiffinfo.getImageLocation1());
        this.mImageB2 = Common.loadImageFromAsset(this, this.mDiffinfo.getImageLocation2());
        setImageViewBitmap(this.mImageV1, this.mImageB1);
        setImageViewBitmap(this.mImageV2, this.mImageB2);
        if (this.mResumeAt != this.mLevelDuration) {
            for (int i = 0; i < this.mHitPoints.size(); i++) {
                this.mDiffPoint = this.mDiffinfo.getPoint(((Integer) this.mHitPoints.get(i)).intValue());
                this.mDiffinfo.getPoint(((Integer) this.mHitPoints.get(i)).intValue()).setDetected(true);
                AnimateHit(this.mDiffPoint, false);
            }
        }
        SetGameStatusOnScreen();
        InitializeGameTimer((long) this.mResumeAt, 100);
        this.mGameTimer.start();
    }

    private void SetGameStatusOnScreen() {
        this.mTxtHintCount.setText(Integer.toString(this.mNumOfHints));
        this.mTxtScoreCount.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.score))).append(" : ").append(Integer.toString(this.mTotalScore)).toString());
        this.mTxtHitCount.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.found))).append(" : ").append(Integer.toString(this.mHitPoints.size())).append("/").append(Integer.toString(this.mNumOfDifferencs)).toString());
        this.mTxtErrorCount.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.error))).append(" : ").append(Integer.toString(this.mNumOfErrors)).append("/").append(PlayActivity.this.getResources().getInteger(R.integer.errorsAllowed)).toString());
        if (this.mPlaySound) {
            this.mBtnSound.setImageResource(R.drawable.sound);
        } else {
            this.mBtnSound.setImageResource(R.drawable.mute);
        }
    }

    private void LoadResources() {
        parseXML(getResources().getString(R.string.imageLocation));
        readDBData();
        this.mProgressBar = (SaundProgressBar) findViewById(R.id.progressBar);
        this.mProgressBar.setMax(this.mLevelDuration);
        this.rlImage1 = (RelativeLayout) findViewById(R.id.rlayout1);
        this.rlImage2 = (RelativeLayout) findViewById(R.id.rlayout2);
        this.mBtnSound = (ImageView) findViewById(R.id.imgSound);
        this.mTxtHintCount = (TextView) findViewById(R.id.hintCount);
        this.mBtnHint = (ImageView) findViewById(R.id.imgHint);
        this.mTxtScoreCount = (TextView) findViewById(R.id.scoreCount);
        this.mTxtHitCount = (TextView) findViewById(R.id.hitCount);
        this.mErrorV1 = (ImageView) findViewById(R.id.errorimage1);
        this.mErrorV2 = (ImageView) findViewById(R.id.errorimage2);
        this.mErrorLParams = (LayoutParams) this.mErrorV1.getLayoutParams();
        this.mImageV1 = (TouchImage) findViewById(R.id.gameimage1);
        this.mImageV2 = (TouchImage) findViewById(R.id.gameimage2);
    }

    private void LoadListeners() {
        this.mAnimateError = new RotateAnimation(this.ROTATE_FROM, this.ROTATE_TO, 1, 0.5f, 1, 0.5f);
        this.mAnimateError.setDuration((long) getResources().getInteger(R.integer.errorDuration));
        this.mAnimateError.setRepeatCount(0);
        this.mAnimateError.setAnimationListener(new C06441());
        this.mAnimateHit = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        this.mAnimateHit.setAnimationListener(new C06462());
        this.mImageV1.setOnTouchListener(new C06473());
//        this.mImageV2.setOnTouchListener(new C06484());
        this.mBtnSound.setOnClickListener(new C06495());
        this.mBtnHint.setOnClickListener(new C06506());
    }

    private void LoadConfigParams() {
        this.mPauseFlag = false;
        this.ROTATE_FROM = 0.0f;
        this.ROTATE_TO = ((float) getResources().getInteger(R.integer.errorNumRotations)) * 360.0f;
        this.mSourceXML = getResources().getString(R.string.imageLocation);
        this.mScoreIncrement = getResources().getInteger(R.integer.scoreIncrement);
        this.mLevelDuration = getResources().getInteger(R.integer.levelDuration);
        this.mLevelDuration *= GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;
        this.mHitHeight = (int) TypedValue.applyDimension(1, (float) getResources().getInteger(R.integer.hitCircle), getResources().getDisplayMetrics());
        this.mHitWidth = (int) TypedValue.applyDimension(1, (float) getResources().getInteger(R.integer.hitCircle), getResources().getDisplayMetrics());
        this.mNumOfErrors = getResources().getInteger(R.integer.errorsAllowed);
    }

    private void LoadSharedPreferences() {
        this.mResumeAt = Prefs.getResumePref(getApplicationContext());
        this.mCurStage = Prefs.getStagePref(getApplicationContext());
        this.mHitPoints = Prefs.getPointsPref(getApplicationContext());
        this.mPlaySound = Prefs.getSoundPref(getApplicationContext());
        this.mNumOfErrors = Prefs.getErrorsPref(getApplicationContext());
        this.mNumOfHints = Prefs.getHintsPref(getApplicationContext());
        this.scaledHeight = Prefs.getScaleHPref(getApplicationContext());
        this.scaledWidth = Prefs.getScaleWPref(getApplicationContext());
        this.mTotalScore = Prefs.getTotalsPref(getApplicationContext());
    }

    private void SaveSharedPreferences() {
        Prefs.setResumePref(getApplicationContext(), this.mResumeAt);
        Prefs.setStagePref(getApplicationContext(), this.mCurStage);
        Prefs.setSoundPref(getApplicationContext(), this.mPlaySound);
        Prefs.setPointsPref(getApplicationContext(), this.mHitPoints);
        Prefs.setErrorsPref(getApplicationContext(), this.mNumOfErrors);
        Prefs.setHintsPref(getApplicationContext(), this.mNumOfHints);
        Prefs.setScaleHPref(getApplicationContext(), this.scaledHeight);
        Prefs.setScaleWPref(getApplicationContext(), this.scaledWidth);
        Prefs.setTotalsPref(getApplicationContext(), this.mTotalScore);
    }

    private DifferencePoint CheckForDetection(Bitmap b1, int x, int y) {
        Log.i("TAG", "moving: (" + (int) (((float) x) * (((float) b1.getWidth()) / ((float) this.scaledWidth))) + ", " + (int) (((float) y) * (((float) b1.getHeight()) / ((float) this.scaledHeight)))+ ")");


        return this.mDiffinfo.isPointInRadius((int) (((float) x) * (((float) b1.getWidth()) / ((float) this.scaledWidth))), (int) (((float) y) * (((float) b1.getHeight()) / ((float) this.scaledHeight))), true);
    }

    private void AnimateHit(DifferencePoint point, boolean animate) {
        if (this.mPlaySound) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.hit);
            mp.setOnCompletionListener(new C06517());
            mp.start();
        }
        SetGameStatusOnScreen();
        AnimateHit(this.rlImage1, this.mImageB1, point.getPosX(), point.getPosY(), animate);
//        AnimateHit(this.rlImage2, this.mImageB2, point.getPosX(), point.getPosY(), animate);
    }

    private void AnimateHit(RelativeLayout rl, Bitmap bmp, int x, int y, boolean animate) {
        float scaleX = ((float) this.scaledWidth) / ((float) bmp.getWidth());
        float scaleY = ((float) this.scaledHeight) / ((float) bmp.getHeight());
        x = (int) (((float) x) * scaleX);
        y = (int) (((float) y) * scaleY);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.hit);
        LayoutParams params = new LayoutParams(-2, -2);
        params.addRule(9, -1);
        params.setMargins(x - (this.mHitWidth / 2), y - (this.mHitHeight / 2), ((int) (((float) bmp.getWidth()) * scaleX)) - ((this.mHitWidth / 2) + x), ((int) (((float) bmp.getHeight()) * scaleY)) - ((this.mHitHeight / 2) + y));
        imageView.setLayoutParams(params);
        if (animate) {
            if (this.mHitPoints.size() >= this.mNumOfDifferencs) {
                imageView.startAnimation(this.mAnimateHit);
            } else {
                imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_down));
            }
        }
        rl.addView(imageView);
    }

    private void AnimateError(int x, int y) {
        this.mErrorLParams.leftMargin = x - (this.mErrorV1.getWidth() / 2);
        this.mErrorLParams.topMargin = y - (this.mErrorV1.getHeight() / 2);
        this.mErrorV1.setLayoutParams(this.mErrorLParams);
        this.mErrorV1.startAnimation(this.mAnimateError);
//        this.mErrorV2.setLayoutParams(this.mErrorLParams);
//        this.mErrorV2.startAnimation(this.mAnimateError);
    }

    private void InitializeGameTimer(long millisInFuture, long countDownInterval) {
        this.mGameTimer = new C06528(millisInFuture, countDownInterval);
    }

    private void setImageViewBitmap(TouchImage touchView, Bitmap bmp) {
        touchView.setImageBitmap(bmp);
        touchView.getViewTreeObserver().addOnPreDrawListener(new C06539(touchView));
    }

    private void readDBData() {
        try {
            StatusInfoDB siDB = new StatusInfoDB(getApplicationContext());
            siDB.open();
            int i = 0;
            while (i < this.mDiffList.size()) {
                Cursor dbScoreCursor = siDB.getScore(i);
                if (dbScoreCursor != null) {
                    if (dbScoreCursor.getCount() > 0) {
                        ((DifferencesInfo) this.mDiffList.get(i)).setDatePlayed(dbScoreCursor.getString(dbScoreCursor.getColumnIndex(StatusInfoDB.LAST_UPDATE)));
                        ((DifferencesInfo) this.mDiffList.get(i)).setDuration(dbScoreCursor.getInt(dbScoreCursor.getColumnIndex(StatusInfoDB.MIN_DURATION)));
                        ((DifferencesInfo) this.mDiffList.get(i)).setErrors(dbScoreCursor.getInt(dbScoreCursor.getColumnIndex(StatusInfoDB.NUM_ERRORS)));
                        ((DifferencesInfo) this.mDiffList.get(i)).setCompleted(true);
                        ((DifferencesInfo) this.mDiffList.get(i)).setUnlocked(true);
                        dbScoreCursor.close();
                    } else {
                        ((DifferencesInfo) this.mDiffList.get(i)).setUnlocked(true);
                        i = this.mDiffList.size();
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
            this.mDiffList = myXMLHandler.getDiffList();
            is.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    private void initSharedPreferences() {
        Bundle bundle = getIntent().getExtras();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);

    }

    private void updateExp(){
        Log.i("TAG",""+PlayActivity.this.mTotalScore);
        User user = new User();
        user.setNewExp(50);
        changeExp(user);
    }

    private void changeExp(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).changeExp(mEmail,user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::Response,this::handleError));
    }

    private void Response(Response response) {

    }

    private void handleError(Throwable error) {



        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.gameResult),message,Snackbar.LENGTH_SHORT).show();

    }

}
