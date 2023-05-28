package pl.bestsoft.snake.model.model;

//Identifie le numéro du serpent.
public enum SnakeNumber {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

    //nombre de serpent
    private final int number;

    private SnakeNumber(final int number) {
        this.number = number;
    }

    //Renvoie l'identifiant d'un serpent.
    public int getNumber() {
        return number;
    }
}
