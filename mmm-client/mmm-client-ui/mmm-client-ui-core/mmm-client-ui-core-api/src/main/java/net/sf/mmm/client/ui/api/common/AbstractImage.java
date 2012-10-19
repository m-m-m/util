/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.client.ui.api.attribute.AttributeReadHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteAltText;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOnlySizeInPixel;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSize;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStyles;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteUrl;

/**
 * This is the abstract interface for an image (file of the type PNG, JPEG, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractImage extends AttributeWriteUrl, AttributeWriteAltText, AttributeWriteStyles,
    AttributeReadHtmlId, AttributeWriteSize, AttributeWriteOnlySizeInPixel {

  // nothing to add

}
