package com.challenge.filescore.service;


import com.challenge.filescore.dto.Names;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class FileScorerService {

    public void run(String[] args) {
        String response;
        BigInteger fileScore;

        try {
            List<Names> fileData = parseFileData(args);
            fileScore = calculateFileScore(fileData);
            response = "Total score for requested file: " + fileScore;

        }catch (IOException e){
            log.error("Error reading from requested file to score. File Path: {} ", args);
            log.error("Error cause: {}", e.getMessage());
            response = "Unable to score requested file. Error occurred while accessing file.";
        }
        System.out.println(response);
    }

    public BigInteger calculateFile(List<Names> fileDate, boolean isSortByFirstName){
        fileDate = fileDate.stream()
                .sorted(isSortByFirstName
                        ? Comparator.comparing(Names::getFirstName)
                        : Comparator.comparing(Names::getLastName))
                .collect(Collectors.toList());
        return calculateFileScore(fileDate);
    }

    private List<Names> parseFileData(String[] args) throws IOException {
        List<Names> nameList = new ArrayList<>();
        if (args != null && args.length > 0) {
            Stream<String> lines = Files.lines(Paths.get(args[0]));

            lines.map(line -> line.split(",", -1)).flatMap(Arrays::stream).forEach(names -> {
                names = names.replace("\"","");
                String[] name = names.split(" ");
                nameList.add(Names.builder().firstName(StringUtils.trimTrailingWhitespace(name[0])).build());
            });
        }
        return CollectionUtils.isNotEmpty(nameList)
                ? nameList.stream().sorted(Comparator.comparing(Names::getFirstName)).collect(Collectors.toList())
                : nameList;

    }

    private BigInteger calculateFileScore(List<Names> fileData){
        AtomicInteger counter = new AtomicInteger(1);
        List<Integer> nameValues = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(fileData)) {
            fileData.forEach(name -> {
                List<Integer> positionValue = new ArrayList<>();
                name.getFirstName().chars().mapToObj(i -> (char) i)
                        .forEach(letter -> {
                            positionValue.add(letter - 'A' + 1);
                        });
                nameValues.add((positionValue.stream().mapToInt(Integer::intValue).sum()) * counter.get());
                counter.getAndIncrement();
            });
        }

        return CollectionUtils.isNotEmpty(nameValues)
                ? new BigInteger(String.valueOf(nameValues.stream().mapToInt(Integer::intValue).sum()))
                : BigInteger.valueOf(0);
    }
}
