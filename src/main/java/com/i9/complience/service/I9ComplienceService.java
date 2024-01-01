package com.i9.complience.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.i9.complience.model.AuthorizationDTO;
import com.i9.complience.model.DocumentDTO;
import com.i9.complience.utility.AuthroziationDetails;
import com.i9.complience.utility.DateUtlity;

@Service
public class I9ComplienceService {

	@Value("${inputfile}")
	private String inputFilePath;

	@Value("${outputfile}")
	private String outputFilePath;

	@Value("${usersdirectory}")
	private String usersDirectory;

	public void getDetailReport() {

		List<AuthorizationDTO> authDTOS = new AuthroziationDetails().loadAuthroziationDetails(inputFilePath);

		Map<String, List<AuthorizationDTO>> authDtoMap = authDTOS.stream()
				.collect(Collectors.groupingBy(AuthorizationDTO::getSsn));

		Set<Entry<String, List<AuthorizationDTO>>> empSet = authDtoMap.entrySet();

		try {
			FileWriter fWriter = new FileWriter(outputFilePath);
			List<String> listUser = getUsers();
			for (Map.Entry<String, List<AuthorizationDTO>> entry : empSet) {
				// if (entry.getKey().equals("388-33-0209")) {
				for (String s1 : listUser) {
					if (s1.contains(entry.getKey())) {
						File directoryPath = new File(usersDirectory + s1);
						File filesList[] = directoryPath.listFiles();
						if (entry.getKey() != "") {
							List<AuthorizationDTO> authDto = entry.getValue();
							iterateDTO(authDto, filesList, fWriter);
						}
						break;
					}
					// }
				}
			}
			fWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getUsers() {
		List<String> data = new ArrayList<>();
		File listOfDirectory = new File(usersDirectory);
		File[] files = listOfDirectory.listFiles();
		for (File f : files) {
			data.add(f.getName());
		}
		return data;
	}

	private static void iterateDTO(List<AuthorizationDTO> authDto, File filesList[], FileWriter fWriter)
			throws Exception {

		try {

			StringBuffer sb = new StringBuffer();
			sb.append("<html><body>");

			sb.append("<font color=blue>Primary Information :</font>");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Name : <font color=blue>" + authDto.get(0).getFirstName() + " " + authDto.get(0).getLastName()
					+ "</font>");
			sb.append("<br>");
			sb.append("DOB : " + DateUtlity.convertDateToMMMMddyyyy(authDto.get(0).getDateOfBirth()));
			sb.append("<br>");
			sb.append("PhoneNumber : " + authDto.get(0).getPhoneNumber());
			sb.append("<br>");
			sb.append("SSN : " + authDto.get(0).getSsn());
			sb.append("<br>");
			sb.append("Email Id : " + authDto.get(0).getEmailId());
			sb.append("<br>");
			sb.append("Current Address : " + authDto.get(0).getCurrentAddress());
			sb.append("<br>");
			sb.append("Work Location : " + authDto.get(0).getWorkLocation());
			sb.append("<br>");
			if (authDto.get(0).getDateOfEmployment() != null) {
				sb.append("<font color=green>Section 1 Signed Date : </font>"
						+ DateUtlity.convertDateToString(authDto.get(0).getDateOfEmployment()));
			} else {
				sb.append("<font color=red>Section 1 Signed Date Not Provided</font>");
			}
			sb.append("<br>");
			sb.append("<br>");
			sb.append("<font color=green> SSN Document :" + authDto.get(0).getSsn() + " has been provided</font>");
			sb.append("<br>");

			int i = 1;
			List<DocumentDTO> list = new ArrayList<>();
			for (AuthorizationDTO aDto : authDto) {
				list.addAll(aDto.getDocumetDTOList());
			}
//			for (DocumentDTO dt : list) {
//				if (dt.getDateOfI9Completion() != null && !dt.getDateOfI9Completion().isEmpty()
//						&& !dt.getDateOfI9Completion().equals("Nil")) {
//					sb.append("Section 2 Signed Date : " + dt.getDateOfI9Completion());
//					sb.append("<br>");
//				}
//			}
//			sb.append("<br>");
			// int section2DocumentCount = 0;
			for (DocumentDTO dt : list) {
				if (dt.getDocumentTitle() != null && !dt.getDocumentTitle().isEmpty()
						&& !dt.getDocumentTitle().toLowerCase().equals("Nil".toLowerCase())) {
					sb.append(dt.getDocumentTitle() + " - " + dt.getDocumentNumber() + ", Expiry Date - "
							+ (dt.getExpiryDate().equals("Nil") ? "<font color=red>Date not provided</font>"
									: dt.getExpiryDate()));
					sb.append("<br>");
					// section2DocumentCount++;
				}
			}

			sb.append("<br>");
			Set<String> names = new HashSet<>();
			for (File file : filesList) {
				if (file.getName().toLowerCase().contains("section2")
						|| file.getName().toLowerCase().contains("section 2")) {
					names.add(file.getName());
				}
			}
			sb.append("<br>");
			sb.append("No Of Section 2 Docuemnts provided in spread sheet : " + authDto.size());
			sb.append("<br>");
			sb.append("Actually Section 2 Documents provided Count : " + names.size());
			sb.append("<br>");
			sb.append("<br>");
			for (AuthorizationDTO aDto : authDto) {

				StringBuffer sb2 = new StringBuffer();
				boolean i9DateExists = false;
				HashMap<String, String> adviceMap = new HashMap<>();
				if (aDto.getFirstName() != null && !aDto.getFirstName().isEmpty()) {

					if (aDto.getDocumetDTOList() != null && !aDto.getDocumetDTOList().isEmpty()) {

						for (DocumentDTO d : aDto.getDocumetDTOList()) {
							if (d.getDateOfI9Completion() != null && !d.getDateOfI9Completion().isEmpty()
									&& !d.getDateOfI9Completion().toLowerCase().equals("Nil".toLowerCase())) {
								i9DateExists = true;
							}
						}
						if (i9DateExists) {
							if (i == 1)
								sb.append("First Section 2 :");
							if (i == 2)
								sb.append("Second Section 2 :");
							if (i == 3)
								sb.append("Third Section 2 :");
							if (i == 4)
								sb.append("Fourth Section 2 :");
							if (i == 5)
								sb.append("Fifth Section 2 :");
							if (i == 6)
								sb.append("Sixth Section 2 :");
							if (i == 7)
								sb.append("Seventh Section 2 :");
							if (i == 8)
								sb.append("Eighth Section 2 :");
							if (i == 9)
								sb.append("Ninth Section 2 :");
							sb.append("<br>");
							sb.append("==============");
							sb.append("<br>");
						}

						Boolean section2SignedDate = true;
						String i9Date = null;
						int k = 0;
						String firstI9CompliationDate = null;
						for (DocumentDTO d : aDto.getDocumetDTOList()) {
							boolean documentNumberPresent = false;
							if (d.getDateOfI9Completion() != null && !d.getDateOfI9Completion().isEmpty()
									&& !d.getDateOfI9Completion().toLowerCase().equals("Nil".toLowerCase())) {
								i9Date = d.getDateOfI9Completion();
							}
							if (k == 0) {
								firstI9CompliationDate = d.getDateOfI9Completion();
							}
							k++;
							if (d.getDocumentNumber() != null && !d.getDocumentNumber().isEmpty()) {
								for (File file : filesList) {
									String fileName = file.getName().toLowerCase();
									if (d.getDocumentTitle() != null && d.getDocumentTitle().contains("EAD")) {
										if (fileName.contains("_ea")) {
											documentNumberPresent = true;
											break;

										}
									} else if (d.getDocumentTitle() != null && d.getDocumentTitle().toLowerCase()
											.contains("Foreign passport".toLowerCase())) {
										if (fileName.contains("passport")) {
											documentNumberPresent = true;
											break;

										}
									} else if (d.getDocumentTitle() != null && d.getDocumentTitle().contains("I-797")) {
										if (fileName.contains("i797")) {

											documentNumberPresent = true;
											break;

										}
									} else if (d.getDocumentTitle() != null && d.getDocumentTitle().contains("I-94")) {
										if (fileName.contains("i94")) {

											documentNumberPresent = true;
											break;

										}
									} else if (d.getDocumentTitle() != null && d.getDocumentTitle().contains("SSA")) {
										if (fileName.contains("Social Security Number".toLowerCase())) {

											documentNumberPresent = true;
											break;

										}
									} else if (d.getDocumentTitle() != null && d.getDocumentTitle().contains("DL")) {
										if (fileName.contains("Driver License".toLowerCase())) {

											documentNumberPresent = true;
											break;

										}
									}
								}
							}

							if (!d.getDocumentTitle().toLowerCase().equals("Nil".toLowerCase())
									&& !d.getDocumentTitle().isEmpty()) {

								if (firstI9CompliationDate == null || firstI9CompliationDate.isEmpty()) {
									if (d.getExpiryDate() != null && !d.getExpiryDate().isEmpty()
											&& !d.getExpiryDate().equals("-")
											&& !d.getExpiryDate().toLowerCase().equals("D/S".toLowerCase())
											&& !d.getExpiryDate().toLowerCase().equals("Nil".toLowerCase())) {
										boolean flag = DateUtlity.compareDates(DateUtlity.currentDate(),
												d.getExpiryDate());
										if (!flag) {
											adviceMap.put(d.getDocumentTitle(),
													"<font color='grey'><b> A Section 2 needs to be added since there is a new "
															+ d.getDocumentTitle() + " document number "
															+ d.getDocumentNumber() + " and expiry date "
															+ d.getExpiryDate() + "</b> </font></br>");
											// sb2.append(adviceMap.get(d.getDocumentTitle()));
//											sb2.append(
//													"<font color='grey'><b> A Section 2 needs to be added since there is a new "
//															+ d.getDocumentTitle() + " document number "
//															+ d.getDocumentNumber() + " and expiry date "
//															+ d.getExpiryDate() + "</b> </font>");
//											sb2.append("<br>");
										}
									}

								} else {
									if (section2SignedDate) {
										sb.append("<font color=green>Section 2 signed Date : </font>"
												+ d.getDateOfI9Completion());
										sb.append("<br>");
										section2SignedDate = false;
									}

									if (documentNumberPresent) {
										sb.append("<font color=#9900FF>Document Title " + d.getDocumentTitle()
												+ " scanned document has been provided</font>");

									} else {
										sb.append("<font color='red'>Document Title " + d.getDocumentTitle()
												+ " scanned document has not been provided. Please provide.</font>");
									}
									sb.append("<br>");
									sb.append("Document Number : " + d.getDocumentNumber());
									sb.append("<br>");
									if (d.getExpiryDate() != null && d.getExpiryDate().equals("D/S"))

										sb.append(
												"<font color='red'>This is duration of status , please check section 1</font>");
									else if (d.getExpiryDate() != null
											&& d.getExpiryDate().toLowerCase().equals("Nil".toLowerCase()))

										sb.append("<font color='red'>Expiry Date : Not provided</font>");
									else
										sb.append("Expiry Date : " + d.getExpiryDate());

									sb.append("<br>");
									sb.append("<br>");
									if (i9Date != null && !i9Date.isEmpty() && !i9Date.equals("N/A")) {
										if (d.getExpiryDate() != null && !d.getExpiryDate().isEmpty()
												&& !d.getExpiryDate().equals("-")
												&& !d.getExpiryDate().toLowerCase().equals("D/S".toLowerCase())
												&& !d.getExpiryDate().toLowerCase().equals("Nil".toLowerCase())) {
											boolean flag = DateUtlity.compareDates(i9Date, d.getExpiryDate());
											if (flag) {
												adviceMap.put(d.getDocumentTitle(),
														"<font color='grey'><b>" + d.getDocumentTitle()
																+ " has expired, please provide " + d.getDocumentTitle()
																+ " document in next section 2</b></font></br>");
												// sb2.append(adviceMap.get(d.getDocumentTitle()));
//												sb2.append("<font color='grey'><b>" + d.getDocumentTitle()
//														+ " has expired, please provide " + d.getDocumentTitle()
//														+ " document in next section 2</b></font>");
//												sb2.append("<br>");
											}
										}
									} else {
										adviceMap.put(d.getDocumentTitle(),
												"<font color='grey'><b> A Section 2 needs to be added since there is a new "
														+ d.getDocumentTitle() + "</b> </font></br>");
										// sb2.append(adviceMap.get(d.getDocumentTitle()));
//										sb2.append(
//												"<font color='grey'><b> A Section 2 needs to be added since there is a new "
//														+ d.getDocumentTitle() + "</b> </font>");
//										sb2.append("<br>");
									}
								}
							}
						}
					}
				}

				// Comparing previous authorization

//				for (int j = 0; j < i - 1; j++) {
//					String i9Date = null;
//					if (authDto.get(i - 1) != null && authDto.get(i - 1).getDocumetDTOList() != null
//							&& authDto.get(i - 1).getDocumetDTOList().get(0).getDateOfI9Completion() != null
//							&& !authDto.get(i - 1).getDocumetDTOList().get(0).getDateOfI9Completion().isEmpty()) {
//						i9Date = authDto.get(i - 1).getDocumetDTOList().get(0).getDateOfI9Completion();
//					} else {
//						break;
//					}
//					if (i9Date != null && !i9Date.isEmpty() && !i9Date.equals("N/A")) {
//						if (authDto.get(i - 2).getDocumetDTOList() != null
//								&& !authDto.get(i - 2).getDocumetDTOList().isEmpty()) {
//							for (DocumentDTO d : authDto.get(i - 2).getDocumetDTOList()) {
//								try {
//									if (d.getExpiryDate() != null && !d.getExpiryDate().isEmpty()
//											&& !d.getExpiryDate().equals("-")
//											&& !d.getExpiryDate().toLowerCase().equals("D/S".toLowerCase())
//											&& !d.getExpiryDate().toLowerCase().equals("Nil".toLowerCase())) {
//										boolean flag = compareDates(i9Date, d.getExpiryDate());
//										if (flag) {
//
//											sb2.append("<font color='grey'><b>" + d.getDocumentTitle()
//													+ " Got Expired, Please Provide " + d.getDocumentTitle()
//													+ " Document either in this section or in next section2</b></font>");
//											sb2.append("<br>");
//
//										}
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//									System.out.println(d.getDocumentNumber());
//								}
//							}
//						}
//					} else {
//						break;
//					}
//					break;
//				}

//				String i9Date = null;
//				if (authDto.size() > i && authDto.get(i) != null && authDto.get(i).getDocumetDTOList() != null
//						&& authDto.get(i).getDocumetDTOList().get(0).getDateOfI9Completion() != null
//						&& !authDto.get(i).getDocumetDTOList().get(0).getDateOfI9Completion().isEmpty()) {
//					i9Date = authDto.get(i).getDocumetDTOList().get(0).getDateOfI9Completion();
//				}
//				if (i9Date != null && !i9Date.isEmpty() && !i9Date.equals("N/A")) {
//					if (aDto.getDocumetDTOList() != null && !aDto.getDocumetDTOList().isEmpty()) {
//						for (DocumentDTO d : aDto.getDocumetDTOList()) {
//							try {
//								if (d.getExpiryDate() != null && !d.getExpiryDate().isEmpty()
//										&& !d.getExpiryDate().equals("-")
//										&& !d.getExpiryDate().toLowerCase().equals("D/S".toLowerCase())
//										&& !d.getExpiryDate().toLowerCase().equals("Nil".toLowerCase())) {
//									boolean flag = compareDates(i9Date, d.getExpiryDate());
//									if (flag) {
//
//										sb2.append("<font color='green'>Previous: " + d.getDocumentTitle()
//												+ " Got Expired, Please Provide " + d.getDocumentTitle()
//												+ " Document in next section2</font>");
//										sb2.append("<br>");
//
//									}
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println(d.getDocumentNumber());
//							}
//						}
//					}
//				}

				if (authDto.size() > i && authDto.get(i) != null && authDto.get(i).getDocumetDTOList() != null) {
					List<DocumentDTO> dtoList1 = authDto.get(i - 1).getDocumetDTOList();
					List<DocumentDTO> dtoList2 = authDto.get(i).getDocumetDTOList();
					if (dtoList1 != null && !dtoList1.isEmpty() && dtoList2 != null && !dtoList2.isEmpty()) {
						for (DocumentDTO d1 : dtoList1) {
							String signedDate = "";
							for (DocumentDTO d2 : dtoList2) {
								if (d2.getStartDate() != null && !d2.getStartDate().isEmpty()
										&& !d2.getStartDate().equals("Nil")) {
									Date stDate = DateUtlity.convertStringToDate(d2.getStartDate());
									SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
									Calendar c = Calendar.getInstance();
									c.setTime(stDate); // Using today's date
									c.add(Calendar.DATE, 7); // Adding 7 days
									String output = sdf.format(c.getTime());
									System.out.println(output);
									signedDate = ", signed " + output;
								}
								try {
									if (d1.getDocumentTitle() != null && !d1.getDocumentTitle().isEmpty()
											&& !d1.getDocumentTitle().toLowerCase().equals("Nil".toLowerCase())
											&& d2.getDocumentTitle() != null && !d2.getDocumentTitle().isEmpty()
											&& !d2.getDocumentTitle().toLowerCase().equals("Nil".toLowerCase())
											&& d1.getDocumentNumber() != null && !d1.getDocumentNumber().isEmpty()
											&& !d1.getDocumentNumber().toLowerCase().equals("Nil".toLowerCase())
											&& d2.getDocumentNumber() != null && !d2.getDocumentNumber().isEmpty()
											&& !d2.getDocumentNumber().toLowerCase().equals("Nil".toLowerCase())
//											&& d2.getDateOfI9Completion() != null
//											&& !d2.getDateOfI9Completion().isEmpty()
//											&& !d2.getDateOfI9Completion().toLowerCase().equals("Nil".toLowerCase())
									) {

										if (d1.getDocumentTitle().equalsIgnoreCase(d2.getDocumentTitle())) {

											if (!d1.getDocumentNumber().equals(d2.getDocumentNumber())) {
												if (d2.getDateOfI9Completion() != null
														&& !d2.getDateOfI9Completion().isEmpty()
														&& !d2.getDateOfI9Completion().toLowerCase()
																.equals("Nil".toLowerCase())) {
													if (!d2.getDocumentTitle().contains("I-797")) {
														adviceMap.put(d1.getDocumentTitle(),
																"<font color='grey'><b>Insert separate section 2 with new "
																		+ d1.getDocumentTitle() + " document number "
																		+ d2.getDocumentNumber() + " details only "
																		+ signedDate + "</b></font><br>");
													}
												}
												// String date=d2.getDateOfI9Completion();
//												adviceMap.put(d1.getDocumentTitle(),
//														"<font color='grey'><b>Insert separate section 2 with new "
//																+ d1.getDocumentTitle() + " document number "
//																+ d2.getDocumentNumber() + " details only " + signedDate
//																+ "</b></font><br>");
												// sb2.append(adviceMap.get(d1.getDocumentTitle()));
//												sb2.append("<font color='grey'><b>Insert separate section 2 with new "
//														+ d1.getDocumentTitle() + " details only " + signedDate
//														+ "</b></font>");
//												sb2.append("<br>");
//												sb2.append("<font color='green'>Document Number : "
//														+ d1.getDocumentNumber() + "</font>");
//												sb2.append("<br>");
//												sb2.append("<font color='green'>Expiry Date : "
//														+ d1.getExpiryDate() + "</font>");
//												sb2.append("<br>");
//												sb2.append("<br>");

											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}

				if (!adviceMap.isEmpty()) {
					Set<Entry<String, String>> set = adviceMap.entrySet();
					for (Entry<String, String> vat : set) {
						sb2.append(vat.getValue());
					}
				}

				if (sb2 != null && !sb2.toString().isEmpty()) {
					// if (i9DateExists)
					sb.append("<font color='#0114FE' style='font-size:20px'><b>Advice:</b><br></font>");
				}

				sb.append(sb2);
				sb.append("<br>");

				i++;
			}
			sb.append("<br>");
			sb.append("*****************************************************");
			sb.append("<br>");
			sb.append("<body/><html/>");

			fWriter.append(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
