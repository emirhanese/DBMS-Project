import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private String commentText;
    private Timestamp datePosted;

    public Comment(int commentId, String commentText, Timestamp datePosted) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.datePosted = datePosted;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }
}
