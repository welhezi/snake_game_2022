package pl.bestsoft.snake.model.fakes;

import java.io.Serializable;
import java.util.HashMap;

//Un faux objet représentant les planches.
public class FakeMap implements Serializable {
    private static final long serialVersionUID = 1L;
    //Liste des points et faux représentant des objets sur le plateau.
    private HashMap<FakePoint, GameFake> fakeMap = new HashMap<FakePoint, GameFake>();

    //Crée une nouvelle fausse carte.
    //      *
    //      * @param fakeMap
    public FakeMap(HashMap<FakePoint, GameFake> fakeMap) {
        this.fakeMap = fakeMap;
    }

    public FakeMap() {
    }

    //@return carte des faux points et objets sur le terrain.
    public HashMap<FakePoint, GameFake> getFakeMap() {
        return fakeMap;
    }

    //Ajout d'une nouvelle position sur la carte.
    //      *
    //      * @param fakePoint représente un point sur le tableau
    //      * @param gameFake représente un objet sur un champ donné du plateau de jeu
    public void setFake(final FakePoint fakePoint, final GameFake gameFake) {
        fakeMap.put(fakePoint, gameFake);
    }

    //Fausse affectation de carte.
    //      *
    //      * @param carte fausse carte
    public void setMap(final HashMap<FakePoint, GameFake> map) {
        fakeMap = map;
    }
}
