package com.ethpalser.menu;

import com.ethpalser.chess.board.BoardType;
import com.ethpalser.chess.board.ChessBoard;
import com.ethpalser.chess.game.Action;
import com.ethpalser.chess.game.ChessGame;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.game.GameStatus;
import com.ethpalser.chess.log.ChessLog;
import com.ethpalser.chess.piece.Colour;
import com.ethpalser.chess.space.Point;
import com.ethpalser.cli.console.ConsoleWriter;
import com.ethpalser.cli.menu.Context;
import com.ethpalser.cli.menu.Menu;
import com.ethpalser.cli.menu.MenuItem;
import com.ethpalser.cli.menu.event.EventListener;
import com.ethpalser.cli.menu.event.EventType;
import com.ethpalser.data.Saves;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GameMenu extends Menu {

    private final Game current;
    private final ConsoleWriter cw;

    public GameMenu(Game game) {
        super("Start");

        if (game != null) {
            this.current = game;
        } else {
            ChessLog log = new ChessLog();
            ChessBoard board = new ChessBoard(BoardType.STANDARD, log);
            this.current = new ChessGame(board, log);
        }
        this.cw = new ConsoleWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        this.addEventListener(EventType.PRE_RENDER, event -> this.setTextDisplay(this.current.getBoard().toString()));
        this.addEventListener(EventType.ON_CLOSE, this.createSaveListener(this.current));

        this.addChildren(
                this.setupMovePieceCommand(),
                this.setupSaveCommand()
        );
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
                Colour colour = current.getTurn() % 2 == 0 ? Colour.BLACK : Colour.WHITE;
                GameStatus status = current.updateGame(new Action(colour, new Point(args[0]), new Point(args[1])));
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
        saveAction.addEventListener(EventType.SELECT, this.createSaveListener(this.current));
        return saveAction;
    }

    private EventListener createSaveListener(Game game) {
        return event -> {
            long unixTime = System.currentTimeMillis() / 1000L;
            Saves.writeAsString(game.toJson(), Saves.GAME_FILE_DIR, Saves.GAME_FILE_NAME + unixTime + ".save");
            if (EventType.SELECT.equals(event.getEventType())) {
                try {
                    cw.write("Save complete!");
                } catch (IOException ex) {
                    System.err.println("Save failed!");
                }
            }
        };
    }

}
