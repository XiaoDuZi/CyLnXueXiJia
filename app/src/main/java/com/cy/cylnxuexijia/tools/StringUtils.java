package com.cy.cylnxuexijia.tools;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理
 * @author xiaodu
 */
public class StringUtils {


    private StringUtils() { /* cannot be instantiated */
    }

    /**
     * Check a string is empty or not.
     * @param str
     * @return boolean
     */
    public static boolean isStringEmpty(String str) {
        return "null".equals(str) || str == null || str.length() == 0
                || str.trim().length() == 0;
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * <p/>
     * <pre>
     * StringUtils.isBlank(null) = true
     * StringUtils.isBlank("") = true
     * StringUtils.isBlank(" ") = true
     * StringUtils.isBlank("bob") = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsNull(String str) {

        if (isBlank(str) || str.equalsIgnoreCase("null")) {
            return true;
        }
        return false;

    }

    /**
     * <p>
     * Checks if the String contains only unicode digits. A decimal point is not
     * a unicode digit and returns false.
     * </p>
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String
     * (length()=0) will return <code>true</code>.
     * </p>
     * <p/>
     * <pre>
     * StringUtils.isNumeric(null) = false
     * StringUtils.isNumeric(&quot;&quot;) = true
     * StringUtils.isNumeric(&quot; &quot;) = false
     * StringUtils.isNumeric(&quot;123&quot;) = true
     * StringUtils.isNumeric(&quot;12 3&quot;) = false
     * StringUtils.isNumeric(&quot;ab2c&quot;) = false
     * StringUtils.isNumeric(&quot;12-3&quot;) = false
     * StringUtils.isNumeric(&quot;12.3&quot;) = false
     * </pre>
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5Helper(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();// 32位的加密
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSubStr(String str, int subNu, String replace) {

        int strLength = str.length();
        if (strLength >= subNu) {
            str = str.substring((strLength - subNu), strLength);
        } else {
            for (int i = strLength; i < subNu; i++) {
                str += replace;
            }
        }
        return str;
    }

    public static String getUUIDString(String tBrand, String tSeries,
                                       String tUnique, int subNu, String replace) {
        return StringUtils.md5Helper(getSubStr(tBrand, subNu, replace)
                + getSubStr(tSeries, subNu, replace)
                + getSubStr(tUnique, subNu, replace));
    }

    public static String encodeStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(strNum);
        return matcher.matches();
    }

    /**
     * 判断是否为手机号
     * @param string
     * @return
     */
    public static boolean isPhoneNum(String string){
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 字符串变大写
     * @param s
     * @return
     */
    public static String stringChangeCapital(String s) {
        if (equalsNull(s)) {
            return "";
        }
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = Character.toUpperCase(c[i]);
            } else if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = Character.toLowerCase(c[i]);
            }
        }
        return String.valueOf(c);
    }

    /**
     * 逆序每隔3位添加一个逗号
     * @param str
     *            :"31232"
     * @return :"31,232"
     */
    public static String addComma3(String str) {
        if (equalsNull(str)) {
            return "";
        }
        str = new StringBuilder(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * 3 + 3 > str.length()) {
                str2 += str.substring(i * 3, str.length());
                break;
            }
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        // 最后再将顺序反转过来
        return new StringBuilder(str2).reverse().toString();
    }

    public static String handlerStr(String str, int length) {
        if (null != str && !"".equals(str)) {
            if (str.length() > 15) {
                String s = str.substring(0, 15) + "...";
                return s;
            }
            return str;
        }
        return "";
    }

    /**
     * format str
     */
    public static String format(String str, Object... args) {
        if (StringUtils.equalsNull(str)) {
            return null;
        }
        return String.format(str, args);
    }

    /**
     * 截取字符串中的数字
     * @param string
     * @return
     */
    public static String detectionNum(String string){
        if (StringUtils.isStringEmpty(string))return "";
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.replaceAll("").trim();
    }

    /**
     * 截取字符串中的文字
     * @param string
     * @return
     */
    public static String detectionChinese(String string){
        if (StringUtils.isStringEmpty(string))return "";
        String regex="([\u4e00-\u9fa5]+)";
        Matcher matcher = Pattern.compile(regex).matcher(string);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }


}
