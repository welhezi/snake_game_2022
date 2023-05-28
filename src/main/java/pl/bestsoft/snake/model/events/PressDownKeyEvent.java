package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

//Appuyez sur la flèche vers le bas ou équivalent par le joueur.
public class PressDownKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    //Crée un nouvel événement associé à la pression de la flèche par le bas.
    //@Param quitkeys
    public PressDownKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
