package org.extvos.common.utils;

import cn.hutool.core.util.HexUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author shenmc
 */
public class QuickHash {
    private MessageDigest digest;


    public static QuickHash md5() {
        return new QuickHash("MD5");
    }

    public static QuickHash sha1() {
        return new QuickHash("SHA1");
    }

    public static QuickHash sha256() {
        return new QuickHash("SHA-256");
    }

    public static QuickHash sha512() {
        return new QuickHash("SHA-512");
    }

    private QuickHash(String algorithm) {
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public QuickHash random() {
        Random rd = new Random(System.nanoTime());
        byte[] bf = new byte[16];
        rd.nextBytes(bf);
        this.hash(bf);
        return this;
    }

    public QuickHash hash(byte[] in) {
        if (null == digest) {
            return this;
        }
        digest.reset();
        digest.update(in);
        return this;
    }

    public QuickHash hash(String s) {
        return hash(s.getBytes(StandardCharsets.UTF_8));
    }

    public QuickHash hash(Object o) {
        return hash(o.toString());
    }

    public QuickHash hash(Reader reader) throws IOException {
        if (null == digest) {
            return this;
        }
        digest.reset();
        int n = -1;
        char[] buf = new char[1024];
        StringBuilder builder = new StringBuilder();

        for (n = reader.read(buf); n > 0; ) {
            builder.append(buf, 0, n);
        }
        digest.update(builder.toString().getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public QuickHash hash(File file) throws IOException {
        return hash(new FileReader(file));
    }

    public String hex() {
        if (null == digest) {
            return "";
        }
        return HexUtil.encodeHexStr(digest.digest());
    }

    public byte[] bytes() {
        if (null == digest) {
            return new byte[0];
        }
        return digest.digest();
    }

    public String[] hexSegments(int... ns) {
        if (null == digest) {
            return new String[0];
        }
        List<String> ls = new LinkedList<String>();
        if (ns.length < 1) {
            return new String[]{hex()};
        } else {
            String s = hex();
            int pos = 0;
            for (int n : ns) {
                if ((pos + n) >= s.length()) {
                    throw new StringIndexOutOfBoundsException();
                } else {
                    ls.add(s.substring(pos, pos + n));
                    pos += n;
                }
            }
            if (pos < s.length()) {
                ls.add(s.substring(pos, s.length()));
            }
            return ls.toArray(new String[0]);
        }
    }

    public static void main(String[] args) {
        String src = "1231235413463457456874569856967809";
        System.out.println("md5    > " + QuickHash.md5().hash(src).hex());
        System.out.println("sha1   > " + QuickHash.sha1().hash(src).hex());
        System.out.println("sha256 > " + QuickHash.sha256().hash(src).hex());
        System.out.println("sha512 > " + QuickHash.sha512().hash(src).hex());
        System.out.println("> " + String.join(",", QuickHash.sha1().hash(src).hexSegments(2, 4, 8)));
        System.out.println("> " + String.join(",", QuickHash.sha1().hash(src).hexSegments()));
    }
}
