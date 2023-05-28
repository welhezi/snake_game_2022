package pl.bestsoft.snake.model.messages;

import java.io.Serializable;

// Messages envoyés sur le réseau au joueur.

public class InfoMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    // Texte contenant le message
    private final String message;

    public InfoMessage(final String message) {
        this.message = message;
    }

    //@return message pour le joueur.
    public String getMessage() {
        return message;
    }
}
