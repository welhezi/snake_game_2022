package pl.bestsoft.snake.model.model;

//Décrit la taille de la partie du corps du serpent.
public enum BodySize {
    NORMAL, BIG;

    //change la taille de la partie du corps en grande
    BodySize grow() {
        return BIG;
    }
}
