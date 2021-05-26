package org.extvos.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenmc
 */
public class QrCode {
    public static class Generator {
        private int size = 128;
        private int bgColor = 0xFFFFFFFF;
        private int frontColor = 0xFF000000;
        private String format = "png";
        private String contents;

        private Generator() {}

        public Generator size(int s){
            size = s;
            return this;
        }
        public Generator color(int c){
            frontColor = c;
            return this;
        }
        public Generator background(int c){
            bgColor = c;
            return this;
        }
        public Generator format(String f){
            format = f;
            return this;
        }

        public Generator content(String content) {
            contents = content;
            return this;
        }

        public BufferedImage bufferedImage() throws Exception {
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // Create a qr code with the url as content and a size of WxH px
            bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            MatrixToImageConfig matrixConfig = new MatrixToImageConfig(
                frontColor, bgColor);
            // Load QR image
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixConfig);

            // Initialize combined image
            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();

            // Write QR code to new image at position 0/0
            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            return combined;
        }

        public boolean write(OutputStream out) throws Exception {
            return ImageIO.write(bufferedImage(), format, out);
        }
    }

    public static Generator format(String f){ return new Generator().format(f); }
    public static Generator content(String f){ return new Generator().content(f); }
    public static Generator size(int s){ return new Generator().size(s); }
    public static Generator color(int s){ return new Generator().color(s); }
    public static Generator background(int s){ return new Generator().background(s); }

}
