package proceed;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Editor_Control implements Initializable {
    //Sidebar Buttons
    public JFXButton TEXT,LIST,CODE,PATH,INFO;
    //borderpane
    public BorderPane BORDERPANE;
    //anchors
    public AnchorPane PARA_SECTIONS_ANCHPANE,LIST_ANCHORPANE,CODE_ANCHORPANE,FILE_PATH_ANCHORPANE,PREAMBLE_ANCHORPANE;
    //preview textfield
    public TextArea PREVIEW_PANE;

    //PARA_SECTIONS_ANCHPANE
    public TextField ps_heading_tf;
    public TextArea ps_body_text_tf;
    public JFXButton add_par_bt,add_sec_bt,add_subsec_bt;
    public TextField item_tf;
    public JFXButton add_item;
    public JFXButton end_list;
    public JFXButton file_path_button;
    public TextField FilePath_addition_tf;
    public JFXButton line_break_bt;

    //CODE
    public JFXButton code_add;
    public TextArea code_tf;
    public TextArea code_text_tf;

    //FILE
    public JFXButton add_filepath;
    public TextArea fp_tf;

    //LIST_ANCHORPANE
    public JFXButton lv_new_item,lv_edit_item,lv_delete_item,lv_ADD;
    public ListView lv_list_view;
    public TextField lv_heading;

    //PREAMBLE_ANCHORPANE
    public JFXComboBox doc_type_c,font_size_c,font_style_c;
    public TextField title_t,subtitle_t,author_t;
    public DatePicker date_picker_t;

    public JFXButton zoom_in_pta, zoom_out_pta;
    public boolean isList_start = false;
    public JFXButton generate;
    public JFXButton undo_bt;
    public JFXButton file_path_item_bt;
    public Text prnt;


    int display_size_pta = 18;
    ObservableList<String> types = FXCollections.observableArrayList("Article","Presentation");
    ObservableList<String> styles = FXCollections.observableArrayList("Serif","Sans-Serif","Monospace");
    ObservableList<String> sizes = FXCollections.observableArrayList("a4paper","letterpaper","a5paper","b5paper","executivepaper","legalpaper");

    boolean section = false;

    String final_str = "";
    String last_state = "";
    String last_state_preview = "";
    boolean isLastState = false;
    public void injector(String doc_type, String size, String style, String title, String subtitle,String author){
        doc_type_c.setValue(doc_type);
        font_size_c.setValue(size);
        font_style_c.setValue(style);
        title_t.setText(title);
        subtitle_t.setText(subtitle);
        author_t.setText(author);

        doc_type_c.setItems(types);
        font_size_c.setItems(sizes);
        font_style_c.setItems(styles);
        PREVIEW_PANE.setWrapText(true);
        PREVIEW_PANE.setEditable(false);
        ps_body_text_tf.setWrapText(true);
        code_tf.setWrapText(true);
        fp_tf.setWrapText(true);

        date_picker_t.setValue(LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sidebar buttons
        TEXT.setOnAction(actionEvent -> {
            BORDERPANE.setCenter(PARA_SECTIONS_ANCHPANE);
        });
        LIST.setOnAction(actionEvent -> {
            BORDERPANE.setCenter(LIST_ANCHORPANE);
        });
        CODE.setOnAction(actionEvent -> {
            BORDERPANE.setCenter(CODE_ANCHORPANE);
        });
        PATH.setOnAction(actionEvent -> {
            BORDERPANE.setCenter(FILE_PATH_ANCHORPANE);
        });
        INFO.setOnAction(actionEvent -> {
            BORDERPANE.setCenter(PREAMBLE_ANCHORPANE);
        });

        zoom_in_pta.setOnAction(actionEvent -> {
            zoom_pta(+1);
        });
        zoom_out_pta.setOnAction(actionEvent -> {
            zoom_pta(-1);
        });

        Timeline UpdateTracker = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            if(title_t.getText().equals("") || subtitle_t.getText().equals("") || author_t.getText().equals("")) {
                generate.setDisable(true);
                print("Please enter all details in info section before generating");
            }
            else{
                generate.setDisable(false);
                emptify();
            }

            if(ps_body_text_tf.getText().equals(""))
                line_break_bt.setDisable(false);
            else line_break_bt.setDisable(true);

            if(FilePath_addition_tf.getText().equals("")){
                   file_path_button.setDisable(true);
                   file_path_item_bt.setDisable(true);
            }else{
                file_path_button.setDisable(false);
                file_path_item_bt.setDisable(false);
            }

            if(isList_start){
                add_par_bt.setDisable(true);
                add_sec_bt.setDisable(true);
                add_subsec_bt.setDisable(true);
                if(!FilePath_addition_tf.getText().equals("")) file_path_item_bt.setDisable(false);
                file_path_button.setText("Path as item");
                generate.setDisable(true);
                code_add.setText("Add Code as Listed Item");
                PATH.setDisable(true);
                LIST.setDisable(true);
            }
            else{
                if(!ps_heading_tf.getText().equals("")){
                    add_par_bt.setDisable(true);
                    add_sec_bt.setDisable(false);
                    if(section) add_subsec_bt.setDisable(false); }
                else if(ps_body_text_tf.getText().equals("")){
                    add_par_bt.setDisable(true);
                    add_sec_bt.setDisable(true);
                    add_subsec_bt.setDisable(true); }
                else if(ps_heading_tf.getText().equals("")) {
                    add_sec_bt.setDisable(true);
                    add_subsec_bt.setDisable(true);
                    add_par_bt.setDisable(false); }
                else if(!ps_body_text_tf.getText().equals("")){
                    add_par_bt.setDisable(false); }
                file_path_button.setText("Add Path");
                generate.setDisable(false);
                code_add.setText("Add Code");
                file_path_item_bt.setDisable(true);
                PATH.setDisable(false);
                LIST.setDisable(false);
            }

            if (lv_list_view.getSelectionModel().getSelectedItem()==null){
                 lv_edit_item.setDisable(true);
                 lv_delete_item.setDisable(true);
            }
            else {
                 lv_edit_item.setDisable(false);
                 lv_delete_item.setDisable(false);
            }


            if(item_tf.getText().equals("")){
                 add_item.setDisable(true);
                 if(isList_start) end_list.setDisable(false);
                 else end_list.setDisable(true);
            }
            else{
                 add_item.setDisable(false);
                 if(isList_start) end_list.setDisable(false);
                 else end_list.setDisable(true);
                 if(!FilePath_addition_tf.getText().equals(""))file_path_item_bt.setDisable(false);
            }

             if(code_tf.getText().equals("")) {
                 code_add.setDisable(true);
             }
             else{
                 code_add.setDisable(false);
             }

             if(isLastState){
                 undo_bt.setDisable(false);
             }
             else undo_bt.setDisable(true);

             if(fp_tf.getText().equals(""))
                 add_filepath.setDisable(true);
             else add_filepath.setDisable(false);

        }));

        add_par_bt.setOnAction(actionEvent -> {
            save_last_state();
            PREVIEW_PANE.setText(PREVIEW_PANE.getText()+ps_body_text_tf.getText()+" ");
            final_str = final_str +"\n" +ps_body_text_tf.getText();
            ps_body_text_tf.setText("");
        });
        add_sec_bt.setOnAction(actionEvent -> {
            save_last_state();
            PREVIEW_PANE.setText(PREVIEW_PANE.getText()+"\n================================\n"+ps_heading_tf.getText().toUpperCase()+"\n================================\n"+ps_body_text_tf.getText() + " ");
            final_str = final_str + "\n" +
                    "\\section{"+ps_heading_tf.getText()+"}\n"+ps_body_text_tf.getText();
            section = true;
            ps_heading_tf.setText("");
            ps_body_text_tf.setText("");
        });
        add_subsec_bt.setOnAction(actionEvent -> {
            save_last_state();
            PREVIEW_PANE.setText(PREVIEW_PANE.getText()+"\n-\n"+ps_heading_tf.getText().toUpperCase()+"\n- - - - - - - - - - \n"+ps_body_text_tf.getText() + " ");
            final_str = final_str + "\n" +
                    "\\subsection{"+ps_heading_tf.getText()+"}\n"+ps_body_text_tf.getText();
            ps_heading_tf.setText("");
            ps_body_text_tf.setText("");
        });
        add_item.setOnAction(actionEvent -> {
            save_last_state();
            if(!isList_start){
                final_str = final_str + "\n\\begin{enumerate}\n\\item " + item_tf.getText();
                if(item_tf.getText().contains("\\path")){
                    String path = item_tf.getText();
                    PREVIEW_PANE.setText(
                            PREVIEW_PANE.getText()
                            + path.substring(0, path.indexOf("\\path{"))
                            + " PATH:["
                            + path.substring(path.indexOf("\\path{")+6,path.indexOf("}"))
                            + "]");
                }
                else
                PREVIEW_PANE.setText(PREVIEW_PANE.getText() + "\nLIST:[\n(->)\t"+item_tf.getText());
            }
            else{
                final_str = final_str + "\n\\item " + item_tf.getText();
                if(item_tf.getText().contains("\\path")){
                    String path = item_tf.getText();
                    PREVIEW_PANE.setText(
                            PREVIEW_PANE.getText() +
                            path.substring(0, path.indexOf("\\path{"))
                                    + " PATH:["
                                    + path.substring(path.indexOf("\\path{")+6,path.indexOf("}"))
                                    + "]");
                }
                else
                PREVIEW_PANE.setText(PREVIEW_PANE.getText() + "\n(->)\t"+item_tf.getText());
            }
            item_tf.setText("");
            isList_start = true;
        });
        end_list.setOnAction(actionEvent -> {
            isList_start = false;
            PREVIEW_PANE.setText(PREVIEW_PANE.getText() +"\n]\n");
            final_str = final_str + "\n\\end{enumerate}";
        });
        file_path_button.setOnAction(actionEvent -> {
            save_last_state();
            if(isList_start){
                PREVIEW_PANE.setText(PREVIEW_PANE.getText()+"\n(->)\tFILEPATH:["+FilePath_addition_tf.getText()+"] ");
                final_str = final_str +"\n\\item\t\\path{" + FilePath_addition_tf.getText() + "}";
            }
            else{
                PREVIEW_PANE.setText(PREVIEW_PANE.getText()+"FILEPATH:["+FilePath_addition_tf.getText()+"] ");
                final_str = final_str +"\n\\path{" + FilePath_addition_tf.getText() + "}";
            }

            FilePath_addition_tf.setText("");
        });
        file_path_item_bt.setOnAction(actionEvent -> {
            item_tf.setText(item_tf.getText() + " \\path{"+ FilePath_addition_tf.getText()+"} ");
            FilePath_addition_tf.setText("");
        });
        line_break_bt.setOnAction(actionEvent -> {
            PREVIEW_PANE.setText(PREVIEW_PANE.getText() + "\n");
            final_str = final_str + "\\\\";
        });

        lv_new_item.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proceed/list_item_adder.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                ListItemAdder listItemAdder = fxmlLoader.getController();
                listItemAdder.injector(lv_list_view);
                Stage stage = new Stage();
                stage.setTitle("Add Item");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            lv_list_view.setStyle("-fx-background-color: #0e0e0e");
        });
        lv_edit_item.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proceed/list_item_adder.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                ListItemAdder listItemAdder = fxmlLoader.getController();
                listItemAdder.injector(lv_list_view,lv_list_view.getSelectionModel().getSelectedItem().toString(),lv_list_view.getSelectionModel().getSelectedIndex());
                Stage stage = new Stage();
                stage.setTitle("Add Item");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            lv_list_view.getItems().remove(lv_list_view.getSelectionModel().getSelectedIndex());
            });
        lv_delete_item.setOnAction(actionEvent -> {
                lv_list_view.getItems().remove(lv_list_view.getSelectionModel().getSelectedIndex());
            });
        lv_ADD.setOnAction(actionEvent -> {
            save_last_state();
            List<String> all_items = lv_list_view.getItems();

            String str = "\\begin{enumerate}\n";
            for (String s:all_items) {
                str = str + "\\item " + s + "\n";
            }
            str = str + "\\end{enumerate}";

            final_str = final_str + "\n" + str;

            String prestr = "\\>(_LIST_)\n";
            for (String s:all_items) {
                prestr = prestr + "*) " + s + "\n";
            }
            PREVIEW_PANE.setText(PREVIEW_PANE.getText()+"\n"+prestr);

            System.out.println(final_str);

            lv_list_view.getItems().clear();
        });

        code_add.setOnAction(actionEvent -> {
            save_last_state();
            //no super text
            if(code_text_tf.getText().equals("")){
                if(isList_start){
                    PREVIEW_PANE.setText(PREVIEW_PANE.getText() +"\n(->) CODE:[\n"
                            + code_text_tf + "\n"
                            + code_tf.getText()
                            + "\n]");
                    final_str = final_str + "\n\\item "
                            +code_text_tf.getText() + "\n"
                            +"\\begin{lstlisting}[language=Java]\n"
                            + "\n"
                            + code_tf.getText()+
                            "\\end{lstlisting}";
                }

                else {
                    PREVIEW_PANE.setText(PREVIEW_PANE.getText() +"\nCODE:[\n"
                            + code_text_tf + "\n"
                            + code_tf.getText()
                            + "\n]");
                    final_str = final_str + "\n"
                            + code_text_tf.getText()
                            + "\n"
                            + "\\begin{lstlisting}[language=Java]\n"
                            + code_tf.getText()
                            + "\\end{lstlisting}";
                }
            }
            else {
                if(isList_start){
                    PREVIEW_PANE.setText(PREVIEW_PANE.getText() +"\n(->) CODE:[\n"
                                                                + code_tf.getText()
                                                                + "\n]");
                    final_str = final_str + "\n\\item "+ code_text_tf.getText()
                            +"\n\\begin{lstlisting}[language=Java]\n"
                            + code_tf.getText()
                            + "\n\\end{lstlisting}";
                }
                else {
                    PREVIEW_PANE.setText(PREVIEW_PANE.getText() +"\nCODE:[\n"
                                                                + code_tf.getText()
                                                                + "\n]");
                    final_str = final_str + code_text_tf.getText()
                            + "\n\\begin{lstlisting}[language=Java]\n"
                            + code_tf.getText()
                            + "\n\\end{lstlisting}";
                }
            }
            code_tf.setText("");
            code_text_tf.setText("");
        });

        add_filepath.setOnAction(actionEvent -> {
            PREVIEW_PANE.setText(PREVIEW_PANE.getText() + "\n=======\nPATH:["+fp_tf.getText()+"]\n=======\n");
            final_str = final_str + "\\\\\\\\" + "\n\\vspace{5mm}\n" +
                    "\\path{"+fp_tf.getText()+"}\n" +
                    "\\vspace{5mm}\\\\";
            fp_tf.setText("");
        });

        generate.setOnAction(actionEvent -> {

            String generated = "\\documentclass["+font_size_c.getValue().toString().toLowerCase()+"]{"+doc_type_c.getValue().toString().toLowerCase()+"}\n" +
                    "\n" +
                    "\\usepackage[english]{babel}\n" +
                    "\\usepackage[utf8]{inputenc}\n" +
                    "\\usepackage{amsmath}\n" +
                    "\\usepackage{graphicx}\n" +
                    "\\usepackage[colorinlistoftodos]{todonotes}\n" +
                    "\\usepackage[obeyspaces]{url}\n" +
                    "%\\usepackage{hyperref}\n" +
                    "\n" +
                    "\\usepackage{setspace}\n" +
                    "\\usepackage{listings}\n" +
                    "\\usepackage{xcolor}\n" +
                    "\n" +
                    "\\definecolor{codegreen}{rgb}{0,0.6,0}\n" +
                    "\\definecolor{codeblue}{rgb}{0,0,0.6}\n" +
                    "\\definecolor{codegray}{rgb}{0.5,0.5,0.5}\n" +
                    "\\definecolor{codepurple}{rgb}{0.58,0,0.82}\n" +
                    "\\definecolor{backcolour}{rgb}{0.95,0.95,0.92}\n" +
                    "\n" +
                    "\\lstset{language=Java,\n" +
                    "\tbackgroundcolor=\\color{backcolour},\n" +
                    "\tbackgroundcolor=\\color{backcolour},   \n" +
                    "\tcommentstyle=\\color{codegreen},\n" +
                    "\tkeywordstyle=\\color{magenta},\n" +
                    "\tnumberstyle=\\tiny\\color{codegray},\n" +
                    "\tstringstyle=\\color{codepurple},\n" +
                    "\tbasicstyle=\\ttfamily\\footnotesize,\n" +
                    "\tbreakatwhitespace=false,         \n" +
                    "\tbreaklines=true,                 \n" +
                    "\tcaptionpos=b,                    \n" +
                    "\tkeepspaces=true,                 \n" +
                    "\tnumbers=left,                    \n" +
                    "\tnumbersep=5pt,                  \n" +
                    "\tshowspaces=false,                \n" +
                    "\tshowstringspaces=false,\n" +
                    "\tshowtabs=false,                  \n" +
                    "\ttabsize=2\n" +
                    "}\n" +
                    "\n" +
                    "\\title{"+title_t.getText().toString()+"\\\\ \\vspace{5mm}\n" +
                    "\t\\large "+subtitle_t.getText().toString()+"}\n" +
                    "\n" +
                    "\\author{"+author_t.getText().toString()+"}\n" +
                    "\\date{"+date_picker_t.getValue().toString()+"}\n" +
                    "\n" +
                    "\\begin{document}\n" +
                    "\\maketitle";
            //System.out.println(generated + final_str + "\n\\end{document}");

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proceed/generate.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Generate generate = fxmlLoader.getController();
                generate.importer(generated + final_str + "\n\\end{document}");
                Stage stage = new Stage();
                stage.setTitle("Generated Code");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        undo_bt.setOnAction(actionEvent -> {
            undo();
        });

        UpdateTracker.setCycleCount(Timeline.INDEFINITE);
        UpdateTracker.play();
    }

    void save_last_state(){
        last_state = last_state + final_str;
        last_state_preview = last_state_preview + PREVIEW_PANE.getText();
        isLastState = true;
    }

    void undo(){
        final_str = last_state;
        PREVIEW_PANE.setText(last_state_preview);
        last_state = "";
        last_state_preview = "";
        isLastState = false;
    }

    void zoom_pta(int z){
        PREVIEW_PANE.setStyle("-fx-font-size: "+(display_size_pta+z*2));
        display_size_pta = display_size_pta + z;
    }

    void print(String s){
        prnt.setText(s);
    }
    void emptify(){
        print("");
    }
}