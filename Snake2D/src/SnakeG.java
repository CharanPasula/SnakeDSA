import javax.swing.*;

public class SnakeG extends JFrame {

    Board board;
    SnakeG(){
        board=new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }


    public static void main(String[] args) {

        SnakeG snake=new SnakeG();
        }
    }
