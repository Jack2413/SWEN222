package resources;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum IconResources {
	
		Info("Info.png");
		
		public final Icon icon;

		IconResources(String resourceName) {
			try {
			icon = new ImageIcon(IconResources.class.getResource(resourceName));
			}catch(Exception e){
				throw new Error(e);
			}
		}
}


