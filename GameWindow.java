import javax.swing.*;

public class GameWindow extends JFrame {

    GameWindow(){
        this.add(new GameCanvas());
        this.setTitle("SnakeII");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
