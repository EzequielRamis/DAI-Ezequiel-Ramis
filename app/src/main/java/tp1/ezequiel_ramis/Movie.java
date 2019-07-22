package tp1.ezequiel_ramis;

public class Movie {
    private String _id;
    private String _title;
    private String _year;
    private String _poster;

    private String _rated;
    private String _released;
    private String _runtime;
    private String _genre;
    private String _director;
    private String _plot;

    public Movie() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public Movie(String id, String title, String year, String poster, String rated, String released, String runtime, String genre, String director, String plot) {
        _id = id;
        _title = title;
        _year = year;
        _poster = poster;

        _rated = rated;
        _released = released;
        _runtime = runtime;
        _genre = genre;
        _director = director;
        _plot = plot;
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

    public String get_rated() {
        return _rated;
    }

    public String get_released() {
        return _released;
    }

    public String get_runtime() {
        return _runtime;
    }

    public String get_genre() {
        return _genre;
    }

    public String get_director() {
        return _director;
    }

    public String get_plot() {
        return _plot;
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

    public void set_rated(String _rated) {
        this._rated = _rated;
    }

    public void set_released(String _released) {
        this._released = _released;
    }

    public void set_genre(String _genre) {
        this._genre = _genre;
    }

    public void set_director(String _director) {
        this._director = _director;
    }

    public void set_runtime(String _runtime) {
        this._runtime = _runtime;
    }

    public void set_plot(String _plot) {
        this._plot = _plot;
    }
}
