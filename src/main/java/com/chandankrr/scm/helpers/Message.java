package com.chandankrr.scm.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Message {

    private String content;

    @Builder.Default
    private MessageType messageType = MessageType.blue;

}
