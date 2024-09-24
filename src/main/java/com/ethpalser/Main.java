package com.ethpalser;

import com.ethpalser.chess.board.BoardType;
import com.ethpalser.chess.board.ChessBoard;
import com.ethpalser.chess.game.ChessGame;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.log.ChessLog;
import com.ethpalser.cli.console.ConsoleRunner;
import com.ethpalser.cli.menu.Menu;
import com.ethpalser.data.DataWriter;
import com.ethpalser.data.SaveData;
import com.ethpalser.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        ChessLog log = new ChessLog();
        ChessBoard board = new ChessBoard(BoardType.STANDARD, log);
        Game chessGame = new ChessGame(board, log);

        Menu main = new MainMenu(chessGame);
        ConsoleRunner runner = new ConsoleRunner(main);
        runner.open();
        // Save data once the game ends todo: override back and exit commands in a game to save
        DataWriter dataWriter = new DataWriter();
        dataWriter.write(chessGame, Game.class, SaveData.FILE_DIR);
    }

}
