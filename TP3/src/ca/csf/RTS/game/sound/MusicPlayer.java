package ca.csf.RTS.game.sound;

import java.nio.file.Paths;

import org.jsfml.audio.*;
import org.jsfml.audio.SoundSource.Status;


public class MusicPlayer {
	
	Music music = new Music();
	Music[] musicList = new Music[10];
	
	public MusicPlayer(){
		//Music list
		try {
			Music musicBuffer = new Music();
			musicBuffer.openFromFile(Paths.get("./ressource/wc2all1.ogg"));
			musicList[1] = musicBuffer;
			musicBuffer = new Music();
			
			musicBuffer.openFromFile(Paths.get("./ressource/wc2intro.ogg"));
			musicList[0] = musicBuffer;
			
		} catch (Exception e) {

		}
		
	}
	
	public void playMusic(int song){

	music = musicList[song];
	// Change some parameters
	music.setVolume(50);
	
	// Play it
	music.play();
	}
	
	public void musicPlaylist(){
		if(music.getStatus() ==  Status.STOPPED){
			int temp = 1;
			if(temp > 1){
				this.playMusic(0);
				temp = 1;
			} else {
				this.playMusic(1);
				temp++;
			}
		}
	}
}
