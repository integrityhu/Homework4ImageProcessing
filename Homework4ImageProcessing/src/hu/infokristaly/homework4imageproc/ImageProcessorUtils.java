package hu.infokristaly.homework4imageproc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImageProcessorUtils {
    public static Image getImageFromResourceAsStream(String fileName, Class<?> classForPath) throws IOException {
        Image result = ImageIO.read(classForPath.getResourceAsStream(fileName));
        return result;
    }

    public static Image getImage(Path fileName) throws IOException {
        Image result = ImageIO.read(fileName.toFile());
        return result;
    }

    public static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if (src.getWidth() > src.getHeight()) {
            factor = ((double) src.getHeight() / (double) src.getWidth());
            finalh = (int) (finalw * factor);
        } else {
            factor = ((double) src.getWidth() / (double) src.getHeight());
            finalw = (int) (finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }

    public static BufferedImage getScaledImageWithImgscalr(BufferedImage src, int w, int h) {
        BufferedImage resizedImg;
        if (src.getWidth() > src.getHeight()) {
            resizedImg = Scalr.resize(src, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, w, h, Scalr.OP_ANTIALIAS);
            if (resizedImg.getHeight() > h) {
                resizedImg = Scalr.resize(resizedImg, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_HEIGHT, w, h, Scalr.OP_ANTIALIAS);
            }
        } else {
            resizedImg = Scalr.resize(src, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_HEIGHT, w, h, Scalr.OP_ANTIALIAS);
            if (resizedImg.getWidth() > w) {
                resizedImg = Scalr.resize(resizedImg, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, w, h, Scalr.OP_ANTIALIAS);
            }
        }

        return resizedImg;
    }
    
    public static void saveBufferedImage(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, "jpg", file);
    }
}
