package hu.infokristaly.homework4imageproc;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageProcessorUtils {
	public Image getImageFromResourceAsStream(String fileName) throws IOException {
		Image result = ImageIO.read(getClass().getResourceAsStream(fileName));
		return result;
	}
}
