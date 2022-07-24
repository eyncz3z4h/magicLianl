package csumissu.fakewechat.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csumissu.fakewechat.R;

/**
 * http://www.cnblogs.com/lichenwei/p/4676214.html
 * http://www.cnblogs.com/lichenwei/p/4411607.html
 *
 * @author sunyaxi
 * @date 2016/5/26
 */
public class WeiboUtils {

    private static final String TAG = WeiboUtils.class.getSimpleName();
    private static final String AT = "@[-\u4e00-\u9fa5\\w]+";
    private static final String TOPIC = "#[\u4e00-\u9fa5\\w]+#";
    private static final String EMOJI = "\\[[\u4e00-\u9fa5\\w]+\\]";
    private static final String URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final String REGEX = "(" + AT + ")|(" + TOPIC + ")|(" + EMOJI + ")|(" + URL + ")";
    private static final int GROUP_AT = 1;
    private static final int GROUP_TOPIC = 2;
    private static final int GROUP_EMOJI = 3;
    private static final int GROUP_URL = 4;

    public static SpannableString transformContent(Context context, String content, float textSize) {
        SpannableString spannableString = new SpannableString(content);
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(spannableString);
        int linkColor = context.getResources().getColor(R.color.cornflowerBlue);

        while (matcher.find()) {
            final String at = matcher.group(GROUP_AT);
            final String topic = matcher.group(GROUP_TOPIC);
            String emoji = matcher.group(GROUP_EMOJI);
            String url = matcher.group(GROUP_URL);

            if (!TextUtils.isEmpty(at)) {
                int startIndex = matcher.start(GROUP_AT);
                int endIndex = startIndex + at.length();
                spannableString.setSpan(new MyClickableSpan(at, linkColor), startIndex, endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (!TextUtils.isEmpty(topic)) {
                int startIndex = matcher.start(GROUP_TOPIC);
                int endIndex = startIndex + topic.length();
                spannableString.setSpan(new ForegroundColorSpan(linkColor), startIndex, endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (!TextUtils.isEmpty(emoji)) {
                int resId = EmotionUtils.getImgByName(emoji);
                if (resId <= 0) {
                    Log.w(TAG, "unmapped emoji " + emoji);
                    continue;
                }
                int startIndex = matcher.start(GROUP_EMOJI);
                int endIndex = startIndex + emoji.length();
                spannableString.setSpan(new MyImageSpan(context, resId, (int) textSize),
                        startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (!TextUtils.isEmpty(url)) {
                int startIndex = matcher.start(GROUP_URL);
                int endIndex = startIndex + url.length();
                spannableString.setSpan(new MyURLSpan(url, linkColor), startIndex, endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannableString;
    }

    private static class MyClickableSpan extends ClickableSpan {

        private String iAtPerson;
        private int iLinkColor;

        public MyClickableSpan(String atPerson, @ColorInt int linkColor) {
            iAtPerson = atPerson;
            iLinkColor = linkColor;
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(widget.getContext(), "view person " + iAtPerson, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(iLinkColor);
        }
    }

    private static class MyURLSpan extends URLSpan {

        private int iLinkColor;

        public MyURLSpan(String url, @ColorInt int color) {
            super(url);
            iLinkColor = color;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(iLinkColor);
        }
    }

    private static class MyImageSpan extends ImageSpan {

        private int iDrawableSize;

        public MyImageSpan(Context context, @DrawableRes int resourceId, int drawableSize) {
            super(context, resourceId, ALIGN_BASELINE);
            iDrawableSize = drawableSize;
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = super.getDrawable();
            drawable.setBounds(0, 0, iDrawableSize, iDrawableSize);
            return drawable;
        }
    }

}
