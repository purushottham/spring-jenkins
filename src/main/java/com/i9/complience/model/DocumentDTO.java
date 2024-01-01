package com.i9.complience.model;

public class DocumentDTO {
	String documentTitle;
	String issuingAuthority;
	String documentNumber;
	String expiryDate;
	String dateOfI9Completion;

	String startDate;

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDateOfI9Completion() {
		return dateOfI9Completion;
	}

	public void setDateOfI9Completion(String dateOfI9Completion) {
		this.dateOfI9Completion = dateOfI9Completion;
	}

	public DocumentDTO(String documentTitle, String issuingAuthority, String documentNumber, String expiryDate,
			String dateOfI9Completion, String startDate) {
		super();
		this.documentTitle = documentTitle;
		this.issuingAuthority = issuingAuthority;
		this.documentNumber = documentNumber;
		this.expiryDate = expiryDate;
		this.dateOfI9Completion = dateOfI9Completion;
		this.startDate = startDate;
	}

}
