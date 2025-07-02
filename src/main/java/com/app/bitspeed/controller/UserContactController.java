package com.app.bitspeed.controller;


import com.app.bitspeed.model.UserInfoSaveRequest;
import com.app.bitspeed.model.UserInfoSaveResponse;
import com.app.bitspeed.service.UserContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserContactController {

  private final UserContactService userContactService;

  @PostMapping("/identify")
  public UserInfoSaveResponse userInfoSave(@RequestBody UserInfoSaveRequest requestBody) {

    try {
      if (requestBody.getPhoneNumber().isEmpty() && requestBody.getEmail().isEmpty()) {
        throw new RuntimeException("PhoneNumber and Email is missing in the request body");
      }
      return userContactService.saveUserInfo(requestBody);

    } catch (RuntimeException e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    } catch (Exception e1) {
      log.error(e1.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

}
