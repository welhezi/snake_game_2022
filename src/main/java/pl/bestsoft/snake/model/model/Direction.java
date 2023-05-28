package pl.bestsoft.snake.model.model;

//Il décrit la direction dans laquelle le serpent se déplace
public enum Direction {
    //Le serpent se déplace vers le nord
    NORTH(0, -1) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return NORTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return SOUTH;
        }
    },

    //Le serpent se déplace vers le sud
    SOUTH(0, 1) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return SOUTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return NORTH;
        }

    },

    //Le serpent se déplace vers l'est
    EAST(1, 0) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return EAST;
        }

        @Override
        Direction turnBack() {
            return WEST;
        }
    },

    //Le serpent se déplace vers l'ouest
    WEST(-1, 0) {
        @Override
        Direction goEast() {
            return WEST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return EAST;
        }
    },

    //Le serpent se déplace dans la direction inconnue
    UNKNOW(0, 0) {
        @Override
        Direction goEast() {
            return UNKNOW;
        }

        @Override
        Direction goNorth() {
            return UNKNOW;
        }

        @Override
        Direction goSouth() {
            return UNKNOW;
        }

        @Override
        Direction goWest() {
            return UNKNOW;
        }

        @Override
        Direction turnBack() {
            return UNKNOW;
        }
    };

    //Informations sur le vecteur de déplacement
    private int vectorAlfa, vectorBeta;

    private Direction(final int vectorAlfa, final int vectorBeta) {
        this.vectorAlfa = vectorAlfa;
        this.vectorBeta = vectorBeta;
    }

    //Renvoie des informations sur le vecteur de direction alpha.
    int getVectorAlfa() {
        return vectorAlfa;
    }

    //Renvoie des informations sur le vecteur de direction bêta.
    int getVectorBeta() {
        return vectorBeta;
    }

    //Bascule vers l'est chaque fois que possible.
    abstract Direction goEast();

    //Changements vers le nord lorsque cela est possible.
    abstract Direction goNorth();

    //Bascule vers le sud chaque fois que possible.
    abstract Direction goSouth();

    //Changez de direction vers l'ouest dès que possible.
    abstract Direction goWest();

    //Change de direction en sens inverse.
    abstract Direction turnBack();

}