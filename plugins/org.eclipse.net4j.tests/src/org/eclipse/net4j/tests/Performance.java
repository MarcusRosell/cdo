package org.eclipse.net4j.tests;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.CountDownLatch;

/**
 * @author Eike Stepper
 */
public class Performance
{
  public static void main(String[] args) throws Exception
  {
    testInetAddress();
    testServerSocket();
    testSocket();
    testSelector();
  }

  public static void testInetAddress() throws Exception
  {
    System.out.println(InetAddress.class.getName());
    for (int i = 0; i < 2; i++)
    {
      long start = System.currentTimeMillis();
      InetAddress inet = InetAddress.getByName("localhost");
      inet.getHostAddress();
      long duration = System.currentTimeMillis() - start;

      System.out.println(duration);
    }
  }

  public static void testServerSocket() throws IOException
  {
    System.out.println(ServerSocket.class.getName());
    for (int i = 0; i < 2; i++)
    {
      long start = System.currentTimeMillis();
      ServerSocket serverSocket = new ServerSocket(2036);
      long duration = System.currentTimeMillis() - start;

      System.out.println(duration);
      serverSocket.close();
    }
  }

  public static void testSocket() throws Exception
  {
    System.out.println(Socket.class.getName());
    for (int i = 0; i < 2; i++)
    {
      final CountDownLatch latch = new CountDownLatch(1);
      new Thread()
      {
        @Override
        public void run()
        {
          try
          {
            ServerSocket serverSocket = new ServerSocket(2036);
            latch.countDown();

            Socket socket = serverSocket.accept();
            socket.close();
            serverSocket.close();
          }
          catch (IOException ex)
          {
            ex.printStackTrace();
            latch.countDown();
          }
        }
      }.start();

      latch.await();
      Thread.sleep(500);

      Socket socket = new Socket(Proxy.NO_PROXY);
      SocketAddress endpoint = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2036);

      long start = System.currentTimeMillis();
      socket.connect(endpoint);
      long duration = System.currentTimeMillis() - start;

      System.out.println(duration);
      Thread.sleep(500);
    }
  }

  public static void testSelector() throws IOException
  {
    SelectorProvider provider = SelectorProvider.provider();
    System.out.println(provider.getClass().getName());
    for (int i = 0; i < 2; i++)
    {
      long start = System.currentTimeMillis();
      Selector selector = provider.openSelector();
      long duration = System.currentTimeMillis() - start;

      System.out.println(duration);
      selector.close();
    }
  }
}
