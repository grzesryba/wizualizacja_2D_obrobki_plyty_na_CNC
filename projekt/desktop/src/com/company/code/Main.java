package com.company.code;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        wybór folderu startowego do przeszukiwania plików G-code:
//        File startingPath = new File("C:/isac/lav/lav");    //na Maszynę
//        File startingPath = new File("C:/CNCPROG");   //na laptopa do taty
        File startingPath = new File("C:\\Users\\Grzesiu\\programowanie\\java\\wizualizacja_2D_obrobki_plyty_na_CNC\\przykladowe_programy_G_code");  //mój laptop

        FileCommandReader fileReader = new FileCommandReader();
        PrintingService printingService = new JPrinting();

        JButton button = new JButton("Select File");
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(startingPath);
        for (; ; ) {

            File file = new File("");
            int x = fileChooser.showOpenDialog(button);
            if (x == JFileChooser.CANCEL_OPTION) {
                break;
            } else if (x == JFileChooser.APPROVE_OPTION)
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            if (file.exists()) {

                try {
                    List<String> list = fileReader.readLinesFromFile(file);

                    Board board = fileReader.readCommands(list);

                    printingService.printBoard(board);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
            while (printingService.getFrame().isActive()) {
                continue;
            }
        }
        System.out.println("Koniec");
    }
}
