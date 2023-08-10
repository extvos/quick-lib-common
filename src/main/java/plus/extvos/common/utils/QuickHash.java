package plus.extvos.common.utils;

import cn.hutool.core.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Mingcai SHEN
 */
public class QuickHash {
    private MessageDigest digest;

    private final static Logger log = LoggerFactory.getLogger(QuickHash.class);


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

            log.error(">>", e);
        }
    }

    public QuickHash random() {
        Random rd = new Random(System.nanoTime());
        byte[] bf = new byte[16];
        rd.nextBytes(bf);
        this.hash(bf);
        return this;
    }

    public QuickHash update(byte[] in) {
        if (null != digest) {
            digest.update(in);
        }
        return this;
    }

    public QuickHash update(byte[] in, int off, int len) {
        if (in == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > in.length) || (len < 0) ||
                ((off + len) > in.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return this;
        }
        if (null != digest) {
            digest.update(Arrays.copyOfRange(in, off, len));
        }
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


    public QuickHash hash(InputStream reader) throws IOException {
        if (null == digest) {
            return this;
        }
        digest.reset();
        int len = 0;
        byte[] bytes = new byte[1024 * 100];
        while ((len = reader.read(bytes)) > 0) {
            digest.update(bytes, 0, len);
        }
        return this;
    }

    public QuickHash hash(File file) throws IOException {
        return hash(new FileInputStream(file));
    }

    public String hex() {
        if (null == digest) {
            return "";
        }
        return HexUtil.encodeHexStr(digest.digest());
    }

    public String base64() {
        if (null == digest) {
            return "";
        }
        return Base64.getEncoder().encodeToString(digest.digest());
//        return HexUtil.(digest.digest());
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
        System.out.println("md5    > " + QuickHash.md5().hash(src).hex() + " > " + QuickHash.md5().hash(src).base64());
        System.out.println("sha1   > " + QuickHash.sha1().hash(src).hex() + " > " + QuickHash.sha1().hash(src).base64());
        System.out.println("sha256 > " + QuickHash.sha256().hash(src).hex() + " > " + QuickHash.sha256().hash(src).base64());
        System.out.println("sha512 > " + QuickHash.sha512().hash(src).hex() + " > " + QuickHash.sha512().hash(src).base64());
        System.out.println("> " + String.join(",", QuickHash.sha1().hash(src).hexSegments(2, 4, 8)));
        System.out.println("> " + String.join(",", QuickHash.sha1().hash(src).hexSegments()));
    }
}
