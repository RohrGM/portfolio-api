package com.rohr.portfolio_api.v1.client.api_mock.domain.dto;

import com.rohr.portfolio_api.v1.client.api_mock.domain.enums.JobTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String nome;
    private JobTitle cargo;
}
