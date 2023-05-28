package pl.bestsoft.snake.controler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.events.TimerEvent;

import java.util.concurrent.BlockingQueue;

//La minuterie déduit le temps entre les mouvements de serpent suivants.
@Component
public class Timer implements Runnable {
    //La file d'attente de verrouillage dans laquelle la minuterie place les événements.
    private final BlockingQueue<GameEvent> blockingQueue;

    @Value("${Timer.exception}")
    private String timerErrorText;

    //Crée une minuterie.
    //      *
    //      * @Param BlockingQueue Blocking Pug contenant des événements du joueur
    public Timer(final BlockingQueue<GameEvent> blockingQueue)
    {
        this.blockingQueue = blockingQueue;
    }

    //Il dort le fil puis met TimeRevent dans une file d'attente de verrouillage.
   // @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);
                blockingQueue.add(new TimerEvent());
            }
        } catch (Exception e) {
            System.out.println(timerErrorText);
            e.printStackTrace();
        }
    }
}
