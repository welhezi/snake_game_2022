package pl.bestsoft.snake.model.model;

//Renvoie l'identifiant d'un serpent.
enum SnakeStatus {
    //le serpent est vivant
    ALIVE(1) {
        @Override
        boolean isDead() {
            return false;
        }
    },
    //le serpent est mort
    DEAD(0) {
        @Override
        boolean isDead() {
            return true;
        }
    };

    //Spécifie l'état du serpent.
    private final int stan;

    private SnakeStatus(final int stan) {
        this.stan = stan;
    }

    //Renvoie l'état d'un serpent.
    int getStan() {
        return stan;
    }

    //Renvoie des informations indiquant si le serpent est mort.
    //  @return si le serpent est mort vrai sinon faux
    abstract boolean isDead();

    //Change le statut du serpent en MORT
    SnakeStatus uderzyl() {
        return DEAD;
    }
}
