/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation;

/**
 * This is the interface for a progress-bar. It is a widget used to display
 * feedback about an ongoing backgroud process.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIProgressBar extends UIWidget, UiReadOrientation {

  /** the type of this object */
  String TYPE = "ProgressBar";

  /**
   * This method gets the current progress. It is a value in the range of
   * <code>[0, 100]</code> and represents the completeness of the progress in
   * percent.
   * 
   * @return the progress.
   */
  int getProgress();

  /**
   * This method sets the progress to the given value.
   * 
   * @param newProgress is the new progress. It must be in the range of
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
