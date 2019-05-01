package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Eliza extends Message{
    HashMap<String, Message> messages;
    HashMap<String, String> words = new HashMap<>();
    String fileName = "words.txt";//This is the file that will be looked for.
    Scanner input = new Scanner(System.in);
    String answer;
    int random;

    public Eliza(){
        messages = new HashMap<>();
    }
    //this will run all the logic
    public void runEliza(){
        int index=0;
        populateFromFile(words, fileName);
        System.out.println("Good day. What is your problem");
        System.out.print("Enter your response here or Q to quit: ");
        answer = input.nextLine();
        if(!answer.equalsIgnoreCase("pig")) {
            addMessage(answer, new Message(answer));
        }
        while(!answer.equalsIgnoreCase("q")){
            if(answer.equalsIgnoreCase("pig")){
                playPigLatin();
            }else{
                random = (int)(1+Math.random()*20);
                if(random<10){
                    System.out.println(respond(words, answer));
                }else{
                    printHedge();
                }
            }//end if-else
            System.out.print("Enter your response here or Q to quit: ");
            answer = input.nextLine();
        }

    }

    private String respond(HashMap<String, String> words, String answer) {
        String[] qualifier = {"Why do you say that", "You seem to think that", "So, you are concerned that"};
        //This will return a String array of the Message being retrieved by the key in the answer String
        String[] m = messages.get(answer).getMessageArray();
        String str="";
        //Iterate through each word
        for(int i=0; i<m.length; i++){
            //if the word in m is contained as a key in the hashmap words
            if(words.containsKey(m[i])){
                //then replace the word at that location with the value the key is paired with
                m[i] = words.get(m[i]);
            }
        }
        //generate random number to select from the qualifier array
        int randIndex = (int) (Math.random() * qualifier.length - 1);
        str = qualifier[randIndex];
        str = str.concat(" " + String.join(" ", m));
//        messages.get(answer).setMessage(str);
        return str;
    }

    private void printHedge() {
        String[] hedge = {"Please tell me more", "Many of my patients tell me the same thing"};
        int index = (int)(Math.random() * hedge.length);
        System.out.println(hedge[index]);
    }

    public void playPigLatin(){
        String answer;
        System.out.print("Enter a word to play: ");
        answer = input.nextLine();
        String[] arrAns = answer.split(" ");
        while(!answer.equalsIgnoreCase("no pig")) {
            answer = pigLatin(arrAns);
            System.out.println(answer);
        }
    }
    private static String pigLatin(String[] arrAns) {
        String str="", tempStr="";
        HashMap<Character, String> replacement = new HashMap<>();
        //This populates the replacement HashMap
        populateFromPigFile(replacement, "pigLatin.txt");
        for(int i=0; i<arrAns.length;i++){//This iterates through the words
            //if the first letter on the word contains a consonant add "ay" to the end

            //This checks if the replacement HashMap contains the first letter of the word
            //and if it does than it appends ay to the end of word
            if(replacement.containsKey(arrAns[i].charAt(0))){
                tempStr = arrAns[i].concat(replacement.get(arrAns[i].charAt(0)));
            }
        }
        return str.concat(String.join("", tempStr));
    }


    public void addMessage(String str, Message message){
        messages.put(str, message);
    }

    private static void populateFromPigFile(HashMap<Character, String> myMap, String fileName) {
        File file = new File(fileName);
        try {
            Scanner scanInput = new Scanner(file);
            while (scanInput.hasNextLine()) {
                myMap.put(scanInput.next().charAt(0), scanInput.nextLine());

            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        }
    }

    private static void populateFromFile(HashMap<String, String> myMap, String fileName) {
        File file = new File(fileName);
        try {
            Scanner scanInput = new Scanner(file);
            while (scanInput.hasNextLine()) {
                myMap.put(scanInput.next(), scanInput.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        }
    }

    public void populateFromFile(String fileName){
        //this will use a hashmap
    }

    private static String replaceWords(String[] strArr, String[] qualifier, HashMap<String, String> hashMap) {
        String str = "";
        for (int i = 0; i < strArr.length; i++) {
            if (hashMap.containsKey(strArr[i])) {
                strArr[i] = hashMap.get(strArr[i]);
            }
        }
        //This for loop randomly selects a qualifier to prepend to the string that will be returned
        int index = (int) (Math.random() * qualifier.length - 1);
        for (int i = 0; i < qualifier.length; i++) {
            if (i == index) {
                str = qualifier[index];
            }
        }
        return str.concat(" " + String.join(" ", strArr));
    }
}
