package resources;

import javax.swing.ImageIcon;
/**
 * 
 * @author Zhancheng Gan
 *
 */

public enum PlayerResources {
	
	Up("Player_10.png"),
	Down("Player_1.png"),
	Left("Player_4.png"),
	Right("Player_7.png");
	
	
	public final ImageIcon image;
	
	PlayerResources(String resourceName) {
		try {
			String path = "/Player/"+resourceName;
			image = new ImageIcon(PlayerResources.class.getResource(path));
		} catch (Exception e) {
			throw new Error(e);
		}
	}
}
