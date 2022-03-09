package plus.extvos.common;

import plus.extvos.common.exception.ResultException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Assert, a generic assertion toolkit.
 *
 * @author Mingcai SHEN
 */
public class Assert {


    public static void equals(Object o, Object v, ResultException... e) throws ResultException {
        if (!o.equals(v)) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("values not equals");
        }
    }

    public static void notEquals(Object o, Object v, ResultException... e) throws ResultException {
        if (o.equals(v)) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("values are equals");
        }
    }

    public static void notNull(Object o, ResultException... e) throws ResultException {
        if (null == o) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("null value is not acceptable");
        }
    }

    public static void isNull(Object o, ResultException... e) throws ResultException {
        if (null != o) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("not null value is not acceptable");
        }
    }

    public static void isTrue(boolean f, ResultException... e) throws ResultException {
        if (!f) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("false value is not acceptable");
        }
    }

    public static void notEmpty(Serializable s, ResultException... e) throws ResultException {
        if (null == s || s.toString().isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("empty is not acceptable");
        }
    }

    public static void notEmpty(Map<?, ?> s, ResultException... e) throws ResultException {
        if (null == s || s.isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("empty is not acceptable");
        }
    }

    public static void notEmpty(Collection<?> s, ResultException... e) throws ResultException {
        if (null == s || s.isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("empty is not acceptable");
        }
    }

    public static void isEmpty(Serializable s, ResultException... e) throws ResultException {
        if (null != s && !s.toString().isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("not empty is not acceptable");
        }
    }

    public static void isEmpty(Map<?, ?> s, ResultException... e) throws ResultException {
        if (null != s && !s.isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("not empty is not acceptable");
        }
    }

    public static void isEmpty(Collection<?> s, ResultException... e) throws ResultException {
        if (null != s && !s.isEmpty()) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("not empty is not acceptable");
        }
    }

    public static void lessThan(Integer s, Integer v, ResultException... e) throws ResultException {
        if (s >= v) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value can not be greater than " + v);
        }
    }

    public static void lessThan(Long s, Long v, ResultException... e) throws ResultException {
        if (s >= v) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value can not be greater than " + v);
        }
    }

    public static void greaterThan(Integer s, Integer v, ResultException... e) throws ResultException {
        if (s <= v) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value can not be less than " + v);
        }
    }

    public static void greaterThan(Long s, Long v, ResultException... e) throws ResultException {
        if (s <= v) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value can not be less than " + v);
        }
    }


    public static void between(Integer v, Integer begin, Integer end, ResultException... e) throws ResultException {
        if (v < begin || v > end) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value not between " + begin + "," + end);
        }
    }

    public static void between(Long v, Long begin, Long end, ResultException... e) throws ResultException {
        if (v < begin || v > end) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value not between " + begin + "," + end);
        }
    }

    public static void notBetween(Integer v, Integer begin, Integer end, ResultException... e) throws ResultException {
        if (v >= begin && v <= end) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value is between " + begin + "," + end);
        }
    }

    public static void notBetween(Long v, Long begin, Long end, ResultException... e) throws ResultException {
        if (v >= begin && v <= end) {
            throw e.length > 0 ? e[0] : ResultException.badRequest("value is between " + begin + "," + end);
        }
    }

}
