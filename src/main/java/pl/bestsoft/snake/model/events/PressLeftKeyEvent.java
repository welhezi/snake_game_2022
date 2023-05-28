package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

//Appuyez sur votre flèche gauche ou équivalent par le joueur.
public class PressLeftKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    //Crée un nouvel événement associé à la pression de la flèche gauche.
    public PressLeftKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
