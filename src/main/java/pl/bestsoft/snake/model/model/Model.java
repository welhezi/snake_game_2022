package pl.bestsoft.snake.model.model;

import pl.bestsoft.snake.model.fakes.FakeMap;
import pl.bestsoft.snake.model.fakes.ScoreFakeMap;

//Modèle de jeu.
public class Model {
    //Le plateau sur lequel se déroule le jeu
    private final Board board;

    //Crée un modèle.
    public Model() {
        board = new Board();
    }

    //Il termine le jeu.
    public void endGame() {
        board.endGame();
    }

    //Retourne si le jeu est terminé.
    public boolean gameIsEnd() {
        return board.gameIsEnd();
    }

    //Renvoie l'intégralité du faux tableau.
    public FakeMap getFake() {
        return board.getFake();
    }

    //Change la direction du serpent vers l'est.
    //      identifiant de serpent @param
    public void goEast(SnakeNumber snakeNumber) {
        board.goEast(snakeNumber);
    }

    //Change la direction du serpent vers le nord.
    //      identifiant de serpent @param
    public void goNorth(SnakeNumber snakeNumber) {
        board.goNorth(snakeNumber);
    }

    //Il détourne le serpent vers le sud.
    //      identifiant de serpent @param
    public void goSouth(SnakeNumber snakeNumber) {
        board.goSouth(snakeNumber);
    }

    //Il détourne le serpent vers l'ouest.
    //      identifiant de serpent @param
    public void goWest(SnakeNumber snakeNumber) {
        board.goWest(snakeNumber);
    }

    //Renvoie des informations indiquant si le jeu est en cours.
    public boolean inGame() {
        return board.inGame();
    }

    //Démarre le jeu avec le nombre spécifié de serpents.
    public void startGame(final int howManySnakes) {
        board.startGame(howManySnakes);
    }

    //Il déplace les serpents et déplace les serpents
    public void moveSnakes() {
        board.moveSnakes();
    }

    //Renvoie des informations sur les résultats des serpents
    //      @retourner la carte des résultats
    public ScoreFakeMap getScoreFakeMap() {
        return board.getScoreFakeMap();
    }

}
