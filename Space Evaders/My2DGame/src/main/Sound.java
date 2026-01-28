package main;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// CLASSE DO SOM

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	 // ARMAZENA OS TEMPOS Q O EFEITO SONORO FOI TOCADO
    private Map<Integer, Long> lastPlayed = new HashMap<>();
    private long soundDelay = 400; // DELAY, MS 
	
    
    // PREENCHENDO O ARRAY
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/backgroundMusic.wav");
		soundURL[1] = getClass().getResource("/sound/repairKitSound.wav");
		soundURL[2] = getClass().getResource("/sound/gasGaloon.wav");
		soundURL[3] = getClass().getResource("/sound/door.wav");
		soundURL[4] = getClass().getResource("/sound/doorClosed.wav");
		soundURL[5] = getClass().getResource("/sound/victory.wav");
		soundURL[6] = getClass().getResource("/sound/fail.wav");
		soundURL[7] = getClass().getResource("/sound/acid.wav");
	}
	
	// MÉTODOS PARA TOCAR
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
	public void playWithDelay(int i) {
        long now = System.currentTimeMillis();
        long lastTime = lastPlayed.getOrDefault(i, 0L);

        if (now - lastTime >= soundDelay) { 
            setFile(i);
            play();
            lastPlayed.put(i, now);
        }
	}
}
