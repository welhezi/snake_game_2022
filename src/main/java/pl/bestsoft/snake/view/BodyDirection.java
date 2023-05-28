package pl.bestsoft.snake.view;

import pl.bestsoft.snake.model.model.Direction;

//Informations sur la direction d'où et vers où la partie du corps se déplace.
public class BodyDirection {
    //Il décrit la direction d'où le serpent est venu sur le terrain.
    Direction from;
    //Il décrit la direction dans laquelle le serpent se déplace depuis le champ.
    Direction where;

    public BodyDirection(final Direction from, final Direction where) {
        this.from = from;
        this.where = where;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BodyDirection other = (BodyDirection) obj;
        if (from == null) {
            if (other.from != null) {
                return false;
            }
        } else if (!from.equals(other.from)) {
            return false;
        }
        if (where == null) {
            if (other.where != null) {
                return false;
            }
        } else if (!where.equals(other.where)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((where == null) ? 0 : where.hashCode());
        return result;
    }
}
