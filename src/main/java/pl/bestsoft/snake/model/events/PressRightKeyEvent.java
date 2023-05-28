package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

//Appui sur la flèche droite ou équivalent par le joueur.
public class PressRightKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    //Crée un nouvel objet lorsque la flèche droite est enfoncée.
    public PressRightKeyEvent(final KeySetID whichSetKeys)
    {
        super(whichSetKeys);
    }
}
