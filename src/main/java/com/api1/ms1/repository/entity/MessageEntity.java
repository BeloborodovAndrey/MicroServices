package com.api1.ms1.repository.entity;


import com.api1.ms1.model.MessageDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "session_id", unique = true)
    private Integer sessionId;

    @Column(name = "MS1_timestamp")
    private Date ms1TimeStamp;

    @Column(name = "MS2_timestamp")
    private Date ms2TimeStamp;

    @Column(name = "MS3_timestamp")
    private Date ms3TimeStamp;

    @Column(name = "end_timestamp")
    private Date endTimeStamp;

    public static MessageEntity from(MessageDto messageDto) {
        if (messageDto != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setSessionId(messageDto.getSessionId());
            messageEntity.setMs1TimeStamp(messageDto.getMs1TimeStamp());
            messageEntity.setMs2TimeStamp(messageDto.getMs2TimeStamp());
            messageEntity.setMs3TimeStamp(messageDto.getMs3TimeStamp());
            messageEntity.setEndTimeStamp(messageDto.getEndTimeStamp());
            return messageEntity;
        }
        return new MessageEntity();
    }
}
