package sanguine.view.graphical.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This class is responsible for the logic of an Error Message screen.
 *
 * <p>If an error is thrown, this panel should become visible. Otherwise, it should always
 * be invisible.
 */
public class ErrorMessagePanel extends JPanel {

  private final ErrorMessagePanel errorPanel;

  /**
   * Represents an instance of the error message panel.
   * Sets up the panel so it is in a proper state when called.
   */
  public ErrorMessagePanel() {
    errorPanel = this;

    setLayout(new BorderLayout());
    setBackground(Color.BLACK);

    JLabel errorMessage = new JLabel("Invalid Move! Press 'ok' to try again: ",
            SwingConstants.CENTER);
    errorMessage.setForeground(Color.WHITE);
    errorMessage.setFont(new Font("Arial", Font.BOLD, 18));

    JButton okButton = new JButton("OK");
    okButton.addActionListener(new HideErrorMessageScreenListener());

    this.add(errorMessage, BorderLayout.CENTER);
    this.add(okButton, BorderLayout.SOUTH);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  /**
   * This class is responsible for listening for a button click.
   * Intended to be used with the ok button in ErrorMessagePanel
   */
  private class HideErrorMessageScreenListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      errorPanel.setVisible(false);

      Window originalFrame = SwingUtilities.getWindowAncestor(errorPanel);
      originalFrame.requestFocusInWindow();
    }
  }
}
