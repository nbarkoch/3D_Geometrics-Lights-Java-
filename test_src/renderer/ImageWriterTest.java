package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    /**
     * Test method for
     * {@link renderer.ImageWriter#ImageWriter(String, double, double, int, int)}.
     * we will consider creating an image with one color for background image and with a second color for grid.
     * In this test we will build a grid of 10x16 squares on the screen (ViewPlane) 1000 x 1600 and a resolution of 500 x 800
     */
    @Test
    void testImageWriterConstructor() {
        ImageWriter image_test = new ImageWriter("image_test", 1600, 1000, 800, 500);
        Color bg = new Color(0, 0, 255);
        Color lines = new Color(250, 0, 0);
        int x_lines = 16;
        int y_lines = 10;
        try {
            for (int i = 0; i < image_test.getNy(); i++) {
                for (int j = 0; j < image_test.getNx(); j++) {
                    if (i % (image_test.getNy() / y_lines) == 0 || j % (image_test.getNx() / x_lines) == 0)
                        image_test.writePixel(j, i, lines.getColor());
                    else
                        image_test.writePixel(j, i, bg.getColor());
                }
            }
        } catch (Exception e) {
            assertTrue(false, "an error occurred while trying to color the pixels");
        }
        image_test.writeToImage();
    }
}
