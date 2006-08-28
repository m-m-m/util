/* $Id: UIDevice.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.GraphicsDevice;

import net.sf.mmm.ui.toolkit.api.UIDeviceIF;

/**
 * This class is the implementation of the UIDeviceIF interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDevice implements UIDeviceIF {

    /** the actual device */
    private GraphicsDevice device;

    /**
     * The constructor.
     * 
     * @param graphicsDevice
     *        is the AWT device to represent.
     */
    public UIDevice(GraphicsDevice graphicsDevice) {

        super();
        this.device = graphicsDevice;
    }

    /**
     * This method gets the graphics device. The method is only for internal
     * usage - do NOT access from outside (never leave the API).
     * 
     * @return the AWT graphics device object.
     */
    public GraphicsDevice getGraphicsDevice() {

        return this.device;
    }

    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    public String toString() {

        return this.device.getIDstring();
    }
}