package com.samnangthorn.hatt_app10;

import java.util.ArrayList;

public class RAM {

    // exercise_DB
    private static ArrayList<String> exerciseName_List = new ArrayList<String>();
    private static ArrayList<String> mainMuscle_List = new ArrayList<String>();
    private static ArrayList<String> subMuscle_List = new ArrayList<String>();
    private static ArrayList<String> exerciseDescription_List = new ArrayList<String>();
    public static int randomIndex;

    // write methods (input and update)
    public static void write_exerciseName(String eName){
        exerciseName_List.add(eName);
    }

    public static void write_mainMuscle(String mainMuscle){
        mainMuscle_List.add(mainMuscle);
    }

    public static void write_subMuscle(String subMuscle){
        subMuscle_List.add(subMuscle);
    }

    public static void write_exerciseDescription(String des){
        exerciseDescription_List.add(des);
    }

    // read methods (access and view)
    public static ArrayList read_exerciseName(){
        return exerciseName_List;
    }

    public static String read_exerciseNameAt(int index){
        return exerciseName_List.get(index);
    }

    public static ArrayList read_mainMuscle(){
        return mainMuscle_List;
    }

    public static String read_mainMuscleAt(int index){
        return mainMuscle_List.get(index);
    }

    public static ArrayList read_subMuscle(){
        return subMuscle_List;
    }

    public static String read_subMuscleAt(int index){
        return subMuscle_List.get(index);
    }

    public static ArrayList read_exerciseDescription(){
        return exerciseDescription_List;
    }

    public static String read_exerciseDescriptionAt(int index){
        return exerciseDescription_List.get(index);
    }

    // other methods
    public static int getList_length(){
        return exerciseName_List.size();
    }
}
