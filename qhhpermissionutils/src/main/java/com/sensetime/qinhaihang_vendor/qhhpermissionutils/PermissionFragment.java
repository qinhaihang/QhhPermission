package com.sensetime.qinhaihang_vendor.qhhpermissionutils;

import android.content.pm.PackageManager;
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

import java.util.Random;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 16:11
 * @des  中间层，用于接收 onRequestPermissionsResult
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class PermissionFragment extends Fragment {

    private static final int MAX_TRY_COUNT = 10; //requesCode 刷新次数
    private FragmentActivity mActivity;

    private SparseArray<ICallbackManager.IPermissionCallback> mCallbacks = new SparseArray<>();
    private Random mCodeGenerator = new Random();

    public PermissionFragment() {
    }

    public static PermissionFragment getInstance(){
        return new PermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
    }

    private int createRequestCode(){
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
            tryCount++;
        } while (mCallbacks.indexOfKey(requestCode) >= 0 && tryCount < MAX_TRY_COUNT);

        return requestCode;
    }

    /**
     * 查询权限是否申请
     * @param permissions
     */
    public void checkPermission(String...permissions){

        for (String permission : permissions) {
            int permissionStatus = (int) ContextCompat.checkSelfPermission(mActivity, permission);
            if(permissionStatus == PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
    }

    public void requestPermissions(@NonNull String[] permissions,ICallbackManager.IPermissionCallback callback){
        int requestCode = createRequestCode();
        mCallbacks.put(requestCode,callback);
        requestPermissions(permissions,requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionCallback(requestCode, permissions, grantResults);
    }

    private void handlePermissionCallback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        ICallbackManager.IPermissionCallback callback = mCallbacks.get(requestCode);

        if(null == callback){
            return;
        }

        mCallbacks.remove(requestCode);

        int length = grantResults.length;
        for (int i = 0; i < length; i++) {

            String permission = permissions[i];
            int grantResult = grantResults[i];

            callback.onAcceptCallback(new Permission(
                    permission,
                    grantResult == PackageManager.PERMISSION_GRANTED,
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)
            ));

        }
    }
}
