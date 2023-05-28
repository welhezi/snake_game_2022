package pl.bestsoft.snake.model.model;

import java.io.Serializable;
//Une partie du corps du serpent.
public class SnakeBody implements Serializable {
    private static final long serialVersionUID = 1L;
    //L'emplacement des parties du corps sur le plateau.
    final private Coordinates coordinates;
    //description de la direction de la partie du corps, la direction d'entrée dans le champ
    private Direction fromDirection;
    //description de la direction de la partie du corps, kierune à la sortie du champ
    private Direction toDirection;
    //La taille de la partie du corps
    private BodySize bodySize = BodySize.NORMAL;

    //Crée une nouvelle partie du corps
    //      @param coordonne la position de la partie du corps
    //      @param de la direction d'où le serpent est venu sur le terrain
    //      @param est la direction dans laquelle le serpent part de ce champ
    public SnakeBody(final Coordinates coordinates, final Direction from, final Direction to) {
        this.coordinates = coordinates;
        this.fromDirection = from;
        this.toDirection = to;
    }

    //Informations sur l'emplacement des parties du corps.
    // @revenir
    Coordinates getCoordinates() {
        return coordinates;
    }

    //informations sur la taille des parties du corps.
    // @return BIG s'il y avait une pomme mangée par un serpent à cet endroit
    BodySize getBodySize() {
        return bodySize;
    }

    //Informations sur l'origine du serpent sur le tableau.
    //      @revenir
    Direction getFrom() {
        return fromDirection;
    }

    //Informations sur l'endroit où le serpent va de cet endroit sur le tableau.
    //      @revenir
    Direction getWhere() {
        return toDirection;
    }

    //Augmentation de la partie du corps après avoir mangé une pomme.
    void grow() {
        bodySize = bodySize.grow();
    }

    //Réglage de la direction d'où le serpent est venu aux données sur le tableau.
    //      @param de
    void setFrom(final Direction from) {
        this.fromDirection = from;
    }

    //Définissez la direction dans laquelle le serpent part de cet endroit sur le plateau.
    //      @param ceci
    void setTo(final Direction to) {
        this.toDirection = to;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
        result = prime * result
                + ((fromDirection == null) ? 0 : fromDirection.hashCode());
        result = prime * result
                + ((toDirection == null) ? 0 : toDirection.hashCode());
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
        SnakeBody other = (SnakeBody) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
