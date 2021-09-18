package com.samnangthorn.hatt_app10;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

// this class is responsible for saving data and retrieve data for report to the user
// data will be save as share preferences file.xml and will be recycle within 1 month
// thus the data file size won't increase over time
// data will save in a row refer to the weeks, and column refer to days in those weeks
public class Folder {

    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private String lastDateActive;
    // Total of R(ROW) are 4: R1, R2, R3, R4
    private int R_INDEX;
    // Total of C(COLUMN) are 7: C1, C2, C3, C4, C5, C6, C7
    private int C_INDEX;

    public Folder(Context context){
        this.getData = context.getSharedPreferences("report_data", Context.MODE_PRIVATE);
        this.editData = getData.edit();
        this.lastDateActive = getData.getString("lastDateActive", "000000");
        this.R_INDEX = getData.getInt("R_INDEX", 0);
        if(this.R_INDEX == 0){
            this.R_INDEX = 1;
        }
        this.C_INDEX = getData.getInt("C_INDEX", 0);
        if(this.C_INDEX == 0){
            this.C_INDEX = 1;
        }
    }

    public void addCompleted(String currentDate, String workoutName, double totalTime, ArrayList<String> exerciseNameList){
        // C = Complete, I = Incomplete, M = Miss, N = Not Assign
        String saveString = "C" + "@" + currentDate + "@" + workoutName + "@" + totalTime + "@";
        for(int x = 0; x < exerciseNameList.size(); x++){
            saveString = saveString + exerciseNameList.get(x) + "@";
        }
        this.editData.putString("R" + this.R_INDEX + "_" + this.C_INDEX, saveString);
        this.editData.apply();
        this.lastDateActive = currentDate;
        editData.putString("lastDateActive", currentDate);
        this.C_INDEX++;
        // limit condition
        if(this.C_INDEX > 7){
            this.C_INDEX = 1;
            this.R_INDEX++;
            if(this.R_INDEX > 4){
                this.R_INDEX = 1;
            }
        }
        editData.putInt("R_INDEX", this.R_INDEX);
        editData.putInt("C_INDEX", this.C_INDEX);
        editData.apply();
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

    // Fill Up Miss Day (Date String)
    public String getFillUpLastDateString(int Index){
        String tempStr = this.lastDateActive.substring(3);
        int temInt = Integer.valueOf(tempStr);
        temInt = temInt + Index;
        return this.lastDateActive.substring(0, 4) + temInt;
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

    public int checkTotalDayInterval(String currentDate){
        int returnInteger;
        // in the same year condition
        if(currentDate.substring(0, 2).equalsIgnoreCase(this.lastDateActive.substring(0, 2))){
            // in the same month condition
            if(currentDate.substring(0, 4).equalsIgnoreCase(this.lastDateActive.substring(0, 4))){
                // in the same day condition
                if(currentDate.equalsIgnoreCase(this.lastDateActive)){
                    returnInteger = 0;
                }else{
                    returnInteger = Integer.valueOf(currentDate.substring(4)) - Integer.valueOf(this.lastDateActive.substring(4));
                }
            }else{
                returnInteger = (Integer.valueOf(currentDate.substring(2, 4)) - Integer.valueOf(this.lastDateActive.substring(2, 4)));
                if(returnInteger > 1){
                    // 32 gaps == recycle the report
                    returnInteger = 32;
                }else{
                    // returnInteger == 1 condition
                    returnInteger = (Helper.getTotalDayOfMonth(Integer.valueOf(this.lastDateActive.substring(2, 4))) - Integer.valueOf(currentDate.substring(4)) ) +  Integer.valueOf(currentDate.substring(4));
                }
            }
        }else{
            returnInteger = (Integer.valueOf(currentDate.substring(0, 2)) - Integer.valueOf(this.lastDateActive.substring(0, 2)));
            if(returnInteger > 1){
                // 32 gaps == recycle the report
                returnInteger = 32;
            }else{
                if(Integer.valueOf(this.lastDateActive.substring(2, 4)) == 12 && Integer.valueOf(currentDate.substring(2, 4)) == 1){
                    returnInteger = (Helper.getTotalDayOfMonth(Integer.valueOf(this.lastDateActive.substring(2, 4))) - Integer.valueOf(currentDate.substring(4)) ) +  Integer.valueOf(currentDate.substring(4));
                }else{
                    // 32 gaps == recycle the report
                    returnInteger = 32;
                }
            }
        }
        return returnInteger;
    }
}
