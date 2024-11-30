package entity;

import java.util.List;
import java.util.Objects;

/**
 * A detailed target audience.
 */
public class DetailedTargetAudience {
    private String name;
    private int minAge;
    private int maxAge;
    private String gender;
    private String educationLevel;
    private String occupation;
    private String incomeLevel;
    private String geographicLocation;

    private List<String> interestsAndPassions;
    private List<String> values;
    private List<String> personalityTraits;
    private String lifestyle;

    private boolean isEarlyAdopter;
    private String techSavviness;
    private List<String> gadgetOwnership;
    private List<String> mediaConsumption;
    private List<String> onlineEngagement;
    private boolean isInfluencer;

    private List<String> eventParticipation;
    private List<String> hobbies;
    private List<String> brandAffinity;
    private boolean environmentalConcerns;
    private boolean globalPerspective;
    private boolean multilingualAbilities;

    public DetailedTargetAudience(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(String incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public String getGeographicLocation() {
        return geographicLocation;
    }

    public void setGeographicLocation(String geographicLocation) {
        this.geographicLocation = geographicLocation;
    }

    public List<String> getInterestsAndPassions() {
        return interestsAndPassions;
    }

    public void setInterestsAndPassions(List<String> interestsAndPassions) {
        this.interestsAndPassions = interestsAndPassions;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(List<String> personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public boolean isEarlyAdopter() {
        return isEarlyAdopter;
    }

    public void setEarlyAdopter(boolean inputisEarlyAdopter) {
        this.isEarlyAdopter = inputisEarlyAdopter;
    }

    public String getTechSavviness() {
        return techSavviness;
    }

    public void setTechSavviness(String techSavviness) {
        this.techSavviness = techSavviness;
    }

    public List<String> getGadgetOwnership() {
        return gadgetOwnership;
    }

    public void setGadgetOwnership(List<String> gadgetOwnership) {
        this.gadgetOwnership = gadgetOwnership;
    }

    public List<String> getMediaConsumption() {
        return mediaConsumption;
    }

    public void setMediaConsumption(List<String> mediaConsumption) {
        this.mediaConsumption = mediaConsumption;
    }

    public List<String> getOnlineEngagement() {
        return onlineEngagement;
    }

    public void setOnlineEngagement(List<String> onlineEngagement) {
        this.onlineEngagement = onlineEngagement;
    }

    public boolean isInfluencer() {
        return isInfluencer;
    }

    public void setInfluencer(boolean inputisInfluencer) {
        this.isInfluencer = inputisInfluencer;
    }

    public List<String> getEventParticipation() {
        return eventParticipation;
    }

    public void setEventParticipation(List<String> eventParticipation) {
        this.eventParticipation = eventParticipation;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getBrandAffinity() {
        return brandAffinity;
    }

    public void setBrandAffinity(List<String> brandAffinity) {
        this.brandAffinity = brandAffinity;
    }

    public boolean isEnvironmentalConcerns() {
        return environmentalConcerns;
    }

    public void setEnvironmentalConcerns(boolean environmentalConcerns) {
        this.environmentalConcerns = environmentalConcerns;
    }

    public boolean isGlobalPerspective() {
        return globalPerspective;
    }

    public void setGlobalPerspective(boolean globalPerspective) {
        this.globalPerspective = globalPerspective;
    }

    public boolean isMultilingualAbilities() {
        return multilingualAbilities;
    }

    public void setMultilingualAbilities(boolean multilingualAbilities) {
        this.multilingualAbilities = multilingualAbilities;
    }

    @Override
    public String toString() {
        return "DetailedTargetAudience{" +
                "name='" + name + '\'' +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", gender='" + gender + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", occupation='" + occupation + '\'' +
                ", incomeLevel='" + incomeLevel + '\'' +
                ", geographicLocation='" + geographicLocation + '\'' +
                ", interestsAndPassions=" + interestsAndPassions +
                ", values=" + values +
                ", personalityTraits=" + personalityTraits +
                ", lifestyle='" + lifestyle + '\'' +
                ", isEarlyAdopter=" + isEarlyAdopter +
                ", techSavviness='" + techSavviness + '\'' +
                ", gadgetOwnership=" + gadgetOwnership +
                ", mediaConsumption=" + mediaConsumption +
                ", onlineEngagement=" + onlineEngagement +
                ", isInfluencer=" + isInfluencer +
                ", eventParticipation=" + eventParticipation +
                ", hobbies=" + hobbies +
                ", brandAffinity=" + brandAffinity +
                ", environmentalConcerns=" + environmentalConcerns +
                ", globalPerspective=" + globalPerspective +
                ", multilingualAbilities=" + multilingualAbilities +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailedTargetAudience)) return false;
        DetailedTargetAudience that = (DetailedTargetAudience) o;
        return minAge == that.minAge &&
                maxAge == that.maxAge &&
                isEarlyAdopter == that.isEarlyAdopter &&
                isInfluencer == that.isInfluencer &&
                environmentalConcerns == that.environmentalConcerns &&
                globalPerspective == that.globalPerspective &&
                multilingualAbilities == that.multilingualAbilities &&
                Objects.equals(name, that.name) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(educationLevel, that.educationLevel) &&
                Objects.equals(occupation, that.occupation) &&
                Objects.equals(incomeLevel, that.incomeLevel) &&
                Objects.equals(geographicLocation, that.geographicLocation) &&
                Objects.equals(interestsAndPassions, that.interestsAndPassions) &&
                Objects.equals(values, that.values) &&
                Objects.equals(personalityTraits, that.personalityTraits) &&
                Objects.equals(lifestyle, that.lifestyle) &&
                Objects.equals(techSavviness, that.techSavviness) &&
                Objects.equals(gadgetOwnership, that.gadgetOwnership) &&
                Objects.equals(mediaConsumption, that.mediaConsumption) &&
                Objects.equals(onlineEngagement, that.onlineEngagement) &&
                Objects.equals(eventParticipation, that.eventParticipation) &&
                Objects.equals(hobbies, that.hobbies) &&
                Objects.equals(brandAffinity, that.brandAffinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minAge, maxAge, gender, educationLevel, occupation, incomeLevel, geographicLocation,
                interestsAndPassions, values, personalityTraits, lifestyle, isEarlyAdopter, techSavviness,
                gadgetOwnership, mediaConsumption, onlineEngagement, isInfluencer, eventParticipation,
                hobbies, brandAffinity, environmentalConcerns, globalPerspective, multilingualAbilities);
    }
}
