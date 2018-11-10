package es.source.code.utils;

public class Final {

    public static final String SCOS_SP_Name = "scos_sp";

    public static class ActivityRequestCode {
        public static final int LOGIN_OR_REGISTER_CODE = 0;
        public static final int HELP_CODE = 1;
    }

    public static class ActivityTransferInfo {
        public static final String FROM_ENTRY = "msg_from_SCOSEntry";
        public static final String ENTRY_TO_MAIN = "FromEntry";

        public static final String FROM_LOR = "msg_from_LoginOrRegister";
        public static final String LOR_BACK_TO_MAIN = "Return";
        public static final String LOR_LOGIN_TO_MAIN = "LoginSuccess";
        public static final String LOR_REGISTER_TO_MAIN = "RegisterSuccess";

        public static final String FROM_FV = "msg_from_FoodView";
        public static final String FV_TO_WAIT_ORDER = "waitOrderList";
        public static final String FV_TO_ORDER = "orderList";


    }

    public static class AppTip {
        public static final String INPUT_ERROR_TIP = "输入不符合规则";
        public static final String REGISTER_TIP = "欢迎您成为 SCOS 新用户";
        public static final String ORDER_SUCCESS_TIP = "点菜成功";
        public static final String DISCOUNT_TIP = "您好，老顾客，本次你可享受 7 折优惠";

    }

    public static class HelperInfo {
        public static final String PHONE = "5554";
        public static final String MESSAGE = "test scos helper";
        public static final String MAIL_OK_TIP = "test scos helper";

    }

    public static class FoodArgs {
        public static final String[] CATEGORY_LIST = {"冷菜", "热菜", "海鲜", "酒水"};
        public static final String[] FOOD_ORDERED_STATE = {"未下单菜", "已下单菜"};
    }

    public static class HandlerMessage {
        public static final int START_THREAD = 1;
        public static final int STOP_THREAD = 0;
        public static final int SENT_APP = 10;

    }

    public static class NotifyId {
        public static final int NEW_FOOD = 1;
    }
}
