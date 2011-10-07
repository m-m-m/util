/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncLabelAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UILabelImpl extends AbstractUiWidgetSwt<Label> implements UiLabel {

  /** @see #getAdapter() */
  private final SyncLabelAccess syncAccess;

  /** @see #getImage() */
  private UiImageImpl image;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UILabelImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    int style = SWT.SHADOW_NONE | SWT.LEFT;
    this.syncAccess = new SyncLabelAccess(uiFactory, this, style);
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncLabelAccess getAdapter() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public UiImageImpl getImage() {

    return this.image;
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.image = (UiImageImpl) newIcon;
    if (this.image == null) {
      this.syncAccess.setImage(null);
    } else {
      this.syncAccess.setImage(this.image.getSwtImage());
    }
  }

}
