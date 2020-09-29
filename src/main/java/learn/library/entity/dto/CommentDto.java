package learn.library.entity.dto;

import learn.library.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CommentDto {

    private String id;
    private String name;
    private String subject;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.name = comment.getName();
        this.subject = comment.getSubject();
    }

}
