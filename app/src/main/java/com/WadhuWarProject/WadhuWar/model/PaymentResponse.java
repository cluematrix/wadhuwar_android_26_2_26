package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class PaymentResponse {
    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    @SerializedName("member")
    Member member;

    @SerializedName("payments")
    List<Payments> payments;

    @SerializedName("plans")
    List<Plans> plans;


    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    public Member getMember() {
        return member;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }
    public List<Payments> getPayments() {
        return payments;
    }

    public void setPlans(List<Plans> plans) {
        this.plans = plans;
    }
    public List<Plans> getPlans() {
        return plans;
    }


    public class Member {

        @SerializedName("id")
        String id;

        @SerializedName("fid")
        String fid;

        @SerializedName("first_name")
        String firstName;

        @SerializedName("middle_name")
        String middleName;

        @SerializedName("last_name")
        String lastName;

        @SerializedName("gender")
        String gender;

        @SerializedName("dob")
        Date dob;

        @SerializedName("age")
        String age;

        @SerializedName("email")
        String email;

        @SerializedName("alt_email")
        String altEmail;

        @SerializedName("password")
        String password;

        @SerializedName("mobile_no")
        String mobileNo;

        @SerializedName("wtsapp_no")
        String wtsappNo;

        @SerializedName("current_address")
        String currentAddress;

        @SerializedName("current_country")
        String currentCountry;

        @SerializedName("current_state")
        String currentState;

        @SerializedName("current_district")
        String currentDistrict;

        @SerializedName("current_taluka")
        String currentTaluka;

        @SerializedName("current_village")
        String currentVillage;

        @SerializedName("permnt_address")
        String permntAddress;

        @SerializedName("permnt_country")
        String permntCountry;

        @SerializedName("permnt_state")
        String permntState;

        @SerializedName("permnt_district")
        String permntDistrict;

        @SerializedName("permnt_taluka")
        String permntTaluka;

        @SerializedName("permnt_village")
        String permntVillage;

        @SerializedName("religion_id")
        String religionId;

        @SerializedName("caste_id")
        String casteId;

        @SerializedName("subcaste_id")
        String subcasteId;

        @SerializedName("mothertounge_id")
        String mothertoungeId;

        @SerializedName("sampraday_id")
        String sampradayId;

        @SerializedName("rashi")
        String rashi;

        @SerializedName("main_education_id")
        String mainEducationId;

        @SerializedName("highest_education")
        String highestEducation;

        @SerializedName("other_education")
        String otherEducation;

        @SerializedName("occupation_id")
        String occupationId;

        @SerializedName("main_occupation_id")
        String mainOccupationId;

        @SerializedName("post_name")
        String postName;

        @SerializedName("ofc_address")
        String ofcAddress;

        @SerializedName("ofc_loc")
        String ofcLoc;

        @SerializedName("ofc_country")
        String ofcCountry;

        @SerializedName("ofc_state")
        String ofcState;

        @SerializedName("ofc_district")
        String ofcDistrict;

        @SerializedName("yearly_salary")
        String yearlySalary;

        @SerializedName("birth_time")
        String birthTime;

        @SerializedName("manglik")
        String manglik;

        @SerializedName("hobbies")
        String hobbies;

        @SerializedName("marital_status")
        String maritalStatus;

        @SerializedName("ageofchild")
        String ageofchild;

        @SerializedName("childstay")
        String childstay;

        @SerializedName("noofchi")
        String noofchi;

        @SerializedName("dietry")
        String dietry;

        @SerializedName("lifestyle")
        String lifestyle;

        @SerializedName("bloodgroup")
        String bloodgroup;

        @SerializedName("height")
        String height;

        @SerializedName("height_cm")
        String heightCm;

        @SerializedName("weight")
        String weight;

        @SerializedName("gotra")
        String gotra;

        @SerializedName("color_complex")
        String colorComplex;

        @SerializedName("handicap")
        String handicap;

        @SerializedName("handicap_name")
        String handicapName;

        @SerializedName("father_name")
        String fatherName;

        @SerializedName("father_occ")
        String fatherOcc;

        @SerializedName("father_property")
        String fatherProperty;

        @SerializedName("property_loc")
        String propertyLoc;

        @SerializedName("father_mobile")
        String fatherMobile;

        @SerializedName("mother_name")
        String motherName;

        @SerializedName("mother_occ")
        String motherOcc;

        @SerializedName("mother_mobile")
        String motherMobile;

        @SerializedName("family_loc")
        String familyLoc;

        @SerializedName("family_class")
        String familyClass;

        @SerializedName("family_type")
        String familyType;

        @SerializedName("mama_name")
        String mamaName;

        @SerializedName("mamekul")
        String mamekul;

        @SerializedName("mama_mobile")
        String mamaMobile;

        @SerializedName("mama_occ")
        String mamaOcc;

        @SerializedName("bro_count")
        String broCount;

        @SerializedName("sis_count")
        String sisCount;

        @SerializedName("sibling_names")
        String siblingNames;

        @SerializedName("married_cnt")
        String marriedCnt;

        @SerializedName("sibling_occ")
        String siblingOcc;

        @SerializedName("about_me")
        String aboutMe;

        @SerializedName("profile_created")
        String profileCreated;

        @SerializedName("image1")
        String image1;

        @SerializedName("image2")
        String image2;

        @SerializedName("image3")
        String image3;

        @SerializedName("image4")
        String image4;

        @SerializedName("image5")
        String image5;

        @SerializedName("pref_marital")
        String prefMarital;

        @SerializedName("pref_agefrom")
        String prefAgefrom;

        @SerializedName("pref_ageto")
        String prefAgeto;

        @SerializedName("pref_height")
        String prefHeight;

        @SerializedName("pref_height_cm")
        String prefHeightCm;

        @SerializedName("pref_complex")
        String prefComplex;

        @SerializedName("prefmain_edu")
        String prefmainEdu;

        @SerializedName("pref_edu")
        String prefEdu;

        @SerializedName("prefmain_occ")
        String prefmainOcc;

        @SerializedName("pref_occ")
        String prefOcc;

        @SerializedName("pref_religion")
        String prefReligion;

        @SerializedName("pref_caste")
        String prefCaste;

        @SerializedName("pref_subcaste")
        String prefSubcaste;

        @SerializedName("pref_lifestyle")
        String prefLifestyle;

        @SerializedName("pref_diet")
        String prefDiet;

        @SerializedName("pref_expect")
        String prefExpect;

        @SerializedName("pref_sampraday")
        String prefSampraday;

        @SerializedName("pref_yearsalary")
        String prefYearsalary;

        @SerializedName("pref_mothertongue")
        String prefMothertongue;

        @SerializedName("created")
        String created;

        @SerializedName("modified")
        String modified;

        @SerializedName("is_active")
        String isActive;

        @SerializedName("is_deleted")
        String isDeleted;

        @SerializedName("is_completed")
        String isCompleted;

        @SerializedName("last_login")
        String lastLogin;

        @SerializedName("is_login")
        String isLogin;

        @SerializedName("ip_address")
        String ipAddress;

        @SerializedName("device")
        String device;

        @SerializedName("is_melawa")
        String isMelawa;

        @SerializedName("melawa_id")
        String melawaId;

        @SerializedName("device_id")
        String deviceId;

        @SerializedName("share_cnt")
        String shareCnt;

        @SerializedName("ref_count")
        String refCount;

        @SerializedName("email_verify")
        String emailVerify;

        @SerializedName("mobile_verify")
        String mobileVerify;

        @SerializedName("account_verify")
        String accountVerify;

        @SerializedName("is_active_req")
        String isActiveReq;

        @SerializedName("is_delete_req")
        String isDeleteReq;

        @SerializedName("delete_reason")
        String deleteReason;

        @SerializedName("deleted_date")
        String deletedDate;

        @SerializedName("ex_height")
        String exHeight;

        @SerializedName("is_success_story")
        String isSuccessStory;

        @SerializedName("success_img")
        String successImg;

        @SerializedName("education_certificate")
        String educationCertificate;

        @SerializedName("occupation_certificate")
        String occupationCertificate;

        @SerializedName("verification_doc")
        String verificationDoc;

        @SerializedName("verification_img")
        String verificationImg;

        @SerializedName("college_name")
        String collegeName;

        @SerializedName("property_type_name")
        String propertyTypeName;

        @SerializedName("pref_height_from")
        String prefHeightFrom;

        @SerializedName("pref_height_to")
        String prefHeightTo;

        @SerializedName("pref_country")
        String prefCountry;

        @SerializedName("pref_state")
        String prefState;

        @SerializedName("pref_dist")
        String prefDist;

        @SerializedName("referral_code")
        String referralCode;

        @SerializedName("is_cron")
        String isCron;

        @SerializedName("hide")
        String hide;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }
        public String getFid() {
            return fid;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getFirstName() {
            return firstName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }
        public String getMiddleName() {
            return middleName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getLastName() {
            return lastName;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getGender() {
            return gender;
        }

        public void setDob(Date dob) {
            this.dob = dob;
        }
        public Date getDob() {
            return dob;
        }

        public void setAge(String age) {
            this.age = age;
        }
        public String getAge() {
            return age;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }

        public void setAltEmail(String altEmail) {
            this.altEmail = altEmail;
        }
        public String getAltEmail() {
            return altEmail;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword() {
            return password;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
        public String getMobileNo() {
            return mobileNo;
        }

        public void setWtsappNo(String wtsappNo) {
            this.wtsappNo = wtsappNo;
        }
        public String getWtsappNo() {
            return wtsappNo;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }
        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentCountry(String currentCountry) {
            this.currentCountry = currentCountry;
        }
        public String getCurrentCountry() {
            return currentCountry;
        }

        public void setCurrentState(String currentState) {
            this.currentState = currentState;
        }
        public String getCurrentState() {
            return currentState;
        }

        public void setCurrentDistrict(String currentDistrict) {
            this.currentDistrict = currentDistrict;
        }
        public String getCurrentDistrict() {
            return currentDistrict;
        }

        public void setCurrentTaluka(String currentTaluka) {
            this.currentTaluka = currentTaluka;
        }
        public String getCurrentTaluka() {
            return currentTaluka;
        }

        public void setCurrentVillage(String currentVillage) {
            this.currentVillage = currentVillage;
        }
        public String getCurrentVillage() {
            return currentVillage;
        }

        public void setPermntAddress(String permntAddress) {
            this.permntAddress = permntAddress;
        }
        public String getPermntAddress() {
            return permntAddress;
        }

        public void setPermntCountry(String permntCountry) {
            this.permntCountry = permntCountry;
        }
        public String getPermntCountry() {
            return permntCountry;
        }

        public void setPermntState(String permntState) {
            this.permntState = permntState;
        }
        public String getPermntState() {
            return permntState;
        }

        public void setPermntDistrict(String permntDistrict) {
            this.permntDistrict = permntDistrict;
        }
        public String getPermntDistrict() {
            return permntDistrict;
        }

        public void setPermntTaluka(String permntTaluka) {
            this.permntTaluka = permntTaluka;
        }
        public String getPermntTaluka() {
            return permntTaluka;
        }

        public void setPermntVillage(String permntVillage) {
            this.permntVillage = permntVillage;
        }
        public String getPermntVillage() {
            return permntVillage;
        }

        public void setReligionId(String religionId) {
            this.religionId = religionId;
        }
        public String getReligionId() {
            return religionId;
        }

        public void setCasteId(String casteId) {
            this.casteId = casteId;
        }
        public String getCasteId() {
            return casteId;
        }

        public void setSubcasteId(String subcasteId) {
            this.subcasteId = subcasteId;
        }
        public String getSubcasteId() {
            return subcasteId;
        }

        public void setMothertoungeId(String mothertoungeId) {
            this.mothertoungeId = mothertoungeId;
        }
        public String getMothertoungeId() {
            return mothertoungeId;
        }

        public void setSampradayId(String sampradayId) {
            this.sampradayId = sampradayId;
        }
        public String getSampradayId() {
            return sampradayId;
        }

        public void setRashi(String rashi) {
            this.rashi = rashi;
        }
        public String getRashi() {
            return rashi;
        }

        public void setMainEducationId(String mainEducationId) {
            this.mainEducationId = mainEducationId;
        }
        public String getMainEducationId() {
            return mainEducationId;
        }

        public void setHighestEducation(String highestEducation) {
            this.highestEducation = highestEducation;
        }
        public String getHighestEducation() {
            return highestEducation;
        }

        public void setOtherEducation(String otherEducation) {
            this.otherEducation = otherEducation;
        }
        public String getOtherEducation() {
            return otherEducation;
        }

        public void setOccupationId(String occupationId) {
            this.occupationId = occupationId;
        }
        public String getOccupationId() {
            return occupationId;
        }

        public void setMainOccupationId(String mainOccupationId) {
            this.mainOccupationId = mainOccupationId;
        }
        public String getMainOccupationId() {
            return mainOccupationId;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }
        public String getPostName() {
            return postName;
        }

        public void setOfcAddress(String ofcAddress) {
            this.ofcAddress = ofcAddress;
        }
        public String getOfcAddress() {
            return ofcAddress;
        }

        public void setOfcLoc(String ofcLoc) {
            this.ofcLoc = ofcLoc;
        }
        public String getOfcLoc() {
            return ofcLoc;
        }

        public void setOfcCountry(String ofcCountry) {
            this.ofcCountry = ofcCountry;
        }
        public String getOfcCountry() {
            return ofcCountry;
        }

        public void setOfcState(String ofcState) {
            this.ofcState = ofcState;
        }
        public String getOfcState() {
            return ofcState;
        }

        public void setOfcDistrict(String ofcDistrict) {
            this.ofcDistrict = ofcDistrict;
        }
        public String getOfcDistrict() {
            return ofcDistrict;
        }

        public void setYearlySalary(String yearlySalary) {
            this.yearlySalary = yearlySalary;
        }
        public String getYearlySalary() {
            return yearlySalary;
        }

        public void setBirthTime(String birthTime) {
            this.birthTime = birthTime;
        }
        public String getBirthTime() {
            return birthTime;
        }

        public void setManglik(String manglik) {
            this.manglik = manglik;
        }
        public String getManglik() {
            return manglik;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
        }
        public String getHobbies() {
            return hobbies;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }
        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setAgeofchild(String ageofchild) {
            this.ageofchild = ageofchild;
        }
        public String getAgeofchild() {
            return ageofchild;
        }

        public void setChildstay(String childstay) {
            this.childstay = childstay;
        }
        public String getChildstay() {
            return childstay;
        }

        public void setNoofchi(String noofchi) {
            this.noofchi = noofchi;
        }
        public String getNoofchi() {
            return noofchi;
        }

        public void setDietry(String dietry) {
            this.dietry = dietry;
        }
        public String getDietry() {
            return dietry;
        }

        public void setLifestyle(String lifestyle) {
            this.lifestyle = lifestyle;
        }
        public String getLifestyle() {
            return lifestyle;
        }

        public void setBloodgroup(String bloodgroup) {
            this.bloodgroup = bloodgroup;
        }
        public String getBloodgroup() {
            return bloodgroup;
        }

        public void setHeight(String height) {
            this.height = height;
        }
        public String getHeight() {
            return height;
        }

        public void setHeightCm(String heightCm) {
            this.heightCm = heightCm;
        }
        public String getHeightCm() {
            return heightCm;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
        public String getWeight() {
            return weight;
        }

        public void setGotra(String gotra) {
            this.gotra = gotra;
        }
        public String getGotra() {
            return gotra;
        }

        public void setColorComplex(String colorComplex) {
            this.colorComplex = colorComplex;
        }
        public String getColorComplex() {
            return colorComplex;
        }

        public void setHandicap(String handicap) {
            this.handicap = handicap;
        }
        public String getHandicap() {
            return handicap;
        }

        public void setHandicapName(String handicapName) {
            this.handicapName = handicapName;
        }
        public String getHandicapName() {
            return handicapName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }
        public String getFatherName() {
            return fatherName;
        }

        public void setFatherOcc(String fatherOcc) {
            this.fatherOcc = fatherOcc;
        }
        public String getFatherOcc() {
            return fatherOcc;
        }

        public void setFatherProperty(String fatherProperty) {
            this.fatherProperty = fatherProperty;
        }
        public String getFatherProperty() {
            return fatherProperty;
        }

        public void setPropertyLoc(String propertyLoc) {
            this.propertyLoc = propertyLoc;
        }
        public String getPropertyLoc() {
            return propertyLoc;
        }

        public void setFatherMobile(String fatherMobile) {
            this.fatherMobile = fatherMobile;
        }
        public String getFatherMobile() {
            return fatherMobile;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }
        public String getMotherName() {
            return motherName;
        }

        public void setMotherOcc(String motherOcc) {
            this.motherOcc = motherOcc;
        }
        public String getMotherOcc() {
            return motherOcc;
        }

        public void setMotherMobile(String motherMobile) {
            this.motherMobile = motherMobile;
        }
        public String getMotherMobile() {
            return motherMobile;
        }

        public void setFamilyLoc(String familyLoc) {
            this.familyLoc = familyLoc;
        }
        public String getFamilyLoc() {
            return familyLoc;
        }

        public void setFamilyClass(String familyClass) {
            this.familyClass = familyClass;
        }
        public String getFamilyClass() {
            return familyClass;
        }

        public void setFamilyType(String familyType) {
            this.familyType = familyType;
        }
        public String getFamilyType() {
            return familyType;
        }

        public void setMamaName(String mamaName) {
            this.mamaName = mamaName;
        }
        public String getMamaName() {
            return mamaName;
        }

        public void setMamekul(String mamekul) {
            this.mamekul = mamekul;
        }
        public String getMamekul() {
            return mamekul;
        }

        public void setMamaMobile(String mamaMobile) {
            this.mamaMobile = mamaMobile;
        }
        public String getMamaMobile() {
            return mamaMobile;
        }

        public void setMamaOcc(String mamaOcc) {
            this.mamaOcc = mamaOcc;
        }
        public String getMamaOcc() {
            return mamaOcc;
        }

        public void setBroCount(String broCount) {
            this.broCount = broCount;
        }
        public String getBroCount() {
            return broCount;
        }

        public void setSisCount(String sisCount) {
            this.sisCount = sisCount;
        }
        public String getSisCount() {
            return sisCount;
        }

        public void setSiblingNames(String siblingNames) {
            this.siblingNames = siblingNames;
        }
        public String getSiblingNames() {
            return siblingNames;
        }

        public void setMarriedCnt(String marriedCnt) {
            this.marriedCnt = marriedCnt;
        }
        public String getMarriedCnt() {
            return marriedCnt;
        }

        public void setSiblingOcc(String siblingOcc) {
            this.siblingOcc = siblingOcc;
        }
        public String getSiblingOcc() {
            return siblingOcc;
        }

        public void setAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
        }
        public String getAboutMe() {
            return aboutMe;
        }

        public void setProfileCreated(String profileCreated) {
            this.profileCreated = profileCreated;
        }
        public String getProfileCreated() {
            return profileCreated;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }
        public String getImage1() {
            return image1;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }
        public String getImage2() {
            return image2;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }
        public String getImage3() {
            return image3;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
        }
        public String getImage4() {
            return image4;
        }

        public void setImage5(String image5) {
            this.image5 = image5;
        }
        public String getImage5() {
            return image5;
        }

        public void setPrefMarital(String prefMarital) {
            this.prefMarital = prefMarital;
        }
        public String getPrefMarital() {
            return prefMarital;
        }

        public void setPrefAgefrom(String prefAgefrom) {
            this.prefAgefrom = prefAgefrom;
        }
        public String getPrefAgefrom() {
            return prefAgefrom;
        }

        public void setPrefAgeto(String prefAgeto) {
            this.prefAgeto = prefAgeto;
        }
        public String getPrefAgeto() {
            return prefAgeto;
        }

        public void setPrefHeight(String prefHeight) {
            this.prefHeight = prefHeight;
        }
        public String getPrefHeight() {
            return prefHeight;
        }

        public void setPrefHeightCm(String prefHeightCm) {
            this.prefHeightCm = prefHeightCm;
        }
        public String getPrefHeightCm() {
            return prefHeightCm;
        }

        public void setPrefComplex(String prefComplex) {
            this.prefComplex = prefComplex;
        }
        public String getPrefComplex() {
            return prefComplex;
        }

        public void setPrefmainEdu(String prefmainEdu) {
            this.prefmainEdu = prefmainEdu;
        }
        public String getPrefmainEdu() {
            return prefmainEdu;
        }

        public void setPrefEdu(String prefEdu) {
            this.prefEdu = prefEdu;
        }
        public String getPrefEdu() {
            return prefEdu;
        }

        public void setPrefmainOcc(String prefmainOcc) {
            this.prefmainOcc = prefmainOcc;
        }
        public String getPrefmainOcc() {
            return prefmainOcc;
        }

        public void setPrefOcc(String prefOcc) {
            this.prefOcc = prefOcc;
        }
        public String getPrefOcc() {
            return prefOcc;
        }

        public void setPrefReligion(String prefReligion) {
            this.prefReligion = prefReligion;
        }
        public String getPrefReligion() {
            return prefReligion;
        }

        public void setPrefCaste(String prefCaste) {
            this.prefCaste = prefCaste;
        }
        public String getPrefCaste() {
            return prefCaste;
        }

        public void setPrefSubcaste(String prefSubcaste) {
            this.prefSubcaste = prefSubcaste;
        }
        public String getPrefSubcaste() {
            return prefSubcaste;
        }

        public void setPrefLifestyle(String prefLifestyle) {
            this.prefLifestyle = prefLifestyle;
        }
        public String getPrefLifestyle() {
            return prefLifestyle;
        }

        public void setPrefDiet(String prefDiet) {
            this.prefDiet = prefDiet;
        }
        public String getPrefDiet() {
            return prefDiet;
        }

        public void setPrefExpect(String prefExpect) {
            this.prefExpect = prefExpect;
        }
        public String getPrefExpect() {
            return prefExpect;
        }

        public void setPrefSampraday(String prefSampraday) {
            this.prefSampraday = prefSampraday;
        }
        public String getPrefSampraday() {
            return prefSampraday;
        }

        public void setPrefYearsalary(String prefYearsalary) {
            this.prefYearsalary = prefYearsalary;
        }
        public String getPrefYearsalary() {
            return prefYearsalary;
        }

        public void setPrefMothertongue(String prefMothertongue) {
            this.prefMothertongue = prefMothertongue;
        }
        public String getPrefMothertongue() {
            return prefMothertongue;
        }

        public void setCreated(String created) {
            this.created = created;
        }
        public String getCreated() {
            return created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
        public String getModified() {
            return modified;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
        public String getIsActive() {
            return isActive;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsCompleted(String isCompleted) {
            this.isCompleted = isCompleted;
        }
        public String getIsCompleted() {
            return isCompleted;
        }

        public void setLastLogin(String lastLogin) {
            this.lastLogin = lastLogin;
        }
        public String getLastLogin() {
            return lastLogin;
        }

        public void setIsLogin(String isLogin) {
            this.isLogin = isLogin;
        }
        public String getIsLogin() {
            return isLogin;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }
        public String getIpAddress() {
            return ipAddress;
        }

        public void setDevice(String device) {
            this.device = device;
        }
        public String getDevice() {
            return device;
        }

        public void setIsMelawa(String isMelawa) {
            this.isMelawa = isMelawa;
        }
        public String getIsMelawa() {
            return isMelawa;
        }

        public void setMelawaId(String melawaId) {
            this.melawaId = melawaId;
        }
        public String getMelawaId() {
            return melawaId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
        public String getDeviceId() {
            return deviceId;
        }

        public void setShareCnt(String shareCnt) {
            this.shareCnt = shareCnt;
        }
        public String getShareCnt() {
            return shareCnt;
        }

        public void setRefCount(String refCount) {
            this.refCount = refCount;
        }
        public String getRefCount() {
            return refCount;
        }

        public void setEmailVerify(String emailVerify) {
            this.emailVerify = emailVerify;
        }
        public String getEmailVerify() {
            return emailVerify;
        }

        public void setMobileVerify(String mobileVerify) {
            this.mobileVerify = mobileVerify;
        }
        public String getMobileVerify() {
            return mobileVerify;
        }

        public void setAccountVerify(String accountVerify) {
            this.accountVerify = accountVerify;
        }
        public String getAccountVerify() {
            return accountVerify;
        }

        public void setIsActiveReq(String isActiveReq) {
            this.isActiveReq = isActiveReq;
        }
        public String getIsActiveReq() {
            return isActiveReq;
        }

        public void setIsDeleteReq(String isDeleteReq) {
            this.isDeleteReq = isDeleteReq;
        }
        public String getIsDeleteReq() {
            return isDeleteReq;
        }

        public void setDeleteReason(String deleteReason) {
            this.deleteReason = deleteReason;
        }
        public String getDeleteReason() {
            return deleteReason;
        }

        public void setDeletedDate(String deletedDate) {
            this.deletedDate = deletedDate;
        }
        public String getDeletedDate() {
            return deletedDate;
        }

        public void setExHeight(String exHeight) {
            this.exHeight = exHeight;
        }
        public String getExHeight() {
            return exHeight;
        }

        public void setIsSuccessStory(String isSuccessStory) {
            this.isSuccessStory = isSuccessStory;
        }
        public String getIsSuccessStory() {
            return isSuccessStory;
        }

        public void setSuccessImg(String successImg) {
            this.successImg = successImg;
        }
        public String getSuccessImg() {
            return successImg;
        }

        public void setEducationCertificate(String educationCertificate) {
            this.educationCertificate = educationCertificate;
        }
        public String getEducationCertificate() {
            return educationCertificate;
        }

        public void setOccupationCertificate(String occupationCertificate) {
            this.occupationCertificate = occupationCertificate;
        }
        public String getOccupationCertificate() {
            return occupationCertificate;
        }

        public void setVerificationDoc(String verificationDoc) {
            this.verificationDoc = verificationDoc;
        }
        public String getVerificationDoc() {
            return verificationDoc;
        }

        public void setVerificationImg(String verificationImg) {
            this.verificationImg = verificationImg;
        }
        public String getVerificationImg() {
            return verificationImg;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }
        public String getCollegeName() {
            return collegeName;
        }

        public void setPropertyTypeName(String propertyTypeName) {
            this.propertyTypeName = propertyTypeName;
        }
        public String getPropertyTypeName() {
            return propertyTypeName;
        }

        public void setPrefHeightFrom(String prefHeightFrom) {
            this.prefHeightFrom = prefHeightFrom;
        }
        public String getPrefHeightFrom() {
            return prefHeightFrom;
        }

        public void setPrefHeightTo(String prefHeightTo) {
            this.prefHeightTo = prefHeightTo;
        }
        public String getPrefHeightTo() {
            return prefHeightTo;
        }

        public void setPrefCountry(String prefCountry) {
            this.prefCountry = prefCountry;
        }
        public String getPrefCountry() {
            return prefCountry;
        }

        public void setPrefState(String prefState) {
            this.prefState = prefState;
        }
        public String getPrefState() {
            return prefState;
        }

        public void setPrefDist(String prefDist) {
            this.prefDist = prefDist;
        }
        public String getPrefDist() {
            return prefDist;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }
        public String getReferralCode() {
            return referralCode;
        }

        public void setIsCron(String isCron) {
            this.isCron = isCron;
        }
        public String getIsCron() {
            return isCron;
        }

        public void setHide(String hide) {
            this.hide = hide;
        }
        public String getHide() {
            return hide;
        }

    }

    public class Payments {

        @SerializedName("id")
        String id;

        @SerializedName("member_id")
        String memberId;

        @SerializedName("plan_id")
        String planId;

        @SerializedName("payment_type")
        String paymentType;

        @SerializedName("payment_date")
        Date paymentDate;

        @SerializedName("last_date")
        Date lastDate;

        @SerializedName("count")
        String count;

        @SerializedName("view_count")
        String viewCount;

        @SerializedName("order_id")
        String orderId;

        @SerializedName("paid_amount")
        String paidAmount;

        @SerializedName("payment_mode")
        String paymentMode;

        @SerializedName("response_code")
        String responseCode;

        @SerializedName("response_message")
        String responseMessage;

        @SerializedName("transaction_status")
        String transactionStatus;

        @SerializedName("transaction_id")
        String transactionId;

        @SerializedName("transcation_date")
        String transcationDate;

        @SerializedName("gateway_name")
        String gatewayName;

        @SerializedName("bank_txt_id")
        String bankTxtId;

        @SerializedName("BANKNAME")
        String BANKNAME;

        @SerializedName("is_active")
        String isActive;

        @SerializedName("is_deleted")
        String isDeleted;

        @SerializedName("created")
        String created;

        @SerializedName("send_sms")
        String sendSms;

        @SerializedName("send_t")
        String sendT;

        @SerializedName("modified")
        String modified;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
        public String getMemberId() {
            return memberId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }
        public String getPlanId() {
            return planId;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }
        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentDate(Date paymentDate) {
            this.paymentDate = paymentDate;
        }
        public Date getPaymentDate() {
            return paymentDate;
        }

        public void setLastDate(Date lastDate) {
            this.lastDate = lastDate;
        }
        public Date getLastDate() {
            return lastDate;
        }

        public void setCount(String count) {
            this.count = count;
        }
        public String getCount() {
            return count;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }
        public String getViewCount() {
            return viewCount;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
        public String getOrderId() {
            return orderId;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }
        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }
        public String getPaymentMode() {
            return paymentMode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }
        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }
        public String getResponseMessage() {
            return responseMessage;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }
        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
        public String getTransactionId() {
            return transactionId;
        }

        public void setTranscationDate(String transcationDate) {
            this.transcationDate = transcationDate;
        }
        public String getTranscationDate() {
            return transcationDate;
        }

        public void setGatewayName(String gatewayName) {
            this.gatewayName = gatewayName;
        }
        public String getGatewayName() {
            return gatewayName;
        }

        public void setBankTxtId(String bankTxtId) {
            this.bankTxtId = bankTxtId;
        }
        public String getBankTxtId() {
            return bankTxtId;
        }

        public void setBANKNAME(String BANKNAME) {
            this.BANKNAME = BANKNAME;
        }
        public String getBANKNAME() {
            return BANKNAME;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
        public String getIsActive() {
            return isActive;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
        public String getIsDeleted() {
            return isDeleted;
        }

        public void setCreated(String created) {
            this.created = created;
        }
        public String getCreated() {
            return created;
        }

        public void setSendSms(String sendSms) {
            this.sendSms = sendSms;
        }
        public String getSendSms() {
            return sendSms;
        }

        public void setSendT(String sendT) {
            this.sendT = sendT;
        }
        public String getSendT() {
            return sendT;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
        public String getModified() {
            return modified;
        }

    }

    public class Plans {

        @SerializedName("id")
        String id;

        @SerializedName("user_id")
        String userId;

        @SerializedName("name")
        String name;

        @SerializedName("day")
        String day;

        @SerializedName("contact")
        String contact;

        @SerializedName("amount")
        String amount;

        @SerializedName("month")
        String month;

        @SerializedName("discount")
        String discount;

        @SerializedName("description")
        String description;

        @SerializedName("image")
        String image;

        @SerializedName("is_active")
        String isActive;

        @SerializedName("is_deleted")
        String isDeleted;

        @SerializedName("created")
        String created;

        @SerializedName("modified")
        String modified;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getUserId() {
            return userId;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setDay(String day) {
            this.day = day;
        }
        public String getDay() {
            return day;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }
        public String getContact() {
            return contact;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
        public String getAmount() {
            return amount;
        }

        public void setMonth(String month) {
            this.month = month;
        }
        public String getMonth() {
            return month;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
        public String getDiscount() {
            return discount;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
        public String getIsActive() {
            return isActive;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
        public String getIsDeleted() {
            return isDeleted;
        }

        public void setCreated(String created) {
            this.created = created;
        }
        public String getCreated() {
            return created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
        public String getModified() {
            return modified;
        }

    }
}
