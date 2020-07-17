package com.infraleap.playground.scribbler14;

import com.vaadin.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RGBImageSource implements StreamResource.StreamSource {

    private final int width;
    private final int height;
    private final int[] rgba;

    RGBImageSource(int width, int height, int[] rgba){
        this.width = width;
        this.height = height;
        this.rgba = rgba;
    }

    // This method generates the stream contents
    public InputStream getStream () {
        // Create an image
        BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D drawable = image.createGraphics();

        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                int pos = 4 * (i + j * width);
                int r = rgba[pos];
                int g = rgba[pos+1];
                int b = rgba[pos+2];
                int a = rgba[pos+3];

                drawable.setColor(new Color(r, g, b, a));
                drawable.drawRect(i, j, 1,1);
            }
        }

        try {
            // Write the image to a buffer
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(imagebuffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
