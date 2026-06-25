package com.rohr.portfolio_api.v1.repository;

import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountBudgetProjection;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, JpaSpecificationExecutor<ProjectEntity> {

    @Query(value = "SELECT status, COUNT(*) as count FROM project GROUP BY status", nativeQuery = true)
    List<ProjectCountStatusProjection> countPerStatus();

    @Query(value = """
                SELECT status, SUM(orcamento) as orcamento
                FROM project
                GROUP BY status;
            """, nativeQuery = true)
    List<ProjectCountBudgetProjection> countBudgetPerStatus();

    @Query(value = """
            SELECT AVG(data_termino - data_inicio)
            FROM project
            WHERE status = 6;
            """, nativeQuery = true)
    Float countAverageEnd();
}
