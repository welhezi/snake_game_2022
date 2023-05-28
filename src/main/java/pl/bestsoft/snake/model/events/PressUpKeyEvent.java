package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

//Appui sur une flèche vers le haut ou équivalent par le joueur.
public class PressUpKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    //Crée un nouvel objet avec clé vers le haut.
  //@param whichSetKeys
    public PressUpKeyEvent(final KeySetID whichSetKeys)
    {
        super(whichSetKeys);
    }
}
