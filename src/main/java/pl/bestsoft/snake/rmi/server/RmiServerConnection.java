package pl.bestsoft.snake.rmi.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.bestsoft.snake.rmi.common.IRmiServerConnection;

public class RmiServerConnection implements IRmiServerConnection {

    //@Override
    public boolean isConnectionWithServer() {
        return true;
    }

    public static void startRmiServer() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("rmi_server_context.xml");
    }
}
//ClassPathXmlApplicationContext : charge la définition du contexte à partir d'un fichier XML contenu dans le classpath.