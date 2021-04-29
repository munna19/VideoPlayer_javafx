package videoPlayer;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Controller implements Initializable {

    MediaPlayer player;

    @FXML
    private Slider progress_slider_id;

    @FXML
    private Slider sound_slider_id;

    @FXML
    private MediaView mediaView_id;

    @FXML
    private Button previous_Button_id;

    @FXML
    private Button play_Button_id;

    @FXML
    private Button next_Button_id;

    @FXML
    private Button mute_Button_id;

    private void disposePlayer(){
        MediaPlayer player = mediaView_id.getMediaPlayer();
        if(player != null){
            player.dispose();//release resources
        }
    }

    @FXML
    void OpenFile_ActionButton(ActionEvent event) {

    }

    @FXML
    void Play_ActionButton(ActionEvent event) {

    }

    @FXML
    void Mute_ActionButton(ActionEvent event) {

    }

    @FXML
    void Next_ActionButton(ActionEvent event) {

    }


    @FXML
    void Previous_ActionButton(ActionEvent event) {

    }

    @FXML
    void Quit_ActionButton(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
