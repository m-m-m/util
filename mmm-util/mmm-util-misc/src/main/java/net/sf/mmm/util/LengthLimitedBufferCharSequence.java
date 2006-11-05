/* $ Id: $ */
package net.sf.mmm.util;

/**
 * This is an implementation of the {@link java.lang.CharSequence} interface
 * that is limited to a given maximum length. If more characters are appended to
 * the tail of this sequence, the same amount of characters are taken from the
 * head.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LengthLimitedBufferCharSequence extends AbstractCharSequence {

    /** the actual char buffer (chars are stored in wrap-around style) */
    private final char[] buffer;

    /** the head of the sequence in the buffer */
    private int head;
    
    /** the current length of the sequence */
    private int length;
    
    /**
     * The constructor.
     * 
     * @see #getMaximumLength()
     * 
     * @param maximumLength
     *        is the maximum length this sequence can have.
     */
    public LengthLimitedBufferCharSequence(int maximumLength) {

        super();
        this.buffer = new char[maximumLength];
        this.head = 0;
        this.length = 0;
    }

    /**
     * This method gets the maximum length this buffer can have.<br>
     * E.g. if the maximum length is set to <code>3</code> and the characters
     * of the alphabet (a,b,c,...) are sequentially appended to this sequence
     * its String representation will be "", "a", "ab", "abc", "bcd", "cde",
     * "def", ...
     * 
     * @return the maximum length of this buffer.
     */
    public int getMaximumLength() {

        return this.buffer.length;
    }

    public void append(char[] charBuffer) {
        //???
        //do not wrap and only use single straight array
        //use multiple arrays (two?)
        //jep- shift by swap the two arrays, mmhmm
        //xxxxxxxxxxxxxyyyyyyyyyyyyy
        //pattern that matches may overlap between x and y ranges
        //TODO: calculate min overlap for buffer-size!
        //Jup: Problem bbbbbbbbbbbbbbbbB and then comes new huge buffer
        //-> BXXXXXXXXXXXXXXXXXXXXXXX and bBX could not match!
        
        //StringBuffer sb;
        //String s = new String(new byte[0], 0, 0, "ISO-8859-1");
    }
    
    /**
     * This method appends a single character to the tail of this sequnce.
     * 
     * @see #getMaximumLength()
     * 
     * @param c is the character to append.
     */
    public void append(char c) {

        if (this.length == this.buffer.length) {
            //TODO: wrap-around!
            this.buffer[this.head++] = c;
            if (this.head > this.buffer.length) {
                this.head = 0;
            }
        } else {
            this.buffer[this.length++] = c;
        }
    }

    /**
     * @see java.lang.CharSequence#length()
     */
    public int length() {

        return this.length;
    }

    /**
     * @see java.lang.CharSequence#charAt(int)
     */
    public char charAt(int index) {

        int pos = this.head + index;
        if (pos > this.buffer.length) {
            pos -= this.buffer.length;
        }       
        return this.buffer[pos];
    }

}
