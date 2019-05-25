package resources;
import java.applet.*;
	
	public enum SoundResources {
		//
		Break("break.wav");
		
		public final AudioClip sound;
		
		SoundResources(String resourceName) {
			try {
				sound = java.applet.Applet.newAudioClip(SoundResources.class.getResource(resourceName));
			} catch (Exception e) {
				throw new Error(e);
			}
		}

}

