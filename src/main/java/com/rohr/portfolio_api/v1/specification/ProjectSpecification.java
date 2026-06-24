package com.rohr.portfolio_api.v1.specification;

import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.form.ProjectFilterForm;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProjectSpecification {
    public static Specification<ProjectEntity> withFilters(ProjectFilterForm filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getNome() != null && !filter.getNome().isBlank())
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.getNome() + "%"));

            if (filter.getId() != null)
                predicates.add(cb.equal(root.get("id"), filter.getId()));


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
