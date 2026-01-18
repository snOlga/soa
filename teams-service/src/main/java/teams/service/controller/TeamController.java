package teams.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ejb.service.DTO.TeamDTO;
import teams.service.client.MuleEsbTeamsService;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private MuleEsbTeamsService service;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> get(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.getTeam(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO dto) {
        return new ResponseEntity<>(service.createTeam(dto), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDTO> deleteTeam(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.deleteTeam(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{teamId}/members/{humanId}")
    public ResponseEntity<TeamDTO> add(@PathVariable(name = "teamId") Long teamId, @PathVariable(name = "humanId") Long humanId) {
        return new ResponseEntity<>(service.addMember(teamId, humanId), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{teamId}/members/{humanId}")
    public ResponseEntity<TeamDTO> deleteMember(@PathVariable(name = "teamId") Long teamId, @PathVariable(name = "humanId") Long humanId) {
        return new ResponseEntity<>(service.removeMemberFromTeam(teamId, humanId), HttpStatus.OK);
    }

    @KafkaListener(topics = "${kafka.custom.topicname.deletedHumanId}")
    public void deleteMember(Long humanId) {        
        service.deleteMemberByHumanId(humanId);
    }
}
