package pl.bestsoft.snake.model.fakes;

import pl.bestsoft.snake.model.model.SnakeNumber;

import java.io.Serializable;
import java.util.Map;

//Contient une carte des résultats de tous les joueurs.
public class ScoreFakeMap implements Serializable {
    private static final long serialVersionUID = 1L;
    //Carte des résultats de tous les joueurs.
    private final Map<SnakeNumber, ScoreFake> scoreMap;

    //Crée une nouvelle carte des résultats de tous les joueurs.
    //@param scoreCarte
    public ScoreFakeMap(final Map<SnakeNumber, ScoreFake> scoreMap) {
        this.scoreMap = scoreMap;
    }

    //Renvoie une carte des scores des joueurs.
    //      *
    //      * @revenir
    public Map<SnakeNumber, ScoreFake> getScoreMap() {
        return scoreMap;
    }
}
