package com.society.server.dto.reaction;

import com.society.server.model.enums.ReactionEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {
    private ReactionEnum reactionType;
    private Long respondingUserId;
    private Long targetEntityId;
}
