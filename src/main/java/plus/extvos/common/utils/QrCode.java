package plus.extvos.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mingcai SHEN
 */
public class QrCode {
    public static class Generator {
        private int size = 128;
        private int bgColor = 0xFFFFFFFF;
        private int frontColor = 0xFF000000;
        private String format = "png";
        private String contents;

        private BufferedImage logo;

        private Generator() {
            this.logo = null;
        }

        public Generator size(int s) {
            size = s;
            return this;
        }

        public Generator color(int c) {
            frontColor = c;
            return this;
        }

        public Generator background(int c) {
            bgColor = c;
            return this;
        }

        public Generator format(String f) {
            format = f;
            return this;
        }

        public Generator content(String content) {
            contents = content;
            return this;
        }

        public Generator logo(String filename) throws IOException {
            this.logo = ImageIO.read(new File(filename));
            return this;
        }

        public Generator logo(File file) throws IOException {
            this.logo = ImageIO.read(file);
            return this;
        }

        public BufferedImage bufferedImage() throws Exception {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 1);
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // Create a qr code with the url as content and a size of WxH px
            bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            MatrixToImageConfig matrixConfig = new MatrixToImageConfig(
                    frontColor, bgColor);
            // Load QR image
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixConfig);

            if (this.logo != null) {
                qrImage = combineLogo(qrImage, logo);
            }

            // Initialize combined image
//            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = (Graphics2D) combined.getGraphics();

            // Write QR code to new image at position 0/0
//            g.drawImage(qrImage, 0, 0, null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            return qrImage;
        }

        public boolean write(OutputStream out) throws Exception {
            return ImageIO.write(bufferedImage(), format, out);
        }
    }

    public static BufferedImage combineLogo(BufferedImage matrixImage, BufferedImage logoImage) {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        //开始绘制图片
//        g2.setColor(Color.white);
//        g2.drawRect(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5);
        g2.drawImage(logoImage, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, Color.WHITE, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
        g2.setColor(Color.white);
//        g2.fillRoundRect(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
//        g2.setColor(Color.black);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    public static Generator logo(String f) throws IOException {
        return new Generator().logo(f);
    }

    public static Generator logo(File f) throws IOException {
        return new Generator().logo(f);
    }

    public static Generator format(String f) {
        return new Generator().format(f);
    }

    public static Generator content(String f) {
        return new Generator().content(f);
    }

    public static Generator size(int s) {
        return new Generator().size(s);
    }

    public static Generator color(int s) {
        return new Generator().color(s);
    }

    public static Generator background(int s) {
        return new Generator().background(s);
    }

}
