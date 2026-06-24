package com.rohr.portfolio_api.v1.repository;

import com.rohr.portfolio_api.v1.domain.entity.ProjectMemberRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectMemberRelationRepository extends JpaRepository<ProjectMemberRelation, Long> {


    @Query(value = "SELECT DISTINCT pmr.member_id\n" +
            "FROM project_member_relation pmr\n" +
            "WHERE pmr.member_id IN (\n" +
            "    SELECT pmr2.member_id\n" +
            "    FROM project_member_relation pmr2\n" +
            "    JOIN project p2\n" +
            "        ON p2.id = pmr2.project_id\n" +
            "    WHERE pmr2.member_id IN :memberIds\n" +
            "      AND p2.status NOT IN (6, 7)\n" +
            "    GROUP BY pmr2.member_id\n" +
            "    HAVING COUNT(*) >= 3\n" +
            ")", nativeQuery = true)
    List<Long> findMembersWithMaxProjects(@Param("memberIds") List<Long> memberIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_member_relation WHERE project_id = :projectId", nativeQuery = true)
    void deleteByProjectIds(@Param("projectId") Long projectId);

    List<ProjectMemberRelation> findByProjectId(Long projectId);

}
