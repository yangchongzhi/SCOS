package es.source.code.activity.utils;

import android.content.Context;
import android.graphics.Typeface;
import java.lang.reflect.Field;
import es.source.code.activity.R;

public class Icon {
    public static Typeface getTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
    }

    // 动态获取R.drawable.xx
    public static int getDrawable(String iconName, String type) {
        Field f;
        try {
            switch (type) {
                case "drawable":
                    f = R.drawable.class.getField(iconName);
                    break;
                case "string":
                default:
                    f = R.string.class.getField(iconName);
            }

            return f.getInt(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
