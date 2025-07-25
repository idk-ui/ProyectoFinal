//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl.Type;

public class SoundPlayer {
    private static Clip clip;

    public static void play(String soundFileName) {
        stop();

        try {
            URL soundURL = SoundPlayer.class.getResource("/sounds/" + soundFileName);
            if (soundURL == null) {
                System.err.println("Archivo de sonido no encontrado: " + soundFileName);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (clip.isControlSupported(Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
                gainControl.setValue(6.0F);
            }

            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            ((Exception)e).printStackTrace();
        }

    }

    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }

    }
}
