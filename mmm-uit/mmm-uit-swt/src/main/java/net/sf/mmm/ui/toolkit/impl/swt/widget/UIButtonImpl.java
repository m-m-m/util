/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UIPictureImpl;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButton} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIButtonImpl extends AbstractUIWidget implements UIButton {

  /** the style of the button */
  private final ButtonStyle style;

  /** the synchron access to the button */
  private final SyncButtonAccess syncAccess;

  /** the icon */
  private UIPictureImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param buttonStyle
   *        determines the style of the button.
   */
  public UIButtonImpl(UIFactorySwt uiFactory, UISwtNode parentObject, ButtonStyle buttonStyle) {

    super(uiFactory, parentObject);
    this.style = buttonStyle;
    int swtStyle = UIFactorySwt.convertButtonStyle(buttonStyle);
    this.syncAccess = new SyncButtonAccess(uiFactory, swtStyle);
    this.icon = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIButton#getStyle()
   */
  public ButtonStyle getStyle() {

    return this.style;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#isSelected()
   */
  public boolean isSelected() {

    return this.syncAccess.isSelected();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#setSelected(boolean)
   */
  public void setSelected(boolean selected) {

    this.syncAccess.setSelected(selected);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadIcon#getIcon()
   */
  public UIPictureImpl getIcon() {

    return this.icon;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIcon#setIcon(net.sf.mmm.ui.toolkit.api.UIPicture)
   */
  public void setIcon(UIPicture newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.syncAccess.setImage(null);
    } else {
      this.syncAccess.setImage(this.icon.getSwtImage());
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public SyncButtonAccess getSyncAccess() {

    return this.syncAccess;
  }

}
