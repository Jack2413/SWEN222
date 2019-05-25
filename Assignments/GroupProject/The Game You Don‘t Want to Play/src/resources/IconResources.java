package resources;

import javax.swing.ImageIcon;

/**
 * 
 * @author Zhancheng Gan
 *
 */
public enum IconResources {
	
		Info("Info.png"),
		Die("die.png");
		
		public final  ImageIcon icon;

		IconResources(String resourceName) {
			try {
			icon = new ImageIcon(IconResources.class.getResource(resourceName));
			}catch(Exception e){
				throw new Error(e);
			}
		}
}


