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
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
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
    Text songTitle, songArtist, songAlbum, songYear;

    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private static final String FILENAME = "src/list.txt";

    public void start(Stage stage)
    {
        listView.setItems(songs);
        loadSongs();
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
        TextInputDialog dialog = new TextInputDialog(selectedSong.getTitle());
        dialog.setTitle("Edit Song");
        dialog.setHeaderText("Edit the title of the selected song");
        dialog.setContentText("Title:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            selectedSong.setTitle(result.get());
            songs.set(selectedIndex, selectedSong);
            songTitle.setText(selectedSong.getTitle());
            listView.refresh();
        }
        TextInputDialog dialog2 = new TextInputDialog(selectedSong.getArtist());
        dialog2.setTitle("Edit Song");
        dialog2.setHeaderText("Edit the artist of the selected song");
        dialog2.setContentText("Artist:");
        Optional<String> result2 = dialog2.showAndWait();
        if (result2.isPresent()) {
            selectedSong.setArtist(result2.get());
            songs.set(selectedIndex, selectedSong);
            songArtist.setText(selectedSong.getArtist());
            listView.refresh();
        }
        TextInputDialog dialog1 = new TextInputDialog(selectedSong.getAlbum());
        dialog1.setTitle("Edit Song");
        dialog1.setHeaderText("Edit the album of the selected song");
        dialog1.setContentText("Album:");
        Optional<String> result1 = dialog1.showAndWait();
        if (result1.isPresent()) {
            selectedSong.setAlbum(result1.get());
            songs.set(selectedIndex, selectedSong);
            songAlbum.setText(selectedSong.getAlbum());
            listView.refresh();
        }
        TextInputDialog dialog3 = new TextInputDialog(Integer.toString(selectedSong.getYear()));
        dialog3.setTitle("Edit Song");
        dialog3.setHeaderText("Edit the year of the selected song");
        dialog3.setContentText("Year:");
        Optional<String> result3 = dialog3.showAndWait();
        if (result3.isPresent()) {
            selectedSong.setYear(Integer.parseInt(result3.get()));
            songs.set(selectedIndex, selectedSong);
            songYear.setText(selectedSong.getYear()+"");
            listView.refresh();
        }
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
            songTitle.setFill(Color.BLACK);
            songTitle.setFont(Font.font(null, FontWeight.BOLD, 12));
            songArtist.setText(s.getArtist());
            songArtist.setFill(Color.BLACK);
            songArtist.setFont(Font.font(null, FontWeight.BOLD, 12));
            songAlbum.setText(s.getAlbum());
            songAlbum.setFill(Color.BLACK);
            songAlbum.setFont(Font.font(null, FontWeight.BOLD, 12));
            songYear.setText(Integer.toString(s.getYear()));
            songYear.setFill(Color.BLACK);
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
            songTitle.setFill(Color.BLACK);
            songTitle.setFont(Font.font(null, FontWeight.BOLD, 12));
            songArtist.setText(s.getArtist());
            songArtist.setFill(Color.BLACK);
            songArtist.setFont(Font.font(null, FontWeight.BOLD, 12));
            songAlbum.setText(s.getAlbum());
            songAlbum.setFill(Color.BLACK);
            songAlbum.setFont(Font.font(null, FontWeight.BOLD, 12));
            songYear.setText(Integer.toString(s.getYear()));
            songYear.setFill(Color.BLACK);
            songYear.setFont(Font.font(null, FontWeight.BOLD, 12));
       }
    }
}
