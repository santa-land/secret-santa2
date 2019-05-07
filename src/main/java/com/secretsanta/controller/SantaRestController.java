package com.secretsanta.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.secretsanta.model.Santa;
import com.secretsanta.service.SantaService;
 
@RestController
public class SantaRestController {
 
    // Service which will do all data retrieval/manipulation work
    @Autowired
    SantaService santaService;

    // --------------------------------- Get the last Santa
    // -----------------------------------------
    @RequestMapping(value = "/lastsanta", method = RequestMethod.GET)
    public ResponseEntity<String> getlastSanta() {
        System.out.println("Trying to get the last santa...");
        String lastSanta = santaService.lastSanta();
        if (lastSanta == null) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<String>(lastSanta, HttpStatus.OK);
    }

    // -------------------------------- Get number of Santas
    // ----------------------------------------
    @RequestMapping(value = "/countsanta", method = RequestMethod.GET)
    public ResponseEntity<String> getNumberOfSantas() {
        System.out.println("Trying to count number of santas ...");
        int size = santaService.santaSize();
        String santaSize = Integer.toString(size);
        if (santaSize.isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<String>(santaSize, HttpStatus.OK);
    }

    // -------------------------------- POST a new Santa
    // --------------------------------------------
    @RequestMapping(value = "/register/{name}/{spous}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createASanta(@PathVariable("name") String name, @PathVariable("spous") String spous) {

        System.out.println("Creating Santa " + name);
        Santa santa = new Santa(name, spous);

        if (santaService.isSantaExist(santa)) {
            System.out.println("A Santa with name " + santa.getSantaname() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        santaService.saveSanta(santa);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // -------------------------------- POST my match
    // ----------------------------------------------
    @RequestMapping(value = "/myMatch", method = RequestMethod.POST)
    public ResponseEntity<String> findMatch(@RequestBody String santaName, UriComponentsBuilder ucBuilder) {
        System.out.println("Checking match for " + santaName);

        Santa santaMatch = santaService.findMyMatch(santaName);
        if (santaMatch == null) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<String>(santaMatch.getSantaname(), HttpStatus.OK);
    }

    // -------------------------------- POST make matches
    // ------------------------------------------
    @RequestMapping(value = "/makeMatch/", method = RequestMethod.POST)
    public ResponseEntity<Void> makeMatch(@RequestBody String username, @RequestBody String password) {
        System.out.println("checking match for santas");

        santaService.createMatch(username, password);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // -------------------------------- POST delete the family
    // -------------------------------------
    @RequestMapping(value = "/deleteFamily/", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteFamily(@RequestBody String username, @RequestBody String password) {
        System.out.println("deleting the family ...");

        santaService.deleteAllSantas(username, password);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}