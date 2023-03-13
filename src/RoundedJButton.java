import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedJButton extends JButton {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 30;

    public RoundedJButton(String text) {
        super(text);
        setUI(new RoundedButtonUI());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    private static class RoundedButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();
            int x = 0;
            int y = 0;
            int w = b.getWidth();
            int h = b.getHeight();
            int arc = h / 2; // set the arc height to be the same as the button height for fully rounded
                             // corners
            if (model.isPressed()) {
                // if the button is pressed, move the drawing coordinates down and to the right
                x = 1;
                y = 1;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(b.getBackground());
            g2.fillRoundRect(x, y, w - 1, h - 1, arc, arc); // decrease the width and height by 1 to remove the gaps
            g2.setColor(b.getForeground());
            g2.drawRoundRect(x + 1, y + 1, w - 3, h - 3, arc, arc); // decrease the width and height by 1 to remove the
                                                                    // gaps
            g2.dispose();
            super.paint(g, c);
        }
    }
}