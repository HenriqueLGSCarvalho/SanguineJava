package sanguine.view.graphical.view.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import sanguine.controller.FeaturesListener;

/**
 * This class is responsible for listening for clicks on the keyboard.
 * Relevant Keybinds:
 * - C: Confirm Move
 * - P: Pass Turn
 */
public class KeyboardPressListener extends KeyAdapter {

  private final FeaturesListener listener;

  /**
   * Represents an instance of this KeyListener.
   *
   * @param listener the Listener
   */
  public KeyboardPressListener(FeaturesListener listener) {
    this.listener = listener;
  }

  // Whenever a key is pressed, perform whatever this method demands
  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();

    // If pressed key is C, then confirm move
    if (keyCode == KeyEvent.VK_C) {
      listener.onPressConfirmMove();
    }
    // If press key is P, then pass turn
    if (keyCode == KeyEvent.VK_P) {
      listener.onPressConfirmPass();
    }

  }
}
