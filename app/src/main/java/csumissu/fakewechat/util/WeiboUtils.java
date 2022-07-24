package csumissu.fakewechat.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.cnblogs.com/lichenwei/p/4676214.html?utm_source=tuicool&utm_medium=referral
 * @author sunyaxi
 * @date 2016/5/26
 */
public class WeiboUtils {

    private static final String AT = "@[\u4e00-\u9fa5\\w]+";
    private static final String TOPIC = "#[\u4e00-\u9fa5\\w]+#";
    private static final String EMOJI = "\\[[\u4e00-\u9fa5\\w]+\\]";
    private static final String URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";


    public static SpannableString transformContent(Context context, String content, TextView textView) {
        SpannableString spannableString = new SpannableString(content);
        Pattern pattern = Pattern.compile("(" + AT + ")(" + TOPIC + ")(" + EMOJI + ")(" + URL + ")");
        Matcher matcher = pattern.matcher(spannableString);

        System.out.println("new " + URL.matches("http://weibo.com/u/3952070245"));
        System.out.println(URL.matches("http://t.cn/R5AWapG"));

        if (matcher.find()) {
            // 要实现文字的点击效果，这里需要做特殊处理
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            // 重置正则位置
            matcher.reset();
        } else {
            System.out.println("not find -- " + content);
        }

        while (matcher.find()) {
            // 根据group的括号索引，可得出具体匹配哪个正则(0代表全部，1代表第一个括号)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            String emoji = matcher.group(3);
            String url = matcher.group(4);
            System.out.println("at=" + at + ", topic=" + topic + ", emoji=" + emoji + ", url=" + url);

        }
        return spannableString;
    }


}
