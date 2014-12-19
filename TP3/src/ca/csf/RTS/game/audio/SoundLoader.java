package ca.csf.RTS.game.audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.audio.SoundBuffer;

public class SoundLoader {

	private static final String RESSOURCE_SOUNDLIST_PATH = "./ressource/soundlist.txt";
	private static ArrayList<SoundBuffer> soundBufferUnitsList = new ArrayList<SoundBuffer>();
	private static ArrayList<SoundBuffer> soundBufferEventsList = new ArrayList<SoundBuffer>();
	private static ArrayList<SoundBuffer> soundBufferAmbientList = new ArrayList<SoundBuffer>();
	private static ArrayList<SoundBuffer> soundBufferCombatList = new ArrayList<SoundBuffer>();

	public static void initialize() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(new File(RESSOURCE_SOUNDLIST_PATH)));
			String line;
			int temp = 1;
			SoundBuffer soundBuffer = new SoundBuffer();

			while ((line = read.readLine()) != null) {
				BufferedReader reader = new BufferedReader(new FileReader(new File(line)));
				String lineReader;

				while ((lineReader = reader.readLine()) != null) {
					soundBuffer.loadFromFile(Paths.get(lineReader));

					switch (temp) {
					case 1:
						soundBufferUnitsList.add(soundBuffer);
						break;
					case 2:
						soundBufferEventsList.add(soundBuffer);
						break;
					case 3:
						soundBufferCombatList.add(soundBuffer);
						break;
					case 4:
						soundBufferAmbientList.add(soundBuffer);
						break;
					}

					soundBuffer = new SoundBuffer();
				}
				temp++;
				reader.close();
			}

			read.close();

		} catch (Exception e) {

		}
	}

	public static SoundBuffer getUnit(int unitID, int soundType) {
		// Loads the sound related to the unit and the action this unit is doing.
		// Randomized in order to not always hear the same sound.
		// Type: 0 = Ready, 1 = Dead, 2-5 = What, 6-9 = Acknowledge, 10-13 = Attack
		// soundType value: 0 = Ready, 1 = Dead, 2 = What, 3 = Acknowledge, 4 = Attack
		switch (soundType) {
		case 0:
		case 1:
			break;
		case 2:
			soundType = (int) (Math.random() * 4) + 2;
			break;
		case 3:
			soundType = (int) (Math.random() * 4) + 6;
			break;
		case 4:
			soundType = (int) (Math.random() * 4) + 10;
			break;
		}

		// Each unit owns 14 different sounds.
		soundType += unitID * 14;

		return soundBufferUnitsList.get(soundType);
	}

	public static SoundBuffer getCombat(int typeOne, int typeTwo) {
		// TODO randomize soundType SoundType + 1 to 4 = range aka 4 sounds per type
		// Loads the sound related to the type of attack versus the type of armor.

		return soundBufferCombatList.get(typeOne);
	}

	public static SoundBuffer getEvent(int eventType) {
		// Simply loads the sound related to the error
		return soundBufferEventsList.get(eventType);
	}

}
