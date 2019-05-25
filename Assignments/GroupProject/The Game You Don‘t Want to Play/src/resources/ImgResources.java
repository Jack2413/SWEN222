package resources;

import java.awt.Image;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Zhancheng Gan
 */

public enum ImgResources {
	//
	fightingBackGroud("fighting.png"),
	equipmentBackGroud("equipmentPanel.png"),
	characterBackGroud("characterPanel.png"),
	dialogBackGroud("dialogPanel.png"),
	dialogBackGroud2("dialogPanel2.png"),
	BackGround("background.jpg");
	public final Image img;

	ImgResources(String resourceName) {
		try {
			img = ImageIO.read(ImgResources.class.getResource(resourceName));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
}
