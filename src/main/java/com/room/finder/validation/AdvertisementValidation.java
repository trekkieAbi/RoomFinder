package com.room.finder.validation;

import com.room.finder.constant.AppConstant;
import com.room.finder.model.AdvertisementStatus;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

public class AdvertisementValidation {
    public static boolean validateRoomAvailableDate(Date date){
        boolean status=false;
        if(date.before(new Date())){
            status=true;
        }
return status;
    }

    public static boolean validateRoomImage(MultipartFile[] multipartFiles){
        boolean status=false;
        if(multipartFiles.length<= AppConstant.MAX_IMAGE_NO &&multipartFiles.length>=AppConstant.MIN_IMAGE_NO){
            status=true;
        }
        return status;
    }
    public static boolean validateModeratorSelectionStatus(String status){
        boolean resultStatus=false;
        if(status.equals(AdvertisementStatus.under_review.toString())){
            resultStatus=true;
        }
        return resultStatus;

    }

    public static boolean validateImageFile(String fileName){
        boolean status=false;
        String extension="";
        int i=fileName.lastIndexOf('.');
        if(i>=0){
            extension=fileName.substring(i+1);
        }
        for(int i1=0;i1<AppConstant.fileSupportedExtension.length;i1++){
            if(extension.equalsIgnoreCase(AppConstant.fileSupportedExtension[i1])){
                status=true;
                break;
            }
        }
        return status;
    }

    public static boolean validateRoomRent(Integer rentFee){
        boolean status=false;
        if(rentFee>0){
            status=true;
        }
        return status;
    }


}
