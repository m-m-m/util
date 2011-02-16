/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UIPictureImpl;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncLabelAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UILabel} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UILabelImpl extends AbstractUIWidget implements UILabel {

  /** the synchron access to the label */
  private final SyncLabelAccess syncAccess;

  /** the icon */
  private UIPictureImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UILabelImpl(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject);
    int style = SWT.SHADOW_NONE | SWT.LEFT;
    this.syncAccess = new SyncLabelAccess(uiFactory, style);
    this.icon = null;
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
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public UIPictureImpl getIcon() {

    return this.icon;
  }

  /**
   * {@inheritDoc}
   */
  public void setIcon(UiImage newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.syncAccess.setImage(null);
    } else {
      this.syncAccess.setImage(this.icon.getSwtImage());
    }
  }

}
