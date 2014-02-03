/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;


/**
 * A simple variant of {@link AbstractClickFeatureBehavior}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractSimpleClickFeatureBehavior extends AbstractClickFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractSimpleClickFeatureBehavior(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

}
