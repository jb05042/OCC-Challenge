package com.challenge.filescore;


import com.challenge.filescore.service.FileScorerService;

public class FileScorer {

    public static void main(String[] args) {
        var fileScoreApp = new FileScorerService();
        fileScoreApp.run(args);
    }

}
