package com.room.finder.mapper;

import com.room.finder.model.Moderator;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModeratorMapper {
    Moderator findModeratorByUser(Integer userId);
    Integer saveModerator(Moderator moderator);
}
