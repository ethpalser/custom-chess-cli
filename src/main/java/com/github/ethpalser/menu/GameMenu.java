package main.java.com.github.ethpalser.menu;

import com.chess.game.Game;
import com.chess.game.Vector2D;
import com.chess.game.movement.Action;
import com.github.ethpalser.menu.ActionMenu;
import com.github.ethpalser.menu.MenuAction;
import com.github.ethpalser.menu.MenuItem;

public class GameMenu extends ActionMenu {

    private Game game;

    public GameMenu(Game game) {
        super("Game");
        this.game = game;
        this.addChildren(
                this.setupMovePieceCommand(),
                this.setupSaveCommand(),
                this.setupUndoCommand(),
                this.setupRedoCommand(),
                this.setupExitCommand()
        );
    }

    @Override
    public void updateDisplay() {
        this.setTextDisplay(this.game.getBoard().toString());
    }

    private MenuItem setupMovePieceCommand() {
        return new MenuAction("Move") {
            @Override
            public void execute(String s) {
                // Todo: Convert string s to two vectors and verify it is valid
                game.executeAction(new Action(game.getTurnColour(), new Vector2D(), new Vector2D()));
            }
        };
    }

    private MenuItem setupSaveCommand() {
        return new MenuAction("Save") {
            @Override
            public void execute(String s) {

            }
        };
    }

    private MenuItem setupUndoCommand() {
        return new MenuAction("Undo") {
            @Override
            public void execute(String s) {

            }
        };
    }

    private MenuItem setupRedoCommand() {
        return new MenuAction("Redo") {
            @Override
            public void execute(String s) {

            }
        };
    }

    /**
     * Exit should Save the game and then leave. This should override all exit commands of the menu.
     *
     * @return MenuItem that performs an action before exiting the menu.
     */
    private MenuItem setupExitCommand() {
        return new MenuAction("Exit") {
            @Override
            public void execute(String s) {

            }
        };
    }

}
