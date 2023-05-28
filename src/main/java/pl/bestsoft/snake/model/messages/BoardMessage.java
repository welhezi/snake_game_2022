package pl.bestsoft.snake.model.messages;

import pl.bestsoft.snake.model.fakes.FakeMap;

import java.io.Serializable;

 //Une classe qui encapsule une fausse carte pour l'envoyer sur le réseau.

public class BoardMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;

     // Un faux objet contenant des plateaux de jeu.
    private final FakeMap fakeMap;

    public BoardMessage(final FakeMap fakeMap) {
        this.fakeMap = fakeMap;
    }

    //Renvoie un faux objet de l'ensemble du plateau.
    // plateau de jeu @retour
    public FakeMap getFakeMap() {
        return fakeMap;
    }
}
