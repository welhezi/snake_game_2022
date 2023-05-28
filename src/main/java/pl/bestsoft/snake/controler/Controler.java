package pl.bestsoft.snake.controler;

import org.springframework.stereotype.Controller;
import pl.bestsoft.snake.model.events.*;
import pl.bestsoft.snake.model.fakes.FakeMap;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.Model;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.network.NetworkModule;
import pl.bestsoft.snake.snake.KeySetID;
import pl.bestsoft.snake.snake.PlayerID;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

//Game controller.
@Controller
public class Controler {
    //Le modèle dont la méthode est causée par le contrôleur.
    private final Model model;
    //La file d'attente de blocage dans laquelle les événements sont lancés.
    private final BlockingQueue<GameEvent> blockingQueue;
    //Nombre de serpents dans le jeu.
    private final int snakes;
    //Module réseau.
    private final NetworkModule networkModule;
    //Carte des actions liées aux événements jetés dans la file d'attente de blocage.
    private final Map<Class<? extends GameEvent>, GameAction> actions;
    //Carte d'action concernant le changement de direction du tuyau
    private final Map<Class<? extends PlayerEvent>, TurningAction> turningActions;
    //Carte contenant des actions sur un tuyau spécifique
    private final Map<SnakeNumber, LinkedList<GameEvent>> playerEvents;
    //Carte contenant les ensembles de mappage pour les tuyaux qu'ils contrôlent
    private final Map<KeySetID, SnakeNumber> keySetIDMap;
   // Carte contenant des clients mappant sur les tuyaux qu'ils contrôlent
    private final Map<PlayerID, SnakeNumber> playerIDMap;

    /**
     * Crée un nouvel objet contrôleur.
     *  @param model          model
     * @param blockingQueue  file d'attente de verrouillage
     * @param howManyClients Nombre de clients participant au jeu
     * @param snakes         Nombre de tuyaux au tableau
     * @param isLocal          ou jeu local
     */
    public Controler(final Model model, final BlockingQueue<GameEvent> blockingQueue, final int howManyClients, final int snakes, boolean isLocal) {
        this.model = model;
        this.blockingQueue = blockingQueue;
        this.snakes = snakes;
        this.networkModule = new NetworkModule(blockingQueue, isLocal);
        actions = fillActions();
        turningActions = fillTurning();
        playerEvents = fillPlayerEvent();
        keySetIDMap = fillKeySetIDMap();
        playerIDMap = fillPlayerIDMap();
        networkModule.begin(howManyClients);
    }

    //Commence le contrôleur.
    public void begin() {
        while (true) {
            try {
                GameEvent gameEvent = blockingQueue.take();
                actions.get(gameEvent.getClass()).perform(gameEvent);
            } catch (Exception e) {
                System.out.println("Le blocage a déclenché une exception");
                e.printStackTrace();
            }
        }
    }

    //Remplit la carte des ensembles de clés sur les numéros de serpent.
    // @return keysetidmap
    private Map<KeySetID, SnakeNumber> fillKeySetIDMap() {
        Map<KeySetID, SnakeNumber> keySetIDMap = new HashMap<KeySetID, SnakeNumber>();
        keySetIDMap.put(new KeySetID(1), SnakeNumber.FIRST);
        keySetIDMap.put(new KeySetID(2), SnakeNumber.SECOND);
        keySetIDMap.put(new KeySetID(3), SnakeNumber.THIRD);
        keySetIDMap.put(new KeySetID(4), SnakeNumber.FOURTH);
        return Collections.unmodifiableMap(keySetIDMap);
    }

    //Remplit le MAPE de la mappage d'identification client sur les numéros de serpent.
    //@return PlayerIdmap
    private Map<PlayerID, SnakeNumber> fillPlayerIDMap() {
        Map<PlayerID, SnakeNumber> playerIDMap = new HashMap<PlayerID, SnakeNumber>();
        playerIDMap.put(new PlayerID(1), SnakeNumber.FIRST);
        playerIDMap.put(new PlayerID(2), SnakeNumber.SECOND);
        playerIDMap.put(new PlayerID(3), SnakeNumber.THIRD);
        playerIDMap.put(new PlayerID(4), SnakeNumber.FOURTH);
        return Collections.unmodifiableMap(playerIDMap);
    }

    //Crée des listes d'événements sur les serpents.
    //      * Une file d'attente distincte est créée pour chaque tuyau dans lequel ils sont stockés
    //      * Informations sur les pressions des clés entre les intervalles d'horloge.
    //      *
    //      * @return playerevents

    private Map<SnakeNumber, LinkedList<GameEvent>> fillPlayerEvent() {
        Map<SnakeNumber, LinkedList<GameEvent>> playerEvent = new HashMap<SnakeNumber, LinkedList<GameEvent>>();
        playerEvent.put(SnakeNumber.FIRST, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.SECOND, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.THIRD, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.FOURTH, new LinkedList<GameEvent>());
        return Collections.unmodifiableMap(playerEvent);
    }

    //Remplit la carte des actions liées aux événements à venir.
    //      *
    //      * @return actions
    private Map<Class<? extends GameEvent>, GameAction> fillActions() {
        Map<Class<? extends GameEvent>, GameAction> actions = new HashMap<Class<? extends GameEvent>, GameAction>();
        actions.put(PressDownKeyEvent.class, new PressDownAction());
        actions.put(PressLeftKeyEvent.class, new PressLeftAction());
        actions.put(PressRightKeyEvent.class, new PressRightAction());
        actions.put(PressUpKeyEvent.class, new PressUpAction());
        actions.put(TimerEvent.class, new TimerAction());
        actions.put(NewGameEvent.class, new NewGameAction());
        actions.put(EndGameEvent.class, new EndGameAction());
        return Collections.unmodifiableMap(actions);
    }

    //Remplit la carte des actions liées au changement de direction du tuyau.
    //      *
    //      * @return Turningacts
    private Map<Class<? extends PlayerEvent>, TurningAction> fillTurning() {
        Map<Class<? extends PlayerEvent>, TurningAction> turningAction = new HashMap<Class<? extends PlayerEvent>, TurningAction>();
        turningAction.put(PressUpKeyEvent.class, new GoNorth());
        turningAction.put(PressDownKeyEvent.class, new GoSouth());
        turningAction.put(PressLeftKeyEvent.class, new GoWest());
        turningAction.put(PressRightKeyEvent.class, new GoEast());
        return Collections.unmodifiableMap(turningAction);
    }

    //Il envoie des informations sur les joueurs sur la fin du jeu.
    private void sendEndInformation() {
        if (!model.inGame()) {
            networkModule.sendAllPlayersMessage(new InfoMessage("End of the game"));
        }
    }

    //Il envoie des faux joueurs de Mape.
    private void sendFakeBoardAllClients() {
        FakeMap fakeMap = model.getFake();
        networkModule.sendAllPlayersFakeMap(fakeMap);
    }

    //Il envoie des joueurs sur les résultats aux joueurs.
    private void sendScoreInformation() {
        networkModule.sendAllPlayersScore(new ScoreMessage(model.getScoreFakeMap()));
    }

    //La classe chargée de réaliser des actions liées aux événements jetés dans la file d'attente de blocage.
    private abstract class GameAction {
        abstract void perform(final GameEvent gameEvent);
    }

    //Fin du jeu.
    private class EndGameAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            model.endGame();
        }
    }

    //Démarrer un nouveau jeu.
    private class NewGameAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer() && (!networkModule.allPlayersAreConected())) {
                return;
            }
            for (LinkedList<GameEvent> liknedList : playerEvents.values()) {
                liknedList.clear();
            }
            model.startGame(snakes);
        }
    }

    //Appuyez sur les flèches vers le bas.
    private class PressDownAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressDownKeyEvent pressDownKeyEvent = (PressDownKeyEvent) gameEvent;
                if (pressDownKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressDownKeyEvent.getID())).addLast(pressDownKeyEvent);
                }
            } else {
                PressDownKeyEvent pressDownKeyEvent = (PressDownKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressDownKeyEvent.getWhichSetKeys())).addLast(pressDownKeyEvent);
            }
        }
    }

    //Appuyez sur la flèche gauche.
    private class PressLeftAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressLeftKeyEvent pressLeftKeyEvent = (PressLeftKeyEvent) gameEvent;
                if (pressLeftKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressLeftKeyEvent.getID())).addLast(pressLeftKeyEvent);
                }
            } else {
                PressLeftKeyEvent pressLeftKeyEvent = (PressLeftKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressLeftKeyEvent.getWhichSetKeys())).addLast(pressLeftKeyEvent);
            }
        }
    }

    //Appuyez sur la flèche vers la droite.
    private class PressRightAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressRightKeyEvent pressRightKeyEvent = (PressRightKeyEvent) gameEvent;
                if (pressRightKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressRightKeyEvent.getID())).addLast(pressRightKeyEvent);
                }
            } else {
                PressRightKeyEvent pressRightKeyEvent = (PressRightKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressRightKeyEvent.getWhichSetKeys())).addLast(pressRightKeyEvent);
            }
        }
    }

    //Appuyer sur la flèche vers le haut.
    private class PressUpAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressUpKeyEvent pressUpKeyEvent = (PressUpKeyEvent) gameEvent;
                if (pressUpKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressUpKeyEvent.getID())).addLast(pressUpKeyEvent);
                }
            } else {
                PressUpKeyEvent pressUpKeyEvent = (PressUpKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressUpKeyEvent.getWhichSetKeys())).addLast(pressUpKeyEvent);
            }
        }
    }

    //Interruption d'horloge informant la nécessité de se déplacer à travers les serpents.
    private class TimerAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (!model.inGame()) {
                return;
            }
            for (SnakeNumber snakeNumber : playerEvents.keySet()) {
                if (!(playerEvents.get(snakeNumber).isEmpty())) {
                    turningActions.get(playerEvents.get(snakeNumber).getFirst().getClass()).perform(snakeNumber);
                    playerEvents.get(snakeNumber).clear();
                }
            }
            model.moveSnakes();
            sendFakeBoardAllClients();
            sendEndInformation();
            sendScoreInformation();
        }
    }

    //Une classe abstraite responsable de la modification de la direction.
    private abstract class TurningAction {
        abstract void perform(SnakeNumber snakeNumber);
    }

    //Changer la direction vers l'est.
    private class GoEast extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goEast(snakeNumber);
        }
    }

    //Changer la direction vers le nord.
    private class GoNorth extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goNorth(snakeNumber);
        }
    }

    //Changer la direction vers le sud.
    private class GoSouth extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goSouth(snakeNumber);
        }
    }

    //Changer la direction vers l'ouest.
    private class GoWest extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goWest(snakeNumber);
        }
    }
}
