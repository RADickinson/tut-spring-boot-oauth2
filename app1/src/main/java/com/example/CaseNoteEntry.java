package com.example;

import java.util.Date;

public class CaseNoteEntry {

  private String entryType;
  private String entrySubtype;
  private String practitioner;
  private String eventDetails;
  private Date eventDate;
  
  public CaseNoteEntry(String entryType, String entrySubtype, String practitioner, String eventDetails, Date eventDate) {
    this.entryType = entryType;
    this.entrySubtype = entrySubtype;
    this.practitioner = practitioner;
    this.eventDetails = eventDetails;
    this.eventDate = eventDate;
  }

  public String getEntryType() {
    return entryType;
  }

  public void setEntryType(String entryType) {
    this.entryType = entryType;
  }

  public String getEntrySubtype() {
    return entrySubtype;
  }

  public void setEntrySubtype(String entrySubtype) {
    this.entrySubtype = entrySubtype;
  }

  public String getPractitioner() {
    return practitioner;
  }

  public void setPractitioner(String practitioner) {
    this.practitioner = practitioner;
  }

  public String getEventDetails() {
    return eventDetails;
  }

  public void setEventDetails(String eventDetails) {
    this.eventDetails = eventDetails;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }

  @Override
  public String toString() {
    return "CaseNoteEntry(entryType="+entryType+",entrySubtype="+entrySubtype+",practitioner="+practitioner+",eventDetails="+eventDetails+",eventDate="+eventDate+")";
  }

}
