package com.sensetime.qinhaihang_vendor.qhhpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void requestPermission(View view) {
//        PermissionHelper.init(this)
//                .requestPermissions(new String[]{Manifest.permission.CAMERA}, new ICallbackManager.IPermissionCallback() {
//            @Override
//            public void onAcceptCallback(Permission permission) {
//                Log.d("qhh","name = "+permission.name+",granted = "+permission.granted);
//            }
//        });

//        PermissionHelper.init(this)
//                .checkPermission(new ICallbackManager.IPermissionCallback() {
//                    @Override
//                    public void onAcceptCallback(Permission permission) {
//                        Log.d("qhh","name = "+permission.name+",granted = "+permission.granted);
//                    }
//                }, Manifest.permission.CAMERA,
//                        Manifest.permission.READ_PHONE_STATE);

        PermissionHelper.init(this)
                .checkPermission(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE);
    }
}
