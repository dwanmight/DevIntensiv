package com.junior.dwan.devintensiv.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.junior.dwan.devintensiv.R;
import com.junior.dwan.devintensiv.data.managers.DataManager;
import com.junior.dwan.devintensiv.utils.ConstantManager;
import com.junior.dwan.devintensiv.utils.ValidateEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = ConstantManager.DEV_PREFIX + "MainActivity";
    private int mCurrentMode = 0;
    private ImageView mCallImg, mSendEmailImg, mWatchVkImg, mWatchGitImg;
    CoordinatorLayout mCoordinatorLayout;
    Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private DataManager mDataManager;
    private RelativeLayout mProfilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout.LayoutParams mAppBarBarParams = null;
    private AppBarLayout mAppBar;
    private File mPhotofile = null;
    private Uri mSelectedImage = null;
    private ImageView mProfileImageView;

    EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private List<EditText> mUserInfoViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataManager = DataManager.getINSTANCE();

        Log.d(TAG, "Created");
        mCallImg = (ImageView) findViewById(R.id.call_img);
        mSendEmailImg = (ImageView) findViewById(R.id.send_email_img);
        mWatchVkImg = (ImageView) findViewById(R.id.watch_vk_img);
        mWatchGitImg = (ImageView) findViewById(R.id.watch_github_img);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserMail = (EditText) findViewById(R.id.email_et);
        mUserVk = (EditText) findViewById(R.id.vk_et);
        mUserGit = (EditText) findViewById(R.id.github_et);
        mUserBio = (EditText) findViewById(R.id.about_et);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mProfilePlaceholder = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.appBar_layout);
        mProfileImageView = (ImageView) findViewById(R.id.user_photo_img);

        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mProfilePlaceholder.setOnClickListener(this);
        mCallImg.setOnClickListener(this);
        mSendEmailImg.setOnClickListener(this);
        mWatchVkImg.setOnClickListener(this);
        mWatchGitImg.setOnClickListener(this);
        mUserPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mFab.setOnClickListener(this);
        setupToolbar();
        setupDrawer();
        loadUserInfoValues();
        Picasso.with(this)
                .load(mDataManager.getPreferencesManager().loadUserPhoto())
                .placeholder(R.drawable.myphoto)
                .into(mProfileImageView);


//        Bitmap bm=BitmapFactory.decodeResource(getResources(),R.drawable.myphoto);
//        RoundedAvatarDrawable avatarDrawable =new RoundedAvatarDrawable(bm);
//        mCallImg.setBackground(avatarDrawable);

//        List<String> test=mDataManager.getPreferencesManager().loadUserProfileData();
        if (savedInstanceState == null) {
            //activity create at first time

        } else {
            //activity was already created
            mCurrentMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentMode);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUserInfoValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (mCurrentMode == 0) {
                    changeEditMode(1);
                    mCurrentMode = 1;
                } else {
                    changeEditMode(0);
                    mCurrentMode = 0;
                }
//                showProgress();
//                runWithDelay();
                break;
            case R.id.profile_placeholder:
                //// TODO: 11.09.2016 Откуда загружать фотографии
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
            case R.id.call_img:
                if (ValidateEditText.isPhoneValid(mUserPhone))
                    userCallToPhone();
                else showSnackBar("Enter a valid number");
                break;
            case R.id.send_email_img:
                if(ValidateEditText.isEmailValid(mUserMail.getText()))
                userSendEmail();
                else showSnackBar("Enter a valid email address");

                break;
            case R.id.watch_vk_img:
                userWatchVk();
                break;
            case R.id.watch_github_img:
                userWatchGit();
                break;
        }
    }

    private void runWithDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: Выполнить с задержкой
                hideProgress();
            }
        }, 5000);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentMode);
        Log.d(TAG, "onSaveState");
    }

    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        mAppBarBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    /**
     * Получение результата из другой активности(фото из галереи или камеры)
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantManager.TAKE_REQUEST_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    mSelectedImage = data.getData();

                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.TAKE_REQUEST_PICTURE:
                if (resultCode == RESULT_OK && mPhotofile != null) {
                    mSelectedImage = Uri.fromFile(mPhotofile);
                    insertProfileImage(mSelectedImage);
                }
                break;
        }
    }

    /**
     * переключает режим редактирования
     *
     * @param mode = 1 режим редактирования, 0 режим просмотра.
     */
    private void changeEditMode(int mode) {
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_done_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                mUserPhone.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mUserPhone, InputMethodManager.SHOW_IMPLICIT);

                lockToolbar();
                showProfilePlaceholder();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);

                unlockToolbar();
                hideProfilePlaceholder();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

                saveUserInfoValues();
            }
        }

    }

    private void loadUserInfoValues() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));

        }

    }

    private void saveUserInfoValues() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    private void loadPhotoFromGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, "Выберите фото"), ConstantManager.TAKE_REQUEST_GALLERY);

    }

    private void loadPhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mPhotofile = createImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mPhotofile != null) {
                //// TODO: 12.09.2016 передать фотофайл в интент
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotofile));
                startActivityForResult(takeCaptureIntent, ConstantManager.TAKE_REQUEST_PICTURE);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);

            Snackbar.make(mCoordinatorLayout, "Для корректной работы приложения необходимо дать требуемые разрешения", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openApplicationSetting();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }

        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    private void showProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.VISIBLE);

    }

    private void hideProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.GONE);

    }

    private void lockToolbar() {
        mAppBar.setExpanded(true, true);
        mAppBarBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarBarParams);
    }

    private void unlockToolbar() {
        mAppBarBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarBarParams);
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {getString(R.string.user_profile_dialog_gallery), getString(R.string.user_profile_dialog_camera), getString(R.string.user_profile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.user_profile_dialog_title);
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiceItem) {
                        switch (choiceItem) {
                            case 0:
                                // TODO: 11.09.2016 загрузить из галереи
                                loadPhotoFromGallery();
                                break;
                            case 1:
                                //// TODO: 11.09.2016 загузить из камеры
                                loadPhotoFromCamera();
                                break;
                            case 2:
                                //// TODO: 11.09.2016 отмена
//                                dialog.cancel();
                                openApplicationSetting();
                                break;
                        }
                    }
                });
                return builder.create();
        }
        return null;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd:HHmmss").format(new Date());
        String imageFilename = "JPEG_" + timeStamp + "_";
        File storageDirection = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDirection);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return image;
    }

    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this)
                .load(mSelectedImage)
                .into(mProfileImageView);
        //// TODO: 12.09.2016
        mDataManager.getPreferencesManager().saveUserPhoto(selectedImage);
    }

    public void openApplicationSetting() {
        Intent appSettingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));

        startActivityForResult(appSettingIntent, ConstantManager.PERMISSION_REQUEST_SETTING_CODE);
    }

    private void userCallToPhone() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mUserPhone.getText().toString()));
        startActivity(callIntent);
    }

    private void userSendEmail() {
        Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUserMail.getText().toString()});
        sendEmailIntent.setType("message/rfc822");
        if (mPhotofile != null) {
            sendEmailIntent.setType("image/*");
            sendEmailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mPhotofile));
        }
        startActivity(Intent.createChooser(sendEmailIntent, "Choose an Email client :"));
    }

    private void userWatchVk() {
        Intent watchVkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mUserVk.getText().toString()));
        startActivity(watchVkIntent);
    }

    private void userWatchGit() {
        Intent watchGitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mUserGit.getText().toString()));
        startActivity(watchGitIntent);
    }
}
