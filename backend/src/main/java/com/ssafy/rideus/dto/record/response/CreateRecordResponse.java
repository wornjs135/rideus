package com.ssafy.rideus.dto.record.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecordResponse {

    private String recordId;

    public static CreateRecordResponse from(String recordId) {
        CreateRecordResponse createRecordResponse = new CreateRecordResponse();
        createRecordResponse.recordId = recordId;

        return createRecordResponse;
    }
}
