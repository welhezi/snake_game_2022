package pl.bestsoft.snake.model.model;

import pl.bestsoft.snake.model.fakes.BodyFake;
import pl.bestsoft.snake.model.fakes.FakeMap;
import pl.bestsoft.snake.model.fakes.FakePoint;

import java.util.ArrayList;
import java.util.LinkedList;

//Contient des informations sur le tuyau.
class Snake {
    //Contient une liste des parties du corps qui composent le serpent.
    private final LinkedList<SnakeBody> snake = new LinkedList<SnakeBody>();
    //La direction dans laquelle le serpent se déplace.
    private Direction direction;
    //L'état dans lequel se trouve le serpent.
    private SnakeStatus stanSnake = SnakeStatus.ALIVE;
    //Le nombre de points marqués par le serpent
    private int score = 0;
    //L'angle du serpent se déplace
    private static int angle = 20;
    //Numéro d'identification du serpent
    private final SnakeNumber snakeNumber;

    //Crée un nouveau serpent.
     //@param angle alpha, alpha de la queue
    //       @param bêta queue bêta angle
    //       @param snakeNumber L'identifiant du serpent
    //      @param emptyPoints liste des points vides sur le plateau

    Snake(final int alfa, final int beta, final SnakeNumber snakeNumber, final ArrayList<EmptyPoint> emptyPoints) {
        direction = Direction.EAST;
        makeSnake(alfa, beta, emptyPoints);
        this.snakeNumber = snakeNumber;

    }

    //Renvoie l'angle de déplacement du serpent.
    //      @angle de retour du mouvement
    static int getAngle() {
        return angle;
    }

    //Vérifie si un champ donné est occupé par un serpent.
    //      @param snakeBody référence au nouveau champ
    //       @revenir
    boolean isBusy(final SnakeBody snakeBody) {
        if (inGame() == 0) {
            return false;
        }
        return snake.contains(snakeBody);
    }

    //Remplit la fausse carte avec tous les points occupés par le serpent.
    //      @param mapFake une fausse carte où l'on entre dans des parties du corps du serpent
    void fillFake(final FakeMap mapFake) {
        if (inGame() == 0) {
            return;
        }
        for (SnakeBody p : snake) {
            mapFake.setFake(new FakePoint(p.getCoordinates()), new BodyFake(snakeNumber, p.getFrom(), p.getWhere(), p.getBodySize()));
        }
    }

    //Renvoie la direction dans laquelle le serpent se déplace.
    Direction getDirection() {
        return direction;
    }

   //Il rend la tête du serpent.
   //      @return témoignages sur la tête du serpent
    SnakeBody getFirst() {
        return snake.getFirst();
    }

    //Renvoie une liste des parties du corps du serpent.
    LinkedList<SnakeBody> getList() {
        return snake;
    }

    //Le nombre actuel de points possédés par le serpent.
    //      @return combien de points le serpent a
    int getScore() {
        return score;
    }

    //Change la direction du serpent vers l'est.
    void goEast() {
        direction = direction.goEast();
    }

    //Change la direction du serpent vers le nord.
    void goNorth() {
        direction = direction.goNorth();
    }

    //Il détourne le serpent vers le sud.
    void goSouth() {
        direction = direction.goSouth();
    }

    //Il détourne le serpent vers l'ouest.
    void goWest() {
        direction = direction.goWest();
    }

    //Indique si le serpent est impliqué dans le jeu.
    //      @return true si le serpent est vivant false sinon
    int inGame() {
        return stanSnake.getStan();
    }

    //Renvoie des informations indiquant si le serpent est mort.
    //      @revenir
    boolean isNotAlive() {
        if (stanSnake.isDead()) {
            return true;
        }
        return false;
    }

    //Vérifie si le serpent a touché l'adversaire.
    //      @param anotherSnake référence à l'adversaire
    //       @param emptyPoints liste des points vides sur le plateau.
    void isCollisionBetweenSnakes(final Snake anotherSnake, final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        LinkedList<SnakeBody> anotherSnakeList = anotherSnake.getList();
        for (SnakeBody p : anotherSnakeList) {
            if ((!(p.equals(anotherSnake.getFirst()))) && (snake.contains(p))) {
                stanSnake = stanSnake.uderzyl();
                for (SnakeBody snakeBody : snake) {
                    if (!(snakeBody == snake.getFirst())) {
                        emptyPoints.add(new EmptyPoint(snakeBody.getCoordinates()));
                    }
                }
                break;
            }
        }
    }

    //Fait mourir le serpent.
    void killSnake() {
        stanSnake = stanSnake.uderzyl();
    }

    //Déplace le serpent pendant qu'il mange la pomme.
    // @param emptyPoints référence à la liste des points libres sur le plateau
    void moveSnakeWithEatApple(final ArrayList<EmptyPoint> emptyPoints) {
        score += 10;
        moveSnakeHead(emptyPoints);
    }

    //Déplace le serpent sans manger la pomme.
    //      @param emptyPoints liste des points vides sur le plateau
    void moveSnakeWithoutEatApple(final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        emptyPoints.add(new EmptyPoint(snake.getLast().getCoordinates()));

        snake.removeLast();
        (snake.getLast()).setFrom(Direction.UNKNOW);


        moveSnakeHead(emptyPoints);
    }

    //Renvoie des informations indiquant si le serpent est vivant.
    //      @return vrai si vivant faux sinon
    boolean snakeIsAlive() {
        return stanSnake.getStan() == 1;
    }

   //Calcule les coordonnées de la nouvelle position des parties du corps du serpent.
   // @return nouvelle partie du corps
    SnakeBody newBody() {
        SnakeBody tmp = snake.getFirst();
        int alfa = tmp.getCoordinates().getAlfa();
        int beta = tmp.getCoordinates().getBeta();
        alfa = getNewAngle(alfa, direction.getVectorAlfa());
        beta = getNewAngle(beta, direction.getVectorBeta());
        return new SnakeBody(new Coordinates(alfa, beta), direction, Direction.UNKNOW);
    }

    //Calcule un nouvel angle.
    //      @param oldAngle
    //      @param isMove détermine si le serpent se déplace dans cette direction
    //      @revenir
    private static int getNewAngle(final int oldAngle, final int isMove) {
        if (isMove == 0) {
            return oldAngle;
        }
        int newAngle = oldAngle + isMove * angle;
        double x, y;
        x = Math.sin(Math.toRadians(newAngle));
        y = Math.cos(Math.toRadians(newAngle));
        if ((x + 2e-10) >= 0) {
            if (y < 0) {
                newAngle = 180 - (int) Math
                        .round((Math.toDegrees(Math.asin(x))));
                return newAngle;
            } else {
                return (int) Math.round((Math.toDegrees(Math.asin(x))));
            }
        } else {
            if (y < 0) {
                newAngle = 360 - (int) Math.round(Math.toDegrees(Math.acos(y)));
                return newAngle;
            } else {
                newAngle = 360 + (int) Math.round(Math.toDegrees(Math.asin(x)));
                return newAngle;
            }
        }
    }

    //Bouge la tête du serpent
    //      @param emptyPoints liste des points vides sur le plateau
    private void moveSnakeHead(final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        SnakeBody tmp = newBody();
        if (isBusy(tmp)) {
            killSnake();
            for (SnakeBody snakeBody : snake) {
                emptyPoints.add(new EmptyPoint(snakeBody.getCoordinates()));
            }
            return;
        }
        (snake.getFirst()).setTo(direction);

        snake.addFirst(tmp);
        emptyPoints.remove(new EmptyPoint(tmp.getCoordinates()));
    }

    //Crée un serpent.
    // @param alpha valeur de l'angle alpha de la queue du serpent
    // @param beta valeur de l'angle bêta de la queue du serpent
    // @param emptyPoints liste des points vides sur le plateau
    private void makeSnake(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        setTail(alfa, beta, emptyPoints);
        setBody(alfa + angle, beta, emptyPoints);
        setHead(alfa + 2 * angle, beta, emptyPoints);
    }

    //Ajoute la partie du corps à la liste des parties du corps du serpent
    //      @param alpha angle alpha de la partie du corps
    //      @param beta beta angle de la partie du corps
    //      @param emptyPoints liste des points vides sur le plateau
    private void setBody(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), direction, direction));
    }

    //Donne la tête à la liste des parties du corps du serpent
    // @param alpha head alpha angle
    // @param beta head beta angle
    // @param emptyPoints liste des points vides sur le tableau
    private void setHead(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), direction, Direction.UNKNOW));
    }

    //Ajoute la queue à la liste des parties du corps du serpent
    //       @param alpha angle alpha de la queue de serpent
    //       @param beta angle beta de la queue du serpent
    //       @param emptyPoints liste des points vides sur le plateau
    private void setTail(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), Direction.UNKNOW, direction));
    }
}
