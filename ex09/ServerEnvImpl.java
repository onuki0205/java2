import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.net.*;

public class ServerEnvImpl extends UnicastRemoteObject implements ServerEnv {

  public ServerEnvImpl() throws RemoteException {
    super();
  }

  public String showServerEnv(){
    String os = System.getProperty("os.name");
    InetAddress inetAddress = null;
    try{
      inetAddress = InetAddress.getLocalHost();
      inetAddress.getHostName();
    }catch(UnknownHostException e){
      e.printStackTrace();
    }
    return "Server Env: OS of "+inetAddress.getHostName()+" is "+ os;
  }

  public static void main(String args[]) {

    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    try {
      ServerEnvImpl obj = new ServerEnvImpl();
      Naming.rebind("//localhost:5555/ServerEnv", obj);
      System.out.println("ServerEnv Server bound in registry");
    } catch (Exception e) {
      System.out.println("ServerEnvmpl err: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
