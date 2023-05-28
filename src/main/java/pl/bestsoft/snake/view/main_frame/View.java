package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.model.messages.BoardMessage;
import pl.bestsoft.snake.model.messages.GameMessage;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.network.ClientNetwork;

import javax.swing.*;

//Construit l'interface graphique du jeu.
public class View {
    //Le cadre dans lequel les éléments sont placés.
    private GameFrame mainFrame;
    //Le tableau principal sur lequel rampent les serpents.
    private BoardPanel mainBoard;
    //Panneau avec les résultats des joueurs.
    private ScorePanel scorePanel;
    //Un module réseau qui gère la connexion au serveur.
    private final ClientNetwork clientNetwork;
    //Il nous indique si le jeu est joué sur une machine locale ou non
    private final boolean isLocal;

    public View(boolean isLocal) {
        clientNetwork = new ClientNetwork(this);
        this.isLocal = isLocal;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                scorePanel = new ScorePanel();
                mainFrame = new GameFrame(clientNetwork);
                mainBoard = new BoardPanel();

                mainFrame.add(mainBoard);
                mainFrame.add(scorePanel);
                mainFrame.setLocationRelativeTo(null);
            }
        });
    }

    //Met à jour les scores des joueurs.
    //      @param scoreMessage
    public void actScores(final ScoreMessage scoreMessage) {

        scorePanel.actScore(scoreMessage);
    }

    //Il nomme la connexion au serveur et affiche le cadre de l'interface graphique.
    // @param IPNumber Le numéro IP du serveur
    public void display(final String IPNumber) {
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                mainFrame.setVisible(true);
            }
        });
        clientNetwork.conectToServer(IPNumber, isLocal);
    }

    //Affiche des informations à l'écran.
    //@param infoMessage
    public void showInfoMessage(final InfoMessage infoMessage) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JOptionPane.showMessageDialog(mainFrame, infoMessage.getMessage());
                System.exit(0);
            }
        });
    }

    //Met à jour la vue du plateau sur lequel se déplacent les serpents
    // @param message d'information sur fakeMap et l'identifiant du serpent
    public void updateBoard(final GameMessage message) {
        final BoardMessage boradMessage = (BoardMessage) message;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainBoard.setFake(boradMessage.getFakeMap());
                mainBoard.repaint();
            }
        });
    }
}