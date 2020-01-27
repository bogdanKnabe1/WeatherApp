package com.example.weatherapp.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.NotificationService;
import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.view.ui.ForecastFragment;
import com.example.weatherapp.view.ui.TodayFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;



public class MainActivity extends BaseActivity {

    private static final String TAG = "MapActivity";

    //A client that handles connection / connection failures for Google locations
    private FusedLocationProviderClient mFusedLocationClient;

    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private TextView mTitle;
    private Toolbar toolbar;
    NotificationService notificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = toolbar.findViewById(R.id.toolbarName);


        //Navigation bottom menu
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new TodayFragment())
                    .commit();
        }

        //getCurrent FCM Token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //Bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startAnimationsToday();
                            selectedFragment = new TodayFragment();
                            break;
                        case R.id.nav_forecast:
                            startAnimationsForecast();
                            selectedFragment = new ForecastFragment();
                            break;
                    }

                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).
                            commit();

                    return true;
                }
            };


    public void startAnimationsForecast() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        FrameLayout f = findViewById(R.id.fragment_container);
        f.clearAnimation();
        f.startAnimation(anim);
    }

    public void startAnimationsToday() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.reset();
        FrameLayout f = findViewById(R.id.fragment_container);
        f.clearAnimation();
        f.startAnimation(anim);
    }

}


