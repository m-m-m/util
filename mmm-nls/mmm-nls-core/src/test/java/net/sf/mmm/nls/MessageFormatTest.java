/* $ Id: MessageFormatTest.java $ */
package net.sf.mmm.nls;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * TODO: This class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MessageFormatTest {

    /**
     * The constructor.
     * 
     */
    public MessageFormatTest() {

        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        ResourceBundle bundle = ResourceBundle.getBundle("net.sf.mmm.nls.NLSTest"); 
        MessageFormat f = new MessageFormat(bundle.getString("MSG"));
        System.out.println(f.format(new Object[] {"is", "test"}));
    }
}
