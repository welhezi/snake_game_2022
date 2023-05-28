package pl.bestsoft.snake.model.messages;

import pl.bestsoft.snake.model.fakes.ScoreFakeMap;

import java.io.Serializable;

//Il contient des informations sur le nombre de points marqués par les joueurs.
public class ScoreMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    //Carte avec les scores des joueurs.
    private final ScoreFakeMap scoreFakeMap;

    public ScoreMessage(ScoreFakeMap scoreFakeMap) {
        this.scoreFakeMap = scoreFakeMap;
    }

    // Renvoie une carte des scores des joueurs.
     // Renvoie une carte des partitions des joueurs.

    public ScoreFakeMap getScoreFakeMap() {
        return scoreFakeMap;
    }
}
