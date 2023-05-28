package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

//Une classe abstraite représentant l'événement lié à la clé du joueur.
public abstract class KeyEvent extends PlayerEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    //Ensemble des clés à partir de laquelle l'événement vient de
    private final KeySetID whichSetKeys;

    //Crée un événement clé.
    // @Param déterminer la clé à partir de laquelle l'événement vient.
    public KeyEvent(final KeySetID whichSetKeys) {
        this.whichSetKeys = whichSetKeys;
    }

    //Renvoie l'ID de la clé à partir de laquelle l'événement vient.
    // @return ID Set of Keys
    public KeySetID getWhichSetKeys() {
        return whichSetKeys;
    }

    //Il informe s'il provient de l'ensemble de clés de base.
    // @return true si l'événement provient de l'ensemble de clés de base.
    public boolean isBasicSet() {
        return whichSetKeys.equals(new KeySetID(1));
    }
}
