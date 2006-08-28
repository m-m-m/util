/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that has a tooltip.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteTooltipIF {

    /**
     * This method gets the tooltip text of this object.
     * 
     * @return the tooltip text or <code>null</code> if tooltip is disabled.
     */
    String getTooltipText();

    /**
     * This method sets the tooltip text of this object.
     * 
     * @param tooltip
     *        is the new tooltip text or <code>null</code> to disable the
     *        tooltip.
     */
    void setTooltipText(String tooltip);

}