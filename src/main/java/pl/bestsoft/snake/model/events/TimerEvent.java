package pl.bestsoft.snake.model.events;

import java.io.Serializable;

//Un événement lié à la nécessité pour le serpent de se déplacer.
//  * Ces événements sont jetés dans la file d'attente de blocage de temps en temps par le fil du minuteur.
public class TimerEvent extends GameEvent implements Serializable {
    private static final long serialVersionUID = 1L;

}
