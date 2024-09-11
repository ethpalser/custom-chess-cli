package com.github.ethpalser.menu;

import com.ethpalser.chess.game.Action;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.game.GameStatus;
import com.ethpalser.chess.piece.Colour;
import com.ethpalser.chess.space.Point;
import com.ethpalser.cli.console.ConsoleWriter;
import com.ethpalser.cli.menu.Menu;
import com.ethpalser.cli.menu.MenuItem;
import com.ethpalser.cli.menu.event.EventType;
import com.github.ethpalser.data.DataWriter;
import com.github.ethpalser.data.SaveData;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GameMenu extends Menu {

    private final Game game;
    private final DataWriter writer;

    public GameMenu(Game game) {
        super("Start");
        this.game = game;
        this.writer = new DataWriter();
        this.addEventListener(EventType.PRE_RENDER, event -> this.setTextDisplay(game.getBoard().toString()));
        this.addChildren(
                this.setupMovePieceCommand(),
                this.setupSaveCommand()
        );
    }

    private MenuItem setupMovePieceCommand() {
        // todo: allow this command be the default command, so typing "move" is not required
        MenuItem movePieceAction = new MenuItem("Move");
        movePieceAction.addEventListener(EventType.EXECUTE, event ->
                game.executeAction(new Action(game.getTurnColour(), new Vector2D(), new Vector2D())));
        return movePieceAction;
    }

    private MenuItem setupSaveCommand() {
        MenuItem saveAction = new MenuItem("Save");
        saveAction.addEventListener(EventType.EXECUTE, event -> writer.write(this.game, Game.class, SaveData.FILE_DIR));
        return saveAction;
    }

}
