package Entities;

import java.util.Date;
import java.util.List;

public class ProfessionalUser extends User {

    private String profession;
    private List<String> specializationSector;
    private String curriculumVitae;
    private double averageRating;

    public ProfessionalUser(String username, String password, String name, String surname, Date dateOfBirth, char gender, String email, String country, String profession, List<String> specializationSector, String curriculumVitae) {
        super(username, password, name, surname, dateOfBirth, gender, email, country);
        this.profession = profession;
        this.specializationSector = specializationSector;
        this.curriculumVitae = curriculumVitae;
        this.averageRating = 0;
    }

    public String getProfession() {
            return profession;
        }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<String> getSpecializationSector() {
        return specializationSector;
    }

    public void setSpecializationSector(List<String> specializationSector) {
        this.specializationSector = specializationSector;
    }

    public String getCurriculumVitae() {
        return curriculumVitae;
    }

    public void setCurriculumVitae(String curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "ProfessionalUser{" +
                "profession='" + profession + '\'' +
                ", specializationSector=" + specializationSector +
                ", curriculumVitae='" + curriculumVitae + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
