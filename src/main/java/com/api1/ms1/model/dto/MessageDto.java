package com.api1.ms1.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageDto {

    private Integer sessionId;

    private Date ms1TimeStamp;

    private Date ms2TimeStamp;

    private Date ms3TimeStamp;

    private Date endTimeStamp;
}
