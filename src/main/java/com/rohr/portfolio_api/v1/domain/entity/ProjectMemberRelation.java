package com.rohr.portfolio_api.v1.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "project_member_relation")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private Long memberId;
}
