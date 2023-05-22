package com.room.finder.mapper;
import com.room.finder.model.Authority;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;
import java.util.Optional;

@Mapper
public interface AuthorityMapper {
    Integer saveAuthority(Authority authorities);
   Integer updateAuthority(Authority authority);
   Integer deleteAuthority(Integer id);
    Authority findById(Integer id);
    ArrayList<Authority> getAllAuthority();
    Optional<Authority> findByName(String name);
}
