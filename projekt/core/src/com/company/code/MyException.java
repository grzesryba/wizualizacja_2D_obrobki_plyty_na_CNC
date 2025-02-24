package com.company.code;

import javax.swing.*;

public class MyException extends RuntimeException {
    public MyException(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

}
