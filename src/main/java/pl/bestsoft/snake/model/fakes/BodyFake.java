package pl.bestsoft.snake.model.fakes;

import pl.bestsoft.snake.model.model.BodySize;
import pl.bestsoft.snake.model.model.Direction;
import pl.bestsoft.snake.model.model.SnakeNumber;

import java.io.Serializable;

//Un faux objet représentant une partie du corps d'un serpent sur le plateau.
public class BodyFake extends GameFake implements Serializable {

    private static final long serialVersionUID = 1L;
    //Identité du serpent.
    final private SnakeNumber snakeNumber;
    //La direction d'où le serpent est venu dans le champ.
    final private Direction from;
    //La direction dans laquelle le serpent va du champ donné.
    final private Direction to;
    //La taille du serpent dans le champ donné.
    final private BodySize bodySize;


     //Crée un nouvel objet partie du corps du serpent.
     // @param snakeNumber identifiant serpent
     // @param de la direction d'où vient le serpent au champ donné.
     // @param est la direction dans laquelle va le serpent du champ donné
     // @param bodySize la taille des parties du corps dans la boîte donnée

    public BodyFake(final SnakeNumber snakeNumber, final Direction from, final Direction to, final BodySize bodySize) {
        this.snakeNumber = snakeNumber;
        this.from = from;
        this.to = to;
        this.bodySize = bodySize;
    }

    //Renvoie la taille du serpent.
    //      *
    //      * @taille du tuyau de retour
    public BodySize getBodySize() {
        return bodySize;
    }

    //Renvoie la direction d'où vient le serpent.
    //      *
    //      * @return la direction d'où le serpent est venu sur le terrain
    public Direction getFrom() {
        return from;
    }

    //Renvoie la direction dans laquelle le serpent se dirige vers le champ sélectionné.
    //      *
    //      * @return la direction dans laquelle le serpent part du champ sélectionné
    public Direction getTo() {
        return to;
    }

    //Renvoie l'ID du serpent.
    //      *
    //      * @return le numéro d'identification du serpent
    public SnakeNumber getWhichPlayer() {
        return snakeNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((bodySize == null) ? 0 : bodySize.hashCode());
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BodyFake other = (BodyFake) obj;
        if (bodySize != other.bodySize)
            return false;
        if (from != other.from)
            return false;
        if (to != other.to)
            return false;
        if (snakeNumber != other.snakeNumber)
            return false;
        return true;
    }
}
