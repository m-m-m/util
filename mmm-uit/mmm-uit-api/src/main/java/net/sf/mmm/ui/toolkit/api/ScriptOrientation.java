/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum ScriptOrientation {

  LEFT_TO_RIGHT {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return true;
    }
    
  },
  
  RIGHT_TO_LEFT {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return false;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return true;
    }
    
  },
  
  LEFT_TO_RIGHT_VERTICAL {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return false;
    }
    
  },
  
  
  RIGHT_TO_LEFT_VERTICAL {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return false;
    }
    
  };

  
  public abstract boolean isLeftToRight();

  public abstract boolean isHorizontal();
  
}
