package org.shanoir.uploader.service.wsdl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.shanoir.uploader.ShUpConfig;
import org.shanoir.uploader.ShUpOnloadConfig;
import org.shanoir.uploader.model.Study;
import org.shanoir.uploader.model.dto.ExaminationDTO;
import org.shanoir.uploader.model.dto.SubjectDTO;
import org.shanoir.uploader.model.dto.SubjectStudyDTO;
import org.shanoir.uploader.utils.Util;

/**
 * 
 * Service layer for org.shanoir.ws.generated.uploader.ShanoirUploaderService.
 *
 * @author mkain
 *
 */
public class ShanoirUploaderServiceClientNG {

	private static Logger logger = Logger.getLogger(ShanoirUploaderServiceClientNG.class);
	
	private static final String SHANOIR_SERVER_URL = "shanoir.server.url";

	private static final String SERVICE_STUDIES_NAMES_CENTERS = "service.studies.names.centers";

	private HttpClient httpClient;
	
	private String serverURL;
	
	private String serviceURLStudiesNamesAndCenters;

	public ShanoirUploaderServiceClientNG() {
		this.httpClient = HttpClientBuilder.create().build();
		this.serverURL = ShUpConfig.shanoirNGServerProperties.getProperty(SHANOIR_SERVER_URL);
		this.serviceURLStudiesNamesAndCenters = this.serverURL
			+ ShUpConfig.shanoirNGServerProperties.getProperty(SERVICE_STUDIES_NAMES_CENTERS);
		logger.info("ShanoirUploaderServiceNG successfully initialized.");
	}
	
	public List<Study> findStudiesWithStudyCards() {
		try {
			HttpGet httpGet = new HttpGet(this.serviceURLStudiesNamesAndCenters);
			// add token here dynamically, as it can change during one usage of ShUp
			httpGet.addHeader("Authorization", "Bearer " + ShUpOnloadConfig.getKeycloakInstalled().getTokenString());
			HttpResponse response = httpClient.execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				String body = handler.handleResponse(response);
//				logger.info(body);
				List<Study> studies = Util.getMappedList(response, Study.class);
				return studies;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
//		List<org.shanoir.ws.generated.uploader.StudyDTO> studyList = null;
//		if (studyList != null) {
//			List<StudyDTO> studyDTOList = new ArrayList<StudyDTO>();
//			for (org.shanoir.ws.generated.uploader.StudyDTO s : studyList) {
//				List<CenterDTO> centerList = new ArrayList<CenterDTO>();
//				List<StudyCardDTO> studyCardList = new ArrayList<StudyCardDTO>();
//				// Centers part
//				if (s.getCenters() != null) {
//					for (org.shanoir.ws.generated.uploader.CenterDTO c : s.getCenters()) {
//						if (c.getInvestigators() != null) {
//							List<InvestigatorDTO> investigatorList = new ArrayList<InvestigatorDTO>();
//							for (org.shanoir.ws.generated.uploader.InvestigatorDTO i : c.getInvestigators()) {
//								investigatorList.add(new InvestigatorDTO(i.getId(), i.getName()));
//							}
//							centerList.add(new CenterDTO((long) c.getId(), c.getName(), investigatorList));
//						} else {
//							centerList.add(new CenterDTO((long) c.getId(), c.getName()));
//						}
//					}
//				}
//				// StudyCards part
//				if (s.getStudyCards() != null) {
//					for (org.shanoir.ws.generated.uploader.StudyCardDTO sc : s.getStudyCards()) {
//						studyCardList.add(new StudyCardDTO((long) sc.getId(), sc.getName(), (long) sc.getCenterId(),
//								sc.getCenterName(), sc.getAcqEquipmentManufacturer(),
//								sc.getAcqEquipmentManufacturerModel(), sc.getAcqEquipmentSerialNumber()));
//					}
//				}
//				// Study part
//				studyDTOList.add(new StudyDTO((long) s.getId(), s.getName(), studyCardList, centerList));
//			}
//			return studyDTOList;
//		}
	}

	public SubjectDTO findSubjectBySubjectIdentifier(String subjectIdentifier) throws Exception {
		org.shanoir.ws.generated.uploader.SubjectDTO subject = null;
		if (subject != null) {
			List<SubjectStudyDTO> subjectStudyListDTO = new ArrayList<SubjectStudyDTO>();
			if (subject.getSubjectStudyDTOList() != null) {
				for (org.shanoir.ws.generated.uploader.SubjectStudyDTO ssd : subject.getSubjectStudyDTOList()) {
					subjectStudyListDTO.add(new SubjectStudyDTO(ssd.getId(), ssd.getStudyId(),
							ssd.isPhysicallyInvolved(), ssd.getSubjectStudyIdentifier(), ssd.getSubjectType()));
				}
			}
			SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getBirthDate(), subject.getName(),
					subject.getSex(), subject.getImagedObjectCategory(), subject.getLanguageHemisphericDominance(),
					subject.getManualHemisphericDominance(), subjectStudyListDTO, subject.getIdentifier());
			return subjectDTO;
		} else {
			return null;
		}
	}

	public List<ExaminationDTO> findExaminationsBySubjectId(Long subjectId) throws Exception {
		if (subjectId != null) {
			List<org.shanoir.ws.generated.uploader.ExaminationDTO> examinations = null;
			List<ExaminationDTO> examinationDTOs = new ArrayList<ExaminationDTO>();
			for (org.shanoir.ws.generated.uploader.ExaminationDTO e : examinations) {
				examinationDTOs.add(new ExaminationDTO(e.getId(), e.getExaminationDate(), e.getComment()));
			}
			return examinationDTOs;
		} else {
			return null;
		}
	}
	
	public String uploadFile(final String folderName, final File file) throws Exception {
		final FileDataSource fDS = new FileDataSource(file);
		final DataHandler dataHandler = new DataHandler(fDS);
		final String result = null;
		if (!"200".equals(result)) {
			logger.error(result);
			throw new Exception("File upload error occured!");
		}
		return result;
	}
	
	/**
	 * This method creates a subject on the server.
	 * 
	 * @param studyId
	 * @param studyCardId
	 * @param modeSubjectCommonName
	 * @param subjectDTO
	 * @return boolean true, if success
	 */
	public org.shanoir.ws.generated.uploader.SubjectDTO createSubject(
			final Long studyId,
			final Long studyCardId,
			final boolean modeSubjectCommonName,
			final org.shanoir.ws.generated.uploader.SubjectDTO subjectDTO) {
		return null;
	}
	
	/**
	 * This method creates an examination on the server.
	 * 
	 * @param studyId
	 * @param subjectId
	 * @param centerId
	 * @param investigatorId
	 * @param examinationDate
	 * @param examinationComment
	 * @return
	 */
	public long createExamination(final Long studyId, final Long subjectId, final Long centerId, final Long investigatorId,
			final Date examinationDate, final String examinationComment) {
		XMLGregorianCalendar examinationDateAsXMLGregorianCalendar = Util.toXMLGregorianCalendar(examinationDate);
		return 1;
	}

}