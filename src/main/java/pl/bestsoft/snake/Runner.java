package pl.bestsoft.snake;

import org.springframework.context.ApplicationContext;
import pl.bestsoft.snake.spring.ContextProvider;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;

import java.awt.*;

public class Runner {
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            //@Override
            public void run() {
                ApplicationContext context = ContextProvider.getSpringContext();

                ChooseGameTypeWindow frame =
                        context.getBean("chooseGameTypeWindow",
                                ChooseGameTypeWindow.class);
                frame.display();
            }
        });
    }
}

//EventQueue est une classe indépendante de la plate-forme qui met en file d’attente les événements, à la fois des classes homologues sous-jacentes et des classes d’applications approuvées.
//Object getBean(String) : Obtenir une instance d'un bean géré par le conteneur
//L'interface ApplicationContext hérite des interfaces BeanFactory et MessageSource.
//Elle ajoute des fonctionnalités permettant notamment l'accès aux ressources et une gestion d'événements.
//org.springframework.beans.factory.BeanFactory : définit les fonctionnalités de base de conteneur.
//org.springframework.context.ApplicationContext : propose quelques fonctionnalités supplémentaires comme une gestion d'événements et de ressources