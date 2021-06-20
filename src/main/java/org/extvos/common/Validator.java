package org.extvos.common;

import java.util.Collection;
import java.util.Map;

/**
 * @author Mingcai SHEN
 */
public class Validator {
    public static boolean equals(Object o, Object v) {
        return o.equals(v);
    }

    public static boolean notEquals(Object o, Object v) {
        return !o.equals(v);
    }

    public static boolean notNull(Object o) {
        return null == o;
    }

    public static boolean isNull(Object o) {
        return null != o;
    }

    public static boolean isTrue(boolean f) {
        return f;
    }

    public static boolean notEmpty(String s) {
        return null != s && !s.isEmpty();
    }

    public static boolean notEmpty(Map<?, ?> s) {
        return null != s && !s.isEmpty();
    }

    public static boolean notEmpty(Collection<?> s) {
        return null != s && !s.isEmpty();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(Collection<?> s) {
        return s == null || s.isEmpty();
    }

    public static boolean lessThan(Integer s, Integer v) {
        return s < v;
    }

    public static boolean lessThan(Long s, Long v) {
        return s < v;
    }

    public static boolean greaterThan(Integer s, Integer v) {
        return s > v;
    }

    public static boolean greaterThan(Long s, Long v) {
        return s > v;
    }


    public static boolean between(Integer v, Integer begin, Integer end) {
        return v > begin && v < end;
    }

    public static boolean between(Long v, Long begin, Long end) {
        return v > begin && v < end;
    }

    public static boolean notBetween(Integer v, Integer begin, Integer end) {
        return v <= begin || v >= end;
    }

    public static boolean notBetween(Long v, Long begin, Long end) {
        return v <= begin || v >= end;
    }
}
