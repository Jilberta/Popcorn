package ge.quickscope.model.items;

/**
 * Created by Jay on 7/5/2015.
 */
public class Review {
    private String id, author, content;
//    private String author;
//    private String content;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {

        return "Review{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
