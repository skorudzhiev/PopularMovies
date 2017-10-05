package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable Plugin was used to implement the parcelable-related methods down below
 * @https://github.com/mcharmas/android-parcelable-intellij-plugin/
 */
public class Movie implements Parcelable {
    private int id;
    private String title;
    private String releaseDate;
    private String posterPath;
    private Double voteAverage;
    private String overview;

    public Movie() {}

    /**
     * Initializing Getters and Setters
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String Title){
        title = Title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String ReleaseDate){
        releaseDate = ReleaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getDbPosterPath(){
        return posterPath;
    }

    public void setPosterPath(String Posterpath){
        posterPath = Posterpath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
    public void setVoteAverage(Double VoteAverage){
        voteAverage = VoteAverage;
    }

    public String getDetailedVoteAverage() {
        return String.valueOf(getVoteAverage()) + "/10";
    }

    public String getOverview(){return overview;}
    public void setOverview(String Overview) {
        if(!Overview.equals("null")) {
            overview = Overview;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.overview);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
