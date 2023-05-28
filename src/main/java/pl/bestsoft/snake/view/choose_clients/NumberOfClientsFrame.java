package pl.bestsoft.snake.view.choose_clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.model.Model;
import pl.bestsoft.snake.util.Const;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.util.KeymapUtil;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.LinkedBlockingQueue;

//Une fenêtre qui permet de choisir le nombre de joueurs sur le serveur.
@Component
public class NumberOfClientsFrame extends JFrame {

    private static final int BUTTON_WIDTH = 275;

    private static final int BUTTON_HEIGHT = 78;

    @Value("${ChooseGameTypeWindow.gameType}")
    private String frameTitle;

    @Value("${ChooseGameTypeWindow.TwoPlayers}")
    private String player2Text;

    @Value("${ChooseGameTypeWindow.ThreePlayers}")
    private String player3Text;

    @Value("${ChooseGameTypeWindow.FourPlayers}")
    private String player4Text;

    @Value("${ChooseNumberOfClientsWindow.DefaultIP}")
    private String defaultIPNumber;

    //Crée une fenêtre pour sélectionner le nombre de joueurs.
    private LinkedBlockingQueue<GameEvent> blockingQueue;

    public void init() {
        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setTitle(frameTitle);
        setSize(340, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Const.Colors.BACKGROUND_COLOR);
        setUndecorated(true);
        setWindowMoveble();
    }

    private void initializeComponents() {
        JButton player2 = createBtn("player2.png", 30, 70, player2Text, new TwoClientsAction());
        JButton player3 = createBtn("player3.png", 30, 170, player3Text, new ThreeClientsAction());
        JButton player4 = createBtn("player4.png", 30, 270, player4Text, new FourClientsAction());
        JLabel titleLabel = createTitleLabel();
        JLabel closeBtn = createCloseBtn();

        add(player2);
        add(player3);
        add(player4);
        add(titleLabel);
        add(closeBtn);
    }

    private JButton createBtn(String imageTitle, int x, int y, String label, ActionListener listener) {
        JButton btn = new JButton(label);
        btn.addActionListener(listener);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusable(false);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setForeground(Color.darkGray);
        Icon icon = new ImageIcon(getClass().getResource("/images/" + imageTitle));
        btn.setIcon(icon);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel createCloseBtn() {
        final ImageIcon imgUn = new ImageIcon(ImageLoader.load("close_btn.png"));
        final JLabel button = new JLabel(imgUn);
        button.addMouseListener(new CloseBtnAction());
        button.setBounds(295, 5, 40, 40);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createTitleLabel() {
        final JLabel button = new JLabel("Nombre de joueurs");
        button.setForeground(Const.Colors.LABEL_COLOR);
        button.setFont(new Font("Ravie", Font.PLAIN, 15));
        button.setBounds(50, 10, 240, 20);
        return button;
    }

    private void setWindowMoveble() {
        final Point point = new Point();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!e.isMetaDown()) {
                    point.x = e.getX();
                    point.y = e.getY();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!e.isMetaDown()) {
                    Point p = getLocation();
                    setLocation(p.x + e.getX() - point.x,
                            p.y + e.getY() - point.y);
                }
            }
        });
    }

    //Affiche une fenêtre.
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    //Masque la fenêtre.
    public void hideWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setVisible(false);
            }
        });
    }

    public void setBlockingQueue(LinkedBlockingQueue<GameEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    //Bouton d'écoute pour deux joueurs.
    private class TwoClientsAction implements ActionListener {
        //@Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(2).start();
        }
    }

    //Bouton d'écoute pour trois joueurs.
    private class ThreeClientsAction implements ActionListener {

        //@Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(3).start();
        }

    }

    //Bouton d'écoute pour four joueurs.
    private class FourClientsAction implements ActionListener {

        //@Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(4).start();
        }

    }

    private class CloseBtnAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    ChooseGameTypeWindow frame = new ChooseGameTypeWindow();
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
        }
    }

    //La classe responsable de la création du serveur avec le nombre de clients sélectionné.
    private class MakeServer extends Thread {
        //Le nombre de clients sur le serveur.
        private final int howManyClients;
        MakeServer(final int howManyClients) {
            this.howManyClients = howManyClients;
        }
        //Démarre le thread du serveur et crée un client.
        @Override
        public void run() {
            KeymapUtil.showDefaultKeymap();
            Model model = new Model();
            Controler controler = new Controler(model,blockingQueue, howManyClients, howManyClients, false);
            View view = new View(false);
            view.display(defaultIPNumber);
            controler.begin();
        }
    }
}
