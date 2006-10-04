/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JProgressBar;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;

/**
 * This class extends the JProgressBar with the feature of a centered text
 * indicating the progress in percent.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MyProgressBar extends JProgressBar {

    /** the serialization UID */
    private static final long serialVersionUID = 4062920601245748616L;

    /**
     * The constructor.
     * 
     * @param o
     *        is the orientation of the progress-bar.
     */
    public MyProgressBar(Orientation o) {

        super((o == Orientation.HORIZONTAL)
                ? JProgressBar.HORIZONTAL
                : JProgressBar.VERTICAL, 0, 100);
    }

    /**
     * @see java.awt.Component#paint(java.awt.Graphics)
     * 
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Rectangle bounds = g.getClipBounds();
        String text = getValue() + "%";
        Rectangle textBounds = g.getFontMetrics().getStringBounds(text, g).getBounds();
        if (getOrientation() == HORIZONTAL) {
            int hSpace = bounds.width - textBounds.width;
            int xpos = bounds.x;
            if (hSpace > 0) {
                xpos += (hSpace / 2);
            }
            g.setColor(Color.BLACK);
            g.drawString(text, xpos, bounds.y + textBounds.height - 3);            
        } else {
            //TODO
        }
    }
    
}