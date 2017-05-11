package org.shanoir.ng.configuration.amqp;

import java.util.concurrent.CountDownLatch;

import org.shanoir.ng.shared.exception.ShanoirStudyException;
import org.shanoir.ng.study.Study;
import org.shanoir.ng.study.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.shanoir.ng.mapper.SubjectMapper;

import org.springframework.amqp.core.Message;
import org.shanoir.ng.subject.dto.SubjectDTO;
import org.shanoir.ng.subject.dto.SubjectStudyDTO;
import org.shanoir.ng.subject.SubjectStudy;
import org.shanoir.ng.subject.Subject;
import org.shanoir.ng.subject.SubjectService;
import org.shanoir.ng.shared.exception.ShanoirSubjectException;
import com.google.gson.Gson;

/**
 * RabbitMQ message receiver with RPC Callback.
 *
 * @author atouboul
 *
 */
 public class RabbitMqRPCReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(RabbitMqRPCReceiver.class);

	// private CountDownLatch latch = new CountDownLatch(1);
	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private SubjectService subjectService;

	@RabbitListener(queues = "subject_queue_with_RPC_to_ng")
	public String receiveAndReply(byte[] msg) {
    Subject newSubject = null;
		String message = null;

    // Try to read incoming msg
		try{
			message = new String(msg,"UTF-8");
  		LOG.info(" [x] Received request for " + message);
		}catch(IOException ioe){
			LOG.error("IO EXCEPTION " + ioe );
		}

    // msg deserialization into Subject DTO and then into subject
		final Gson oGson = new Gson();
		final SubjectDTO subjectDTO = oGson.fromJson(message, SubjectDTO.class);
		Subject subject = subjectMapper.subjectDTOToSubject(subjectDTO);

    // try to save subject into db
		try {
  		newSubject = subjectService.save(subject);
  	} catch (ShanoirSubjectException e) {
  		LOG.error("ShanoirSubjectException : " + e);
  	}

    // return rabbitmq message with newly created subject id
		LOG.info(" [.] Returned Subject Id" + String.valueOf(newSubject.getId()));
		return String.valueOf(newSubject.getId());
	}

}
