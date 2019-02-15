package com.sensetime.qinhaihang_vendor.qhhpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.PermissionHelper;
import com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean.Permission;
import com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback.ICallbackManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void requestPermission(View view) {
        PermissionHelper.init(this)
                .requestPermissions(new String[]{Manifest.permission.CAMERA}, new ICallbackManager.IPermissionCallback() {
                    @Override
                    public void onAcceptCallback(Permission permission) {
                        Log.d("qhh","name = "+permission.name+",granted = "+permission.granted);
                    }
                });
    }
}
