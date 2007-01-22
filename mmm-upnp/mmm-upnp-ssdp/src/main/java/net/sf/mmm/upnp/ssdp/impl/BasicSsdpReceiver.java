/* $Id$ */
package net.sf.mmm.upnp.ssdp.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import net.sf.mmm.upnp.ssdp.api.SsdpRequest;
import net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver;

/**
 * This is the basic implemetation of the
 * {@link net.sf.mmm.upnp.ssdp.api.SsdpReceiver} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicSsdpReceiver extends AbstractSsdpReceiver {

  /** @see #setThreadPool(ExecutorService) */
  private ExecutorService threadPool;

  /** @see #connect() */
  private MulticastListener listener;

  /**
   * The constructor
   */
  public BasicSsdpReceiver() {

    super();
    this.threadPool = null;
    this.listener = null;
  }

  /**
   * This method connects the multicast-socket.
   * 
   * @throws IOException
   *         if the operation failed.
   */
  protected void connect() throws IOException {

    if (this.listener != null) {
      throw new IllegalStateException("Connect called twice without disconnect!");
    }
    this.listener = new MulticastListener();
    this.threadPool.execute(this.listener);
  }

  /**
   * This method disconnects the multicast-socket.
   * 
   * @throws IOException
   *         if the operation failed.
   */
  protected void disconnect() throws IOException {

    if (this.listener != null) {
      this.listener.disconnect();
      this.listener = null;
    }
  }

  /**
   * This method sets the thread-pool used to create the listener thread.
   * 
   * @param executor
   *        the threadPool to set.
   */
  @Resource
  public void setThreadPool(ExecutorService executor) {

    this.threadPool = executor;
  }

  /**
   * This inner class is the actual listener implementation.
   */
  private class MulticastListener implements Runnable {

    /** the multicast socket to listen to */
    private MulticastSocket socket;

    /** @see #run() */
    private boolean listening;

    /** the internet address to listen to */
    private InetAddress address;

    /**
     * The constructor
     * 
     * @throws IOException
     *         if the operation failed with an I/O problem.
     */
    public MulticastListener() throws IOException {

      this(InetAddress.getByName(SsdpRequest.MULTICAST_ADDRESS));
    }

    /**
     * The constructor
     * 
     * @param mutlicastAddress
     *        the multicast address to listen to.
     * @throws IOException
     *         if the operation failed with an I/O problem.
     */
    public MulticastListener(InetAddress mutlicastAddress) throws IOException {

      super();
      this.listening = true;
      this.address = mutlicastAddress;
      this.socket = new MulticastSocket(SsdpRequest.MULTICAST_PORT);
      this.socket.joinGroup(this.address);
    }

    /**
     * This method disconnects the multicast-socket.
     * 
     * @throws IOException
     *         if the operation failed.
     */
    protected void disconnect() throws IOException {

      try {
        this.listening = false;
        this.socket.leaveGroup(this.address);
      } finally {
        this.socket.close();
        this.socket = null;
      }
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {

      byte[] buffer = new byte[1024];
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
      while (this.listening) {
        try {
          this.socket.receive(packet);
          byte[] data = packet.getData();
          //SsdpRequest request;
          //notifyListeners(request);
        } catch (IOException e) {
          // TODO: error handling!!!
          e.printStackTrace();
        }
      }
    }
  }

}
