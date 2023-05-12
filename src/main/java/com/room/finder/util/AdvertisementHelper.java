package com.room.finder.util;

import com.room.finder.constant.AppConstant;
import com.room.finder.dto.AdvertisementDto;
import com.room.finder.mapper.RoomMapper;
import com.room.finder.model.Room;
import com.room.finder.service.ImageUploadService;
import com.room.finder.validation.AdvertisementValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Transactional(propagation = Propagation.REQUIRED)
@Component
@RequiredArgsConstructor
public class AdvertisementHelper {

    private RoomMapper roomMapper;

    private ImageUploadService imageUploadService;

    public void createAndSaveRoom(MultipartFile[] multipartFiles, AdvertisementDto advertisementDto) throws IOException {
        Room room = new Room();
        for (MultipartFile eachMultipartFile : multipartFiles
        ) {
            if(AdvertisementValidation.validateImageFile(eachMultipartFile.getOriginalFilename())) {
                Map<String, String> fileMessage = imageUploadService.uploadImage(AppConstant.path,eachMultipartFile);
                for (Map.Entry<String, String> entry : fileMessage.entrySet()
                ) {
                    room.setImagePath(entry.getValue());
                    room.setImage(entry.getKey());
                }
                int i = 0;
                room.setName(advertisementDto.getRoomDtoList().get(i).getName());
                i = i + 1;
                room.setAdvertisementId(advertisementDto.getId());
                roomMapper.saveRoom(room);
            }else{
                throw new IOException("UnsupportedMediaType");
            }

        }
    }
}
