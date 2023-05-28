package pl.bestsoft.snake.model.model;

enum GameStatus {
    //Le jeu continue.
    INGAME {
        @Override
        GameStatus endGame() {
            return ENDED;
        }

        @Override
        boolean inGame() {
            return true;
        }

        @Override
        GameStatus newGame() {
            return INGAME;
        }
    },
    //Le jeu est terminé.
    ENDED {
        @Override
        GameStatus endGame() {
            return ENDED;
        }

        @Override
        boolean inGame() {
            return false;
        }

        @Override
        GameStatus newGame() {
            return INGAME;
        }
    };

    //Changez le statut du jeu en terminé.
    abstract GameStatus endGame();

    //Indique si le jeu est en cours.
    abstract boolean inGame();

    //Changez le statut du jeu en INGAME.
    abstract GameStatus newGame();
}
