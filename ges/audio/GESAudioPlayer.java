package ges.audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class GESAudioPlayer implements Runnable {

    AudioInputStream audioInputStream;
    AudioFormat audioFormat;
    SourceDataLine currentLine;
    DataLine.Info dataLineInfo;
    Thread audioThread;
    volatile boolean stopRead;
    String audioSoundFileName;

    public GESAudioPlayer(){
        audioThread = new Thread(this);
    }

    public void loadFormFile(String fileName) throws IOException, UnsupportedAudioFileException {
        this.audioSoundFileName = fileName;
        audioInputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResourceAsStream(fileName));
        audioFormat = audioInputStream.getFormat();
        dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

    }

    public void pause(){
        stopRead = true;
        try {
            audioThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException, UnsupportedAudioFileException {
        pause();
        loadFormFile(audioSoundFileName);
    }

    public void play(){
        if(audioThread != null) {
            audioThread.interrupt();
            audioThread = new Thread(this);
            audioThread.start();
        }
    }

    public boolean isStopped(){
        return stopRead;
    }

    public Line getLine(){
        return currentLine;
    }

    public Thread getAudioThread(){
        return audioThread;
    }

    protected void playSound() throws LineUnavailableException, IOException {
        stopRead = false;
        currentLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        currentLine.open(audioFormat);
        currentLine.start();

        byte[] bytes = new byte[1024];
        while ((audioInputStream.read(bytes, 0, bytes.length) >= 0 && !stopRead)){
            currentLine.write(bytes, 0, bytes.length);
        }
        currentLine.close();
    }

    @Override
    public void run() {
        try {
            if(dataLineInfo != null) {
                playSound();
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        } finally {
            if(currentLine != null && currentLine.isOpen()){
                currentLine.close();
            }
        }
    }
}
