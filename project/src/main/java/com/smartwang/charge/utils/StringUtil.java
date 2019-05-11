package com.smartwang.charge.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.smartwang.project.R;
import com.smartwang.charge.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }
    /**
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 是否是手机号码
     * 移动：134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通：130,131,132,145,152,155,156,1709,171,176,185,186
     * 电信：133,134,153,1700,177,180,181,189
     */
    public static boolean isMobile(String mobile) {
        if (isBlank(mobile) || mobile.length() != 11){
            return false;
        }
        String regex = "(\\+\\d+)?1(3[0-9]|4[579]|5[0-35-9]|6[0-35-9]|7[0-9]|8[0-9]|9[0-9])\\d{8}$";
        return Pattern.matches(regex, mobile);
    }
    /**
     * 隐藏手机号中间四位
     */
    public static String getHideMobile(String mobile){
        if (isMobile(mobile)){
            return mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
        }else{
        return "";
        }
    }

    /**
     * 隐藏银行卡除后四位
     */
    public static String getHideBankCode(String code){
        int length = code.length();
        if (length >= 4) {
            return "***************" + code.substring(length - 4, length);
        }else{
            return "";
        }
    }

    /**
     * 判断密码格式 登录密码
     * @param pw
     * @return
     */
    public static boolean isPassword(String pw){
        if (isBlank(pw) || pw.length()<6 || pw.length()>20){
            return false;
        }
        return true;
    }

    /**
     * 判断密码格式,交易密码
     * @param pw
     * @return
     */
    public static boolean isDealPassword(String pw){
        if (isBlank(pw) || pw.length()!= 6 || !isNum(pw)){
            return false;
        }
        return true;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 使用StringBuffer追加字符串
     * @param str 字符串数组
     * @return 完整字符串
     */
    public static String append(String...str) {
        StringBuffer sb = new StringBuffer();
        int len = str.length;
        for (int i = 0; i < len; i++) {
            if (null != str[i]) {
                sb.append(str[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 检验护照
     * 护照号码的格式：
     * 因私普通护照号码格式有:14/15+7位数,G+8位数；因公普通的是:P.+7位数；
     * 公务的是：S.+7位数 或者 S+8位数,以D开头的是外交护照.D=diplomatic
     *
     * @return 是合格身护照返回true, 否则返回false
     */
    public static boolean checkPassport(String string) {
        String regex
                = "^[a-zA-Z0-9-]{1,30}$";
        return startCheck(regex, string);
    }

    private static boolean startCheck(String reg, String string) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);
        tem = matcher.matches();
        return tem;
    }

    /**
     * 判断身份证格式
     */
    public static boolean isIdCardNo(String idNum){
        //定义判别用户身份证号的正则表达式（要么是15位或18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idNum);
        if(!idNumMatcher.matches()){
            return false;
        }
        return true;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static final String MSG_REPLACE_STR = "%s";

    public static String replace(String src, String...str) {
        if (str == null) {
            return src;
        }
        int count = countStr(src, MSG_REPLACE_STR);
        if (count != str.length) {
            return null;
        }
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("$")){
                str[i] = Matcher.quoteReplacement("$");
            };
            src = src.replaceFirst(MSG_REPLACE_STR, str[i]);
        }
        count = 0;
        return src;
    }

    /**
     * 计算src中包含str的个数
     * 可以优化 --> indexOf(a, b)
     * @return
     */
    public static int countStr(String src, String str) {
        int count = 0;
        if (src.indexOf(str) == -1) {
            return 0;
        } else if (src.indexOf(str) != -1) {
            count += countStr(src.substring(src.indexOf(str) + str.length()), str);
            return count + 1;
        }
        return 0;
    }

    public static boolean isCount(String count){
        if (isInteger(count) || isDouble(count)){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        if (isBlank(value)){
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        if (isBlank(value)){
            return false;
        }
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean copyText(String value){
        if (StringUtil.isNotBlank(value)) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) BaseApplication.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", value);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtil.showShort(R.string.text_copy_succeed);
           return true;
        } else {
            ToastUtil.showShort(R.string.text_copy_failed);
            return false;
        }
    }
}
