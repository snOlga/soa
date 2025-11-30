package humans.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import humans.service.DTO.KafkaBooleanDTO;
import humans.service.repository.HumanRepository;

@Service
public class KafkaHumanService {

    @Autowired
    HumanRepository repo;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value(value = "${kafka.custom.topicname.is-human-present.res}")
    private String topicRes;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.custom.topicname.is-human-present}")
    public void isHumanPresent(String id) throws JsonProcessingException {
        Long humanId = Long.parseLong(id);
        System.err.println("HERE1");
        if (repo.findById(humanId).isPresent())
            kafkaTemplate.send(topicRes, objectMapper.writeValueAsString(new KafkaBooleanDTO(humanId, true)));
        System.err.println("HERE2");
    }

}
