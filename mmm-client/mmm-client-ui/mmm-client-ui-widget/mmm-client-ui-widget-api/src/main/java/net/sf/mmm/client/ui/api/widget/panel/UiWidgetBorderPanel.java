/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite;

/**
 * This is the interface for a {@link UiWidgetPanel panel widget} that shows a single {@link #getChild()
 * child} that is surrounded by a {@link #getLabel() labeled} border.<br/>
 * Here you can see an example (the options are the {@link #setChild(UiWidgetRegular) content} and not part of
 * the {@link UiWidgetBorderPanel} itself):
 * 
 * <pre>
 * <fieldset>
 * <legend>What is your choice?</legend>
 * <input type="radio">Option1</input>
 * <input type="radio">Option2</input>
 * <input type="radio">Option3</input>
 * </fieldset>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetBorderPanel extends UiWidgetPanel<UiWidgetRegular>,
    UiWidgetSingleMutableComposite<UiWidgetRegular>, AttributeWriteLabel, UiWidgetReal {

  // nothing to add

}
