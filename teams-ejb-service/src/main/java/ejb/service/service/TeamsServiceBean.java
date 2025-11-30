package ejb.service.service;

import ejb.service.DTO.KafkaBooleanDTO;
import ejb.service.DTO.TeamDTO;
import ejb.service.entity.TeamEntity;
import ejb.service.exception.HumanAlreadyInTeamException;
import ejb.service.exception.HumanNotFoundException;
import ejb.service.exception.TeamNotFoundException;
import ejb.service.mapper.TeamMapper;
import ejb.service.repository.TeamRepository;
import ejb.service.service.i.TeamsService;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless(name = "TeamsService")
public class TeamsServiceBean implements TeamsService {
    final String IS_HUMAN_PRESENT_TOPIC = "isHumanPresent";
    final String IS_HUMAN_PRESENT_RES_TOPIC = "isHumanPresentRes";

    @Inject
    TeamRepository repo;
    TeamMapper mapper = new TeamMapper();
    KafkaProducerService kafkaProducer = new KafkaProducerService();
    KafkaConsumerService kafkaConsumer = new KafkaConsumerService(IS_HUMAN_PRESENT_RES_TOPIC);
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TeamDTO get(Long id) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        removeAllDeletedHumans(id);
        return mapper.toDTO(repo.findById(id));
    }

    @Override
    public TeamDTO create(TeamDTO dto) {
        for (Long humanId : dto.getHumans()) {
            if (!isHumanPresent(humanId))
                throw new HumanNotFoundException();
        }
        TeamEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO delete(Long id) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(id);
        entity.setIsDeleted(true);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO add(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId);

        if (entity.getHumans().contains(humanId))
            throw new HumanAlreadyInTeamException();
        entity.getHumans().add(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO deleteMember(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId);

        if (!entity.getHumans().contains(humanId))
            throw new TeamNotFoundException();
        entity.getHumans().remove(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    private void checkTeamAndHuman(Long teamId, Long humanId) {
        if (!repo.existsById(teamId))
            throw new TeamNotFoundException();
        if (!isHumanPresent(humanId))
            throw new HumanNotFoundException();
    }

    private final int TIMES_TO_ASK = 10;

    private boolean isHumanPresent(Long id) {
        kafkaProducer.sendMessage(IS_HUMAN_PRESENT_TOPIC, "id", id.toString());
        for (int i = 0; i < TIMES_TO_ASK; i++) {
            ConsumerRecords<String, String> pollRes = kafkaConsumer.poll();
            if (pollRes.isEmpty()) {
                try {
                    wait(200);
                } catch (InterruptedException ignore) {
                }
                continue;
            }
            for (ConsumerRecord<String, String> consumerRecord : pollRes) {
                try {
                    KafkaBooleanDTO res = objectMapper.readValue(consumerRecord.value(), KafkaBooleanDTO.class);
                    if (res.getId() == id && res.getResult())
                        return true;
                    if(res.getId() == id && !res.getResult())
                        return false;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return true;
    }

    private void removeAllDeletedHumans(Long teamId) {
        TeamEntity entity = repo.findById(teamId);
        entity.setHumans(entity.getHumans().stream().filter(this::isHumanPresent).collect(Collectors.toList()));
        repo.save(entity);
    }
}
