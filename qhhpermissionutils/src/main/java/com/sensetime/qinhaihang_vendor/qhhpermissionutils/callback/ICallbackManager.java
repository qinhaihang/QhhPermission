package com.sensetime.qinhaihang_vendor.qhhpermissionutils.callback;

import com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean.Permission;

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

    interface IPermissionCallback{
        void onAcceptCallback(Permission permission);
    }

}
