package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Section;
import by.itra.pikachy.api.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class PostSearchService {

    private final PostMapper postMapper;
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private FullTextEntityManager fullTextEntityManager;

    public FullTextEntityManager getFullTextEntityManager() {
        if (fullTextEntityManager == null) {
            fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        }
        return fullTextEntityManager;
    }

    public List<PostDto> search(String text) {
        QueryBuilder builder = getFullTextEntityManager()
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Post.class)
                .get();
        List<Post> result = fullTextWithLike(text, builder, fullTextEntityManager);
        return result.size() == 0 ?
                fullText(text, builder, fullTextEntityManager).stream().map(postMapper::toDto).collect(Collectors.toList()) :
                result.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    private List<Post> fullTextWithLike(String text,
                                        QueryBuilder builder,
                                        FullTextEntityManager manager) {
        Query query = builder
                .keyword()
                .wildcard()
                .onFields("title",
                        "description",
                        "sections.article",
                        "sections.title",
                        "commentaries.textCommentary")
                .matching("*" + text + "*")
                .createQuery();
        FullTextQuery jpaQuery = manager.createFullTextQuery(query, Post.class);
        return jpaQuery.getResultList();
    }

    private List<Post> fullText(String text,
                                QueryBuilder builder,
                                FullTextEntityManager manager) {
        Query query = builder
                .keyword()
                .onFields("title",
                        "description",
                        "sections.article",
                        "sections.title",
                        "commentaries.textCommentary")
                .matching(text)
                .createQuery();
        FullTextQuery jpaQuery = manager.createFullTextQuery(query, Post.class, Section.class);
        return jpaQuery.getResultList();
    }
}