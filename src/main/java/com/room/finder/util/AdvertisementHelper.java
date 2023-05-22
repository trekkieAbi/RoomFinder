package com.room.finder.util;

import com.room.finder.dto.AdvertisementDto;
import com.room.finder.dto.RoomDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Component
public class AdvertisementHelper {
    private String FixedPath = "images/";
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private ImageUploadService imageUploadService;

    public AdvertisementHelper(RoomMapper roomMapper, ImageUploadService imageUploadService) {
        this.roomMapper = roomMapper;
        this.imageUploadService = imageUploadService;
    }

    public void createAndSaveRoom(MultipartFile[] multipartFiles, AdvertisementDto advertisementDto) throws IOException {
        Room room = new Room();
        ArrayList<RoomDto> roomList = new ArrayList<>();
        for (MultipartFile eachMultipartFile : multipartFiles
        ) {
            if (AdvertisementValidation.validateImageFile(eachMultipartFile.getOriginalFilename())) {
                Map<String, RoomInformation> fileMessage = imageUploadService.getInformationAboutRoom(FixedPath, eachMultipartFile);
                for (Map.Entry<String, RoomInformation> entry : fileMessage.entrySet()
                ) {
                    room.setImagePath(entry.getValue().getFullPath());
                    room.setImage(entry.getKey());
                    room.setName(entry.getValue().getRoomName());
                    room.setAdvertisementId(advertisementDto.getId());
                    roomMapper.saveRoom(room);
                    roomList.add(getRoomDto(room));
                }

            } else {
                throw new IOException("UnsupportedMediaType");
            }

        }
        ArrayList<RoomDto> roomDtos = roomMapper.selectRoomDtoByAdvertisement(advertisementDto.getId());
        if (roomDtos.size() == multipartFiles.length) {
            uploadImageWithRoom(multipartFiles,roomDtos);//this block is execute while saving(creating) the advertisement
        } else if (roomDtos.contains(roomList)) {
            uploadImageWithRoom(multipartFiles,roomList);//this block is execute while editing the advertisement
        } else {
            throw new RuntimeException("something went wrong");
        }
    }


    private RoomDto getRoomDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(roomDto.getId());
        roomDto.setImage(roomDto.getImage());
        roomDto.setName(room.getName());
        return roomDto;
    }


    private void uploadImageWithRoom(MultipartFile[] multipartFiles, List<RoomDto> roomDtos) throws IOException {
        for (RoomDto eachRoomDto : roomDtos//this blocks ensure that the image name in db is same as the name in local folder where image stored
        ) {
            for (MultipartFile eachMultipartFile : multipartFiles
            ) {
                imageUploadService.uploadImage(FixedPath, eachMultipartFile,eachRoomDto.getImageFullPath());
                break;
            }
        }
    }
}

