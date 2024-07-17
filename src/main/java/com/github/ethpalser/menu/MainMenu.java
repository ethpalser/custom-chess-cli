package main.java.com.github.ethpalser.menu;

import com.chess.game.Game;
import com.github.ethpalser.menu.MenuAction;
import com.github.ethpalser.menu.MenuItem;
import com.github.ethpalser.menu.SimpleMenu;

public class MainMenu extends SimpleMenu {

    private Game current;

    public MainMenu() {
        super("Main");
        this.current = new Game();
        this.addChild(setupResumeCommand());
        this.addChild(new GameMenu(current));
    }

    // Load the latest game
    private MenuItem setupResumeCommand() {
        return new MenuAction("Resume") {
            @Override
            public void execute(String s) {

            }
        };
    }

}
