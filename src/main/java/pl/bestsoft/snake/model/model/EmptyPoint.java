package pl.bestsoft.snake.model.model;

//Une classe représentant un point libre sur le plateau
class EmptyPoint {
    //Emplacement du point sur le tableau
    private final Coordinates coordinates;

    //Crée un nouveau point vide sur le plateau.
     //@param coordonne la position du point sur la carte
    public EmptyPoint(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //Renvoie la position d'un point sur le tableau.
    //   @retourne la position du point sur le tableau
    Coordinates getCoordinates() {
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
        EmptyPoint other = (EmptyPoint) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
