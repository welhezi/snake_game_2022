package pl.bestsoft.snake.model.fakes;

import java.io.Serializable;

//Fournit des informations sur le score du joueur.
public class ScoreFake implements Serializable {
    private static final long serialVersionUID = 1L;
    //Score du joueur.
    private final int score;

    //Crée un nouvel objet Score Fake
    //      *
    //      * Note @param

    public ScoreFake(final int score) {
        this.score = score;
    }

    //Renvoie le score du joueur.
    //      *
    //      * @revenir
    public int getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + score;
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
        ScoreFake other = (ScoreFake) obj;
        if (score != other.score)
            return false;
        return true;
    }
}
