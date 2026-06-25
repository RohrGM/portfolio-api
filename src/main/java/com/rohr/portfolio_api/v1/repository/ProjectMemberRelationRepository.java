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


    @Query(value = """
            SELECT DISTINCT pmr.member_id
            FROM project_member_relation pmr
            WHERE pmr.member_id IN (
                SELECT pmr2.member_id
                FROM project_member_relation pmr2
                JOIN project p2
                    ON p2.id = pmr2.project_id
                WHERE pmr2.member_id IN :memberIds
                  AND p2.status NOT IN (6, 7)
                GROUP BY pmr2.member_id
                HAVING COUNT(*) >= 3
            )
            """, nativeQuery = true)
    List<Long> findMembersWithMaxProjects(@Param("memberIds") List<Long> memberIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_member_relation WHERE project_id = :projectId", nativeQuery = true)
    void deleteByProjectIds(@Param("projectId") Long projectId);

    @Query(value = """
            SELECT COUNT(DISTINCT pmr.member_id)
            FROM project_member_relation pmr
            WHERE pmr.project_id IN (
                SELECT id
                FROM project
                WHERE status NOT IN (6, 7)
            )""", nativeQuery = true)
    Long countAllocatedMembers();

    List<ProjectMemberRelation> findByProjectId(Long projectId);

}
