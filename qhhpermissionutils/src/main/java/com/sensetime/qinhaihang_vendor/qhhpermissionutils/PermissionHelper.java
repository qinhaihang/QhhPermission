package com.sensetime.qinhaihang_vendor.qhhpermissionutils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean.Permission;
import com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback.ICallbackManager;

import java.util.List;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 17:23
 * @des
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class PermissionHelper {

    private static final String REQUEST_PERMISSION = "request_permission";
    private static FragmentActivity mActivity;

    public PermissionHelper(FragmentActivity activity) {
        mActivity = activity;
    }

    public static PermissionHelper init(FragmentActivity activity) {
        return new PermissionHelper(activity);
    }

    private PermissionFragment getFragment() {
        FragmentManager manager = mActivity.getSupportFragmentManager();
        PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(REQUEST_PERMISSION);

        if (null == fragment) {
            fragment = PermissionFragment.getInstance();
            manager.beginTransaction()
                    .add(fragment, REQUEST_PERMISSION)
                    .commitAllowingStateLoss();
            manager.executePendingTransactions(); //立即执行 commit 的事务
        }

        return fragment;
    }

    /**
     * 打开设置页面打开权限
     *
     * @param context
     */
    private void startSettingActivity(@NonNull Activity context) {

        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +
                            context.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivity(intent); //这里的requestCode和onActivityResult中requestCode要一致
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询权限是否开启,如果有提醒禁止再次提醒，则跳转到设置页面
     *
     * @param permissions
     */
    public void checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getFragment().checkPermission(new ICallbackManager.IPermissionListCallback() {
                @Override
                public void onResultCallback(List<Permission> permissions) {
                    for (Permission permission : permissions) {
                        Log.d("qhh", "permission = " + permission.name);
                    }
                }

                @Override
                public void onCheckResultCallback(List<String> permissions) {
                    //返回的是用户拒绝过的权限
                    for (String permission : permissions) {
                        Log.d("qhh", "deny permission = " + permission);
                    }
                }
            }, permissions);
        }
    }

}
