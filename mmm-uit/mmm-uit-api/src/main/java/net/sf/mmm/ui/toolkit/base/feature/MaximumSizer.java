/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadSize} interface that builds the
 * maximum width/height out of
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize preferred-size}
 * objects. This is supposed to be used as
 * {@link net.sf.mmm.ui.toolkit.api.common.LayoutConstraints#size size} in
 * {@linkplain net.sf.mmm.ui.toolkit.api.common.LayoutConstraints} to emulate
 * a grid layout.<br>
 * TODO: nobody can understand this crappy explanation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MaximumSizer implements UiReadSize {

  /** the list of sizes to build maximum of */
  private final List<UiReadPreferredSize> sizeList;

  /** if <code>true</code> the width will be override */
  private final boolean overrideWidth;

  /** if <code>true</code> the height will be override */
  private final boolean overrideHeight;

  /**
   * The constructor.
   * 
   * @param widthOverride if <code>true</code> the {@link #getHeight() height}
   *        will be the maximum height of all added sizes.
   * @param heightOverride
   */
  public MaximumSizer(boolean widthOverride, boolean heightOverride) {

    super();
    this.sizeList = new ArrayList<UiReadPreferredSize>();
    this.overrideHeight = heightOverride;
    this.overrideWidth = widthOverride;
  }

  /**
   * This method adds the given {@link UiReadPreferredSize size} to the
   * size-list. The maximum width/height will be determined over all sized in
   * the list.
   * 
   * @param size is the size to add.
   */
  public void add(UiReadPreferredSize size) {

    this.sizeList.add(size);
  }

  /**
   * This method removes the given {@link UiReadPreferredSize size} from the
   * size-list.
   * 
   * @param size is the size to remove.
   */
  public void remove(UiReadPreferredSize size) {

    this.sizeList.remove(size);
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    int width = 0;
    if (this.overrideWidth) {
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UiReadPreferredSize size = this.sizeList.get(i);
        int currentWidth = size.getPreferredWidth();
        if (currentWidth > width) {
          width = currentWidth;
        }
      }
    }
    return width;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    int height = 0;
    if (this.overrideHeight) {
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UiReadPreferredSize size = this.sizeList.get(i);
        int currentHeight = size.getPreferredHeight();
        if (currentHeight > height) {
          height = currentHeight;
        }
      }
    }
    return height;
  }

}
