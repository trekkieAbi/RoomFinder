package com.room.finder.mapper;
import com.room.finder.dto.RoomDto;
import com.room.finder.model.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RoomMapper {
    Integer saveRoom(Room room);
    ArrayList<RoomDto> selectRoomDtoByAdvertisement(Integer advertisementId);
    RoomDto selecRoomById(Integer id);

   Integer deleteRoom(Integer id);

}
