package projects.suchushin.org.twitterpersonposts;

import io.realm.RealmObject;

public class RealmVariant extends RealmObject {
    public long bitrate;

    public String contentType;

    public String url;


    public RealmVariant(){

    }

    RealmVariant(long bitrate, String contentType, String url) {
        this.bitrate = bitrate;
        this.contentType = contentType;
        this.url = url;
    }
}
