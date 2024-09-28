package com.ethpalser.menu;

import com.ethpalser.chess.game.Action;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.game.GameStatus;
import com.ethpalser.chess.piece.Colour;
import com.ethpalser.chess.space.Point;
import com.ethpalser.cli.console.ConsoleWriter;
import com.ethpalser.cli.menu.Context;
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
    private final ConsoleWriter cw;

    public GameMenu(Game game) {
        super("Start");

        this.game = game;
        this.writer = new DataWriter();
        this.addEventListener(EventType.PRE_RENDER, event -> this.setTextDisplay(game.getBoard().toString()));
        this.addEventListener(EventType.ON_CLOSE, event -> writer.write(this.game.toJson(), SaveData.FILE_DIR, SaveData.FILE_NAME));

        this.addChildren(
                this.setupMovePieceCommand(),
                this.setupSaveCommand()
        );
        this.cw = new ConsoleWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    @Override
    public boolean isSubmitOnLeave() {
        return true;
    }

    private MenuItem setupMovePieceCommand() {
        MenuItem movePieceAction = new MenuItem("Move");
        movePieceAction.addEventListener(EventType.SELECT, event -> {
            String[] args = event.getArgs();
            // Assuming args are: [point1Str, point2Str]
            if (args.length == 2) {
                Colour colour = game.getTurn() % 2 == 0 ? Colour.BLACK : Colour.WHITE;
                GameStatus status = game.updateGame(new Action(colour, new Point(args[0]), new Point(args[1])));
                if (!GameStatus.ONGOING.equals(status)) {
                    try {
                        switch (status) {
                            case WHITE_IN_CHECK -> cw.write("white check\n");
                            case BLACK_IN_CHECK -> cw.write("black check\n");
                            case STALEMATE -> {
                                cw.write("stalemate\n");

                                Context.getInstance().pop(); // Leave the game
                            }
                            case WHITE_WIN, BLACK_WIN -> {
                                cw.write("checkmate!\n");

                                Context.getInstance().pop(); // Leave the game
                            }
                            case NO_CHANGE -> cw.write("move not allowed\n");
                            default -> {
                                // Do nothing
                            }
                        }
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
        saveAction.addEventListener(EventType.SELECT, event -> {
            writer.write(this.game.toJson(), SaveData.FILE_DIR, SaveData.FILE_NAME);
            try {
                cw.write("game saved\n");
            } catch (IOException e) {
                // do nothing
            }
        });
        return saveAction;
    }

}
