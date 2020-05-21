import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.File;


public class SimpleHTTPServer {

  private final int port;

  public SimpleHTTPServer(int port)
  {
    this.port = port;
  }

  public void start() {
    // TODO
    ServerSocket serverSocket = new ServerSocket(this.port);
    Socket sock = null;
    ServerSocket serverSock = null;
    try {
      serverSock = new ServerSocket(this.port);

      while(true) {
        sock = serverSock.accept();
        Handler handle = new Handler(sock);
        handle.start();

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      //Socketを閉じる
      try {

        if (sock != null) {
          sock.close();
        }

      } catch (IOException e) {
      }

      // ServerSocketを閉じる
      try {

        if (serverSock != null) {
          serverSock.close();
        }

      } catch (IOException e) {
      }

    }
  }

  private class Handler {
    private final Socket connection;

    Handler(Socket connection) {
      this.connection = connection;
    }

    public void start() throws IOException{

      try{
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));//チェーニグ
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());//チェーニング
        String line;

        if ((line = in.readLine()) != null) {//リクエストライン GET /simplePage.html HTTP/1.1 のような順で入っている。
          //それぞれGET = リクエストメソッド
          // /index.html = リクエストURI
          // HTTP/1.1 = HTTPのバージョン
          System.out.println(line);
          String[] req = line.split(" ");//スペースごとにスプリット
          String reqMethod = req[0];//一単語目

          // GETの場合のみ処理
          if (reqMethod.equals("GET")) {

            String fileName = req[1];//２単語目 filenameに対応
            fileName = fileName.replaceAll("^/", "");
            File file = new File(fileName);
            //System.out.println(fileName);
            byte [] content;
            if(file.exists()){

              content = Files.readAllBytes(file.toPath());
            }else{
              File file404 = new File("404.html");
              content = Files.readAllBytes(file404.toPath());
            }
            String headerStr = "HTTP/1.0 200 OK\r\n"
            + "Server: SimpleHTTPServer\r\n"
            + "Content-length: " + content.length + "\r\n"
            + "Content-type: text/html"
            + "; charset=utf-8" + "\r\n\r\n";
            byte[] header = headerStr.getBytes(Charset.forName("UTF-8"));

            out.write(header, 0, header.length);
            out.write(content, 0, content.length);
            out.flush();
          }
        }
      }catch(IOException e){
        e.printStackTrace();
      }
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