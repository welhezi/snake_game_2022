package pl.bestsoft.snake.snake;

import java.io.Serializable;

//Fournit des informations sur un ensemble de clés.
public class KeySetID implements Serializable {
    private static final long serialVersionUID = 1L;
    //Spécifie le numéro du jeu de clés
    private final int setNumber;

    public KeySetID(int setNumber) {
        this.setNumber = setNumber;
    }

    //Renvoie le numéro d'un ensemble de clés.
    //      @revenir
    public int getSetNumber()
    { return setNumber; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + setNumber;
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
        KeySetID other = (KeySetID) obj;
        if (setNumber != other.setNumber)
            return false;
        return true;
    }
}
