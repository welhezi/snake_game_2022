package pl.bestsoft.snake.model.model;

//Informations sur l'état de la pomme.
enum AppleStatus {
    //La pomme a été mangée par un serpent
    EATEN {
        @Override
        boolean isEaten() {
            return true;
        }
    },

    //La pomme n'a pas été mangée par le serpent
    NOEATEN {
        @Override
        boolean isEaten() {
            return false;
        }
    };

    //Change la pomme à manger.
    AppleStatus eatApple() {
        return EATEN;
    }

    //Informations indiquant si la pomme a été mangée.

    abstract boolean isEaten();

   //Change l'état de la pomme à manger
    AppleStatus newApple() {
        return NOEATEN;
    }
}
