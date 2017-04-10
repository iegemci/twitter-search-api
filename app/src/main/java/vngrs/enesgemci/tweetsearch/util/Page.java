package vngrs.enesgemci.tweetsearch.util;

/**
 * Created by enesgemci on 08/04/2017.
 */

public enum Page {

    DASHBOARD("Search"),
    DETAIL("Detail");

    private String title;

    Page(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}