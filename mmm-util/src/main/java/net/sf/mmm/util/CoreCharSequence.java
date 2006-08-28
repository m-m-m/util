/* $ Id: $ */
package net.sf.mmm.util;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class CoreCharSequence implements CharSequence {

    /** the hash of this sequence or <code>0</code> if not cached */
    private int cachedHash;

    /**
     * The constructor.
     */
    public CoreCharSequence() {

        super();
        this.cachedHash = 0;
    }

    /**
     * This implementation is to be discussed, because it violates the contract
     * of the equals method as defined by {@link Object}.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }
        if (other instanceof CharSequence) {
            CharSequence otherSequence = (CharSequence) other;
            int len = length();
            if (len == otherSequence.length()) {
                int i = len;
                while (i-- != 0) {
                    if (charAt(i) != otherSequence.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method must be called whenever the char sequence changed its length
     * or data.
     */
    protected void contentChanged() {

        this.cachedHash = 0;
    }

    /**
     * @see java.lang.Object#hashCode()
     * {@inheritDoc}
     */
    public int hashCode() {

        // behave like java.lang.String
        int hash = this.cachedHash;
        if (hash == 0) {
            int len = length();
            for (int i = 0; i < len; i++) {
                hash = 31 * hash + charAt(i);
            }
            this.cachedHash = hash;
        }
        return hash;
    }

    /**
     * @see CharSequence#toString()
     * {@inheritDoc}
     */
    public String toString() {

        return new StringBuffer(this).toString();
    }

}
