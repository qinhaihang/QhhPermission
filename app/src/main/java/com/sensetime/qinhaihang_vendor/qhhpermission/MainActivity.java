package com.sensetime.qinhaihang_vendor.qhhpermission;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.PermissionHelper;
import com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback.ICallbackManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ICallbackManager.IDenyPermissionCallback, ICallbackManager.IRequestCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void requestPermission(View view) {
        PermissionHelper.getInstance()
                .init(this)
                .setmDenyPermissionCallback(this)
                .setmRequestCallback(this)
                .checkPermission(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public void onDenyPermissions(List<String> permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("您拒绝了相关权限，将会影响App功能的使用，请到权限管理中打开权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionHelper.getInstance().startSettingActivity(MainActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.create().dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onAllPermissonGranted(boolean flag) {
        Log.d("qhh","onAllPermissonGranted = "+ flag);
    }
}
