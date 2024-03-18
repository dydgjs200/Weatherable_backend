package com.weatherable.weatherable.Controller;

import com.weatherable.weatherable.DTO.UserDTO;
import com.weatherable.weatherable.DTO.UserForMyPageDTO;
import com.weatherable.weatherable.Entity.UserEntity;
import com.weatherable.weatherable.Service.ClosetService;
import com.weatherable.weatherable.Service.S3Upload;
import com.weatherable.weatherable.Service.UserService;
import com.weatherable.weatherable.enums.DefaultRes;
import com.weatherable.weatherable.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    S3Upload s3Upload;



    @GetMapping("")
    public ResponseEntity<DefaultRes<UserForMyPageDTO>> getUserByUserid(@RequestParam String userid) throws Exception {
        UserForMyPageDTO result = userService.getUserInfoForMyPage(userid);
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, "유저 데이터 fetch 완료", result),
                HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<DefaultRes<String>> updateUserNickname(@RequestBody UserDTO userDTO) {
        String result = userService.changeUserNickname(userDTO.getNickname(), userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @PatchMapping("/physical")
    public ResponseEntity<DefaultRes<String>> updateUserHeightAndWeight(@RequestBody UserDTO userDTO) {
        String result = userService.changeUserHeightAndWeight(userDTO.getHeight(), userDTO.getWeight(), userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @Value("${cloud.aws.default.imgPath}")
    private String defaultImgPath;

    @PatchMapping("/image")
    public ResponseEntity<DefaultRes<String>> updateUserImagePath(@RequestBody UserDTO userDTO, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        String imagePath;
        if (imageFile.isEmpty()) {
        imagePath = defaultImgPath;
        } else {
        imagePath = s3Upload.saveImageFile(imageFile);
        }
        String result = userService.changeUserImagePath(imagePath, userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @PatchMapping("/introduction")
    public ResponseEntity<DefaultRes<String>> updateUserIntroduction(@RequestBody UserDTO userDTO) {
        String result = userService.changeUserIntroduction(userDTO.getIntroduction(), userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<DefaultRes<String>> updateUserPassword(@RequestBody UserDTO userDTO) {
        String existingPassword = userService.retrieveExistingPasswordById(userDTO.getId());
        boolean arePasswordsEquals = userService.matchPassword(userDTO.getPassword(), existingPassword);
        if (arePasswordsEquals) {
            return new ResponseEntity<>(
                    DefaultRes.res(StatusCode.BAD_REQUEST, "패스워드가 동일합니다."),
                    HttpStatus.BAD_REQUEST);
        }
        String result = userService.changeUserPassword(userDTO.getPassword(), userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @PatchMapping("/style")
    public ResponseEntity<DefaultRes<String>> updateUserStyle(@RequestBody UserDTO userDTO) {
        String result = userService.changeUserStyle(userDTO.getFavoriteStyle(), userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<DefaultRes<String>> deleteUserAccount(@RequestBody UserDTO userDTO) {
        String result = userService.deleteUserAccount(userDTO.getId());
        return new ResponseEntity<>(
                DefaultRes.res(StatusCode.OK, result),
                HttpStatus.OK);
    }
}
