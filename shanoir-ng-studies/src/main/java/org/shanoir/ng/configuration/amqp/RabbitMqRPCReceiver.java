package org.shanoir.ng.configuration.amqp;

import java.io.IOException;

import org.shanoir.ng.mapper.SubjectMapper;
import org.shanoir.ng.shared.exception.ShanoirStudiesException;
import org.shanoir.ng.shared.exception.ShanoirSubjectException;
import org.shanoir.ng.study.StudyService;
import org.shanoir.ng.study.dto.StudyStudyCardDTO;
import org.shanoir.ng.subject.Subject;
import org.shanoir.ng.subject.SubjectService;
import org.shanoir.ng.subject.dto.SubjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

/**
 * RabbitMQ message receiver with RPC Callback.
 *
 * @author atouboul
 *
 */
public class RabbitMqRPCReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(RabbitMqRPCReceiver.class);

	@Autowired
	private StudyService studyService;

	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private SubjectService subjectService;

	@RabbitListener(queues = "subject_queue_with_RPC_to_ng")
	public String receiveAndReply(byte[] msg) {
		Subject newSubject = null;
		String message = null;
		try {
			message = new String(msg, "UTF-8");
		} catch (IOException ioe) {
			LOG.error("Error while getting rabbitmq message", ioe);
		}

		LOG.info(" [x] Received request for " + message);
		final Gson oGson = new Gson();
		final SubjectDTO subjectDTO = oGson.fromJson(message, SubjectDTO.class);
		Subject subject = subjectMapper.subjectDTOToSubject(subjectDTO);

		try {
			newSubject = subjectService.save(subject);
		} catch (ShanoirSubjectException e) {
			System.out.println(e);
		}

		LOG.info(" [.] Returned Subject Id" + String.valueOf(newSubject.getId()));
		return String.valueOf(newSubject.getId());
	}

	@RabbitListener(queues = "studycard_queue_to_study")
	public void receiveMessageFromMsStudyCard(byte[] msg) {
		String message = null;
		try {
			message = new String(msg, "UTF-8");
		} catch (IOException ioe) {
			LOG.error("Error while getting rabbitmq message", ioe);
		}

		LOG.info(" [x] Received request for " + message);
		final Gson oGson = new Gson();
		final StudyStudyCardDTO dto = oGson.fromJson(message, StudyStudyCardDTO.class);

		try {
			studyService.updateFromMsStudyCard(dto);
		} catch (ShanoirStudiesException e) {
			// Error logged on service.
		}
	}

}
