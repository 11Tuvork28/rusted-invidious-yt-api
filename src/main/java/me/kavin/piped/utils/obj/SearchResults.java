package me.kavin.piped.utils.obj;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.kavin.piped.utils.SearchItemSerializer;
import me.kavin.piped.utils.obj.search.SearchItem;

public class SearchResults {

    @JsonSerialize(using = SearchItemSerializer.class)
    public ObjectArrayList<SearchItem> items;
    public String nextpage, suggestion;
    public boolean corrected;

    public SearchResults(ObjectArrayList<SearchItem> items, String nextpage) {
        this.nextpage = nextpage;
        this.items = items;
    }

    public SearchResults(ObjectArrayList<SearchItem> items, String nextpage, String suggestion, boolean corrected) {
        this.items = items;
        this.nextpage = nextpage;
        this.suggestion = suggestion;
        this.corrected = corrected;
    }
}
