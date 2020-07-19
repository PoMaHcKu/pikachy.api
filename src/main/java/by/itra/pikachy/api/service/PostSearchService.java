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
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class PostSearchService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PostMapper postMapper;
    private FullTextEntityManager fullTextEntityManager;

    public List<PostDto> search(String text) {
        if (fullTextEntityManager == null || !fullTextEntityManager.isOpen()) {
            fullTextEntityManager = initializeFullTextEntityManager();
        }
        QueryBuilder builder = fullTextEntityManager
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

    private FullTextEntityManager initializeFullTextEntityManager() {
        this.fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fullTextEntityManager;
    }
}