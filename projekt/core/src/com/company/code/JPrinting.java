package com.company.code;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class JPrinting implements PrintingService{

    JFrame frame = new JFrame("");

    public void printBoard(Board board) {

        frame = new JFrame("Drills");
//        frame.setSize(board.getWidth() + 100,board.getHeight() + 100);

        int frameWidth = 500;
        int frameHeight = 600;

        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {

                double widthScale = calculateScale(board.getWidth(), frameWidth - 100);
                double heightScale = calculateScale(board.getHeight(), frameHeight - 100 - 40);
                double scale = Math.min(widthScale, heightScale);


                int xDistance = (int) (50 / scale);//(int) (restOfXPlace / scale);//(int) ((frameWidth - board.getWidth()*scale)/2/scale);
                int yDistance = (int) (50 / scale);//(int) (restOfYPlace / sca le);//(int) ((frameHeight - (board.getHeight()-50/scale)*scale)/2/scale);
                scale *= 0.9;


                Graphics2D g2d = (Graphics2D) g;
                AffineTransform tx = new AffineTransform();
                tx.concatenate(g2d.getTransform());
                tx.scale(scale, scale);
                g2d.setTransform(tx);
                g2d.drawRect(xDistance, yDistance, board.getWidth()/* + 2*/, board.getHeight()/* + 2*/);
//                g2d.setStroke(new BasicStroke(2));
                for (Drill drill : board.getDrills()) {
                    if (drill.isVertical()) {
                        if (drill.getDiameter() == 4)
                            g2d.setPaint(Color.blue);
                        else if (drill.getDiameter() == 8)
                            g2d.setPaint(Color.red);
                    } else {
                        g2d.setPaint(Color.black);
                    }
                    int x = board.getWidth() - drill.getY().intValue() - (drill.getDiameter().intValue()) - 1;
                    int y = /*board.getHeight() - */drill.getX().intValue() - (drill.getDiameter().intValue()) - 1;
                    g2d.drawOval(x + xDistance, y + yDistance, drill.getDiameter().intValue() * 2, drill.getDiameter().intValue() * 2);
                }
                for (Saw line : board.getLines()) {
                    g2d.setColor(Color.black);
                    g2d.drawLine((int) (board.getWidth() - line.getStartY() + xDistance), (int) line.getStartX() + yDistance, (int) (board.getWidth() - line.getEndY() + xDistance), (int) line.getEndX() + yDistance);
                }
                for (Frez line : board.getFrezLines()) {
                    g2d.setColor(Color.RED);
                    g2d.drawLine((int) (board.getWidth() - line.getSpy() + xDistance), (int) line.getSpx() + yDistance, (int) (board.getWidth() - line.getEpy() + xDistance), (int) line.getEpx() + yDistance);
                }
            }
        };
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean isFrameOpen(){
        return frame.isActive();
    }

    public JFrame getFrame() {
        return frame;
    }

    private static Double calculateScale(double boardSize, double frameSize) {
        return frameSize / boardSize;
    }
}
