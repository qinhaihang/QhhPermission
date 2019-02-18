package com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean.Permission;

import java.util.List;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 16:19
 * @des
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public interface ICallbackManager {

    interface IPermissionListCallback {
        void onResultCallback(List<Permission> permissions);  //返回已经请求权限之后的结果

        void onCheckResultCallback(List<String> permissions); //返回的是用户拒绝过的权限
    }

    interface IDenyPermissionCallback {
        void onDenyPermissions(List<String> permissions);
    }

    interface IRequestCallback{
        void onAllPermissonGranted(boolean flag);
    }

}
