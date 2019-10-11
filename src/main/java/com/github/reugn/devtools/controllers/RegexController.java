package com.github.reugn.devtools.controllers;

import com.github.reugn.devtools.services.RegexService;
import com.github.reugn.devtools.utils.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegexController implements Initializable, Logger {

    @FXML
    private TextField regexExpression;
    @FXML
    private Button regexCalculateButton;
    @FXML
    private Button regexClearButton;
    @FXML
    private Label regexMessage;
    @FXML
    private TextArea regexTarget;
    @FXML
    private TextArea regexResult;
    @FXML
    private Label regexLabel;
    @FXML
    private CheckComboBox<String> regexFlagsComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        regexExpression.setPrefWidth(512);
        HBox.setMargin(regexExpression, new Insets(10, 5, 10, 0));
        HBox.setMargin(regexCalculateButton, new Insets(10, 5, 10, 0));
        HBox.setMargin(regexClearButton, new Insets(10, 5, 10, 0));
        HBox.setMargin(regexFlagsComboBox, new Insets(10, 5, 10, 0));

        regexLabel.setPadding(new Insets(15, 5, 10, 0));
        regexMessage.setPadding(new Insets(15, 5, 10, 0));
        regexMessage.setTextFill(Color.RED);

        regexFlagsComboBox.getItems().addAll("global", "multiline", "insensitive", "unicode");
        regexFlagsComboBox.setTitle("Flags");
        regexFlagsComboBox.getCheckModel().checkIndices(0);
    }

    @FXML
    public void handleKeyMatch(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            doMatch();
        }
    }

    @FXML
    public void handleMatch(ActionEvent actionEvent) {
        doMatch();
    }

    private void doMatch() {
        regexMessage.setText("");
        if (validateInput()) {
            String result = "";
            try {
                result = RegexService.match(regexExpression.getText(), regexTarget.getText(),
                        regexFlagsComboBox.getCheckModel().getCheckedItems());
                regexResult.setText(result);
            } catch (Exception e) {
                regexMessage.setText("Invalid regex");
            }
        }
    }

    private boolean validateInput() {
        resetBorders();
        boolean isValid = true;
        if (regexExpression.getText().isEmpty()) {
            regexExpression.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
            isValid = false;
        }
        if (regexTarget.getText().isEmpty()) {
            regexTarget.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
            isValid = false;
        }
        return isValid;
    }

    private void resetBorders() {
        regexExpression.setBorder(Border.EMPTY);
        regexTarget.setBorder(Border.EMPTY);
    }

    @FXML
    public void handleClear(ActionEvent actionEvent) {
        resetBorders();
        regexExpression.setText("");
        regexTarget.setText("");
        regexResult.setText("");
    }
}
