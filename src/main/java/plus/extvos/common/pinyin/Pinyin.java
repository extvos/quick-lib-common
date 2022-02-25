package plus.extvos.common.pinyin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 将汉字翻译为拼音, 通过『中文分词逆向最大匹配法』匹配多音词
 * Created by rex on 2015/4/27.
 */
public class Pinyin {
    private String charsContent = "";
    private String wordsContent = "";
    private final Map<String, String> wordsDict = new HashMap<String, String>();

    private final int WordMaxLen = 10;
    private final char SEP = ',';
    private final char REP = 7;

    public Pinyin() throws PinyinException {
        try {
            this.loadContent();
        } catch (IOException e) {
            throw new PinyinException("load words library failed.");
        }

        this.parseChars();
        this.parseWords();
    }

    private void loadContent() throws IOException {
        InputStream isChars = this.getClass().getResourceAsStream("chars.csv");
        assert isChars != null;
        this.charsContent = this.inputStream2String(isChars).trim();

        InputStream isWords = this.getClass().getResourceAsStream("words.csv");
        assert isWords != null;
        this.wordsContent = this.inputStream2String(isWords).trim();
    }

    private String inputStream2String(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    private void parseChars() {
        String[] charsArr = this.charsContent.split("\n");
        for (String charStr : charsArr) {
            String[] charEle = charStr.split(",");
            this.wordsDict.put(charEle[0].trim(), SEP + charEle[1].trim());
        }
    }

    private void parseWords() {
        String[] wordsArr = this.wordsContent.split("\n");
        for (String wordStr : wordsArr) {
            String[] wordEle = wordStr.split(",");
            StringBuilder pinyin = new StringBuilder();

            for (int i = 1; i < wordEle.length; i++) {
                pinyin.append(SEP).append(wordEle[i]);
            }

            this.wordsDict.put(wordEle[0].trim(), pinyin.toString().trim());
        }
    }

    private String translatePinyin(String content) {
        String cont = content;
        cont = cont.replaceAll(this.SEP + "", this.REP + "");

        StringBuilder result = new StringBuilder();

        int len = 0;
        int tailStartIdx = 0;
        int tailEndIdx = 0;
        int leftEndIdx = 0;
        String tail = "";
        StringBuilder left = new StringBuilder(cont);

        while (left.length() > 0) {
            // outer
            len = left.length();
            int cutLen = Math.min(len, this.WordMaxLen);
            tailStartIdx = len - cutLen;
            tailEndIdx = len;
            leftEndIdx = tailStartIdx;
            tail = cont.substring(tailStartIdx, tailEndIdx);
            left = new StringBuilder(cont.substring(0, leftEndIdx));

            while (tail.length() > 1) {
                String value = this.wordsDict.get(tail);
                if (value != null && !"".equals(value)) {
                    result.insert(0, value);
                    tail = "";
                    break;
                } else {
                    left.append(tail.charAt(0));
                    tail = tail.substring(1, tail.length());
                }
            }
            if (tail.length() > 0) {
                String value = this.wordsDict.get(tail);
                value = (value != null) ? value : SEP + tail;
                result.insert(0, value);
                tail = "";
            }
        }

        if (result.charAt(0) == this.SEP) {
            result = new StringBuilder(result.substring(1, result.length()));
        }


        return result.toString();
    }

    /**
     * 拼音，带分隔符
     *
     * @param content 内容
     * @param sep     分隔符
     * @return string with translated content and seperator.
     */
    public String translateWithSep(String content, String sep) {
        String result = this.translatePinyin(content);
        result = result.replaceAll(this.REP + "", sep);
        return result;
    }

    /**
     * 拼音，带分隔符,默认逗号
     *
     * @param content 内容
     * @return string with translated content
     */
    public String translateWithSep(String content) {
        String result = this.translatePinyin(content);
        result = result.replaceAll(this.REP + "", ",");
        return result;
    }

    /**
     * 拼音数组，带分隔符
     *
     * @param content string
     * @return string array of translated content
     */
    public String[] translateInArray(String content) {
        String result = this.translatePinyin(content);
        String[] resultArray = result.split(this.SEP + "");
        for (int idx = 0; idx < resultArray.length; idx++) {
            String element = resultArray[idx];
            resultArray[idx] = element.replace(this.REP, this.SEP);
        }
        return resultArray;
    }

    /**
     * 将汉字翻译成拼音
     *
     * @param content 内容
     * @return string of translated content
     */
    public String translate(String content) {
        String result = this.translatePinyin(content);
        result = result.replaceAll(SEP + "", "");
        result = result.replaceAll(this.REP + "", this.SEP + "");
        return result;
    }

    /**
     * 除去音调
     *
     * @param contents
     * @return string with contents un-marked
     */
    private String unMark(String contents) {
        String content = contents;

        content = content.replaceAll("ā", "a");
        content = content.replaceAll("á", "a");
        content = content.replaceAll("ǎ", "a");
        content = content.replaceAll("à", "a");

        content = content.replaceAll("ō", "o");
        content = content.replaceAll("ó", "o");
        content = content.replaceAll("ǒ", "o");
        content = content.replaceAll("ò", "o");

        content = content.replaceAll("ē", "e");
        content = content.replaceAll("é", "e");
        content = content.replaceAll("ě", "e");
        content = content.replaceAll("è", "e");

        content = content.replaceAll("ī", "i");
        content = content.replaceAll("í", "i");
        content = content.replaceAll("ǐ", "i");
        content = content.replaceAll("ì", "i");

        content = content.replaceAll("ū", "u");
        content = content.replaceAll("ú", "u");
        content = content.replaceAll("ǔ", "u");
        content = content.replaceAll("ù", "u");

        content = content.replaceAll("ǖ", "ü");
        content = content.replaceAll("ǘ", "ü");
        content = content.replaceAll("ǚ", "ü");
        content = content.replaceAll("ǜ", "ü");

        return content;
    }

    /**
     * 不带音调拼音，带分隔符
     *
     * @param content 内容
     * @param sep     分割符
     * @return string of translated content without marks.
     */
    public String translateWithSepNoMark(String content, String sep) {
        String result = this.translateWithSep(content, sep);
        return this.unMark(result);
    }

    /**
     * 不带音调拼音，带分隔符, 默认逗号
     *
     * @param content string
     * @return string of translated content
     */
    public String translateWithSepNoMark(String content) {
        String result = this.translateWithSep(content, ",");
        return this.unMark(result);
    }

    /**
     * 不带音调拼音
     *
     * @param content string
     * @return string of translated without marks.
     */
    public String translateNoMark(String content) {
        String result = this.translate(content);
        return this.unMark(result);
    }

    /**
     * 不带音调拼音数组，带分隔符
     *
     * @param content string
     * @return string array of translated content
     */
    public String[] translateInArrayNoMark(String content) {
        String result = this.translatePinyin(content);
        String[] resultArray = result.split(this.SEP + "");
        for (int idx = 0; idx < resultArray.length; idx++) {
            String element = resultArray[idx];
            element = this.unMark(element);
            resultArray[idx] = element.replace(this.REP, this.SEP);
        }
        return resultArray;
    }

    /**
     * 翻译为拼音首字母
     *
     * @param content string
     * @return string of translated content in first character.
     */
    public String translateFirstChar(String content) {
        String[] result = this.translateInArray(content);
        StringBuilder chars = new StringBuilder();
        for (String element : result) {
            chars.append(element.charAt(0));
        }
        return chars.toString();
    }


    public static void main(String[] args) {
        try {
            Pinyin py = new Pinyin();
            String s1 = py.translate("南鹞北鹰");
            System.out.println("  >> " + s1);
            System.out.println("  >> " + py.translateNoMark("南阳市"));
            System.out.println("  >> " + py.translateWithSep("南阳市"));
            System.out.println("  >> " + py.translateNoMark("南阳"));
            System.out.println("  >> " + py.translateFirstChar("南阳市"));
            System.out.println("  >> " + py.translateFirstChar("南阳"));

            for (String arg : py.translateInArray("中国人民解放军")) {
                System.out.println("  >> " + arg);
            }
            for (String arg : py.translateInArrayNoMark("中国人民解放军")) {
                System.out.println("  >> " + arg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
