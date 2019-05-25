package resources;

import java.applet.*;

/**
 * 
 * @author Zhancheng Gan
 */
public enum SoundResources {
	//
	Break("break.wav"),
	Meun("meunBGM.wav"),
	FightBGM("fightBGM.wav"),
	Fight1("fight1.wav"),
	Fight2("fight2.wav");

	public final AudioClip sound;

	SoundResources(String resourceName) {
		try {
			sound = java.applet.Applet.newAudioClip(SoundResources.class.getResource(resourceName));
		} catch (Exception e) {
			throw new Error(e);
		}
	}

}
