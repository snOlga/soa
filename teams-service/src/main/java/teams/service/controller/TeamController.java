package teams.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ejb.service.DTO.TeamDTO;
import ejb.service.ejb.i.TeamsService;

@RestController
@RequestMapping("/teams")
public class TeamController {
    
    @Autowired
    private TeamsService service;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDTO> deleteTeam(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{teamId}/members/{humanId}")
    public ResponseEntity<TeamDTO> add(@PathVariable Long teamId, @PathVariable Long humanId) {
        return new ResponseEntity<>(service.add(teamId, humanId), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{teamId}/members/{humanId}")
    public ResponseEntity<TeamDTO> deleteMember(@PathVariable Long teamId, @PathVariable Long humanId) {
        return new ResponseEntity<>(service.deleteMember(teamId, humanId), HttpStatus.OK);
    }
}
