package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.model.fakes.ScoreFake;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.util.Const;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

//Il contient des panneaux avec des informations sur le nombre de points marqués par les joueurs.

class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    //Liste des panneaux avec les résultats des joueurs
    private final Map<SnakeNumber, PlayerScorePanel> players;

    public ScorePanel() {
        setBounds(50, 440, 360, 90);
        setLayout(new GridLayout(1, 4));
        setBorder(BorderFactory.createTitledBorder("Resultats"));
        players = new HashMap<SnakeNumber, PlayerScorePanel>();
        players.put(SnakeNumber.FIRST, new PlayerScorePanel(Const.Colors.RED));
        players.put(SnakeNumber.SECOND, new PlayerScorePanel(Const.Colors.GREEN));
        players.put(SnakeNumber.THIRD, new PlayerScorePanel(Const.Colors.YELLOW));
        players.put(SnakeNumber.FOURTH, new PlayerScorePanel(Const.Colors.MAGENTA));
        add(players.get(SnakeNumber.FIRST));
        add(players.get(SnakeNumber.SECOND));
        add(players.get(SnakeNumber.THIRD));
        add(players.get(SnakeNumber.FOURTH));
    }

    //Mise à jour des performances des joueurs.
    //@param scoreMessage d'informations sur le score et l'identifiant du serpent
    void actScore(final ScoreMessage scoreMessage) {
        Map<SnakeNumber, ScoreFake> scoreFakeMap = scoreMessage.getScoreFakeMap().getScoreMap();
        for (SnakeNumber snakeNumber : scoreFakeMap.keySet()) {
            players.get(snakeNumber).actScore(scoreFakeMap.get(snakeNumber).getScore());
        }
    }
}
