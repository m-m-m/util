/* $ Id: $ */
package net.sf.mmm.util;

/**
 * This is an implementation of the {@link java.lang.CharSequence} interface
 * that allows to build a wrapping buffer.
 * 
 * This implementation is not thread-safe!
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DetectionBuffer extends AbstractCharSequence {

    /** empty char array for performance */
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /** the buffer of buffers */
    private final char[][] buffer;

    /** the index of first buffer */
    private int head;

    /** the length of the sequence */
    private int length;

    /**
     * The constructor.
     * 
     * @param totalBufferCount
     *        is the number of buffers that can be added without wrapping.
     */
    public DetectionBuffer(int totalBufferCount) {

        super();
        this.buffer = new char[totalBufferCount][];
        // initialize buffer
        for (int i = 0; i < this.buffer.length; i++) {
            this.buffer[i] = EMPTY_CHAR_ARRAY;
        }
        this.head = 0;
        this.length = 0;
    }

    private int incAndWrap(int indexPosition) {

        indexPosition++;
        if (indexPosition > this.buffer.length) {
            indexPosition = 0;
        }
        return indexPosition;
    }

    /**
     * This method determines if the buffer is full and the next call of
     * {@link #add(char[])} will cause that the head of the sequence is shifted
     * upwards.
     * 
     * @return <code>true</code> if the buffer is full, <code>false</code>
     *         otherwise.
     */
    public boolean isFull() {

        return (this.buffer[this.head].length > 0);
    }

    /**
     * This method adds new character data to the tail of the sequence.<br>
     * If the buffer is {@link #isFull() full}, the head of the sequence will
     * be shifted upwards. The shift will increase the amount 
     * 
     * @param bufferSlice
     */
    public void add(char[] bufferSlice) {

        this.length -= this.buffer[this.head].length;
        this.buffer[this.head] = bufferSlice;
        this.length += bufferSlice.length;
        this.head = incAndWrap(this.head);
        contentChanged();
    }

    /**
     * @see java.lang.CharSequence#length()
     * 
     */
    public int length() {

        return this.length;
    }

    /**
     * @see java.lang.CharSequence#charAt(int)
     * 
     */
    public char charAt(int index) {

        int bufferIndex = this.head;
        while ((index > this.buffer[bufferIndex].length)) {
            index -= this.buffer[bufferIndex].length;
            bufferIndex = incAndWrap(bufferIndex);
            if (bufferIndex == this.head) {
                throw new IndexOutOfBoundsException("Index (" + index + ") greater than length ("
                        + length() + ")!");
            }
        }
        return this.buffer[bufferIndex][index];
    }

}
