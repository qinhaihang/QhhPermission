package com.sensetime.qinhaihang_vendor.qhhpermissionutils;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean.Permission;
import com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback.ICallbackManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 16:11
 * @des 中间层，用于接收 onRequestPermissionsResult
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class PermissionFragment extends Fragment {

    private static final int MAX_TRY_COUNT = 10; //requesCode 刷新次数
    private FragmentActivity mActivity;

    private SparseArray<ICallbackManager.IPermissionListCallback> mListCallbacks = new SparseArray<>();
    private Random mCodeGenerator = new Random();

    public PermissionFragment() {
    }

    public static PermissionFragment getInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
    }

    private int createRequestCode() {
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
            tryCount++;
        } while (mListCallbacks.indexOfKey(requestCode) >= 0 && tryCount < MAX_TRY_COUNT);

        return requestCode;
    }

    /**
     * 查询权限是否申请
     *
     * @param permissions
     */
    public void checkPermission(ICallbackManager.IPermissionListCallback listCallback, String... permissions) {

        ArrayList<String> requestPermissionList = new ArrayList<>();
        ArrayList<String> denyPermissionList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int grs = 0;
            for (String permission : permissions) {
                int permissionStatus = (int) ContextCompat.checkSelfPermission(mActivity, permission);
                grs += permissionStatus;
                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                        //之前用户禁止过该权限，提示用户权限用处，以及是否重新去开启
                        denyPermissionList.add(permission);
                    } else {
                        requestPermissionList.add(permission);
                    }

                }
            }

            if (grs == 0) { //全部已经授权过，不必再申请
                listCallback.onResultCallback(null);
            }

            if (!requestPermissionList.isEmpty()) {
                requestPermissions(requestPermissionList.toArray(new String[requestPermissionList.size()]), listCallback);
            }

            if (!denyPermissionList.isEmpty()) {
                listCallback.onCheckResultCallback(denyPermissionList);
            }

        } else { //不需要申请权限
            listCallback.onResultCallback(null);
        }

    }

    public void requestPermissions(@NonNull String[] permissions, ICallbackManager.IPermissionListCallback listCallback) {
        int requestCode = createRequestCode();
        mListCallbacks.put(requestCode, listCallback);
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionCallback(requestCode, permissions, grantResults);
    }

    private void handlePermissionCallback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ICallbackManager.IPermissionListCallback callback = mListCallbacks.get(requestCode);

        if (null == callback) {
            return;
        }

        mListCallbacks.remove(requestCode);

        int length = grantResults.length;
        ArrayList<Permission> needSetPermissions = new ArrayList<>();

        for (int i = 0; i < length; i++) {

            String permission = permissions[i];
            int grantResult = grantResults[i];

            needSetPermissions.add(new Permission(
                    permission,
                    grantResult == PackageManager.PERMISSION_GRANTED,
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)
            ));

        }

        callback.onResultCallback(needSetPermissions);
    }
}
