package org.example.twothousandfortyeight;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.Random;

public class GameBoardController extends Backend {

    private final Backend backend = new Backend();
    private final Color foregroundColor = Color.BLACK;
    private final Color backgroundColor = Color.WHITE;

    @FXML
    private GridPane gameBoard;
    @FXML
    private Label score;

    @FXML
    public void handleKeyPressed(KeyEvent ke) {
        String before = Arrays.deepToString(backend.getBlocks());

        switch (ke.getCode()) {
            case UP -> backend.shift(true, true);
            case DOWN -> backend.shift(true, false);
            case LEFT -> backend.shift(false, false);
            case RIGHT -> backend.shift(false, true);
            default -> { return; }
        }

        if (!before.equals(Arrays.deepToString(backend.getBlocks()))) {
            backend.insert();
        }
        paintAll(backend.getBlocks());
    }

    private void paintAll(Block[][] blocks) {
        int c = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                GridPane pane = (GridPane) gameBoard.getChildren().get(c);

                pane.getChildren().clear();
                Label label = new Label(blocks[i][j] != null ? Integer.toString(blocks[i][j].getValue()):" ");
                label.setTextAlignment(TextAlignment.CENTER);
                label.setTextFill(backgroundColor);
                label.setFont(new Font(30));

                pane.add(label, 0, 0);
                pane.setAlignment(Pos.CENTER);

                if (blocks[i][j] != null) {

                    pane.setBackground(new Background(new BackgroundFill(foregroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    pane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                c++;
            }
        }
    }

    private int getRandomInt() {
        return new Random().nextInt(gameBoard.getRowCount() * gameBoard.getColumnCount());
    }

    public void initialize() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                GridPane pane = new GridPane();
                pane.setBackground(Background.fill(backgroundColor));
                GridPane.setHgrow(pane, Priority.ALWAYS);
                GridPane.setVgrow(pane, Priority.ALWAYS);
                pane.setAlignment(Pos.CENTER);
                pane.setBorder(new Border(new BorderStroke(foregroundColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


                Label emptyLabel = new Label(" ");
                pane.add(emptyLabel, 0, 0);

                this.gameBoard.add(pane, i, j);
            }
        }
        paintAll(backend.getBlocks());
    }
}
