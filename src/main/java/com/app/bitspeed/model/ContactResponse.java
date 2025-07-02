package com.app.bitspeed.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

  private int primaryContactId;

  private List<String> emails;

  private List<String> phoneNumbers;

  private List<Integer> secondaryContactIds;

}
