package model;

import controller.CareerController;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Player {
    private int player_id;
    private String player_username;
    private String player_password;
    private String player_name;
    private String player_surname;
    private Date birth_date;
    private Date retirement_date;
    private String foot;
    private String[] rolesArray;
    private String[] characteristic;
    private int totalScoredGoal;
    private int totalConcededGoal;
    private int individualTrophy;

    public Player(int player_id, String player_username, String player_password, String player_name, String player_surname, Date birth_date, Date retirement_date, String foot, String[] rolesArray, String[] characteristic, int totalScoredGoal, int totalConcededGoal, int individualTrophy) {
        this.player_id = player_id;
        this.player_username = player_username;
        this.player_password = player_password;
        this.player_name = player_name;
        this.player_surname = player_surname;
        this.birth_date = birth_date;
        this.retirement_date = retirement_date;
        this.foot= foot;
        this.rolesArray = rolesArray;
        this.characteristic = characteristic;
        this.totalScoredGoal = totalScoredGoal;
        this.totalConcededGoal = totalConcededGoal;
        this.individualTrophy = individualTrophy;
    }

    public String getUsername() {
        return player_username;
    }

    public String getPassword() {
        return player_password;
    }

    public String getName() {
        return player_name;
    }

    public String getSurname() {
        return player_surname;
    }

    public java.sql.Date getBirthDate() {
        return (java.sql.Date) birth_date;
    }

    public Date getRetireDate() {
        return retirement_date;
    }

    public String getFoot() {
        return foot;
    }

    public String getPlayer_role(){
        return Arrays.toString(rolesArray);
    }

    public String getPlayer_characteristic(){return Arrays.toString(characteristic);}

    public int getPlayer_id() {
        return player_id;
    }

    public int getIndividualTrophy(){



        return individualTrophy;}
    public int getTotalScoredGoal() {
        return totalScoredGoal;
    }

    public int getTotalConcededGoal() {
        return totalConcededGoal;
    }


    public List<CareerInfo> getCareerInfoList() {
        // Implementa la logica per ottenere la lista delle informazioni sulla carriera dal tuo database
        // Restituisci la lista delle informazioni sulla carriera
        return CareerController.getCareerInfo(getPlayer_id());  // Assumi che ci sia un metodo simile in DBManager
    }

    public int calcolaEta(){

        Date dateFromProgram = getBirthDate();

        // Ottieni la data odierna
        LocalDate currentDate = LocalDate.now();

        // Converti la data di tipo Date a LocalDate
        LocalDate localDateFromProgram = ((java.sql.Date) dateFromProgram).toLocalDate();

        // Calcola la differenza tra le date in anni
        Period period = Period.between(localDateFromProgram, currentDate);
        int yearsDifference = period.getYears();


        return yearsDifference;
    }

    public String getCurrentTeam() {
        List<CareerInfo> careerInfoList = getCareerInfoList();

        for (CareerInfo careerInfo : careerInfoList) {
            if (careerInfo.getEndDate() == null) {
                return careerInfo.getTeamName();
            }
        }

        return " ";
    }

}