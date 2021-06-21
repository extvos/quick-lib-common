package plus.extvos.common.utils;


/**
 * @author Mingcai SHEN
 */
public class PrimitiveConvert {
    public static class Convertor {
        private String src;

        protected Convertor(String s) {
            src = s;
        }

        private byte toByte() {
            return Byte.parseByte(src);
        }

        private short toShort() {
            return Short.parseShort(src);
        }

        private int toInt() {
            return Integer.parseInt(src);
        }

        private long toLong() {
            return Long.parseLong(src);
        }

        private float toFloat() {
            return Float.parseFloat(src);
        }

        private double toDouble() {
            return Double.parseDouble(src);
        }

        private boolean toBoolean() {
            return "TRUE".equalsIgnoreCase(src) || "T".equalsIgnoreCase(src) || "YES".equalsIgnoreCase(src) || "Y".equalsIgnoreCase(src);
        }

        public Object to(Class<?> cls) {
            if (cls.equals(src.getClass())) {
                return src;
            }
            if (cls.equals(Byte.class) || cls.equals(byte.class)) {
                return toByte();
            }
            if (cls.equals(Short.class) || cls.equals(short.class)) {
                return toShort();
            }
            if (cls.equals(Integer.class) || cls.equals(int.class)) {
                return toInt();
            }
            if (cls.equals(Long.class) || cls.equals(long.class)) {
                return toLong();
            }
            if (cls.equals(Float.class) || cls.equals(float.class)) {
                return toFloat();
            }
            if (cls.equals(Double.class) || cls.equals(double.class)) {
                return toDouble();
            }
            if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
                return toBoolean();
            }
            return null;
        }
    }

    public static Convertor from(String src) {
        return new Convertor(src);
    }
}
