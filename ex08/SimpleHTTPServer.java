import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.*;


public class SimpleHTTPServer {

  private final int port;
  
  public SimpleHTTPServer(int port)
  {    
    this.port = port; 
  }

  public void start() {
    // TODO
    // Start a ServerSocker
    // wait for connection
    // then send the corresponding socket to an instance of Handler
    // and let it handle the request
    Socket socket = nill;
    ServerSocket serverSocket = null;

    while(true){
        socket = serverSocket.accept();
        Handler handler = new Handler(socket);
        handler.start();
    }
    
    }
  }

  private class Handler {
    private final Socket connection;

    Handler(Socket connection) {
      this.connection = connection;
    }

    public void start() {
      // Get InputStream and OutputStream from the socket.
      //
      // 1. read the request from the client.
      // 2. if it is not starting by "GET" then ignore
      // 3. otherwise, extract the file name from the request.
      // It will look like: "GET /filename.html HTTP/1.1"
      // You can split the string by whitespaces.
      // 4. open the file and reads its content
      // 5. create an HTTP header
      // 6. send the header then the content via the OutputStream
      return;
    }
  }


  public static void main(String[] args) {
    int port;
    try {
      port = Integer.parseInt(args[0]);
      if (port < 1024 || port > 65535) port = 8080;
    } catch (RuntimeException ex) {
      port = 8080;
    }

    try {
      SimpleHTTPServer server = new SimpleHTTPServer(port);
      server.start();
      
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.println("Usage: java SimpleHTTPServer [port]");
    }
  }
  
}