package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.PlayerID;

import java.io.Serializable;

//Événements liés au joueur.
public abstract class PlayerEvent extends GameEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    //ID du joueur
    private PlayerID ID;

    //Crée un nouvel événement de joueur.
    public PlayerEvent() {
        ID = new PlayerID(0);
    }

    //Renvoie le numéro d'identification du joueur.
    //      *
    //      * @return Numéro d'identification du joueur
    public PlayerID getID() {
        return ID;
    }

   //Donner au joueur un numéro d'identification.
   //      *
   //      * Lecteur d'identification du numéro d'identification @param
    public void setID(final PlayerID ID) {
        this.ID = ID;
    }
}
