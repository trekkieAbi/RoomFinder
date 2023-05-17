package com.room.finder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.room.finder.model.Moderator;

@Mapper
public interface ModeratorMapper {
    Moderator findModeratorByUser(Integer userId);
    Integer saveModerator(Moderator moderator);
}