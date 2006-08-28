/* $ Id: $ */
package net.sf.mmm.configuration.base;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is a configuration exception that contains multiple nested configuration
 * exceptions. It is a summary of multiple errors that occured during a chain of
 * operations that should be processed to the end.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CompositeConfigurationException extends ConfigurationException {

    /** uid for serialization */
    private static final long serialVersionUID = -7509766690812596716L;

    /** The agregated exceptions */
    private final List<ConfigurationException> trouble;

    /**
     * The constructor.
     * 
     * @param childExceptions
     */
    public CompositeConfigurationException(List<ConfigurationException> childExceptions) {

        super("");
        this.trouble = childExceptions;
    }

    /**
     * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
     * {@inheritDoc}
     */
    public void printStackTrace(PrintStream s) {

        super.printStackTrace(s);
        for (int i = 0; i < this.trouble.size(); i++) {
            s.println("" + i + ". child exception:");
            this.trouble.get(i).printStackTrace(s);
        }
    }

    /**
     * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
     * {@inheritDoc}
     */
    public void printStackTrace(PrintWriter s) {

        super.printStackTrace(s);
        for (int i = 0; i < this.trouble.size(); i++) {
            s.println("" + i + ". child exception:");
            this.trouble.get(i).printStackTrace(s);
        }
    }

}
