package com.app.bitspeed.service;

import com.app.bitspeed.model.Contact;
import com.app.bitspeed.model.Contact.LinkPrecedence;
import com.app.bitspeed.model.ContactResponse;
import com.app.bitspeed.model.UserInfoSaveRequest;
import com.app.bitspeed.model.UserInfoSaveResponse;
import com.app.bitspeed.repository.ContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserContactService {

  @Autowired
  private ContactRepository contactRepository;

  public UserInfoSaveResponse BuildResponseData(List<Contact> contacts, Contact primary) {

    List<Contact> secondaryContacts = contacts.stream()
        .filter(contact -> contact.getLinkPrecedence().equals(LinkPrecedence.secondary))
        .distinct().toList();

    List<Integer> secondaryIds = secondaryContacts.stream().map(Contact::getId).distinct().toList();

    List<String> phoneNumberList = contacts.stream()
        .map(Contact::getPhoneNumber).filter(Objects::nonNull)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
    if (!phoneNumberList.contains(primary.getPhoneNumber())) {
      phoneNumberList.add(primary.getPhoneNumber());
    }

    List<String> emailList = new ArrayList<>(
        contacts.stream().map(Contact::getEmail).filter(Objects::nonNull)
            .distinct()
            .toList());
    if (!emailList.contains(primary.getEmail())) {
      emailList.add(primary.getEmail());
    }

    return createUserContactResponse(phoneNumberList, emailList, secondaryIds,
        primary.getId());


  }

  public UserInfoSaveResponse saveUserInfo(UserInfoSaveRequest requestBody) {
    String phoneNumber = requestBody.getPhoneNumber();
    String email = requestBody.getEmail();
    Contact primary = null;
    Contact secondary = null;

    try {
      log.info("Calling findByEmailOrPhoneNumber with email={} and phone={}", email, phoneNumber);
      List<Contact> contacts = searchByEmailOrPhoneNumber(email, phoneNumber);

      Optional<Contact> exactMatch = contacts.stream()
          .filter(c -> email.equals(c.getEmail()) && phoneNumber.equals(c.getPhoneNumber()))
          .findFirst();

      /// 1. Exact Match to the email, phoneNumber pair already exists
      if (exactMatch.isPresent()) {
        return BuildResponseData(contacts, exactMatch.get());
      }

      /// 2. No match exists at all
      if (contacts.isEmpty()) {
        primary = createNewUser(email, phoneNumber);
//        log.info("new info saved {}", primary);
        contacts.add(primary);
        return BuildResponseData(contacts, primary);
      } else {

        /// 3. Exists a partial match

        Optional<Contact> primaryMatch = contacts.stream()
            .filter(c -> c.getLinkPrecedence() == LinkPrecedence.primary)
            .findFirst();
        
        /// 4. if primary contact exists in the partial matches
        if (primaryMatch.isPresent()) {
          primary = primaryMatch.get();
          secondary = createSecondaryContact(email, phoneNumber, primary.getId());
          contacts.add(secondary);
          return BuildResponseData(contacts, primary);
        } else {

          Optional<Contact> secondaryMatch = contacts.stream()
              .filter(c -> c.getLinkPrecedence() == LinkPrecedence.secondary)
              .findFirst();

          /// 5. if only secondary contact exists in partial matches
          if (secondaryMatch.isPresent()) {
            int linkedPrimaryId = secondaryMatch.get().getLinkedId();
            secondary = createSecondaryContact(email, phoneNumber,
                linkedPrimaryId);
            contacts.add(secondary);

            primary = contacts.stream()
                .filter(c -> Objects.equals(c.getId(), linkedPrimaryId))
                .findFirst()
                .orElseGet(() -> contactRepository.findById((long) linkedPrimaryId)
                    .orElseThrow(() -> new IllegalStateException("Primary contact not found")));

            return BuildResponseData(contacts, primary);

          } else {
            throw new IllegalStateException(
                "Linked contacts exist but no primary or secondary found.");
          }
        }
      }
    } catch (Exception e) {
      throw e;
    }
  }


  public Contact createNewUser(String email, String phoneNumber) {
    Contact newContact = new Contact();
    if (!email.isEmpty()) {
      newContact.setEmail(email);
    }
    if (!phoneNumber.isEmpty()) {
      newContact.setPhoneNumber(phoneNumber);
    }
    newContact.setLinkPrecedence(LinkPrecedence.primary);
    contactRepository.save(newContact);
    return newContact;
  }

  public Contact createSecondaryContact(String email, String phoneNumber, int primId) {
    Contact secondary = new Contact();
    secondary.setEmail(email);
    secondary.setPhoneNumber(phoneNumber);
    secondary.setLinkPrecedence(LinkPrecedence.secondary);
    secondary.setLinkedId(primId);
    contactRepository.save(secondary);
    return secondary;
  }

  public List<Contact> searchByEmailOrPhoneNumber(String email, String phoneNumber) {
    return contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);
  }

  public UserInfoSaveResponse createUserContactResponse(List<String> phoneNumberList,
      List<String> emailList, List<Integer> secondaryIds, int primId) {
    ContactResponse contact = new ContactResponse();

    contact.setPhoneNumbers(phoneNumberList);
    contact.setEmails(emailList);
    contact.setSecondaryContactIds(secondaryIds);
    contact.setPrimaryContactId(primId);

    UserInfoSaveResponse response = new UserInfoSaveResponse();
    response.setContact(contact);
    return response;
  }
}




