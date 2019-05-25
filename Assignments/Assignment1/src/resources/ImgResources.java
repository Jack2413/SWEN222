package resources;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum ImgResources {
	//
	White("White.png"), Black("Black.png"),
	BackGroud("BackGroud.jpg");
	
	public final Image img;

	ImgResources(String resourceName) {
		try {
			img = ImageIO.read(ImgResources.class.getResource(resourceName));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
}
