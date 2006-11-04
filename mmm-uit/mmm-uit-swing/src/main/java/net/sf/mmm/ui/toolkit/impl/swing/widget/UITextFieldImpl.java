/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITextField} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITextFieldImpl extends AbstractUIWidget implements UITextField {

  /** the native swing text field */
  private final JTextField textField;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITextFieldImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.textField = new JTextField();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   * 
   */
  public @Override
  JComponent getSwingComponent() {

    return this.textField;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   * 
   */
  public void setText(String text) {

    this.textField.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   * 
   */
  public String getText() {

    return this.textField.getText();
  }

}
