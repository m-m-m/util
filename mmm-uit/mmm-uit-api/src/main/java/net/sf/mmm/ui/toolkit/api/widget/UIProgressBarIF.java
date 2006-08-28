/* $Id: UIProgressBarIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIReadOrientationIF;

/**
 * This is the interface for a progress-bar. It is a widget used to display
 * feedback about an ongoing backgroud process.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIProgressBarIF extends UIWidgetIF, UIReadOrientationIF {

    /** the type of this object */
    String TYPE = "ProgressBar";

    /**
     * This method gets the current progress. It is a value in the range of
     * <code>[0, 100]</code> and represents the completeness of the progress
     * in percent.
     * 
     * @return the progress.
     */
    int getProgress();

    /**
     * This method sets the progress to the given value.
     * 
     * @param newProgress
     *        is the new progress. It must be in the range of
     *        <code>[0, 100]</code>.
     */
    void setProgress(int newProgress);

    /**
     * This method determines the status of the indeterminate flag of the
     * progress-bar.
     * 
     * @return <code>true</code>, if the progress-bar continously,
     *         <code>false</code>
     */
    boolean isIndeterminate();

}
