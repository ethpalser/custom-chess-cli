package com.github.ethpalser;

import com.github.ethpalser.menu.MainMenu;
import com.github.ethpalser.menu.Menu;
import com.github.ethpalser.ui.CommandMenu;

public class Main {

    public static void main(String[] args) {
        Menu main = new MainMenu();
        CommandMenu runner = new CommandMenu(main);
        runner.open();
    }

}
