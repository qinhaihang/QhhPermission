package com.sensetime.qinhaihang_vendor.qhhpermissionutils;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback.ICallbackManager;

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

    public void checkPermission(String...permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getFragment().checkPermission(permissions);
        }
    }

    public void requestPermissions(@NonNull String[] permissions, ICallbackManager.IPermissionCallback callback) {
        getFragment().requestPermissions(permissions, callback);
    }

    public PermissionFragment getFragment() {
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

}
