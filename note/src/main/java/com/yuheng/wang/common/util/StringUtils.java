package com.yuheng.wang.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 工具类
 * 
 * @author wangyuheng
 * @date 2018/09/03
 */
public class StringUtils {
    private static Log log = LogFactory.getLog(StringUtils.class);
    public static String DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /** 防止SQL和跨站脚本 */
    public static String[] injection = new String[] {"select", "exec", "like", "count", "where", "insert", "update",
        "delete", "and", "onmouseover", "onfocus", "onerror", "or", "join", "union", "truncate", "drop", "script",
        "alert", "embed", "CR", "LF", "svg", "confirm", "prompt", "onload", "iframe", "frame", "exec", "applet",
        "script", "alert", "object", "embed", "onload", "onerror", "onclick", "ondbclick", "jumpto"};

    public static String[] injectionStr = {"{", "}", "<", ">", "[", "]", "·", "!", "#", "%", "^", "&", "`", "~", "/",
        "$", "-", "*", "\"", "=", "@", "_", ",", ".", "。", "?", "？", ":", "；", ";", "！", "select", "insert", "update",
        "delete", "and", "‘", "or", "join", "union", "truncate", "drop", "alter", "script", "expression", "Esc", "Alt",
        "iframe", "eval", "waitfor", "delay", "Tab", "Shift", "Ctrl", "CapsLk", "onerror", "onclick", "exec", "like",
        "count", "where", "onmouseover", "onfocus", "embed", "CR", "LF", "svg", "confirm"};
    public static Pattern P_STRING = Pattern.compile("\\s*|\t|\r|\n");
    public static Pattern P_NUMBER = Pattern.compile("[0-9]*");

    private static String CHAR_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 防止SQL和跨站脚本
     * 
     * @param value
     * @return
     */
    public static String proccessInjection(String value) {
        String ret = value;
        if (value != null) {
            value = value.toLowerCase();
            for (int j = 0; j < injection.length; j++) {
                if (value.indexOf(injection[j]) >= 0) {
                    return "";
                }
            }
            for (int i = 0; i < value.length(); i++) {
                String x = value.substring(i, i + 1);
                if ("{}<>*#&'\\\"%&;$@\'CRLF".indexOf(x) >= 0) {
                    ret = "";
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 防止SQL和跨站脚本 去掉()
     * 
     * @param value
     * @return
     */
    public static String proccessInjectionTwo(String value) {
        String ret = value;
        if (value != null) {
            value = value.toLowerCase();
            for (int j = 0; j < injection.length; j++) {
                if (value.indexOf(injection[j]) >= 0) {
                    return "";
                }
            }
            for (int i = 0; i < value.length(); i++) {
                String x = value.substring(i, i + 1);
                if ("{}<>*|'\\\"%&;$@\'CRLF".indexOf(x) >= 0) {
                    ret = "";
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 富文本编辑器
     * 
     * @param value
     * @return
     */
    public static String proccessHtmlText(String value) {
        if (value != null) {
            value = value.toLowerCase();
            for (int j = 0; j < injection.length; j++) {
                if (value.indexOf(injection[j]) >= 0) {
                    value = value.replaceAll(injection[j], "");
                }
            }
        }
        return value;
    }

    /**
     * 特殊过滤
     * 
     * @param value
     * @return
     */
    public static String proccessActText(String value) {
        if (value != null) {
            String newValue = value.toLowerCase();
            for (int j = 0; j < injection.length; j++) {
                if (newValue.indexOf(injection[j]) >= 0) {
                    value = value.replaceAll(injection[j], "");
                }
            }
        }
        return value;
    }

    public static boolean isInjection(String value) {
        if (value != null) {
            value = value.toLowerCase();
            for (int j = 0; j < injection.length; j++) {
                if (value.indexOf(injection[j]) >= 0) {
                    return true;
                }
            }
            for (int i = 0; i < value.length(); i++) {
                String x = value.substring(i, i + 1);
                if ("{}<>*|'\\\"%&;$@\'CRLF".indexOf(x) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 反编码
     * 
     * @param src
     * @return
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 转大写
     * 
     * @param str
     * @return
     */
    public static String upperString(String str) {
        return str.toUpperCase();
    }

    /**
     * 转小写
     * 
     * @param str
     * @return
     */
    public static String lowerString(String str) {
        return str.toLowerCase();
    }

    /**
     * 不区分大小写比较
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static Boolean compareIngoreCase(String str1, String str2) {
        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 区分大小写比较
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static Boolean compare(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 第一个字母转小写
     * 
     * @param word
     * @return
     */
    public static String lowerFirstLetter(String word) {
        return word.substring(0, 1).toLowerCase() + word.substring(1, word.length());
    }

    /**
     * 第一个字母转大写
     * 
     * @param word
     * @return
     */
    public static String upperFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
    }

    /**
     * 判断字符串是否为空字符串或为Null
     * 
     * @param str
     * @return
     */
    public static Boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断字符串是否为空字符串或为Null
     * 
     * @param str
     * @return
     */
    public static Boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 去掉字符串首尾空格
     * 
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str.trim();
    }

    public static String replaceBlank(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            Matcher m = P_STRING.matcher(str);
            return m.replaceAll("");
        }
    }

    /**
     * 将数组转换为数组字符串以,隔开 中括号第一个属于特殊字符需要转义，第二个不属于特殊符号，不需要转义
     * 
     * @param arrays
     * @return
     */
    public static String array2String(Object[] arrays) {
        StringBuffer sb = new StringBuffer("(");
        for (int i = 0; i < arrays.length; i++) {
            String string = (String)arrays[i];
            sb.append("'" + string + "'" + (i == arrays.length - 1 ? "" : ","));
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 将数组转换为数组字符串以,隔开，不加单引号
     * 
     * @param arrays
     * @return
     */
    public static String array2Str(Object[] arrays) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arrays.length; i++) {
            String string = (String)arrays[i];
            sb.append(string + (i == arrays.length - 1 ? "" : ","));
        }
        return sb.toString();
    }

    /**
     * 根据Class生成hQl初始化语句
     * 
     * @param clazz
     * @return
     */
    public static <T> StringBuffer hQlInit(Class<T> clazz, Object... params) {
        String entityName = clazz.getSimpleName();
        StringBuffer sb = new StringBuffer("from " + entityName + " Z where 1=1 ");
        return sb;
    }

    /**
     * 空格右填充至指定长度
     * 
     * @param str
     * @param length
     * @return
     */
    public static String rightFillBlank(String str, int length) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        if (str.length() > length) {
            return str.substring(0, length);
        }

        sb.append(str);
        for (int i = 0; i < length - str.length(); i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Clob字段转换成String
     * 
     * @param clob
     * @return
     */
    public static String clob2String(Clob clob) {
        BufferedReader br = null;
        try {
            String tmpStr = "";
            StringBuffer sb = new StringBuffer();
            br = new BufferedReader(clob.getCharacterStream());
            while ((tmpStr = br.readLine()) != null) {
                sb.append(tmpStr);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取sql拼接的sql语句
     * 
     * @param sql
     * @param first
     * @param last
     * @return
     */
    public static String getPagingsQl(String sql, int first, int last) {
        String prefix = "select tmp2.* from ( select rownum num,tmp1.* from ( ";
        String stuffix = " ) tmp1 where rownum <= " + last + " ) tmp2 where tmp2.num > " + first;
        return prefix + sql + stuffix;
    }

    /**
     * 获取sql拼接的sql语句
     * 
     * @param sql
     * @param first
     * @param last
     * @return
     */
    public static String getPagingsQl1(String sql, int first, int last) {
        String prefix
            = "select tmp2.order_no,tmp2.camp_line,tmp2.group_id,tmp2.client_code,tmp2.color,tmp2.num1 from ( select rownum num,tmp1.order_no,tmp1.camp_line,tmp1.group_id,tmp1.client_code,tmp1.color,tmp1.num num1 from ( ";
        String stuffix = " ) tmp1 where rownum <= " + last + " ) tmp2 where tmp2.num > " + first;
        return prefix + sql + stuffix;
    }

    /**
     * 字符串隐藏方法
     * 
     * @return
     */
    public static String getSecretsrc(String src) {
        if (!isBlank(src)) {
            StringBuilder sb = new StringBuilder();
            int len = src.length();
            if (len < 2) {
                sb.append(src);
            } else if (len == 5) {
                sb.append(src.substring(0, 2) + "**" + src.substring(4));
            } else if (len > 5 && len <= 10) {
                sb.append(src.substring(0, len / 2 - 2) + "***" + src.substring(len / 2 + 1));
            } else if (len > 10) {
                sb.append(src.substring(0, len / 2 - 2) + "****" + src.substring(len / 2 + 2));
            } else {
                sb.append(src.substring(0, len / 2) + "**");
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 保留小数点后2位数
     * 
     * @param value
     * @param value
     * @return
     */
    public static BigDecimal formatNumber(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取解码后的字符参数
     * 
     * @param request
     * @param paramName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getDecodeParam(HttpServletRequest request, String paramName)
        throws UnsupportedEncodingException {
        String paramValue = request.getParameter(paramName);
        paramValue = StringUtils.isBlank(paramValue) ? "" : java.net.URLDecoder.decode(paramValue.trim(), "utf-8");
        paramValue = StringUtils.proccessInjection(paramValue);
        return paramValue;
    }

    public static String null2str(String str) {
        return str == null ? "" : str;
    }

    public static char[] generateCheckPass() {
        int len = 15;
        char[] rands = new char[len];
        for (int i = 0; i < len; i++) {
            int rand = (int)(new Random().nextInt(62));
            rands[i] = CHAR_STRING.charAt(rand);
        }
        return rands;
    }

    public static Double parseStr2Double(Object object) {
        if (object == null) {
            return null;
        }

        return ((BigDecimal)object).doubleValue();
    }

    public static Long parseStr2Long(Object object) {
        if (object == null) {
            return null;
        }

        return ((BigDecimal)object).longValue();
    }

    public static Integer parseStr2Int(Object object) {
        if (object == null) {
            return null;
        }

        return ((BigDecimal)object).intValue();
    }

    public static Date parseStr2Date(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT);
            date = sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }

        return date;
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     */
    public static String getParameter(String name, HttpServletRequest request) {
        if (name == null) {
            return "";
        }

        String value = request.getParameter(name);
        if (value == null) {
            return null;
        }
        return StringUtils.proccessInjection(value.trim());
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @param defaultValue 参数默认值
     * @return 参数值
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        if (name == null) {
            return "";
        }

        String value = request.getParameter(name);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }

        return StringUtils.proccessInjection(value.trim());
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     */
    public static int getIntParameter(HttpServletRequest request, String name) {
        if (name == null) {
            return 0;
        }
        String value = request.getParameter(name);
        if (value == null) {
            return 0;
        }
        String p = StringUtils.proccessInjection(value.trim());
        return Integer.valueOf(p);
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @param defaultValue 参数默认值
     * @return 参数值
     */
    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        if (name == null) {
            return 0;
        }
        String value = request.getParameter(name);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }
        String p = StringUtils.proccessInjection(value.trim());
        return Integer.valueOf(p);
    }

    /**
     * 获取富文本参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     */
    public static String getHtmlTextParameter(HttpServletRequest request, String name) {
        if (name == null) {
            return "";
        }
        String value = request.getParameter(name);
        if (value == null) {
            return "";
        }
        String p = StringUtils.proccessHtmlText(value.trim());
        return p;
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     */
    public static String[] getParameterValues(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = StringUtils.proccessInjection(values[i]);
            }
        }
        return values;
    }

    /**
     * 获取中文编码参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     * @throws Exception
     */
    public static String getChineseParameter(HttpServletRequest request, String name) throws Exception {
        String value = request.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = URLDecoder.decode(value.trim(), "utf-8");
            value = StringUtils.proccessInjection(value.trim());
        }
        return value;
    }

    /**
     * 获取参数值
     * 
     * @param request HttpServletRequest
     * @param name 参数名
     * @return 参数值
     * @throws Exception
     */
    public static String[] getChineseParameterValues(HttpServletRequest request, String name) throws Exception {
        String[] values = request.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = URLDecoder.decode(values[i].trim(), "utf-8");
                values[i] = StringUtils.proccessInjection(values[i].trim());
            }
        }
        return values;
    }

    /**
     * 三到六位隐藏
     * 
     * @param src
     * @return
     */
    public static String getSecretsrc1(String src) {
        if (!isBlank(src)) {
            StringBuilder sb = new StringBuilder();
            int len = src.length();
            if (len <= 2) {
                sb.append(src);
            } else if (len < 5 && len > 2) {
                sb.append(src.substring(0, 2) + "***");
            } else {
                sb.append(src.substring(0, 2) + "***" + src.substring(5, src.length()));
            }
            return sb.toString();
        }
        return "";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String getToken() {
        String[] str
            = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a",
                "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T", "Y",
                "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        String s = "";
        for (int i = 0; i < 6; i++) {
            int a = (int)(new Random().nextInt(62));
            s += str[a];
        }
        return s;
    }

    /**
     * 去除String集合中重复值
     * 
     * @param list
     * @return
     */
    public static List<String> removeString(List<String> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(i).equals(list.get(j))) {
                        list.remove(j);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 去除换行 制表 和回车
     * 
     * @param str
     * @return
     */
    public static String processWrap(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 路径操作及资源注入
     */
    public static String fileFilter(String file) {
        if (file != null) {
            return file.replaceAll("\\/", "").replaceAll("\\\\", "").replaceAll("\\.", "").replaceAll("\\&", "");
        }
        return null;
    }

    /**
     * 多个字符分隔符分割字符串
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static List<String> splitWordByManySplit(String str) throws Exception {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String regex = "\\s+|\\%+|\\*+|\\?+|\\|+|\\(+|\\)+|\\++|\\_+|\\-+|\\[+|\\]+|\\（+|\\）+|\\【+|\\】+";
        String[] split = str.split(regex);
        List<String> wordsList = new ArrayList<String>();
        StringBuilder sb = new StringBuilder("分词结果:");
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.isNotBlank(split[i])) {
                wordsList.add(split[i]);
                sb.append(split[i] + "\t");
            }
        }
        log.info(sb.toString());
        return wordsList;
    }

    /**
     * 判断是否是整数
     * 
     * @param str
     * @return
     * @author shuyi@sinovatech.com
     * @time 2017年8月3日 上午9:25:10
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return P_NUMBER.matcher(str).matches();
    }

    public static void main(String[] args) {
        try {
            splitWordByManySplit("荣耀 畅玩5A    金色");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 去掉字符串中的某个参数和值
     * 
     * @param url
     * @param params
     * @return
     */
    public static String removeParams(String url, Object... params) {
        if (isBlank(url)) {
            return "";
        }
        String reg = null;
        StringBuffer ps = new StringBuffer();
        ps.append("(");
        for (int i = 0; i < params.length; i++) {
            ps.append(params[i]).append("|");
        }
        ps.deleteCharAt(ps.length() - 1);
        ps.append(")");
        // reg = "(?<=[\\?&])" + ps.toString() + "=[^&]*&?";
        // //这一行是匹配以?或者&符号开头的字符串
        // reg = ps.toString() + "=[^&]*&?";
        reg = ps.toString() + "=[^&]*";
        url = url.replaceAll(reg, "");
        url = url.replaceAll("(\\?|&+)$", "");
        return url;
    }

    /**
     * 根据浏览器不同采用不同的编码获取参数
     * 
     * @param request
     * @param value
     * @return
     * @throws Exception
     */
    public static String getBrowserParam(HttpServletRequest request, String value) throws Exception {

        String encode = "iso-8859-1";
        // 判断编码方式是否是iso-8859-1
        if (value.equals(new String(value.getBytes(encode), encode))) {
            String agent = request.getHeader("User-Agent").toLowerCase();
            // 识别IE浏览器
            boolean flag = agent != null
                && (agent.indexOf("msie") != -1 || (agent.indexOf("rv") != -1 && agent.indexOf("firefox") == -1));
            if (flag) {
                return new String(value.getBytes(encode), "gb2312");
            } else {
                return new String(value.getBytes(encode), "utf-8");
            }
        }
        return value;
    }

}
