package com.company.code;

import javax.swing.*;

public interface PrintingService {
    void printBoard(Board board);
    JFrame getFrame();
    boolean isFrameOpen();
}
