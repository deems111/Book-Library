package learn.library.entity.mappers.interfaces;

import learn.library.entity.Comment;
import learn.library.entity.dto.CommentDto;
import learn.library.entity.splDbEntity.CommentSqlDb;

public interface CommentMapper {
    default CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .subject(comment.getSubject())
                .build();
    }

    default Comment toEntity(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .subject(commentDto.getSubject())
                .build();
    }

    Comment toMongoEntity(CommentSqlDb commentSqlDb);
}
