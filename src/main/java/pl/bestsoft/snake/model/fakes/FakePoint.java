package pl.bestsoft.snake.model.fakes;

import pl.bestsoft.snake.model.model.Coordinates;

import java.io.Serializable;

//Un faux objet représentant un point sur le plateau.
public class FakePoint implements Serializable {
    private static final long serialVersionUID = 1L;
    //Décrit l'emplacement du point sur le tableau
    private final Coordinates coordinates;

    //Crée un nouveau faux point.
    //      *
    //      * Coordonnées @param
    public FakePoint(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //Renvoie une coordonnée qui décrit le point donné.
    //      *
    //      * @revenir
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
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
        FakePoint other = (FakePoint) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
