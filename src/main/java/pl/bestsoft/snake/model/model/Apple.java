package pl.bestsoft.snake.model.model;

import java.util.ArrayList;

//Il stocke des informations sur une pomme sur le tableau

class Apple {
    //Coordonnées des pommes
    private Coordinates coordinates;
    //Informations indiquant si la pomme a été mangée par un serpent
    private AppleStatus appleStatus = AppleStatus.EATEN;

    //Crée une nouvelle pomme.
    Apple() {
        coordinates = null;
    }

    //Choisit un nouvel emplacement pour la pomme dans la liste des points gratuits.
    //@param emptyPoints liste des points gratuits
    void chooseNewPoint(final ArrayList<EmptyPoint> emptyPoints) {
        appleStatus = appleStatus.newApple();
        int chooseIndexEmptyPoint = (int) (Math.random() * emptyPoints.size());
        EmptyPoint chooseEmptyPoint = emptyPoints.get(chooseIndexEmptyPoint);
        coordinates = chooseEmptyPoint.getCoordinates();
        emptyPoints.remove(new EmptyPoint(coordinates));
    }

    //Renvoie un objet qui décrit l'emplacement de la pomme.
    //@return objet contenant les coordonnées de la pomme.
    Coordinates getCoordinates() {
        return coordinates;
    }

    //Renvoie des informations indiquant si une place donnée sur le plateau est occupée par
    //Pomme. Lorsqu'il est occupé, il change l'état de la pomme en mangé.
     //@param snakeBody
    // @return true si l'espace est plein sinon false
    boolean isApple(final SnakeBody snakeBody) {
        if (snakeBody.equals(new SnakeBody(coordinates, Direction.UNKNOW, Direction.UNKNOW))) {
            appleStatus = appleStatus.eatApple();
            return true;
        }
        return false;
    }

   //Montre l'état de la pomme.
    boolean isEaten() {
        return appleStatus.isEaten();
    }
}
