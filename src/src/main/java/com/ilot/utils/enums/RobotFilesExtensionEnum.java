package com.ilot.utils.enums;

import com.ilot.utils.Utilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class RobotFilesExtensionEnum {
    public static final String ROBOT      = "robot";
    public static final String TXT        = "txt";
    public static final String CSV        = "csv";
    public static final String JSON       = "json";
    public static final String PYTHON     = "py";
    public static final String PDF        = "pdf";
    public static final String POWERPOINT = "pptx";

    public static List<String> getList() {
        return Arrays.asList(ROBOT, TXT, CSV, JSON, PYTHON);
    }

    public static boolean isValid(String type) {
        return getList().stream().anyMatch(o -> Utilities.areEquals(o, type));
    }

    public static void areAllValid(List<String> filesExtList) {
        Predicate<String> anyNoneValidPredicate = ext -> !getList().contains(ext);
        if (filesExtList.stream().anyMatch(anyNoneValidPredicate)) {
            throw new RuntimeException("Les extensions des fichiers doivent appartenir à cette liste " + getList().toString());
        }
    }

    public static void hasOneRobotFile(List<String> filesExtList) {
        Predicate<String> robotPredicate = ext -> Utilities.areEquals(ext, ROBOT);
        if (filesExtList.stream().noneMatch(robotPredicate)) {
            throw new RuntimeException("Aucun fichier robot selectionné.");
        }
        if (filesExtList.stream().filter(robotPredicate).count() > 1L) {
            throw new RuntimeException("Un seul fichier robot est demandé.");
        }
    }

    public static void validRobotFilesExtension(List<String> filesExtList) {
        areAllValid(filesExtList);
        hasOneRobotFile(filesExtList);
    }
}
