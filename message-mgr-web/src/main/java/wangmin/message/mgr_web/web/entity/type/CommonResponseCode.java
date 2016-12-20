package wangmin.message.mgr_web.web.entity.type;

/**
 * Created by wm on 2016/12/20.
 */
public enum CommonResponseCode {
    ok(0),
    paramError(1),
    databaseError(2),
    permissionError(3),
    rpcError(4),
    systemError(100),
    otherError(101);

    protected static final CommonResponseCode defaultEnum = ok;

    protected final int value;
    private CommonResponseCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    public static int getValue(CommonResponseCode status) {
        if (status == null)
            return defaultEnum.value;
        else
            return status.value;
    }
    public static CommonResponseCode valueOf(int value, boolean returnDefault) {
        CommonResponseCode[] list = CommonResponseCode.values();
        for (int i=0; i<list.length; ++i) {
            if (list[i].value == value)
                return list[i];
        }

        if (returnDefault)
            return defaultEnum;
        else
            return null;
    }
}
