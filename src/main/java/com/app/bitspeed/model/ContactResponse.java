package com.app.bitspeed.model;

import lombok.Data;

import java.util.List;

@Data
public class ContactResponse {

    private int primaryContactId;

    private List<String> emails;

    private List<String> phoneNumbers;

    private List<Integer> secondaryContactIds;

}
