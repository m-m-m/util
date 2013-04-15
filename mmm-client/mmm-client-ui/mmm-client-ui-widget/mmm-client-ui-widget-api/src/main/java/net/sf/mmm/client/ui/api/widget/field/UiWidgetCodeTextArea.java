/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a text
 * area field for editing source code. It acts like {@link UiWidgetTextArea} but displays the text as pretty
 * formatted source code in {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view mode}.<br/>
 * Here you can see an example in view mode:
 * 
 * <pre style="font-family: monospaced">
 * <span style="width:100%;background: #EEEEEE;">01 <b style="color: #880055">public static final</b> String <b><i style="color: blue;">CONSTANT</i></b> = <span style="color: blue;">"It's magic";</span></span>
 * <span>02</span>
 * <span style="width:100%;background: #EEEEEE;">03 <span style="color: blue">/**</span></span>
 * <span>04  <span style="color: blue">* {&#64;inheritDoc}</span></span>
 * <span style="width:100%;background: #EEEEEE;">05  <span style="color: blue">*&#47;</span></span>
 * <span>06 <b style="color: #880055">public</b> String toString() {</span>
 * <span style="width:100%;background: #EEEEEE;">07   <b style="color: #880055">return</b> <b><i style="color: blue;">CONSTANT</i></b>;</span>
 * <span>08 }</span>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetCodeTextArea extends UiWidgetTextualInputField<String>, AttributeWriteKeyboardFilter,
    AttributeWriteHeightInRows, UiWidgetNative {

  // nothing to add...

}
