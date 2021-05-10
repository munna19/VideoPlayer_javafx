package videoPlayer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/*
     #####     #####     #####
     @author: Abdul Al Mamun (Munna)
     #####     #####     #####
*/

public class Controller implements Initializable {

    MediaPlayer player;
    double value;

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
        try {
            FileChooser fc = new FileChooser();
            File selectedFile = fc.showOpenDialog(null);
            Media media = new Media(selectedFile.toURI().toString());


            //all operation relies
            if(player != null){
                player.dispose();
            }

            player = new MediaPlayer(media);

            mediaView_id.setMediaPlayer(player);


            try{

                DoubleProperty width = mediaView_id.fitWidthProperty();
                DoubleProperty height = mediaView_id.fitHeightProperty();
                width.bind(Bindings.selectDouble(mediaView_id.sceneProperty(),"width"));
                height.bind(Bindings.selectDouble(mediaView_id.sceneProperty(),"height"));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        //time slider
        player.setOnReady(()->{
            //this function work when the player gets ready
            progress_slider_id.setMin(0);
            //slider_id.setMax(player.getMedia().getDuration().toSeconds());
            progress_slider_id.setMax(player.getMedia().getDuration().toMinutes());
            progress_slider_id.setValue(0);

            try {
                play_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        //listener on player
        player.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

                Duration durationD = player.getCurrentTime();

                //slider_id.setValue(durationD.toSeconds());
                progress_slider_id.setValue(durationD.toMinutes());

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        // slider position change
        progress_slider_id.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(progress_slider_id.isPressed()){
                    double slider_value = progress_slider_id.getValue();
                    //player.seek(new Duration(slider_value*1000));
                    player.seek(new Duration(slider_value * 60 * 1000)); // for minute
                }
            }
        });

        //volume slider
        sound_slider_id.setValue(player.getVolume() * 100);
        sound_slider_id.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(sound_slider_id.getValue() / 100);
                value = sound_slider_id.getValue();
                //System.out.println(value);
            }
        });



    }

    @FXML
    void Play_ActionButton(ActionEvent event) {
        try {
            MediaPlayer.Status status = player.getStatus();
            if(status == MediaPlayer.Status.PLAYING){
                player.pause();
                //play_id.setText("Play");
                play_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            }
            else {
                player.play();
                //play_id.setText("Pause");
                play_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pause.png"))));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void Mute_ActionButton(ActionEvent event) throws FileNotFoundException {
        //if(sound_slider_id.getValue() != 0)
        if(value != 0 || sound_slider_id.getValue() != 0){
            sound_slider_id.setValue(player.getVolume() * 0);
            mute_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/mute.png"))));
        }
        else  {
            sound_slider_id.setValue(100);
            mute_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/volume.png"))));
        }

    }

    @FXML
    void Next_ActionButton(ActionEvent event) {
        //for duration forward
        double d = player.getCurrentTime().toSeconds();
        d = d + 10;
        player.seek(new Duration(d * 1000));
    }


    @FXML
    void Previous_ActionButton(ActionEvent event) {
        //for duration backward
        double d = player.getCurrentTime().toSeconds();
        d = d - 10;
        player.seek(new Duration(d * 1000));
    }

    @FXML
    void Quit_ActionButton(ActionEvent event) {
        Platform.exit();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*play_Button_id.setMinHeight(5);
        play_Button_id.setMaxHeight(5);
        play_Button_id.setMinWidth(5);
        play_Button_id.setMaxWidth(5);
         */

        try {
            play_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            previous_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/previous.png"))));
            next_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/next.png"))));
            mute_Button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/volume.png"))));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
