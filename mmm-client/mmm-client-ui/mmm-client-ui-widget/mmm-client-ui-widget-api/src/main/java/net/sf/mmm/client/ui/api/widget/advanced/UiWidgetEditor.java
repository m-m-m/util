/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.advanced;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegularAtomic;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;

/**
 * This is the interface for a {@link UiWidgetRegularComposite regular composite widget} that represents an
 * <em>editor</em>. An editor is a higher-level variant of a {@link UiWidgetField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetEditor<VALUE, FIELD extends UiWidgetField<VALUE>> extends
    UiWidgetRegularComposite<UiWidgetRegularAtomic> {

  UiWidgetLabel getLabel();

  FIELD getField();

}
