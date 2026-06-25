package com.rohr.portfolio_api.v1.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String nome;
    private String cargo;
}
