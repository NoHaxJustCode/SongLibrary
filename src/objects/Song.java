package objects;

public class Song implements Comparable<Song>{
    private String title;
    private String artist;
    private String album;
    private int year;
    
    public Song(String title, String artist, String album, int year) {
      this.title = title;
      this.artist = artist;
      this.album = album;
      this.year = year;
    }
  
    public String getTitle() {
      return title;
    }
  
    public void setTitle(String title) {
      this.title = title;
    }
  
    public String getArtist() {
      return artist;
    }
  
    public void setArtist(String artist) {
      this.artist = artist;
    }
  
    public String getAlbum() {
      return album;
    }
  
    public void setAlbum(String album) {
      this.album = album;
    }
  
    public int getYear() {
      return year;
    }
  
    public void setYear(int year) {
      this.year = year;
    }

    public String toString()
    {
        return title+" "+artist;
    }

    public String saveString()
    {
        return title+"|"+artist+"|"+album+"|"+year;
    }

    @Override
    public int compareTo(Song s) {
        
        if(this.title.toLowerCase().compareTo(s.title.toLowerCase()) == 0)
        {
            return this.artist.toLowerCase().compareTo(s.artist.toLowerCase());
        }
        else
            return this.title.toLowerCase().compareTo(s.title.toLowerCase());
    }
}
