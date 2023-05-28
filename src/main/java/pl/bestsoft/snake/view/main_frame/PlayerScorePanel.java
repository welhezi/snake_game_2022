package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.util.Const;

import javax.swing.*;
import java.awt.*;

//Informations sur le nombre de points marqués par le joueur.
class PlayerScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    //Étiquette sur laquelle sont affichées les informations sur le nombre de points marqués par les joueurs
    private final JLabel score;

    PlayerScorePanel(final Color color) {
        setSize(40, 40);
        setBackground(color);
        setLayout(null);
        score = new JLabel();
        score.setFont(Const.Fonts.BTN_FONT_BOLD);
        score.setBounds(35, 20, 50, 30);
        score.setText("0");
        add(score);
    }

    //Mise à jour du nombre de points marqués.
    // @param newScore nouveau nombre de points marqués
    void actScore(final Integer newScore) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                score.setText(newScore.toString());
            }
        });
    }

}
