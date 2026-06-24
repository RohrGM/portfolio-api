package com.rohr.portfolio_api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String nome;
    private String cargo;
}
