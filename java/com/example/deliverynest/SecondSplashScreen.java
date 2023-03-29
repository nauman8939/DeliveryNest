package com.example.deliverynest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SecondSplashScreen extends BaseActivity {
    private static final int LOADING_DIALOG_DELAY = 3000;
    private LoadingDialog mLoadingDialog;
    private ConnectivityManager mConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mLoadingDialog = new LoadingDialog(this);
        Button signInButton = findViewById(R.id.signinbutton);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void startApp(View view) {
        if (!isDeviceConnected()) {
            showNoInternetConnectionDialog();
        } else {
            final Intent intent = new Intent(SecondSplashScreen.this, LoginScreen.class);
            mLoadingDialog.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.cancel();
                    startActivity(intent);
                }
            };
            handler.postDelayed(runnable, LOADING_DIALOG_DELAY);
        }
    }

    private boolean isDeviceConnected() {
        NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected());
    }

    private void showNoInternetConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondSplashScreen.this);
        builder.setMessage("No Internet Connection,Please Switch On Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), SecondSplashScreen.class));
                        finish();
                    }
                })
                .show();
    }
    public void knowus(View v){
        Intent intent = new Intent(getApplicationContext(),OnBoardingScreen.class);
        startActivity(intent);
    }
}
