/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.state.UIReadSize;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.state.UIReadSize} interface that builds the
 * maximum width/height out of
 * {@link net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize preferred-size}
 * objects. This is supposted to be used as
 * {@link net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints#size size} in
 * {@linkplain net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints} to emulate
 * a grid layout. TODO: nobody can understand this crappy explanation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MaximumSizer implements UIReadSize {

  /** the list of sizes to build maximum of */
  private final List<UIReadPreferredSize> sizeList;

  /** if <code>true</code> the width will be override */
  private final boolean overrideWidth;

  /** if <code>true</code> the height will be override */
  private final boolean overrideHeight;

  /**
   * The constructor.
   * 
   * @param widthOverride
   *        if <code>true</code> the {@link #getHeight() height} will be the
   *        maximum height of all added sizes.
   * @param heightOverride
   */
  public MaximumSizer(boolean widthOverride, boolean heightOverride) {

    super();
    this.sizeList = new ArrayList<UIReadPreferredSize>();
    this.overrideHeight = heightOverride;
    this.overrideWidth = widthOverride;
  }

  /**
   * This method adds the given {@link UIReadPreferredSize size} to the
   * size-list. The maximum width/height will be determined over all sized in
   * the list.
   * 
   * @param size
   *        is the size to add.
   */
  public void add(UIReadPreferredSize size) {

    this.sizeList.add(size);
  }

  /**
   * This method removes the given {@link UIReadPreferredSize size} from the
   * size-list.
   * 
   * @param size
   *        is the size to remove.
   */
  public void remove(UIReadPreferredSize size) {

    this.sizeList.remove(size);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   */
  public int getWidth() {

    int width = 0;
    if (this.overrideWidth) {
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UIReadPreferredSize size = this.sizeList.get(i);
        int currentWidth = size.getPreferredWidth();
        if (currentWidth > width) {
          width = currentWidth;
        }
      }
    }
    return width;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   */
  public int getHeight() {

    int height = 0;
    if (this.overrideHeight) {
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UIReadPreferredSize size = this.sizeList.get(i);
        int currentHeight = size.getPreferredHeight();
        if (currentHeight > height) {
          height = currentHeight;
        }
      }
    }
    return height;
  }

}
