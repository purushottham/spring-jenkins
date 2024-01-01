package com.i9.complience.model;

import java.util.Date;
import java.util.List;

public class AuthorizationDTO {
	String firstName;
	String lastName;
	Date dateOfBirth;
	String phoneNumber;
	String ssn;
	String emailId;
	String currentAddress;
	String workLocation;
	String localOrRemoteEmployment;
	Date dateOfEmployment;
	Date DateOfExit;
	String isI9DoneInThePast;
	String isI9Current;
	String immigrationStatus;
	String listA;
	String notes;
	List<DocumentDTO> documetDTOList;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public String getLocalOrRemoteEmployment() {
		return localOrRemoteEmployment;
	}

	public void setLocalOrRemoteEmployment(String localOrRemoteEmployment) {
		this.localOrRemoteEmployment = localOrRemoteEmployment;
	}

	public Date getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(Date dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	public Date getDateOfExit() {
		return DateOfExit;
	}

	public void setDateOfExit(Date dateOfExit) {
		DateOfExit = dateOfExit;
	}

	public String getIsI9DoneInThePast() {
		return isI9DoneInThePast;
	}

	public void setIsI9DoneInThePast(String isI9DoneInThePast) {
		this.isI9DoneInThePast = isI9DoneInThePast;
	}

	public String getIsI9Current() {
		return isI9Current;
	}

	public void setIsI9Current(String isI9Current) {
		this.isI9Current = isI9Current;
	}

	public String getImmigrationStatus() {
		return immigrationStatus;
	}

	public void setImmigrationStatus(String immigrationStatus) {
		this.immigrationStatus = immigrationStatus;
	}

	public String getListA() {
		return listA;
	}

	public void setListA(String listA) {
		this.listA = listA;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<DocumentDTO> getDocumetDTOList() {
		return documetDTOList;
	}

	public void setDocumetDTOList(List<DocumentDTO> documetDTOList) {
		this.documetDTOList = documetDTOList;
	}

}
