package com.project.videotheque.repository;

import com.project.videotheque.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
    select m from Movie m
    where (:title is null or lower(m.title) like lower(concat('%', :title, '%')))
      and (:year  is null or m.year = :year)
    """)
    Page<Movie> search(@Param("title") String title,
                       @Param("year") Integer year,
                       Pageable pageable);
}
