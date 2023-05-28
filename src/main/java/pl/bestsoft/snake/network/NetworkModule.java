package pl.bestsoft.snake.network;

import pl.bestsoft.snake.controler.Timer;
import pl.bestsoft.snake.model.events.EndGameEvent;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.events.NewGameEvent;
import pl.bestsoft.snake.model.events.PlayerEvent;
import pl.bestsoft.snake.model.fakes.*;
import pl.bestsoft.snake.model.messages.BoardMessage;
import pl.bestsoft.snake.model.messages.GameMessage;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.rmi.server.RmiServerConnection;
import pl.bestsoft.snake.snake.PlayerID;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//Le module réseau permet la communication avec les clients
public class NetworkModule {
    //La file d'attente de blocage dans laquelle le module réseau envoie des événements.
    private final BlockingQueue<GameEvent> blockingQueue;

    //Carte contenant les sockets des clients connectés.
    private final ConcurrentMap<PlayerID, Socket> sockets = new ConcurrentHashMap<PlayerID, Socket>();

    //Une carte contenant l'objectOutputStream des clients connectés.
    private final ConcurrentMap<PlayerID, ObjectOutputStream> objectOutputStreams = new ConcurrentHashMap<PlayerID, ObjectOutputStream>();

    //Il nous indique si le jeu est joué sur une machine locale ou non
    private final boolean isLocal;

    //Nombre maximum de clients connectés au serveur.
    private int numberOfClients;

    //Dernière fausse carte postée.
    private HashMap<FakePoint, GameFake> prevFakeMap = null;

    //Dernière carte de résultats postée.
    private Map<SnakeNumber, ScoreFake> prevScoreMap = null;

    //Crée un module réseau.
    //      @param blockingQueue file d'attente de blocage à laquelle les événements sont lancés par le module réseau.
    //      @param estLocal
    public NetworkModule(final BlockingQueue<GameEvent> blockingQueue, boolean isLocal) {
        this.blockingQueue = blockingQueue;
        this.isLocal = isLocal;
    }

    //Démarre le thread du serveur.
    //      @param howManyClients Nombre de clients connectés au serveur.
    public void begin(final int howManyClients) {
        Server server = new Server(howManyClients);
        Thread t = new Thread(server);
        t.start();
    }

    //Envoie à tous les joueurs les changements qui se sont produits sur le plateau.
    //@param fakeMap la nouvelle fausse carte du tableau est comparée à celle envoyée précédemment.
    public void sendAllPlayersFakeMap(final FakeMap fakeMap) {
        HashMap<FakePoint, GameFake> newFakeMap = fakeMap.getFakeMap();
        if (newFakeMap.isEmpty()) {
            return;
        }
        if (prevFakeMap == null) {
            prevFakeMap = newFakeMap;
            sendAllPlayersMessage(new BoardMessage(fakeMap));
            return;
        }
        HashMap<FakePoint, GameFake> changes = new HashMap<FakePoint, GameFake>();
        for (FakePoint fakePoint : prevFakeMap.keySet()) {
            if ((!(prevFakeMap.get(fakePoint).getClass().equals(newFakeMap.get(fakePoint).getClass())))) {
                changes.put(fakePoint, newFakeMap.get(fakePoint));
            }
            if (newFakeMap.get(fakePoint).getClass().equals(BodyFake.class) && (!prevFakeMap.get(fakePoint).equals(newFakeMap.get(fakePoint)))) {
                changes.put(fakePoint, newFakeMap.get(fakePoint));
            }
        }
        prevFakeMap = newFakeMap;
        sendAllPlayersMessage(new BoardMessage(new FakeMap(changes)));
    }

    //Envoie des informations à tous les joueurs.
    // @param gameMessage
    public void sendAllPlayersMessage(final GameMessage gameMessage) {
        for (ObjectOutputStream objectOutputStream : objectOutputStreams.values()) {
            try {
                objectOutputStream.writeObject(gameMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Envoie des informations au client sélectionné.
    //      @param gameMessage d'informations au joueur
    //      @param quelClient id client
    void sendPlayerMessage(final GameMessage gameMessage, final PlayerID whichClient) {
        try {
            if (objectOutputStreams.containsKey(whichClient)) {
                objectOutputStreams.get(whichClient).writeObject(gameMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Envoyez à tous les joueurs des informations sur les résultats.
    //      La nouvelle carte de résultats est comparée à celle envoyée précédemment.
    //      @param scoreMessage
    public void sendAllPlayersScore(ScoreMessage scoreMessage) {
        if (prevScoreMap == null) {
            prevScoreMap = scoreMessage.getScoreFakeMap().getScoreMap();
            sendAllPlayersMessage(scoreMessage);
            return;
        }
        Map<SnakeNumber, ScoreFake> newScore = scoreMessage.getScoreFakeMap().getScoreMap();
        Map<SnakeNumber, ScoreFake> changesScore = new HashMap<SnakeNumber, ScoreFake>();
        for (SnakeNumber snakeNumber : newScore.keySet()) {
            if (!(prevScoreMap.get(snakeNumber).equals(newScore.get(snakeNumber)))) {
                changesScore.put(snakeNumber, newScore.get(snakeNumber));
            }
        }
        prevScoreMap = newScore;
        if (!changesScore.isEmpty()) {
            sendAllPlayersMessage(new ScoreMessage(new ScoreFakeMap(changesScore)));
        }
    }

    //Renvoie le nombre de clients actuellement connectés.
    //@return Nombre de clients actuellement connectés au serveur.
    int howManyPlayerAct() {
        return objectOutputStreams.size();
    }

    //Indique si plusieurs clients sont connectés
    public boolean isMoreThanOnePlayer() {
        return (getNumberOfClients() > 1);
    }

    //Indique si tous les clients sont connectés au serveur.
    public boolean allPlayersAreConected() {
        return getNumberOfClients() == objectOutputStreams.size();
    }

    //Renvoie le nombre maximum de clients connectés au serveur.
    //@revenir
    private synchronized int getNumberOfClients() {
        return numberOfClients;
    }

    //Définit le nombre maximum de clients connectés au serveur.
    //      @param nombreDeClients
    private synchronized void setNumberOfClients(final int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    //Responsable de la communication entre le serveur et les clients.
    private class PlayerConected implements Runnable {
        //identité du client
        private final PlayerID playerID;
         //Socket client

        private final Socket socket;

        PlayerConected(final PlayerID playerID, final Socket socket) {
            this.playerID = playerID;
            this.socket = socket;
        }

        //Il attend des informations des clients
        //@Override
        public void run() {
            ObjectInputStream objectInputStream;
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            while (sockets.containsValue(socket)) {
                PlayerEvent playerEvent;
                try {
                    playerEvent = (PlayerEvent) objectInputStream.readObject();
                    playerEvent.setID(playerID);
                    blockingQueue.add(playerEvent);
                } catch (IOException e) {
                    objectOutputStreams.remove(playerID);
                    sockets.remove(playerID);
                    sendAllPlayersMessage(new InfoMessage("Joueur " + playerID.getPlayerID() + " fini le jeu "));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                blockingQueue.put(new EndGameEvent());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    //Serveur de jeu Snake
    private class Server implements Runnable {
        //socket server
        private ServerSocket serverSocket;

        Server(final int howManyPlayers) {
            setNumberOfClients(howManyPlayers);
        }

        //Il écoute le port 5555 et démarre le jeu lorsque tous les joueurs sont connectés.
        //@Override
        public void run() {
            if (isLocal) {
                RmiServerConnection.startRmiServer();
            }
            try {
                serverSocket = new ServerSocket(5555);
            } catch (Exception e) {
                showErrorMessageAndExit();
            }
            for (int i = 1; i <= getNumberOfClients(); ++i) {
                Socket tmp;
                try {
                    tmp = serverSocket.accept();
                    sockets.put(new PlayerID(i), tmp);
                    objectOutputStreams.put(new PlayerID(i), new ObjectOutputStream(tmp.getOutputStream()));
                    PlayerConected newPlayer = new PlayerConected(new PlayerID(i), tmp);
                    Thread t = new Thread(newPlayer);
                    t.start();
                } catch (IOException e) {
                    showErrorMessageAndExit();
                }
            }
            Thread timer = new Thread(new Timer(blockingQueue));
            timer.start();
            try {
                blockingQueue.put(new NewGameEvent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void showErrorMessageAndExit() {
            JOptionPane.showMessageDialog(
                    null,
                    "Le serveur ne peut pas être créé",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(-1);
        }
    }
}
