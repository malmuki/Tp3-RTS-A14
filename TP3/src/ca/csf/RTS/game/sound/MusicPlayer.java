
package ca.csf.RTS.game.sound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import org.jsfml.audio.Music;
import org.jsfml.audio.SoundSource.Status;
import org.jsfml.system.Time;


public class MusicPlayer {
	
	Music music = new Music();
	Music[] musicList = new Music[100];
	
	int playlistMax = 0;
	int playlistPlayed = 0;
	
	public MusicPlayer(){
		//Music list
		try {
			BufferedReader read = new BufferedReader(new FileReader(new File("./ressource/musiclist.txt")));
			String line;
			Music musicBuffer = new Music();
			
			while((line = read.readLine()) != null){
				musicBuffer.openFromFile(Paths.get(line));
				musicList[playlistMax] = musicBuffer;
				musicBuffer = new Music();
				playlistMax++;
			}
			
			read.close();
			
		} catch (Exception e) {

		}
		
	}
	
	public void playMusic(int song){

	music = musicList[song];
	// Change some parameters
	music.setVolume(10);
	music.setPlayingOffset(Time.getSeconds(120));
	music.setLoop(false);
	
	// Play it
	music.play();
	}
	
	public void musicPlaylist(){
		if(music.getStatus() ==  Status.STOPPED){
			if(playlistPlayed >= playlistMax){
				this.playMusic(0);
				playlistPlayed = 0;
			} else {
				this.playMusic(playlistPlayed);
				playlistPlayed++;
			}
		}
	}
}
