package com.codesupreme.mototaksiwebapi.menyupro.dto;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpApprovalStateDto {
    private MpApprovalStatus approvalStatus;
    private Boolean isActive;
    private String rejectReason;
}