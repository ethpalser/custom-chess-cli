package com.ethpalser.menu;

import com.ethpalser.chess.game.Action;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.game.GameStatus;
import com.ethpalser.chess.piece.Colour;
import com.ethpalser.chess.space.Point;
import com.ethpalser.cli.console.ConsoleWriter;
import com.ethpalser.cli.menu.Menu;
import com.ethpalser.cli.menu.MenuItem;
import com.ethpalser.cli.menu.event.EventType;
import com.ethpalser.data.DataWriter;
import com.ethpalser.data.SaveData;
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
        MenuItem movePieceAction = new MenuItem("Move");
        movePieceAction.addEventListener(EventType.EXECUTE, event -> {
            String[] args = event.getCommand().split("\\w");
            // Assuming args are: [commandStr, point1Str, point2Str]
            if (args.length == 3) {
                Colour colour = game.getTurn() % 2 == 0 ? Colour.BLACK : Colour.WHITE;
                GameStatus status = game.updateGame(new Action(colour, new Point(args[1]), new Point(args[2])));

                if (!GameStatus.ONGOING.equals(status)) {
                    ConsoleWriter cw = new ConsoleWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
                    try {
                        switch (status) {
                            case WHITE_IN_CHECK -> cw.write("white check");
                            case BLACK_IN_CHECK -> cw.write("black check");
                            case STALEMATE -> cw.write("stalemate");
                            case WHITE_WIN, BLACK_WIN -> cw.write("checkmate!");
                            case NO_CHANGE -> cw.write("move not allowed");
                            default -> {
                                // Do nothing
                            }
                        }
                        cw.close();
                    } catch (IOException ex) {
                        // do nothing
                    }
                }
            }
        });
        return movePieceAction;
    }

    private MenuItem setupSaveCommand() {
        MenuItem saveAction = new MenuItem("Save");
        saveAction.addEventListener(EventType.EXECUTE, event -> {
            writer.write(this.game.toJson(), String.class, SaveData.FILE_DIR);
        });
        return saveAction;
    }

}
