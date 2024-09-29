package com.ethpalser;

import com.ethpalser.cli.console.ConsoleRunner;
import com.ethpalser.cli.menu.Menu;
import com.ethpalser.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        Menu main = new MainMenu();
        ConsoleRunner runner = new ConsoleRunner(main);
        runner.open();
    }

}
