package pl.bestsoft.snake.snake;

import java.io.Serializable;

//Informations sur le numéro d'identification du client
public class PlayerID implements Serializable {
    private static final long serialVersionUID = 1L;
    //Numéro d'identification du client.
    final private int playerID;

    public PlayerID(final int playerID) {
        this.playerID = playerID;
    }

    //Informe sur le numéro d'identification du client.
    //      @return PlayerID
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlayerID other = (PlayerID) obj;
        if (playerID != other.playerID) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        return result;
    }
}
