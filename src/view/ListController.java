package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.Song;

public class ListController {
    @FXML
    ListView<Song> listView;
    @FXML
    TextField songTitle, songArtist, songAlbum, songYear;

    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private static final String FILENAME = "src/list.txt";

    public void start(Stage stage)
    {
        listView.setItems(songs);
        loadSongs();
        if(!songs.isEmpty())
        {
            listView.getSelectionModel().select(0);
            Song s = songs.get(0);
            songTitle.setText(s.getTitle());
            songTitle.setFont(Font.font(null, FontWeight.BOLD, 12));
            songArtist.setText(s.getArtist());
            songArtist.setFont(Font.font(null, FontWeight.BOLD, 12));
            songAlbum.setText(s.getAlbum());
            songAlbum.setFont(Font.font(null, FontWeight.BOLD, 12));
            songYear.setText(Integer.toString(s.getYear()));
            songYear.setFont(Font.font(null, FontWeight.BOLD, 12));
        }
    }
    private void loadSongs() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
              lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        for (String line : lines) {
            String[] parts = line.split(Pattern.quote("|"));
            String title = parts[0];
            String artist = parts[1];
            String album = parts[2];
            int year = Integer.parseInt(parts[3]);
            Song song = new Song(title, artist, album, year);
            songs.add(song);
        }        
    }
    private void saveSongs() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Song song : songs) {
                bw.write(song.saveString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());            
        }
    }
    @FXML
    private void handleEditButton(ActionEvent e) 
    {
        Song selectedSong = listView.getSelectionModel().getSelectedItem();
        if (selectedSong == null) {
            return;
        }
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        String title = songTitle.getText().trim();
        String artist = songArtist.getText().trim();
        String album = songAlbum.getText().trim();
        String year = songYear.getText().trim();
        if(title.equals("") || artist.equals(""))
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error: Left Important Field Blank");
            String errorMsg = "Left Important Field Blank";
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return;
        }
        //search if there is a existing song
        for(int i = 0; i<songs.size(); i++)
        {
            Song song = songs.get(i);
            if(i != selectedIndex && song.getTitle().toLowerCase().equals(title.toLowerCase()) && song.getArtist().toLowerCase().equals(artist.toLowerCase()))
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Song Already Exists");
                String errorMsg = "Song Exists";
                alert.setContentText(errorMsg);
                alert.showAndWait();
                return;        
            }
        }
        if(year.equals("") || Integer.parseInt(year) <= 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error: Year Field");
            String errorMsg = "Year Field Incorrect";
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return;            
        }
        selectedSong.setAlbum(album);
        selectedSong.setArtist(artist);
        selectedSong.setTitle(title);
        selectedSong.setYear(Integer.parseInt(year));
        songTitle.setText(selectedSong.getTitle());
        songArtist.setText(selectedSong.getArtist());
        songAlbum.setText(selectedSong.getAlbum());
        songYear.setText(Integer.toString(selectedSong.getYear()));
        listView.refresh();
        Collections.sort(songs);
        handleListViewSelection();
        saveSongs();
    }
    @FXML
    private void handleAddButton(ActionEvent e)
    {

    }
    @FXML
    private void handleDeleteButton(ActionEvent e)
    {
        
    }
    @FXML
    private void listKeyPress(KeyEvent e)
    {
        if (listView.getSelectionModel().getSelectedIndex() < 0)
            return;
        if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.ENTER)
        {
            Song s = listView.getSelectionModel().getSelectedItem();
            songTitle.setText(s.getTitle());
            songTitle.setFont(Font.font(null, FontWeight.BOLD, 12));
            songArtist.setText(s.getArtist());
            songArtist.setFont(Font.font(null, FontWeight.BOLD, 12));
            songAlbum.setText(s.getAlbum());
            songAlbum.setFont(Font.font(null, FontWeight.BOLD, 12));
            songYear.setText(Integer.toString(s.getYear()));
            songYear.setFont(Font.font(null, FontWeight.BOLD, 12));
        }
    }
    @FXML
    private void handleListViewSelection() {
        if (listView.getSelectionModel().getSelectedIndex() < 0)
            return;
        Song s = listView.getSelectionModel().getSelectedItem();
        if (s != null) {
            songTitle.setText(s.getTitle());
            songTitle.setFont(Font.font(null, FontWeight.BOLD, 12));
            songArtist.setText(s.getArtist());
            songArtist.setFont(Font.font(null, FontWeight.BOLD, 12));
            songAlbum.setText(s.getAlbum());
            songAlbum.setFont(Font.font(null, FontWeight.BOLD, 12));
            songYear.setText(Integer.toString(s.getYear()));
            songYear.setFont(Font.font(null, FontWeight.BOLD, 12));
       }
    }
}
