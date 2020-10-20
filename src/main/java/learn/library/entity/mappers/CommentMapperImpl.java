package learn.library.entity.mappers;

import learn.library.entity.Comment;
import learn.library.entity.mappers.interfaces.BookMapper;
import learn.library.entity.mappers.interfaces.CommentMapper;
import learn.library.entity.splDbEntity.CommentSqlDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapperImpl implements CommentMapper {

    private final BookMapper bookMapper;

    @Override
    public Comment toMongoEntity(CommentSqlDb commentSqlDb) {
        return Comment.builder().id(String.valueOf(commentSqlDb.getId()))
                .book(bookMapper.toMongoEntity(commentSqlDb.getBook()))
                .subject(commentSqlDb.getSubject())
                .name(commentSqlDb.getName())
                .build();
    }
}

