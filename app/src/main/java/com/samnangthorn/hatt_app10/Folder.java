package com.samnangthorn.hatt_app10;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class Folder {

    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    // Total of R(ROW) are 4: R1, R2, R3, R4
    private int R_INDEX;
    // Total of C(COLUMN) are 7: C1, C2, C3, C4, C5, C6, C7
    private int C_INDEX;

    public Folder(Context context){
        this.getData = context.getSharedPreferences("report_data", Context.MODE_PRIVATE);
        this.editData = getData.edit();
        this.R_INDEX = getData.getInt("R_INDEX", 0);
        this.C_INDEX = getData.getInt("C_INDEX", 0);
    }

    public void addCompleted(String currentDate, String workoutName, double totalTime, ArrayList<String> exerciseNameList){
        // C = Complete, I = Incomplete, M = Miss, N = Not Assign
        String saveString = "C" + "@" + currentDate + "@" + workoutName + "@" + totalTime + "@";
        for(int x = 0; x < exerciseNameList.size(); x++){
            saveString = saveString + exerciseNameList.get(x) + "@";
        }
        this.editData.putString("R" + this.R_INDEX + "_" + this.C_INDEX, saveString);
        this.editData.apply();
        this.C_INDEX++;
        // limit condition
        if(this.C_INDEX > 7){
            this.C_INDEX = 1;
            this.R_INDEX++;
            if(this.R_INDEX > 4){
                this.R_INDEX = 1;
            }
        }
    }

    public void addInCompleted(String currentDate, String workoutName, double totalTime, ArrayList<String> exerciseNameList){
        // C = Complete, I = Incomplete, M = Miss, N = Not Assign
        String saveString = "I" + "@" + currentDate + "@" + workoutName + "@" + totalTime + "@";
        for(int x = 0; x < exerciseNameList.size(); x++){
            saveString = saveString + exerciseNameList.get(x) + "@";
        }
        this.editData.putString("R"+this.R_INDEX+"_"+this.C_INDEX, saveString);
        this.editData.apply();
    }

    public void addMiss(String currentDate, String workoutName){
        // C = Complete, I = Incomplete, M = Miss, N = Not Assign
        String saveString = "M" + "@" + currentDate + "@" + workoutName + "@" + 0 + "@";
        this.editData.putString("R"+this.R_INDEX+"_"+this.C_INDEX, saveString);
        this.editData.apply();
    }

    public void addNotAssign(String currentDate){
        // C = Complete, I = Incomplete, M = Miss, N = Not Assign
        String saveString = "N" + "@" + currentDate + "@";
        this.editData.putString("R"+this.R_INDEX+"_"+this.C_INDEX, saveString);
        this.editData.apply();
    }

    // return true if there a data of last week report
    // else return false
    public boolean getLastWeekReportStatus(){
        if(this.R_INDEX > 1){
            return true;
        }else{
            return false;
        }
    }

    // condition last week report must be true
    public String[] getLastWeekReport(){
        String[] returnArray = new String[7];
        String readingString;
        for(int x = 0; x < 7; x++){
            readingString = this.getData.getString("R" + this.R_INDEX + "_" + (x+1), "ERROR").substring(0, 1);
            returnArray[x] = readingString;
        }
        return returnArray;
    }
}
