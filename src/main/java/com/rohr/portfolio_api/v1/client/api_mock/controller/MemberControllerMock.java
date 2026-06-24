package com.rohr.portfolio_api.v1.client.api_mock.controller;

import com.rohr.portfolio_api.v1.client.api_mock.domain.dto.MemberDTO;
import com.rohr.portfolio_api.v1.client.api_mock.domain.form.MemberForm;
import com.rohr.portfolio_api.v1.client.api_mock.service.MemberServiceMock;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/member")
public class MemberControllerMock {

    @Autowired
    private MemberServiceMock memberService;

    @PostMapping("/create")
    public ResponseEntity<MemberDTO> createMember(
            @Valid @RequestBody MemberForm form
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.memberService.createMember(form));
    }

    @GetMapping("/list-all")
    public ResponseEntity<Iterable<MemberDTO>> listAllMembers() {
        return ResponseEntity.ok(this.memberService.listMembers());
    }

    @GetMapping("/find")
    public ResponseEntity<List<MemberDTO>> findMembers(
            @RequestParam List<Long> ids
    ) {
        return ResponseEntity.ok(this.memberService.findMembers(ids));
    }
}
