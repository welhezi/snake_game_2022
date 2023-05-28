package pl.bestsoft.snake.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.bestsoft.snake.rmi.common.IRmiServerConnection;

public class RmiClientConnection {
    private IRmiServerConnection service;

    public void setService(IRmiServerConnection service) {
        this.service = service;
    }

    public boolean isConnected() {
        return service.isConnectionWithServer();
    }

    public static boolean isConnectionToServer() {
        try {
            ApplicationContext clientContext =
                    new ClassPathXmlApplicationContext("rmi_client_context.xml");

            RmiClientConnection clientConnection =
                    clientContext.getBean("clientConnection", RmiClientConnection.class);

            return clientConnection.isConnected();

        } catch (Exception e) {
            return false;
        }
    }
}

//Contexte d’application XML autonome, prenant les fichiers de définition de contexte à partir du chemin de classe,
// interprétation des chemins d’accès simples en tant que noms de ressources de chemin de classe qui incluent le chemin d’accès du package (par exemple, « mypackage/myresource.txt »).
// Utile pour les harnais de test ainsi que pour les contextes d’application intégrés dans les JAR.