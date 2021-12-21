package proceed;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;



public class ListItemAdder implements Initializable {

    public TextArea text_area;
    public JFXButton add_bt;
    public JFXButton cancel_bt;
    public ListView lfrominj;
    int idx = 0;
    boolean edit = false;
    public void injector(ListView l){
        lfrominj = l;
    }

    public void injector(ListView l,String s,int n){
        idx = n;
        edit = true;
        lfrominj = l;
        text_area.setText(s);
        add_bt.setText("Save Changes");
        cancel_bt.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_bt.setOnAction(actionEvent -> {
            if(edit){
                lfrominj.getItems().add(idx,text_area.getText());
            }
            else{
                lfrominj.getItems().add(text_area.getText());
            }
            closeButtonAction();
        });
        cancel_bt.setOnAction(actionEvent -> closeButtonAction());
    }

    private void closeButtonAction(){
        Stage stage = (Stage) cancel_bt.getScene().getWindow();
        stage.close();
    }

}
