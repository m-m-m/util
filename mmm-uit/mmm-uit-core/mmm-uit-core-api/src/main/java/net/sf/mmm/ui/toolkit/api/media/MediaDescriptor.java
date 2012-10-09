/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.media;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadStringTitle;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadUrl;

/**
 * This is the abstract interface for a descriptor of multi-media-data. It is either an
 * {@link AudioDescriptor} for audio tracks or a {@link VideoDescriptor} for videos. You can either implement
 * these interfaces directly by your business objects or use the provided java beans.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface MediaDescriptor extends AttributeReadUrl, AttributeReadStringTitle {

  // nothing to add...

}
