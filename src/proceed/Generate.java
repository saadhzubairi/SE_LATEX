package proceed;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Generate implements Initializable {
    public TextArea ta;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void importer(String s){
        ta.setEditable(false);
        ta.setText(s);
    }
}
