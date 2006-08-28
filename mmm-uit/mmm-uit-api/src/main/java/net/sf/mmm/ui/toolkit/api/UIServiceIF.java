/* $Id$ */
package net.sf.mmm.ui.toolkit.api;

import java.util.Enumeration;

/**
 * This is the interface for the service that provides the UI toolkit. <br>
 * Please take notice that it is illegal to cast any interface of the UI toolkit
 * to an implementing class. The only casts allowed are described in the
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF UIObjectIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIServiceIF {

    /**
     * This method gets the UI factory for the default display.
     * 
     * @return the default factory.
     */
    UIFactoryIF getFactory();

    /**
     * This method gets all available display objects.
     * 
     * @return the list of all available display objects (
     *         {@link UIDisplayIF UIDisplayIF}objects).
     */
    Enumeration getDisplays();

}