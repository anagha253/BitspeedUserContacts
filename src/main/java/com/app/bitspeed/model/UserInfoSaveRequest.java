package com.app.bitspeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSaveRequest {

  private String phoneNumber;
  private String email;


}
