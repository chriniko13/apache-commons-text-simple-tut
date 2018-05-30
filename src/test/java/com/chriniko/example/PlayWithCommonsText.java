package com.chriniko.example;

/*
    From baeldung: http://www.baeldung.com/java-apache-commons-text

    The root package org.apache.commons.text is divided into different sub-packages:

    org.apache.commons.text.diff – diffs between Strings
    org.apache.commons.text.similarity – similarities and distances between Strings
    org.apache.commons.text.translate – translating text
 */

import org.apache.commons.text.StrBuilder;
import org.apache.commons.text.StrSubstitutor;
import org.apache.commons.text.WordUtils;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayWithCommonsText {

    // Handling Text
    @Test
    public void whenCapitalized_thenCorrect() {
        String toBeCapitalized = "to be capitalized!";
        String result = WordUtils.capitalize(toBeCapitalized);

        assertEquals("To Be Capitalized!", result);
    }

    @Test
    public void whenContainsWords_thenCorrect() {
        boolean containsWords = WordUtils
                .containsAllWords("String to search", "to", "search");

        assertTrue(containsWords);
    }


    @Test
    public void whenSubstituted_thenCorrect() {

        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("name", "John");
        substitutes.put("college", "University of Stanford");

        String templateString = "My name is ${name} and I am a student at the ${college}.";

        StrSubstitutor sub = new StrSubstitutor(substitutes);
        String result = sub.replace(templateString);

        assertEquals("My name is John and I am a student at the University of Stanford.", result);
    }

    @Test
    public void whenReplaced_thenCorrect() {
        StrBuilder strBuilder = new StrBuilder("example StrBuilder!");
        strBuilder.replaceAll("example", "new");

        assertEquals(new StrBuilder("new StrBuilder!"), strBuilder);
    }

    // Calculating the Diff between Strings
    @Test
    public void whenEditScript_thenCorrect() {

        StringsComparator cmp = new StringsComparator("ABCFGH", "BCDEFG");
        EditScript<Character> script = cmp.getScript();

        script.visit(new CommandVisitor<Character>() {
            @Override
            public void visitInsertCommand(Character object) {
                System.out.println("visitInsertCommand: " + object);
            }

            @Override
            public void visitKeepCommand(Character object) {
                System.out.println("visitKeepCommand: " + object);
            }

            @Override
            public void visitDeleteCommand(Character object) {
                System.out.println("visitDeleteCommand: " + object);
            }
        });


        int mod = script.getModifications();
        // 4(four) because: DeleteCommand(A), InsertCommand(D), InsertCommand(E), DeleteCommand(H)
        assertEquals(4, mod);

    }


    // Similarities and Distances between Strings
    @Test
    public void whenCompare_thenCorrect() {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        int countLcs = lcs.apply("New York", "New Hampshire");

        assertEquals(5, countLcs);

    }

    @Test
    public void whenCalculateDistance_thenCorrect() {
        LongestCommonSubsequenceDistance lcsd = new LongestCommonSubsequenceDistance();
        int countLcsd = lcsd.apply("New York", "New Hampshire");

        assertEquals(11, countLcsd);
    }

}
