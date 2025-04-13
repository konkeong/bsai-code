package ch05;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class WebpToPngConverter {

    private WebpToPngConverter() {
        // intentionally left blank
    }

    public static void convertWebpToPng(BufferedImage webpImage, String pngPath) {
        try {
            ImageIO.write(webpImage, "png", new File(pngPath));
            System.out.println("Converted to " + pngPath);
        } catch (IOException ex) {
            System.out.println("Failed to convert WEBP to PNG: " + pngPath);
            ex.printStackTrace(System.out);
        }
    }

}
