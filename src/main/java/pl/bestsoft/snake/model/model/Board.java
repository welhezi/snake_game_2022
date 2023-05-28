package pl.bestsoft.snake.model.model;

import pl.bestsoft.snake.model.fakes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Stocke des informations sur le plateau de jeu
class Board {
    //Liste des serpents au tableau.
    private final Map<SnakeNumber, Snake> snake;
    //Pomme au tableau.
    private final Apple apple = new Apple();
    //Statut du jeu.
    private GameStatus gameStatus = GameStatus.ENDED;
    //Liste des points libres sur le tableau.
    private final ArrayList<EmptyPoint> emptyPoints = new ArrayList<EmptyPoint>();

    //Crée un nouveau tableau.
    Board() {
        snake = new HashMap<SnakeNumber, Snake>();
        fillEmpty();
    }

    //Vérifie une collision impliquant de toucher le côté de l'adversaire.
    void checkCollision() {
        for (SnakeNumber snakeNumber : SnakeNumber.values()) {
            for (SnakeNumber secondSnakeNumber : SnakeNumber.values()) {
                if (snake.containsKey(snakeNumber)
                        && snake.containsKey(secondSnakeNumber)
                        && secondSnakeNumber.getNumber() != snakeNumber.getNumber()
                        && snake.get(snakeNumber).snakeIsAlive()
                        && snake.get(secondSnakeNumber).snakeIsAlive()) {

                    snake.get(secondSnakeNumber).isCollisionBetweenSnakes((snake.get(snakeNumber)), emptyPoints);
                }
            }
        }
    }

   //Vérifie un cas particulier de collision dans lequel les deux serpents sont tués.
   //      Collision frontale.
    void checkHeadCollision() {

        for (SnakeNumber snakeNumber : SnakeNumber.values()) {
            for (SnakeNumber secondSnakeNumber : SnakeNumber.values()) {
                if (snake.containsKey(snakeNumber) && snake.containsKey(secondSnakeNumber)
                        && secondSnakeNumber != snakeNumber
                        && snake.get(snakeNumber).snakeIsAlive()
                        && snake.get(secondSnakeNumber).snakeIsAlive()
                        && snake.get(snakeNumber).getFirst().equals(snake.get(secondSnakeNumber).getFirst())
                        && snake.get(snakeNumber).getDirection() == snake.get(secondSnakeNumber).getDirection().turnBack()) {

                    snake.get(snakeNumber).killSnake();
                    snake.get(secondSnakeNumber).killSnake();
                }
            }
        }
    }

    //Vérifie que les têtes de deux serpents ne sont pas au même endroit sur le plateau.
    void checkPlaceCollision() {

        for (SnakeNumber snakeNumber : SnakeNumber.values()) {
            for (SnakeNumber secondSnakeNumber : SnakeNumber.values()) {
                if (snake.containsKey(snakeNumber) && snake.containsKey(secondSnakeNumber)
                        && secondSnakeNumber != snakeNumber
                        && snake.get(snakeNumber).snakeIsAlive()
                        && snake.get(secondSnakeNumber).snakeIsAlive()
                        && snake.get(snakeNumber).getFirst().equals(snake.get(secondSnakeNumber).getFirst())) {
                    snake.get(snakeNumber).killSnake();
                    snake.get(secondSnakeNumber).killSnake();
                }
            }
        }
    }

    //Il termine le jeu.
    void endGame() {
        gameStatus = gameStatus.endGame();
        snake.clear();
    }

    //Remplit une liste de points gratuits.
    private void fillEmpty() {
        for (int alfa = 0; alfa < 360; alfa += Snake.getAngle()) {
            for (int beta = 0; beta < 360; beta += Snake.getAngle()) {
                emptyPoints.add(new EmptyPoint(new Coordinates(alfa, beta)));
            }
        }
    }

    //Indique si la partie est terminée au cas où le joueur aurait encore
    // ne le sait pas
    boolean gameIsEnd() {
        return !gameStatus.inGame();
    }

    //Renvoie la carte de l'ensemble du plateau
    FakeMap getFake() {

        FakeMap tab = new FakeMap();
        if (!inGame()) {
            return tab;
        }
        for (int i = 0; i < (360); i += Snake.getAngle()) {
            for (int j = 0; j < (360); j += Snake.getAngle()) {
                tab.setFake(new FakePoint(new Coordinates(i, j)), new EmptyFake());
            }
        }

        for (Snake s : snake.values()) {
            if (s.inGame() == 1) {
                s.fillFake(tab);
            }
        }

        if (gameStatus.inGame()) {
            tab.setFake(new FakePoint(apple.getCoordinates()),
                    new AppleFake());
        }
        return tab;
    }

    //Renvoie des informations sur le résultat actuel en 꿹
    // identifiant @param
    int getScore(final int id) {
        return snake.get(id).getScore();
    }

    // Change la direction du serpent vers l'Est
    void goEast(SnakeNumber snakeNumber) {
        if (snake.containsKey(snakeNumber)) {
            (snake.get(snakeNumber)).goEast();
        }
    }

    //Change la direction du serpent vers le Nord
    void goNorth(SnakeNumber snakeNumber) {
        if (snake.containsKey(snakeNumber)) {
            (snake.get(snakeNumber)).goNorth();
        }
    }

    //Change la direction du serpent vers le sud
    void goSouth(SnakeNumber snakeNumber) {
        if (snake.containsKey(snakeNumber)) {
            (snake.get(snakeNumber)).goSouth();
        }
    }

   // Change la direction du serpent vers l'ouest.
    void goWest(SnakeNumber snakeNumber) {
        if (snake.containsKey(snakeNumber)) {
            (snake.get(snakeNumber)).goWest();
        }
    }

    //Renvoie le nombre de serpents vivants.
    // @revenir
    int howManyInGames() {
        int iluWGrze = 0;
        for (Snake s : snake.values()) {
            iluWGrze += s.inGame();
        }
        return iluWGrze;
    }

    //Renvoie des informations indiquant si le jeu est en cours
    //  @revenir
    boolean inGame() {
        return gameStatus.inGame();
    }

    //Déplace les serpents
    void moveSnakes() {
        if (!gameStatus.inGame()) {
            return;
        }
        for (Snake s : snake.values()) {
            if (!(s.isNotAlive())) {
                if (apple.isApple(s.newBody())) {
                    s.moveSnakeWithEatApple(emptyPoints);
                } else {
                    s.moveSnakeWithoutEatApple(emptyPoints);
                }
                checkHeadCollision();
            }
        }
        if (apple.isEaten()) {

            apple.chooseNewPoint(emptyPoints);

        }
        checkPlaceCollision();
        checkCollision();
        if (noSnakesInGame()) {
            gameStatus = gameStatus.endGame();
        }

    }

    //Information que tous les serpents sont morts
    //      @revenir
    boolean noSnakesInGame() {
        return (howManyInGames() == 0);
    }

    //Commence le jeu avec un certain nombre de serpents
    //       @param combien de serpents
    void startGame(final int howManySnakes) {
        snake.clear();
        gameStatus = GameStatus.INGAME;
        for (SnakeNumber snakeNumber : SnakeNumber.values()) {
            snake.put(snakeNumber, new Snake(5 * Snake.getAngle(), snakeNumber.getNumber() * 3 * Snake.getAngle(), snakeNumber, emptyPoints));
            if (snakeNumber.getNumber() == howManySnakes) {
                break;
            }
        }
        apple.chooseNewPoint(emptyPoints);
    }


    ScoreFakeMap getScoreFakeMap() {
        Map<SnakeNumber, ScoreFake> scoreFakeMap = new HashMap<SnakeNumber, ScoreFake>();
        for (SnakeNumber snakeNumber : snake.keySet()) {
            scoreFakeMap.put(snakeNumber, new ScoreFake(snake.get(snakeNumber).getScore()));
        }
        return new ScoreFakeMap(scoreFakeMap);
    }
}
