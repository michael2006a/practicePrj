package com.trendmicro.skyaid.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by herbert_yin on 2016/9/9.
 */
public class ReplaceUtil {

    public static void main(String[] args) {

    }

    protected static final String[][] PATTERN_NUM_MAPPING = new String[][]{
            {"575E01", "5.75E+03"},
            {"160E01", "1.60E+03"},
            {"156E00", "1.56E+02"},
    };

    protected static final String[][] PATTERN_TOMBO_MAPPING = new String[][]{
            {"", "【vista】"},
            {"", "【XP】"},
            {"", "【win7】"},
            {"", "【windows7】"},
            {"", "【Win8】"},
            {"", "【win10】"},
            {"", "【vista&win7】"},
            {"", "【vista＆win7】"},
            {"", "【vista7】"},
            {"", "【vista ＆ win7】"},
            {"", "[VISTA&WIN7]"},
            {"", "【windows 7】"},
            {"", "【ti6.0 ti7.0】"},
            {"", "【xp&vista&win7】"},
            {"", "【win8&win10】"},
            {"", "【2012】"},
            {"", "【vista・win7】"},
            {"", "【ti8.0】"},
            {"", "【ti10.0】"},
            {"", "【Microsoft Edge】"},
            {"", "[Ti10.0]"},
            {"", "9_"},
            {"", "8_"},
            {"", "7_"},
            {"", "6_"},
            {"", "5_"},
            {"", "4_"},
            {"", "3_"},
            {"", "2_"},
            {"", "1_"},
            {"", "0_"},
//            {"", "^1."},
//            {"", "^2."},
//            {"", "^3."},
            {"", "a_"},
            {"", "b_"},
            {"", "c_"},
            {"", "d_"},
            {"", "e_"},
            {"", "f_"},
            {"", "g_"},
            {"", "h_"},
            {"", "i_"},
            {"", "j"},
            {"", "VB27："},
            {"", "VB28："},
            {"", "VB29："},
            {"", ".txt"},
    };


    protected static final String[][] PATTERN_WORD_MAPPING = new String[][]{
            {"TROJ_", "TROJ-"},
            {"TROJSAMPLE", "(TROJ_+[A-Z0-9|.|-|_]+)"},
            {"UPLOADFILE", "Cons_[?0-9]{14}_[?a-zA-Z0-9]{8}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{12}.zip"},
            {"GUID", "[?a-zA-Z0-9]{8}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{4}-[?a-zA-Z0-9]{12}"},
            {"EMAIL", "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"},
            {"CASEID", "[?0-9]{1}-[?0-9]{1}-[?0-9]{9,10}"},
            {"FAX", "[?0-9]{3,4}-[?0-9]{2,3}-[?0-9]{3,4}"},
//            {"SN", "[?a-zA-Z]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}"},
//            {"SN", "[?a-zA-Z]{4}-[?0-9]{4}-[?0-9]{4}-[?0-9]{4}"},
            {"MacOS otherwise", "MacOS 縺昴・莉・0"},
            {"MacOS otherwise", "MacOS 縺昴・莉・1"},
            {"MacOS otherwise", "MacOS縺吶∋縺ｦ"},
            {"Windows otherwise", "Windows 縺昴・莉・0"},
            {"Windows otherwise", "Windows 縺昴・莉・1"},
            {"Windows otherwise", "Windows縺吶∋縺ｦ"},
            {"Windows 7 64-bit", "Windows7縲\u008064bit"},
    };


    /**
     * @param line
     * @param regArray
     * @return
     */
    protected static String regReplace(String line, String[][] regArray, Boolean isIgnoreCase) {
        String tmp = line;
//        System.out.println("orginal String: " + tmp);
        for (String[] arr : regArray) {
            // 普通正则替换
            String word = arr[0];
            String reg = arr[1];
            if(isIgnoreCase)
                tmp = tmp.replace(reg.toUpperCase(), word);
            else
                tmp = tmp.replace(reg, word);
        }
        // 删除
//        System.out.println("out String: " + tmp);
        return tmp;
    }

    /**
     * @param line
     * @param regArray
     * @return
     */
    protected static String regReplaceAll(String line, String[][] regArray, Boolean isIgnoreCase) {
        String tmp = line;
//        System.out.println("orginal String: " + tmp);
        for (String[] arr : regArray) {
            // 普通正则替换
            String word = arr[0];
            String reg = arr[1];
            if (isIgnoreCase)
                tmp = tmp.replaceAll(reg.toUpperCase(), word);
            else
                tmp = tmp.replaceAll(reg, word);
        }
        // 删除
//        System.out.println("out String: " + tmp);
        return tmp;
    }

    /**
     * 正则替换
     *
     * @param line
     * @return
     */
    protected static String regReplace(String line) {
        String tmp = line;
        for (String[] arr : PATTERN_WORD_MAPPING) {
            // URL 替换， 因为不能普通正则搞定 所以。。。
            tmp = URLMatch.match(tmp);

            // 标点符号替换， 普通正则不会写~ 奇怪的问题
            String[] cjkMarks = {"。", ",", "\"", "'", "：", "；", "？", "《", "》", "、", "?"};
            String[] cjkReplace = {"", "", "", "", "", "", "", "", "", "", ""};
            tmp = StringUtils.replaceEach(tmp, cjkMarks, cjkReplace);

            // 普通正则替换
            String word = arr[0];
            String reg = arr[1];
            tmp = tmp.replaceAll(reg, word);
        }
        // 删除

        return tmp;
    }
}
