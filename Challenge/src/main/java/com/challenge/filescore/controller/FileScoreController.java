package com.challenge.filescore.controller;

import com.challenge.filescore.dto.Names;
import com.challenge.filescore.service.FileScorerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/score")
@AllArgsConstructor
@Tag(name = "File Score Service", description = "Calculate total score of names in submitted file")
public class FileScoreController {

    private FileScorerService fileScorerService;

    @Operation(summary = "Score file alphabetized by last name")
    @PostMapping(value = "/last-name", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BigInteger> calculateFileScoreByLastName(@Valid @RequestBody List<Names> fileDate){
        log.info("Begin scoring file by last name");
        return ResponseEntity.ok(fileScorerService.calculateFile(fileDate, false));
    }

    @Operation(summary = "Score file alphabetized by first name")
    @PostMapping(value = "/last-name", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BigInteger> calculateFileScoreByFirstName(@Valid @RequestBody List<Names> fileDate){
        log.info("Begin scoring file by first name");
        return ResponseEntity.ok(fileScorerService.calculateFile(fileDate, true));
    }
}
