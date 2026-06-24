package com.rohr.portfolio_api.v1.client.api_mock.domain.form;

import com.rohr.portfolio_api.v1.client.api_mock.domain.enums.JobTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberForm {
    @NotBlank(message = "O NOME é obrigatório.")
    private String nome;
    @NotNull(message = "O CARGO é obrigatório.")
    private JobTitle cargo;
}
