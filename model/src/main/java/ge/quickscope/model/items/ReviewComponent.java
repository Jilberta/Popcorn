package ge.quickscope.model.items;

import java.util.List;

/**
 * Created by Jay on 7/5/2015.
 */
public class ReviewComponent {
    private String id, page;
//    private String page;
    private List<Review> results;
    private Number total_pages, total_results;
//    private Number total_results;

    public String getId() {
        return id;
    }

    public String getPage() {
        return page;
    }

    public List<Review> getResults() {
        return results;
    }

    public Number getTotal_pages() {
        return total_pages;
    }

    public Number getTotal_results() {
        return total_results;
    }
}
