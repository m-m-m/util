/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget.editor;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.state.UIWriteLocale;
import net.sf.mmm.ui.toolkit.api.widget.UIWidget;

/**
 * This is the interface of a date editor. It is a widget where the user can
 * specify a date in a comfortable way.<br>
 * The widget itself should be lightweight and require no more space than a
 * combo-box or a text-field. If more space is required, a pull-down, pop-up, or
 * dialog may be opened via a button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIDateEditor extends UIWidget, UIWriteLocale {

  /** the type of this object */
  String TYPE = "DateEditor";

  /**
   * This method sets the date value of this editor.
   * 
   * @param newDate
   *        is the new date value.
   */
  void setDate(Date newDate);

  /**
   * This method gets the currently selected date value of this editor.
   * 
   * @return the date value.
   */
  Date getDate();

}
