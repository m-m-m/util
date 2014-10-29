/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaValueMax;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaValueMin;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaValueNow;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaValueText;

/**
 * This interface represents the abstract <a href="http://www.w3.org/TR/wai-aria/roles#range">range</a> role. <br>
 * <b>ATTENTION:</b><br>
 * For some reason the specification defined both {@link #getValueNow()} and {@link #getValueText()} that are
 * different representations of the same value. When using both properties please be careful to keep them in
 * sync and consistent.
 * 
 * @see #WAI_ARIA
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface RoleRange extends RoleWidget, AttributeWriteAriaValueMax, AttributeWriteAriaValueMin,
    AttributeWriteAriaValueNow, AttributeWriteAriaValueText {

  // nothing to add...

}
