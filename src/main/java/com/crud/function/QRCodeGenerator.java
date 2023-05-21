package com.crud.function;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import javax.imageio.ImageIO;


public class QRCodeGenerator {
	public static byte[] generateQRCode(String content, int width, int height) {
        try {
            // Set QR code encoding parameters
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Generate QR code matrix
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // Create a BufferedImage to hold the QR code image
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Iterate through each pixel in the QR code matrix and set the corresponding pixel in the BufferedImage
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int rgbColor = bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF; // Black or White color
                    qrCodeImage.setRGB(x, y, rgbColor);
                }
            }

            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            return baos.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
