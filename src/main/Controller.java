package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import proceed.Editor_Control;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public JFXComboBox doc_type_c;
    public JFXComboBox font_size_c;
    public JFXComboBox font_style_c;
    public TextField title_t;
    public TextField subtitle_t;
    public TextField author_t;
    public DatePicker date_picker_t;
    public JFXButton proceed_bt;
    public ImageView img_view;

    public ObservableList<String> types = FXCollections.observableArrayList("Article","Presentation");
    public ObservableList<String> styles = FXCollections.observableArrayList("Serif","Sans-Serif","Monospace");
    public ObservableList<String> sizes = FXCollections.observableArrayList("a4paper","letterpaper","a5paper","b5paper","executivepaper","legalpaper");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File file = new File("C:\\Users\\saadh\\Desktop\\Study\\Semester_5\\Software Engineering\\SE_LATEX\\src\\resources\\LATEX_LOGO.png");
        Image image = new Image(file.toURI().toString());
        img_view.setImage(image);

        doc_type_c.setItems(types);
        font_size_c.setItems(sizes);
        font_style_c.setItems(styles);

        doc_type_c.setValue("Article");
        font_size_c.setValue("a4paper");
        font_style_c.setValue("Serif");

        author_t.setText("<Author Name here>");
        title_t.setText("<Title goes here>");
        subtitle_t.setText("<Subtitle here>");

        date_picker_t.setValue(LocalDate.now());

        proceed_bt.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proceed/Editor.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Editor_Control editor = fxmlLoader.getController();
                editor.injector(doc_type_c.getValue().toString(),
                        font_size_c.getValue().toString(),
                        font_style_c.getValue().toString(),
                        title_t.getText(),
                        subtitle_t.getText(),
                        author_t.getText());
                Stage stage = new Stage();
                stage.setTitle("EDITOR");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



}