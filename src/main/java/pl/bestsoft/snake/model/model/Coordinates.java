package pl.bestsoft.snake.model.model;

import java.io.Serializable;

//Décrit l'emplacement des objets sur le plateau.
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;
    //L'angle alpha de l'objet sur le plateau
    private final int alfa;
    //L'angle bêta d'un objet sur le plateau
    private final int beta;

    public Coordinates(final int alfa, final int beta) {
        this.alfa = alfa;
        this.beta = beta;
    }

   //Renvoie l'angle alpha de l'objet.
   //      @revenir
    public int getAlfa() {
        return alfa;
    }

    //Renvoie l'angle bêta d'un objet.
    //      @revenir
    public int getBeta() {
        return beta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + alfa;
        result = prime * result + beta;
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
        Coordinates other = (Coordinates) obj;
        if (alfa != other.alfa)
            return false;
        if (beta != other.beta)
            return false;
        return true;
    }
}
