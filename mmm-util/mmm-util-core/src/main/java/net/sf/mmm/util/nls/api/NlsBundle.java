/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the marker interface for all "resource-bundles" for this native language support. Instead of a
 * {@link java.util.ResourceBundle} you can create a custom interface extending this one in order to define
 * the {@link NlsMessage#getInternationalizedMessage() messages}. This is done by defining methods with
 * {@link NlsMessage} as return type and the {@link NlsMessage#getArgument(String) NLS arguments} as
 * parameter.<br/>
 * <b>NOTE:</b><br/>
 * You should NOT create an implementation of that interface as this is created for you automatically via
 * {@link NlsBundleFactory#createBundle(Class)}. This is the new and recommended approach for defining and
 * binding {@link NlsMessage}s. For an example see {@link net.sf.mmm.util.NlsMessagesBundleUtilCore}.<br/>
 * <b>IMPORTANT:</b><br/>
 * Unless annotated with {@link NlsBundleKey} the {@link java.util.ResourceBundle#getString(String) key} is
 * derived from the name of the method. You have to ensure that the keys are unique as otherwise the
 * localization is NOT possible. Please strictly avoid having methods with the same name.<br/>
 * <br/>
 * For maintenance and better support of localizers it is strongly recommended to use
 * {@link NlsMessage#getArgument(String) named arguments} instead of {@link NlsMessage#getArgument(int)
 * indexed}. Unfortunately Java does not include names of parameters in abstract methods (including methods of
 * interfaces). The only way to work around this is to add an annotation to each parameter. The
 * {@link NlsBundleFactory} therefore supports the {@link javax.inject.Named} annotation for this purpose. As
 * it is nasty to add these annotations manually here is a script for automation. This is just an example -
 * please add more types as needed.
 * 
 * <pre>sed -i -r 's/([(]|, )((Object|Type|int|Number|String|boolean)([.]{3})?) ([a-z][a-zA-Z0-9]*)/\1@Named("\5") \2 \5/g' MyNlsBundle.java</pre>
 * 
 * <b>ATTENTION:</b><br/>
 * It is strongly recommended NOT to use inheritance for {@link NlsBundle} interfaces. Instead create an
 * interface that directly extends {@link NlsBundle} (and nothing else). However, for purpose of abstraction
 * feel free to add an empty custom extension of {@link NlsBundle} as intermediate interface for your project
 * to decouple dependencies.
 * 
 * @see NlsBundleLocation
 * @see NlsBundleMessage
 * @see NlsBundleKey
 * 
 * @author hohwille
 * @since 2.0.2
 */
@SuppressWarnings("javadoc")
public abstract interface NlsBundle {

  // just a marker interface.

}
