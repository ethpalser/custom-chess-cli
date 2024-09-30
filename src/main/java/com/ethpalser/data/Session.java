package com.ethpalser.data;

import com.ethpalser.chess.game.Game;
import com.ethpalser.chess.piece.Colour;
import com.ethpalser.chess.view.GameView;

public class Session {

    private final Colour player;
    private final GameView game;

    public Session(Game game, Colour player) {
        this.game = new GameView(game);
        this.player = player;
    }

    public Colour getPlayer() {
        return player;
    }

    public GameView getGameView() {
        return game;
    }
}
