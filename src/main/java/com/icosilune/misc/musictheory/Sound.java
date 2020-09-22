package com.icosilune.misc.musictheory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

public class Sound {
  private static final float SAMPLE_RATE = 11025.0f;
  private static final AudioFormat AUDIO_FORMAT = new AudioFormat(SAMPLE_RATE, 8,  // sample size in bits
      2,  // channels
      true,  // signed
      false  // bigendian
  );

  private static final double FADE = 0.1;

  public static Sound create(Waveform waveform, double length) throws IOException, LineUnavailableException {
    return create(waveform, length, t -> {
      if (t < FADE) {
        return t / FADE;
      } else if (t > length - FADE) {
        return (length - t) / FADE;
      } else {
        return 1.0;
      }
    });
  }

  /**
   * Envelope is a function of t from [0,length)
   */
  public static Sound create(Waveform waveform, double length, Function<Double, Double> envelope)
      throws IOException, LineUnavailableException {
    byte[] tone = new byte[(int) (SAMPLE_RATE * length)];

    for (int i = 0; i < tone.length; i++) {
      double t = (1.0 * i) / SAMPLE_RATE;
      double v = waveform.evaluate(t) * envelope.apply(t);
      v = Math.max(-1, Math.min(1, v));

      tone[i] = (byte) (127.0 * v);
    }

    AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(tone), AUDIO_FORMAT, tone.length);

    Clip clip = AudioSystem.getClip();
    clip.open(ais);
    return new Sound(clip);
  }

  private final Clip clip;

  private Sound(Clip clip) {
    this.clip = clip;
  }

  public void playBlocking() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    clip.addLineListener(event -> {
      if (event.getType().equals(LineEvent.Type.START)) {
        latch.countDown();
      }
    });
    clip.start();
    latch.await();
    while (clip.isRunning()) {
    }
    clip.close();
  }
}
