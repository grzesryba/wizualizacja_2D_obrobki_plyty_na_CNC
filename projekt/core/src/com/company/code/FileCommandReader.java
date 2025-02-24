package com.company.code;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCommandReader /*extends JFrame implements ActionListener*/ {

    private JButton button;
    float frezStartX = 0;
    float frezStartY = 0;


//    private File chosenFile = null;

//    public File getChosenFile() {
//        if(chosenFile!=null)
//        return chosenFile;
//        else
//            return new File("");
//    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == button){
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setCurrentDirectory(new File("C:\\Users\\Grzesiu\\Desktop\\projekt do taty na maszynę\\ProgramyTesty"));
//            int response = fileChooser.showOpenDialog(null);
//            if(response == JFileChooser.APPROVE_OPTION){
//                chosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                System.out.println(chosenFile);
//                this.dispose();
//            }
//        }
//    }

//    public void choseFile(){
//        JFrame frame = new JFrame("Choose File");
//        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        frame.setLayout(new FlowLayout());
//
//        button = new JButton("Select File");
//        button.addActionListener(this);
//
//        frame.setVisible(true);
//        frame.add(button);
//        frame.pack();
//    }

    public List<String> readLinesFromFile(File inputFile) {
        List<String> lines = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile))
        ) {
            String s = reader.readLine();
            while (s != null) {
                lines.add(s);
                s = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public Board readCommands(List<String> commands) {
        int boardHeight = 0;
        int boardWidth = 0;
        int sawStartX = 0;
        int sawStartY = 0;
        Optional<String> chosenTool = Optional.empty();
        Board board = null;

        for (String command : commands) {

            if (board == null && boardHeight != 0 && boardWidth != 0) {
                board = new Board(boardHeight, boardWidth);
            } else if (command.contains("(R")) {
                if (command.contains("(R170")) {
                    boardHeight = Integer.parseInt(command.replace("(R170=", "").replace(")", ""));
                } else if (command.contains("(R169")) {
                    boardWidth = Integer.parseInt(command.replace("(R169=", "").replace(")", ""));
                }
            } else if (command.contains("M06T")) {
                chosenTool = Optional.of("M" + command.replace("M06T", ""));
            } else if (command.contains("G0") && command.contains("X") && command.contains("Y") && !chosenTool.get().equals("M95") && !chosenTool.get().equals("M41")) {
                createDrill(board, chosenTool, command);
            } else if (chosenTool.isPresent() && chosenTool.get().equals("M72") && command.contains("G1")) {
                createDrillM72(board, command);
            } else if (chosenTool.isPresent() && chosenTool.get().equals("M95") && command.contains("G0")) {
                String[] s = command.split(" ");
                for (String s1 : s) {
                    if (s1.contains("X")) {
                        sawStartX = Integer.parseInt(s1.replace("X", ""));
                    } else if (s1.contains("Y")) {
                        sawStartY = Integer.parseInt(s1.replace("Y", ""));
                    }
                }
            } else if (chosenTool.isPresent() && chosenTool.get().equals("M95") && command.contains("G1") && command.contains("X") && command.contains("Y")) {
                createSawLine(board, command, sawStartX, sawStartY);
            }


            //Frez
            if (command.contains("M06T41") || command.contains("M6T41")) {
                chosenTool = Optional.of("M41");
            } else if (command.contains("G0") && command.contains("X") && command.contains("Y") && chosenTool.isPresent() && chosenTool.get().equals("M41")) {
                String[] s = command.split(" ");
                for (String s1 : s) {
                    if(s1.contains("X"))
                        frezStartX = Float.parseFloat(s1.replace("X",""));
                    if(s1.contains("Y"))
                        frezStartY = Float.parseFloat(s1.replace("Y",""));
                }
            } else if (command.contains("G1") && command.contains("X") && command.contains("Y") && chosenTool.isPresent() && chosenTool.get().equals("M41")) {
                createFrez(board, command, frezStartX, frezStartY);
            }


        }
        return board;
    }

    private void createFrez(Board board, String command, float frezStartX, float frezStartY) {
        float epx = 0;
        float epy = 0;
        String[] s = command.split(" ");
        for (String s1 : s) {
            if(s1.contains("X"))
                epx = Float.parseFloat(s1.replace("X",""));
            if(s1.contains("Y"))
                epy = Float.parseFloat(s1.replace("Y",""));
        }
        board.getFrezLines().add(new Frez(frezStartX,frezStartY,epx,epy));
        frezStartX = epx;
        frezStartY = epy;
    }

    private void createSawLine(Board board, String command, int sawStartX, int sawStartY) {
        int sawEndX = 0;
        int sawEndY = 0;
        String[] s = command.split(" ");
        for (String s1 : s) {
            if (s1.contains("X")) {
                sawEndX = Integer.parseInt(s1.replace("X", ""));
            } else if (s1.contains("Y")) {
                sawEndY = Integer.parseInt(s1.replace("Y", ""));
            }
        }

        board.getLines().add(new Saw(sawStartX, sawStartY, sawEndX, sawEndY));

    }

    private void createDrillM72(Board board, String command) {
        int depth = 0;
        String[] s = command.split(" ");
        for (String value : s) {
            if (value.contains("X")) {
                depth = board.getHeight() - Integer.parseInt(value.replace("X", ""));
            } else if (value.contains("Y")) {
                depth = board.getWidth() - Integer.parseInt(value.replace("Y", ""));
            }
        }
        int diameter = depth == 23 ? 8 : 4;
        board.getDrills().getLast().setDiameter((float) diameter);
    }

    private void createDrill(Board board, Optional<String> chosenTool0, String line) {
        String chosenTool = chosenTool0
                .filter(s -> !s.equals("M"))
                .orElseThrow(() -> new MyException("Wybrano zły plik lub nie wybrano wiertła"));
        boolean verticalDrill = chosenTool.equals("M72");
        float x = 0;
        float y = 0;
        Tools tool = Tools.valueOf(chosenTool);
        String[] s = line.split(" ");
        for (String value : s) {
            if (value.contains("X")) {
                x = Float.parseFloat(value.replace("X", ""));
            } else if (value.contains("Y")) {
                y = Float.parseFloat(value.replace("Y", ""));
            }
        }

        board.getDrills().add(new Drill(verticalDrill, x, y, tool.getDiameter()));

    }


}
