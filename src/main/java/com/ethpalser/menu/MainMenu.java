package com.ethpalser.menu;

import com.ethpalser.chess.game.ChessGame;
import com.ethpalser.chess.game.Game;
import com.ethpalser.cli.menu.Context;
import com.ethpalser.cli.menu.MenuItem;
import com.ethpalser.cli.menu.SimpleMenu;
import com.ethpalser.cli.menu.event.EventType;
import com.ethpalser.data.Saves;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class MainMenu extends SimpleMenu {

    public MainMenu() {
        super("Main");

        this.addChild(new GameMenu(null));
        this.addChild(setupResumeCommand());

        // Before rendering menu, check if there is anything to load
        this.addEventListener(EventType.PRE_RENDER, event -> {
            Path savePath = Paths.get(Saves.GAME_FILE_DIR);
            if (Files.isDirectory(savePath, LinkOption.NOFOLLOW_LINKS)) {
                MenuItem resumeMenu = this.getChild("Resume");
                try (Stream<Path> paths = Files.list(savePath)) {
                    boolean canLoad = paths.findAny().isPresent();
                    // Todo: Replace 'toggle' with 'set' to remove these hidden checks
                    if ((canLoad && resumeMenu.isHidden()) || (!canLoad && !resumeMenu.isHidden())) {
                        resumeMenu.toggleHidden();
                    }
                } catch (IOException ex) {
                    if (!resumeMenu.isHidden()) {
                        resumeMenu.toggleHidden();
                    }
                }
            }
        });
    }

    // Load the latest game
    private MenuItem setupResumeCommand() {
        MenuItem resumeAction = new MenuItem("Resume");

        resumeAction.addEventListener(EventType.SELECT, event -> {
            Path savePath = Paths.get(Saves.GAME_FILE_DIR);
            String content = null;
            if (Files.isDirectory(savePath, LinkOption.NOFOLLOW_LINKS)) {
                try (Stream<Path> paths = Files.list(savePath)) {
                    Path latest = paths.filter(path -> !Files.isDirectory(path))
                            .max(Comparator.comparingLong(f -> f.toFile().lastModified()))
                            .orElse(null);
                    if (latest != null) {
                        content = Files.readString(latest);
                    }
                } catch (IOException ex) {
                    // Do nothing
                }
            }
            if (content != null) {
                Game resumed = ChessGame.fromJson(content);
                Context.getInstance().push(new GameMenu(resumed));
            }
        });
        return resumeAction;
    }

}
