package com.ethpalser.menu;

import com.ethpalser.chess.game.Game;
import com.ethpalser.cli.menu.Context;
import com.ethpalser.cli.menu.MenuItem;
import com.ethpalser.cli.menu.SimpleMenu;
import com.ethpalser.cli.menu.event.EventType;

public class MainMenu extends SimpleMenu {

    private Game current;

    public MainMenu(Game game) {
        super("Main");
        this.current = game;
        // todo: check if there is at least one file to load to display resume and load
        this.addChild(new GameMenu(this.current));
        this.addChild(setupResumeCommand());
        this.addChild(new LoadMenu());
    }

    // Load the latest game
    private MenuItem setupResumeCommand() {
        MenuItem resumeAction = new MenuItem("Resume");
        resumeAction.addEventListener(EventType.SELECT, event -> {
            // todo: fetch most recent save file, set it to current
            Context.getInstance().push(new GameMenu(this.current));
        });
        return resumeAction;
    }

}
