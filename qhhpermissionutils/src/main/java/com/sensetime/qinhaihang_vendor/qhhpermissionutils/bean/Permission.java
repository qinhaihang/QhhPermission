package com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 16:20
 * @des
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils.bean
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class Permission {
    public String name;
    public boolean granted;
    /**
     * false 选择了 Don’t ask again
     */
    public boolean shouldShowRequestPermissionRationale;

    public Permission(String name, boolean granted) {
        this.name = name;
        this.granted = granted;
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

}
