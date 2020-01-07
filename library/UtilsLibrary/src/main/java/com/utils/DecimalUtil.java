package com.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Synopsis     数值计算工具类，防止丢失精度
 * Author		Mosr
 * Version		${VERSION}
 * Create 	    2018.01.26 16:26
 * Email  		intimatestranger@sina.cn
 */
public class DecimalUtil {

    /**
     * 金钱加法
     */
    public static String add(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.add(b2).toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金钱加法
     */
    public static BigDecimal addB(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.add(b2);
            } catch (NumberFormatException e) {
                return new BigDecimal("0.00");
            }
        } else {
            return new BigDecimal("0.00");
        }
    }

    /**
     * 金钱乘法
     */
    public static String multiply(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.multiply(b2).toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金钱乘法
     */
    public static BigDecimal multiplyB(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.multiply(b2);
            } catch (NumberFormatException e) {
                return new BigDecimal("0.00");
            }
        } else {
            return new BigDecimal("0.00");
        }
    }

    /**
     * 乘法
     *
     * @param v1    乘数
     * @param v2    被乘数
     * @param scale 小数点保留位数,四舍五入
     */
    public static String multiplyWithScale(String v1, String v2, int scale) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                BigDecimal result = b1.multiply(b2);
                result = result.setScale(scale, BigDecimal.ROUND_FLOOR);
                return result.toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 乘法
     *
     * @param v1           乘数
     * @param v2           被乘数
     * @param scale        小数点保留位数
     * @param roundingMode
     *
     * @return
     */
    public static String multiplyWithScale(String v1, String v2, int scale, int roundingMode) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                BigDecimal result = b1.multiply(b2);
                result = result.setScale(scale, roundingMode);
                return result.toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金钱除法
     */
    public static String divide(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.divide(b2).toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 如果除不断，产生無限循环小数，那么就会抛出异常，解决方法就是对小数点后的位数做限制
     *
     * @param v2 小数模式 ，DOWN，表示直接不做四舍五入，参考{@link RoundingMode}
     */
    public static String divideWithRoundingDown(String v1, String v2) {
        return divideWithRoundingMode(v1, v2, RoundingMode.DOWN);
    }

    /** 选择小数部分处理方式 */
    public static String divideWithRoundingMode(String v1, String v2, RoundingMode roundingMode) {
        return divideWithRoundingModeAndScale(v1, v2, roundingMode, 2);
    }

    /**
     * 选择小数点后部分处理方式，以及保留几位小数
     *
     * @param v1           除数
     * @param v2           被除数
     * @param roundingMode 小数处理模式
     * @param scale        保留几位
     */
    public static String divideWithRoundingModeAndScale(String v1, String v2,
                                                        RoundingMode roundingMode, int scale) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                if (greaterThan(b2.toString(), "0")) {
                    return b1.divide(b2, scale, roundingMode).toString();
                } else {
                    return "0.00";
                }
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金钱减法
     */
    public static String subtract(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);

                return b1.subtract(b2).toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金钱减法
     */
    public static String subtract(String v1, String v2, int scale, int roundingMode) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                BigDecimal result = b1.subtract(b2);
                result = result.setScale(scale, roundingMode);
                return result.toString();
            } catch (NumberFormatException e) {
                return "0.00";
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 大于
     */
    public static boolean greaterThan(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                return b1.compareTo(b2) == 1;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 小于
     */
    public static boolean lesshan(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                return b1.compareTo(b2) == -1;
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return true;
    }

    /**
     * 等于
     */
    public static boolean equal(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                return b1.compareTo(b2) == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 大于等于
     */
    public static boolean greaterThanAndEqual(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                return b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 小于等于
     */
    public static boolean lesshanAndEqual(String v1, String v2) {
        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
            try {
                BigDecimal b1 = new BigDecimal(v1);
                BigDecimal b2 = new BigDecimal(v2);
                return b1.compareTo(b2) == -1 || b1.compareTo(b2) == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

}
