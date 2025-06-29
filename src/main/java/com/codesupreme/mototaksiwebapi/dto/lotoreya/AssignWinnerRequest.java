package com.codesupreme.mototaksiwebapi.dto.lotoreya;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AssignWinnerRequest {
    private Long lotoreyaId;
    private List<String> codes;
}

