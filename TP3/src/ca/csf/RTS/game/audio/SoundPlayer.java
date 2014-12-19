package ca.csf.RTS.game.audio;

import org.jsfml.audio.Sound;

public class SoundPlayer {
	
	//This class uses the class SoundLoader to load the SoundBuffers and
	//play sounds accordingly.

	private static Sound sound = new Sound();
	
	public static void initialize(){
		sound.setLoop(false);
		sound.setAttenuation(10.0f);
		sound.setMinDistance(1.0f);
	}
	
	public static void playSound(int unitID, int soundType){
		//Here unitID contains the unit ID and soundType refers to the sound type.
		//Example: position =  12, 71  unitID = 1 (Footman) soundType = 3 (Acknowledge)

		sound.setBuffer(SoundLoader.getUnit(unitID, soundType));
		sound.setPosition(0, 0, 0);
		sound.play();
		
	}
	
	public static void playSound(int eventType){
		//Here we get an warning type or number in order to play the sound related
		//to this error.
		
		sound.setBuffer(SoundLoader.getEvent(eventType));
		sound.setPosition(0, 0, 0);
		sound.play();
	}
	
	
}
