/* $ Id: $ */
package net.sf.mmm.util;

/**
 * This is an abstract implementation of the {@link java.lang.CharSequence} to
 * make life easier.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractCharSequence extends CoreCharSequence {

    /**
     * The constructor.
     */
    public AbstractCharSequence() {

        super();
    }

    /**
     * @see java.lang.CharSequence#subSequence(int, int)
     * {@inheritDoc}
     */
    public CharSequence subSequence(int startPosition, int endPosition) {

        if (startPosition < 0) {
            throw new IndexOutOfBoundsException("Start (" + startPosition
                    + ") must not be negative!");
        }
        if (endPosition < startPosition) {
            throw new IndexOutOfBoundsException("End (" + endPosition
                    + ") must be greater or equal to start (" + startPosition + ")!");
        }
        if (endPosition > length()) {
            throw new IndexOutOfBoundsException("End (" + endPosition
                    + ") greater than length of sequence (" + length() + ")");
        }
        return new CharSubSequence(this, startPosition, endPosition);
    }
    
}
