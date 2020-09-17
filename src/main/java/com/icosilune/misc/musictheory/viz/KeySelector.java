package com.icosilune.misc.musictheory.viz;

import com.icosilune.misc.musictheory.math.Key;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class KeySelector extends JPanel {

  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.add(new KeySelector());
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  interface KeyListener {
    void onHover(Key key, boolean hovering);

    void onClick(Key key);
  }

  private KeyListener keyListener;
  private Key currentKey = Key.major(0);

  public void setKeyListener(KeyListener keyListener) {
    this.keyListener = keyListener;
  }

  KeySelector() {

    setLayout(new GridLayout(2, 13));

    add(new JLabel("maj"));
    for(int i=0;i<12;i++) {
      add(new KeyButton(Key.major(i)));
    }

    add(new JLabel("min"));
    for(int i=0;i<12;i++) {
      add(new KeyButton(Key.minor(i)));
    }
  }

  void setKey(Key key) {
    this.currentKey = key;

    Arrays.stream(getComponents())
        .filter(KeyButton.class::isInstance)
        .map(KeyButton.class::cast)
        .forEach(button -> button.setSelected(button.key == key));
  }

  class KeyButton extends JToggleButton {
    private final Key key;

    KeyButton(Key key) {
      super(key.shortName());
      this.key = key;

      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          if (keyListener != null) {
            keyListener.onHover(key, true);
          }
        }

        @Override
        public void mouseExited(MouseEvent e) {
          if (keyListener != null) {
            keyListener.onHover(key, false);
          }
        }
      });

      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setKey(key);
        }
      });
    }
  }
}
