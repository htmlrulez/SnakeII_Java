import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameCanvas extends JPanel implements ActionListener {

    static final int SNAKE_SIZE = 50;
    static final int SCREEN_WIDTH = SNAKE_SIZE * 18;
    static final int SCREEN_HEIGHT = SNAKE_SIZE * 18;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / SNAKE_SIZE;
    static final int DELAY = 100;


    protected final int[] snakeBodyXCoordinate = new int[GAME_UNITS];
    private final int[] snakeBodyYCoordinate = new int[GAME_UNITS];
    private int bodyParts = 3;
    private int score;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    public GameCanvas(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();

    }
    public void startGame(){
        running = true;
        generateApple();
        timer = new Timer(DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running){
            /*
            for (int i = 0; i < SCREEN_HEIGHT/SNAKE_SIZE; i++){
                g.drawLine(i*SNAKE_SIZE, 0, i*SNAKE_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i* SNAKE_SIZE, SCREEN_WIDTH, i * SNAKE_SIZE);
            }
            */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, SNAKE_SIZE, SNAKE_SIZE);

            for (int i = 0; i < bodyParts; i++){
                if (i == 0){
                    g.setColor(Color.green);
                    g.fillRect(snakeBodyXCoordinate[i], snakeBodyYCoordinate[i], SNAKE_SIZE, SNAKE_SIZE);
                }
                else{
                    g.setColor(Color.cyan);
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(snakeBodyXCoordinate[i], snakeBodyYCoordinate[i], SNAKE_SIZE, SNAKE_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ score))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void generateApple(){
        appleX = random.nextInt(SCREEN_WIDTH / SNAKE_SIZE) * SNAKE_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / SNAKE_SIZE) * SNAKE_SIZE;

    }
    public void move(){
        for (int i = bodyParts; i > 0; i--){
            snakeBodyXCoordinate[i] = snakeBodyXCoordinate[i-1];
            snakeBodyYCoordinate[i] = snakeBodyYCoordinate[i -1];
        }

        switch(direction){
            case 'U':
                snakeBodyYCoordinate[0] -=  SNAKE_SIZE;
                break;
            case 'D':
                snakeBodyYCoordinate[0] +=  SNAKE_SIZE;
                break;
            case 'L':
                snakeBodyXCoordinate[0] -=  SNAKE_SIZE;
                break;
            case 'R':
                snakeBodyXCoordinate[0] +=  SNAKE_SIZE;
                break;

        }

    }
    public void checkApple(){
        if((snakeBodyXCoordinate[0] == appleX) && (snakeBodyYCoordinate[0] == appleY)){
            bodyParts++;
            score++;
            generateApple();
        }

    }
    public void checkCollision(){
        for (int i = bodyParts; i > 0; i--){
            if ((snakeBodyXCoordinate[0] == snakeBodyXCoordinate[i]) && snakeBodyYCoordinate[0] == snakeBodyYCoordinate[i]) {
                running = false;
                break;
            }
        }
        if (snakeBodyXCoordinate[0] < 0){
            running = false;
        }
        if (snakeBodyXCoordinate[0] > SCREEN_WIDTH - SNAKE_SIZE){
            running = false;
        }
        if (snakeBodyYCoordinate[0] < 0){
            running = false;
        }
        if (snakeBodyYCoordinate[0] > SCREEN_HEIGHT - SNAKE_SIZE){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics score_metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+ score, (SCREEN_WIDTH - score_metrics.stringWidth("Score: "+ score))/2, g.getFont().getSize());


        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics gameover_metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - gameover_metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
