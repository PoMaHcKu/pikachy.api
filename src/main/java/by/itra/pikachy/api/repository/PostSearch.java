package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostSearch {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PostMapper postMapper;

    public List<PostDto> search(String text) {
        FullTextEntityManager entityManager = Search.getFullTextEntityManager(this.entityManager);
        try {
            entityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryBuilder builder = entityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Post.class)
                .get();
        List<Post> result = fullTextWithLike(text, builder, entityManager);
        return result.size() == 0 ?
                fullText(text, builder, entityManager).stream().map(postMapper::toDto).collect(Collectors.toList()) :
                result.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    private List<Post> fullTextWithLike(String text,
                                        QueryBuilder builder,
                                        FullTextEntityManager manager) {
        Query query =
                builder
                        .keyword()
                        .wildcard()
                        .onFields("title", "description")
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
                .onFields("title", "description")
                .matching(text)
                .createQuery();
        FullTextQuery jpaQuery = manager.createFullTextQuery(query, Post.class);
        return jpaQuery.getResultList();
    }
}