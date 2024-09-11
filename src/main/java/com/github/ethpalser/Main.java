package com.github.ethpalser;

import com.ethpalser.chess.board.BoardType;
import com.ethpalser.chess.board.ChessBoard;
import com.ethpalser.chess.game.ChessGame;
import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.log.ChessLog;
import com.ethpalser.cli.console.ConsoleReader;
import com.ethpalser.cli.console.ConsoleRunner;
import com.ethpalser.cli.console.ConsoleWriter;
import com.ethpalser.cli.menu.Context;
import com.ethpalser.cli.menu.Menu;
import com.github.ethpalser.data.DataWriter;
import com.github.ethpalser.data.SaveData;
import com.github.ethpalser.menu.MainMenu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) {
        Context context = Context.getInstance();
        ChessLog log = new ChessLog();
        ChessBoard board = new ChessBoard(BoardType.STANDARD, log);
        Game chessGame = new ChessGame(board, log);
        Menu main = new MainMenu(chessGame);
        ConsoleRunner runner = new ConsoleRunner(context, main);
        ConsoleReader reader = new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
        ConsoleWriter writer = new ConsoleWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        runner.open(reader, writer);
        // Save data once the game ends todo: override back and exit commands in a game to save
        DataWriter dataWriter = new DataWriter();
        dataWriter.write(chessGame, Game.class, SaveData.FILE_DIR);
    }

}
