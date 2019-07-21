package tp1.ezequiel_ramis;

public class Movie {
    private String _id;
    private String _title;
    private String _year;
    private String _poster;

    public Movie(String id, String title, String year, String poster) {
        _id = id;
        _title = title;
        _year = year;
        _poster = poster;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_poster(String _poster) {
        this._poster = _poster;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_year(String _year) {
        this._year = _year;
    }

    public String get_id() {
        return _id;
    }

    public String get_poster() {
        return _poster;
    }

    public String get_title() {
        return _title;
    }

    public String get_year() {
        return _year;
    }
}
