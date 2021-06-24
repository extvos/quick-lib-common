/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package plus.extvos.common.utils;

import plus.extvos.common.Validator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mingcai SHEN
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends cn.hutool.core.util.StrUtil {

    private static final char SEPARATOR = '_';


    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * Split string to list
     *
     * @param s         source string
     * @param splitters splitters
     * @return list of string
     */
    public static String[] deepSplit(String s, String... splitters) {
        if (splitters.length < 1) {
            return new String[]{s};
        } else {
            String[] ss = s.split(splitters[0]);
            if (splitters.length > 1) {
                List<String> ret = new LinkedList<>();
                String[] ns = new String[splitters.length - 1];
                System.arraycopy(splitters, 1, ns, 0, (splitters.length - 1));
                for (String x : ss) {
                    String[] xs = deepSplit(x, ns);
                    ret.addAll(Arrays.asList(xs));
                }
                return ret.toArray(new String[]{});
            } else {
                return ss;
            }
        }
    }

    /**
     * Build whole url from parts
     *
     * @param base  base server url maybe
     * @param parts strings
     * @return whole url string
     */
    public static String buildUrl(String base, String... parts) {
        String us = "";

        if (Validator.notEmpty(base)) {
            us = strip(base, "", "/");
        }
        for (String p : parts) {
            if (Validator.isEmpty(p)) {
                continue;
            }
            us = us + addPrefixIfNot(p, "/");
        }
        return us;
    }
}
