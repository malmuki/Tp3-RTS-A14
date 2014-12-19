package ca.csf.RTS.game.audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.audio.Music;
import org.jsfml.audio.SoundSource.Status;

public class MusicPlayer {

	private static Music music = new Music();
	private static ArrayList<Music> musicList = new ArrayList<Music>();

	private static int playlistMax = 0;
	private static int playlistPlayed = 0;

	public static void initialize() {
		// Music list
		try {
			BufferedReader read = new BufferedReader(new FileReader(new File("./ressource/musiclist.txt")));
			String line = new String();
			Music musicBuffer = new Music();

			while ((line = read.readLine()) != null) {
				musicBuffer.openFromFile(Paths.get(line));
				musicList.add(musicBuffer);
				musicBuffer = new Music();
				playlistMax++;
			}

			read.close();

		} catch (Exception e) {

		}

	}

	public static void playMusic(int song) {

		music = musicList.get(song);
		// Change some parameters & make sure it's not already playing
		music.setVolume(15);
		music.setLoop(false);

		// Play it
		music.play();
	}

	public static void musicPlaylist() {
		if (music.getStatus() == Status.STOPPED) {
			if (playlistPlayed >= playlistMax) {
				MusicPlayer.playMusic(0);
				playlistPlayed = 0;
			} else {
				MusicPlayer.playMusic(playlistPlayed);
				playlistPlayed++;
			}
		}
	}

	public static void musicStop() {
		music.stop();
	}

}
