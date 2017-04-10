package vngrs.enesgemci.tweetsearch.data.model;

import java.util.List;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class SearchResponse extends JsonData {

    private List<Status> statuses;
    private MetaData searchMetadata;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public MetaData getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(MetaData searchMetadata) {
        this.searchMetadata = searchMetadata;
    }
}
