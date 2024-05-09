package com.chandankrr.scm.helpers;

import lombok.*;

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
