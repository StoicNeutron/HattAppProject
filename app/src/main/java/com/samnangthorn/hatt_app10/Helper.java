package com.samnangthorn.hatt_app10;

import java.util.ArrayList;

public class Helper {

    private static int[] totalDayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static int[] leapYearTotalDayOfMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static String[] monthList = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private static String currentYear = "99";
    private static String currentMonth = "MONTH";
    public static String[] muscleGroup_List = {"Bicep", "Tricep", "Chest", "Shoulder", "Forearm", "Quadricep", "Abs", "Trap.", "Lat.", "Lower Back", "Calf", "Hamstring"};
    public static String[] eName_List = {"Push Up" , "Pull Up" , "Deadlift" , "Squat" , "Bench Press"};
    public static String[] mTarget_List = {"Chest" , "Lat." , "Lower back" , "Quadricep" , "Chest"};
    public static String[] sTarget_List = {"Tricep; Shoulder" , "Bicep; Trap.; Shoulder" , "Hamstring; Quadricep; Calf; Shoulder; Chest; Trap." , "Calf; Hamstring; Lower Back" , "Tricep; Shoulder"};
    public static String[] des_List = {"A common calisthenics exercise for beginners. Start with hand position on the floor, body straight - face down the floor surface, lower your body as close to the floor, and push yourself back up." , "A common calisthenics exercise for beginners. Start by two hands grabbing a pull-up bar by letting your leg above the ground, then start pulling your chest upward, touching or closing to the bar. Your chin must be above or equal to the bar to count as one rep." , "A famous compound weight training exercise. There are many forms of this exercise, such as Sumo deadlift, Romanian deadlift, ...etc. Start by stand behind the barbell on the floor, and leg almost touches the barbell, then lower your body to a squat position (vary base on the form); both hands grip the barbell, squat up (be aware of your Lower Back), then lift the barbell to a stand position." , "A common calisthenic exercise yet can also be a weight training exercise by adding weight on top. Start by widening your leg position, then lower your body while maintaining your back straight till your knee form a 90 degrees angle of less, then go back up." , "A famous weight training exercise to build a perfect chest muscle. Start by lying on a Bench-Press's bench; chest under the barbell position, then grip the barbell (grip position varies on the type of form). After getting the barbell out from its initial position, lower the barbell close to your chest, then push it back up."};

    public static ArrayList<String> exerciseNameArray = new ArrayList<String>();

    // methods

    public static boolean leapOrNot(int year){
        if(year %4 == 0){
            if(year %100 == 0){
                if (year %400 == 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    // getter methods
    //
    public static int getTotalDayOfMonth(int month) {
        return totalDayOfMonth[month];
    }

    public static int getLeapYearTotalDayOfMonth(int month) {
        return leapYearTotalDayOfMonth[month];
    }

    public static String getCurrentYear() {
        return currentYear;
    }

    public static int getCurrentMonth() {
        int returnInt = 0;
        for (int x = 0; x < monthList.length; x++){
            if (currentMonth.equalsIgnoreCase(monthList[x])){
                returnInt = x;
                break;
            }
        }
        return returnInt;
    }

    public static String getCurrentMonthName() {
        return currentMonth;
    }

    // setter methods
    //
    public static void setCurrentYear(String currentYear) {
        Helper.currentYear = currentYear;
    }

    public static void setCurrentMonth(String currentMonth) {
        Helper.currentMonth = currentMonth;
    }


}