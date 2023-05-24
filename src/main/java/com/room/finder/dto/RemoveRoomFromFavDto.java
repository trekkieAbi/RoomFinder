package com.room.finder.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveRoomFromFavDto {
    private Integer roomId;
    private Integer customerId;
    private Integer advertisementId;
}
