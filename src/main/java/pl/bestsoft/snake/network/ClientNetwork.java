package pl.bestsoft.snake.network;

import pl.bestsoft.snake.model.events.PlayerEvent;
import pl.bestsoft.snake.model.messages.BoardMessage;
import pl.bestsoft.snake.model.messages.GameMessage;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.rmi.client.RmiClientConnection;
import pl.bestsoft.snake.view.main_frame.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

//La classe responsable de la communication avec le serveur.
public class ClientNetwork {
    //Vue cliente.
    final private View view;
    //Le socket avec lequel le client communique.
    private Socket clientSocket;
    //Le flux de sortie vers le serveur.
    private ObjectOutputStream objectOutputStream;

    public ClientNetwork(final View view) {
        this.view = view;
    }

    //Etablissement de la connexion avec le serveur.
    //@param est le numéro IP du serveur
    // @param est local si la connexion est locale
    public void conectToServer(final String IpNumber, boolean isLocal) {
        try {
            if (!isRMIConnection(isLocal)) throw new Exception();
            clientSocket = new Socket(IpNumber, 5555);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Thread t = new Thread(new InputReader(clientSocket));
            t.start();

        } catch (Exception e) {
            view.showInfoMessage(
                    new InfoMessage("RMI signale une erreur de connexion au serveur"));
        }
    }

    private boolean isRMIConnection(boolean isLocal) {
        if (isLocal) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
                if (RmiClientConnection.isConnectionToServer()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    //Envoie les informations d'événement au serveur.
    // @param playerEvent
    public void sendEvent(final PlayerEvent playerEvent) {
        if (objectOutputStream == null) {
            view.showInfoMessage(new InfoMessage("Pas de connexion au serveur"));
            return;
        }
        try {
            objectOutputStream.writeObject(playerEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Écouteur pour les objets du serveur.
    private class InputReader implements Runnable {
        //Flux d'entrée du serveur.
        private ObjectInputStream objectInputStream;
        //Carte des actions entreprises en fonction des objets de message à venir.
        private final HashMap<Class<? extends GameMessage>, GameAction> actions;

        public InputReader(final Socket socket) {
            actions = new HashMap<Class<? extends GameMessage>, ClientNetwork.InputReader.GameAction>();
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            actions.put(BoardMessage.class, new FakeAction());
            actions.put(InfoMessage.class, new InformationAction());
            actions.put(ScoreMessage.class, new ActScore());
        }

        //Traitement des informations entrantes du serveur.
        //@Override
        public void run() {
            while (true) {
                try {
                    GameMessage gameMessage = (GameMessage) objectInputStream.readObject();
                    actions.get(gameMessage.getClass()).perform(gameMessage);
                } catch (IOException e) {
                    view.showInfoMessage(new InfoMessage("La connexion au serveur a été perdue"));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                objectOutputStream.close();
                objectInputStream.close();
                objectInputStream = null;
                objectOutputStream = null;
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //Une classe abstraite chargée d'effectuer des actions.
        private abstract class GameAction {
            abstract void perform(GameMessage gameMessage);
        }

        //Mise à jour des performances des joueurs.
        private class ActScore extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.actScores((ScoreMessage) gameMessage);
            }
        }

        //Mise à jour de la vue du tableau principal.
        private class FakeAction extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.updateBoard(gameMessage);
            }
        }

        //Afficher une fenêtre avec des informations.
        private class InformationAction extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.showInfoMessage((InfoMessage) gameMessage);
            }
        }
    }
}
