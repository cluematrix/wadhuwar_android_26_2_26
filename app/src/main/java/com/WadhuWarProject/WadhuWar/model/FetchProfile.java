

package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FetchProfile implements Serializable {




    @SerializedName("prefmain_occ_name")
    @Expose
    String prefmain_occ_name;

    @SerializedName("pref_occ_name")
    @Expose
    String pref_occ_name;



    @SerializedName("main_occupation_name")
    @Expose
    String main_occupation_name;


    @SerializedName("prefmain_edu_name")
    @Expose
    String prefmain_edu_name;

    @SerializedName("main_education_id")
    @Expose
    String main_education_id;

    @SerializedName("main_education_name")
    @Expose
    String main_education_name;


    @SerializedName("is_active")
    @Expose
    String is_active;

    @SerializedName("already_seen")
    @Expose
    String already_seen;

    @SerializedName("islike")
    @Expose
    String islike;

    @SerializedName("likecount")
    @Expose
    String likecount;

    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("is_delete")
    @Expose
    String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @SerializedName("view_count")
    @Expose
    String view_count;


    @SerializedName("images")
    @Expose
    public List<SliderListImage> images;

    @SerializedName("verification_img")
    @Expose
    public String verification_img;


    @SerializedName("verification_doc")
    @Expose
    String verification_doc;

    @SerializedName("education_certificate")
    @Expose
    String education_certificate;

    @SerializedName("occupation_certificate")
    @Expose
    String occupation_certificate;

    @SerializedName("is_connected")
    @Expose
    String is_connected;


    @SerializedName("is_shortlist")
    @Expose
    String is_shortlist;


    @SerializedName("no_of_child")
    @Expose
    String no_of_child;

    @SerializedName("child_stay")
    @Expose
    String child_stay;

    @SerializedName("age_of_child")
    @Expose
    String age_of_child;


    @SerializedName("chkonline")
    @Expose
    String chkonline;

    @SerializedName("just_joined")
    @Expose
    String just_joined;

    @SerializedName("account_verify")
    @Expose
    String account_verify;

    @SerializedName("premium")
    @Expose
    String premium;

    @SerializedName("acc_type")
    @Expose
    String acc_type;

    @SerializedName("acc_id")
    @Expose
    String acc_id;

    @SerializedName("acc_verify")
    @Expose
    String acc_verify;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("per_ditrict_name")
    @Expose
    String per_ditrict_name;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("fname")
    @Expose
    String fname;
    @SerializedName("mname")
    @Expose
    String mname;
    @SerializedName("lname")
    @Expose
    String lname;
    @SerializedName("gender")
    @Expose
    String gender;
    @SerializedName("dob")
    @Expose
    String dob;
    @SerializedName("age")
    @Expose
    String age;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("alt_email")
    @Expose
    String alt_email;
    @SerializedName("mobile_no")
    @Expose
    String mobile_no;
    @SerializedName("wtsapp_no")
    @Expose
    String wtsapp_no;
    @SerializedName("current_address")
    @Expose
    String current_address;
    @SerializedName("curr_country_id")
    @Expose
    String curr_country_id;
    @SerializedName("curr_country_name")
    @Expose
    String curr_country_name;
    @SerializedName("curr_state_id")
    @Expose
    String curr_state_id;
    @SerializedName("curr_state_name")
    @Expose
    String curr_state_name;
    @SerializedName("curr_district_id")
    @Expose
    String curr_district_id;
    @SerializedName("curr_ditrict_name")
    @Expose
    String curr_ditrict_name;

    @SerializedName("curr_taluka_id")
    @Expose
    String curr_taluka_id;

    @SerializedName("curr_taluka_name")
    @Expose
    String curr_taluka_name;

    @SerializedName("current_village")
    @Expose
    String current_village;

    @SerializedName("per_address")
    @Expose
    String per_address;

    @SerializedName("per_country_id")
    @Expose
    String per_country_id;

    @SerializedName("per_country_name")
    @Expose
    String per_country_name;

    @SerializedName("per_state_id")
    @Expose
    String per_state_id;

    @SerializedName("per_state_name")
    @Expose
    String per_state_name;

    @SerializedName("per_district_id")
    @Expose
    String per_district_id;


    @SerializedName("per_taluka_id")
    @Expose
    String per_taluka_id;


    @SerializedName("per_taluka_name")
    @Expose
    String per_taluka_name;


    @SerializedName("per_village")
    @Expose
    String per_village;


    @SerializedName("religion_id")
    @Expose
    String religion_id;


    @SerializedName("religion_name")
    @Expose
    String religion_name;

    @SerializedName("sampraday_name")
    @Expose
    String sampraday_name;

    @SerializedName("rashi_name")
    @Expose
    String rashi_name;


    @SerializedName("caste_id")
    @Expose
    String caste_id;

    @SerializedName("caste_name")
    @Expose
    String caste_name;


    @SerializedName("subcaste_id")
    @Expose
    String subcaste_id;

    @SerializedName("subcaste_name")
    @Expose
    String subcaste_name;

    @SerializedName("mothertounge_id")
    @Expose
    String mothertounge_id;

    @SerializedName("mothertounge_name")
    @Expose
    String mothertounge_name;

    @SerializedName("education_id")
    @Expose
    String education_id;

    @SerializedName("education_name")
    @Expose
    String education_name;

    @SerializedName("other_education")
    @Expose
    String other_education;

    @SerializedName("occupation_id")
    @Expose
    String occupation_id;

    @SerializedName("occupation_name")
    @Expose
    String occupation_name;

    @SerializedName("college_name")
    @Expose
    String college_name;

    @SerializedName("post_name")
    @Expose
    String post_name;

    @SerializedName("ofc_address")
    @Expose
    String ofc_address;

    @SerializedName("ofc_loc")
    @Expose
    String ofc_loc;

    @SerializedName("ofc_country_id")
    @Expose
    String ofc_country_id;


    @SerializedName("ofc_country_name")
    @Expose
    String ofc_country_name;


    @SerializedName("ofc_state_id")
    @Expose
    String ofc_state_id;


    @SerializedName("ofc_state_name")
    @Expose
    String ofc_state_name;


    @SerializedName("ofc_district_id")
    @Expose
    String ofc_district_id;


    @SerializedName("ofc_ditrict_name")
    @Expose
    String ofc_ditrict_name;


    @SerializedName("yearly_salary")
    @Expose
    String yearly_salary;

    @SerializedName("pref_yearsalary")
    @Expose
    String pref_yearsalary;


    @SerializedName("birth_time")
    @Expose
    String birth_time;

    @SerializedName("manglik")
    @Expose
    String manglik;

    @SerializedName("hobbies")
    @Expose
    String hobbies;

    @SerializedName("marital_status_id")
    @Expose
    String marital_status_id;

    @SerializedName("marital_status_name")
    @Expose
    String marital_status_name;

    @SerializedName("dietry_id")
    @Expose
    String dietry_id;

    @SerializedName("dietry_name")
    @Expose
    String dietry_name;

    @SerializedName("lifestyle_id")
    @Expose
    String lifestyle_id;

    @SerializedName("lifestyle_name")
    @Expose
    String lifestyle_name;

    @SerializedName("bloodgroup_id")
    @Expose
    String bloodgroup_id;

    @SerializedName("bloodgroup_name")
    @Expose
    String bloodgroup_name;

    @SerializedName("height")
    @Expose
    String height;

    @SerializedName("weight")
    @Expose
    String weight;

    @SerializedName("gotra")
    @Expose
    String gotra;

    @SerializedName("color_complex_id")
    @Expose
    String color_complex_id;

    @SerializedName("color_complex_name")
    @Expose
    String color_complex_name;

    @SerializedName("handicap")
    @Expose
    String handicap;

    @SerializedName("handicap_name")
    @Expose
    String handicap_name;

    @SerializedName("father_name")
    @Expose
    String father_name;


    @SerializedName("father_occ_id")
    @Expose
    String father_occ_id;

    @SerializedName("father_occ_name")
    @Expose
    String father_occ_name;


    @SerializedName("father_property")
    @Expose
    String father_property;


    @SerializedName("property_loc")
    @Expose
    String property_loc;


    @SerializedName("property_type_name")
    @Expose
    String property_type_name;


    @SerializedName("father_mobile")
    @Expose
    String father_mobile;


    @SerializedName("mother_name")
    @Expose
    String mother_name;

    @SerializedName("mother_occ")
    @Expose
    String mother_occ;

    @SerializedName("mother_mobile")
    @Expose
    String mother_mobile;

    @SerializedName("family_loc")
    @Expose
    String family_loc;

    @SerializedName("family_class")
    @Expose
    String family_class;

    @SerializedName("family_type")
    @Expose
    String family_type;

    @SerializedName("mama_name")
    @Expose
    String mama_name;

    @SerializedName("mamekul")
    @Expose
    String mamekul;

    @SerializedName("mama_mobile")
    @Expose
    String mama_mobile;

    @SerializedName("mama_occ_id")
    @Expose
    String mama_occ_id;

    @SerializedName("mama_occ_name")
    @Expose
    String mama_occ_name;

    @SerializedName("sibling_count")
    @Expose
    String sibling_count;

    @SerializedName("sibling_names")
    @Expose
    String sibling_names;

    @SerializedName("married_sibling")
    @Expose
    String married_sibling;

    @SerializedName("sibling_occ_id")
    @Expose
    String sibling_occ_id;

    @SerializedName("sibling_occ_name")
    @Expose
    String sibling_occ_name;

    @SerializedName("pref_marital_id")
    @Expose
    String pref_marital_id;

    @SerializedName("pref_marital_name")
    @Expose
    String pref_marital_name;

    @SerializedName("pref_agefrom")
    @Expose
    String pref_agefrom;

    @SerializedName("pref_ageto")
    @Expose
    String pref_ageto;

    @SerializedName("pref_height_from")
    @Expose
    String pref_height_from;

    @SerializedName("pref_height_to")
    @Expose
    String pref_height_to;

    @SerializedName("pref_complex_id")
    @Expose
    String pref_complex_id;

    @SerializedName("pref_complex_name")
    @Expose
    String pref_complex_name;

    @SerializedName("pref_edu_id")
    @Expose
    String pref_edu_id;

    @SerializedName("pref_edu_name")
    @Expose
    String pref_edu_name;

    @SerializedName("pref_religion_id")
    @Expose
    String pref_religion_id;

    @SerializedName("pref_religion_name")
    @Expose
    String pref_religion_name;

    @SerializedName("pref_caste_id")
    @Expose
    String pref_caste_id;

    @SerializedName("pref_caste_name")
    @Expose
    String pref_caste_name;

    @SerializedName("pref_subcaste_id")
    @Expose
    String pref_subcaste_id;

    @SerializedName("pref_subcaste_name")
    @Expose
    String pref_subcaste_name;

    @SerializedName("pref_lifestyle_id")
    @Expose
    String pref_lifestyle_id;

    @SerializedName("pref_lifestyle_name")
    @Expose
    String pref_lifestyle_name;

    @SerializedName("pref_diet_id")
    @Expose
    String pref_diet_id;

    @SerializedName("pref_diet_name")
    @Expose
    String pref_diet_name;

    @SerializedName("pref_expect")
    @Expose
    String pref_expect;

    @SerializedName("pref_sampraday_id")
    @Expose
    String pref_sampraday_id;

    @SerializedName("pref_sampraday_name")
    @Expose
    String pref_sampraday_name;

    @SerializedName("about_me")
    @Expose
    String about_me;

    @SerializedName("profile_id")
    @Expose
    String profile_id;

    @SerializedName("profile_name")
    @Expose
    String profile_name;

    @SerializedName("bro_count")
    @Expose
    String bro_count;

    @SerializedName("sis_count")
    @Expose
    String sis_count;

    @SerializedName("pref_country_id")
    @Expose
    String pref_country_id;

    @SerializedName("pref_country_name")
    @Expose
    String pref_country_name;

    @SerializedName("pref_state_id")
    @Expose
    String pref_state_id;

    @SerializedName("pref_state_name")
    @Expose
    String pref_state_name;

    @SerializedName("pref_dist_id")
    @Expose
    String pref_dist_id;

    @SerializedName("pref_dist_name")
    @Expose
    String pref_dist_name;

    @SerializedName("pref_mothertongue")
    @Expose
    String pref_mothertongue_id;

    @SerializedName("pref_mothertongue_name")
    @Expose
    String pref_mothertongue_name;


    // change on 12-6-2025
    @SerializedName("new_pref_ofc_country_id")
    String newPrefOfcCountryId;

    @SerializedName("new_pref_ofc_country_name")
    String newPrefOfcCountryName;

    @SerializedName("new_pref_ofc_state_id")
    String newPrefOfcStateId;

    @SerializedName("new_pref_ofc_state_name")
    String newPrefOfcStateName;

    @SerializedName("new_pref_ofc_district_id")
    String newPrefOfcDistrictId;

    @SerializedName("new_pref_ofc_ditrict_name")
    String newPrefOfcDitrictName;



    //new by tanmay  16-6-2025
    @SerializedName("created_at")
    String createdAt;

    @SerializedName("msg_limit")
    String msgLimit;


    public void setMsgLimit(String msgLimit) {
        this.msgLimit = msgLimit;
    }
    public String getMsgLimit() {
        return msgLimit;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    //


    public void setNewPrefOfcCountryId(String newPrefOfcCountryId) {
        this.newPrefOfcCountryId = newPrefOfcCountryId;
    }
    public String getNewPrefOfcCountryId() {
        return newPrefOfcCountryId;
    }

    public void setNewPrefOfcCountryName(String newPrefOfcCountryName) {
        this.newPrefOfcCountryName = newPrefOfcCountryName;
    }
    public String getNewPrefOfcCountryName() {
        return newPrefOfcCountryName;
    }

    public void setNewPrefOfcStateId(String newPrefOfcStateId) {
        this.newPrefOfcStateId = newPrefOfcStateId;
    }
    public String getNewPrefOfcStateId() {
        return newPrefOfcStateId;
    }

    public void setNewPrefOfcStateName(String newPrefOfcStateName) {
        this.newPrefOfcStateName = newPrefOfcStateName;
    }
    public String getNewPrefOfcStateName() {
        return newPrefOfcStateName;
    }

    public void setNewPrefOfcDistrictId(String newPrefOfcDistrictId) {
        this.newPrefOfcDistrictId = newPrefOfcDistrictId;
    }
    public String getNewPrefOfcDistrictId() {
        return newPrefOfcDistrictId;
    }

    public void setNewPrefOfcDitrictName(String newPrefOfcDitrictName) {
        this.newPrefOfcDitrictName = newPrefOfcDitrictName;
    }
    public String getNewPrefOfcDitrictName() {
        return newPrefOfcDitrictName;
    }
    //

    public String getPref_yearsalary() {
        return pref_yearsalary;
    }

    public void setPref_yearsalary(String pref_yearsalary) {
        this.pref_yearsalary = pref_yearsalary;
    }

    public String getBro_count() {
        return bro_count;
    }

    public void setBro_count(String bro_count) {
        this.bro_count = bro_count;
    }

    public String getSis_count() {
        return sis_count;
    }

    public void setSis_count(String sis_count) {
        this.sis_count = sis_count;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlt_email() {
        return alt_email;
    }

    public void setAlt_email(String alt_email) {
        this.alt_email = alt_email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getWtsapp_no() {
        return wtsapp_no;
    }

    public void setWtsapp_no(String wtsapp_no) {
        this.wtsapp_no = wtsapp_no;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getCurr_country_id() {
        return curr_country_id;
    }

    public void setCurr_country_id(String curr_country_id) {
        this.curr_country_id = curr_country_id;
    }

    public String getCurr_country_name() {
        return curr_country_name;
    }

    public void setCurr_country_name(String curr_country_name) {
        this.curr_country_name = curr_country_name;
    }

    public String getCurr_state_id() {
        return curr_state_id;
    }

    public void setCurr_state_id(String curr_state_id) {
        this.curr_state_id = curr_state_id;
    }

    public String getCurr_state_name() {
        return curr_state_name;
    }

    public void setCurr_state_name(String curr_state_name) {
        this.curr_state_name = curr_state_name;
    }

    public String getCurr_district_id() {
        return curr_district_id;
    }

    public void setCurr_district_id(String curr_district_id) {
        this.curr_district_id = curr_district_id;
    }

    public String getCurr_ditrict_name() {
        return curr_ditrict_name;
    }

    public void setCurr_ditrict_name(String curr_ditrict_name) {
        this.curr_ditrict_name = curr_ditrict_name;
    }

    public String getCurr_taluka_id() {
        return curr_taluka_id;
    }

    public void setCurr_taluka_id(String curr_taluka_id) {
        this.curr_taluka_id = curr_taluka_id;
    }

    public String getCurr_taluka_name() {
        return curr_taluka_name;
    }

    public void setCurr_taluka_name(String curr_taluka_name) {
        this.curr_taluka_name = curr_taluka_name;
    }

    public String getCurrent_village() {
        return current_village;
    }

    public void setCurrent_village(String current_village) {
        this.current_village = current_village;
    }

    public String getPer_address() {
        return per_address;
    }

    public void setPer_address(String per_address) {
        this.per_address = per_address;
    }

    public String getPer_country_id() {
        return per_country_id;
    }

    public void setPer_country_id(String per_country_id) {
        this.per_country_id = per_country_id;
    }

    public String getPer_country_name() {
        return per_country_name;
    }

    public void setPer_country_name(String per_country_name) {
        this.per_country_name = per_country_name;
    }

    public String getPer_state_id() {
        return per_state_id;
    }

    public void setPer_state_id(String per_state_id) {
        this.per_state_id = per_state_id;
    }

    public String getPer_state_name() {
        return per_state_name;
    }

    public void setPer_state_name(String per_state_name) {
        this.per_state_name = per_state_name;
    }

    public String getPer_district_id() {
        return per_district_id;
    }

    public void setPer_district_id(String per_district_id) {
        this.per_district_id = per_district_id;
    }

    public String getPer_ditrict_name() {
        return per_ditrict_name;
    }

    public void setPer_ditrict_name(String per_ditrict_name) {
        this.per_ditrict_name = per_ditrict_name;
    }

    public String getPer_taluka_id() {
        return per_taluka_id;
    }

    public void setPer_taluka_id(String per_taluka_id) {
        this.per_taluka_id = per_taluka_id;
    }

    public String getPer_taluka_name() {
        return per_taluka_name;
    }

    public void setPer_taluka_name(String per_taluka_name) {
        this.per_taluka_name = per_taluka_name;
    }

    public String getPer_village() {
        return per_village;
    }

    public void setPer_village(String per_village) {
        this.per_village = per_village;
    }

    public String getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(String religion_id) {
        this.religion_id = religion_id;
    }

    public String getReligion_name() {
        return religion_name;
    }

    public void setReligion_name(String religion_name) {
        this.religion_name = religion_name;
    }

    public String getCaste_id() {
        return caste_id;
    }

    public void setCaste_id(String caste_id) {
        this.caste_id = caste_id;
    }

    public String getCaste_name() {
        return caste_name;
    }

    public void setCaste_name(String caste_name) {
        this.caste_name = caste_name;
    }

    public String getSubcaste_id() {
        return subcaste_id;
    }

    public void setSubcaste_id(String subcaste_id) {
        this.subcaste_id = subcaste_id;
    }

    public String getSubcaste_name() {
        return subcaste_name;
    }

    public void setSubcaste_name(String subcaste_name) {
        this.subcaste_name = subcaste_name;
    }

    public String getMothertounge_id() {
        return mothertounge_id;
    }

    public void setMothertounge_id(String mothertounge_id) {
        this.mothertounge_id = mothertounge_id;
    }

    public String getMothertounge_name() {
        return mothertounge_name;
    }

    public void setMothertounge_name(String mothertounge_name) {
        this.mothertounge_name = mothertounge_name;
    }

    public String getEducation_id() {
        return education_id;
    }

    public void setEducation_id(String education_id) {
        this.education_id = education_id;
    }

    public String getEducation_name() {
        return education_name;
    }

    public void setEducation_name(String education_name) {
        this.education_name = education_name;
    }

    public String getOther_education() {
        return other_education;
    }

    public void setOther_education(String other_education) {
        this.other_education = other_education;
    }

    public String getOccupation_id() {
        return occupation_id;
    }

    public void setOccupation_id(String occupation_id) {
        this.occupation_id = occupation_id;
    }

    public String getOccupation_name() {
        return occupation_name;
    }

    public void setOccupation_name(String occupation_name) {
        this.occupation_name = occupation_name;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getOfc_address() {
        return ofc_address;
    }

    public void setOfc_address(String ofc_address) {
        this.ofc_address = ofc_address;
    }

    public String getOfc_loc() {
        return ofc_loc;
    }

    public void setOfc_loc(String ofc_loc) {
        this.ofc_loc = ofc_loc;
    }

    public String getOfc_country_id() {
        return ofc_country_id;
    }

    public void setOfc_country_id(String ofc_country_id) {
        this.ofc_country_id = ofc_country_id;
    }

    public String getOfc_country_name() {
        return ofc_country_name;
    }

    public void setOfc_country_name(String ofc_country_name) {
        this.ofc_country_name = ofc_country_name;
    }

    public String getOfc_state_id() {
        return ofc_state_id;
    }

    public void setOfc_state_id(String ofc_state_id) {
        this.ofc_state_id = ofc_state_id;
    }

    public String getOfc_state_name() {
        return ofc_state_name;
    }

    public void setOfc_state_name(String ofc_state_name) {
        this.ofc_state_name = ofc_state_name;
    }

    public String getOfc_district_id() {
        return ofc_district_id;
    }

    public void setOfc_district_id(String ofc_district_id) {
        this.ofc_district_id = ofc_district_id;
    }

    public String getOfc_ditrict_name() {
        return ofc_ditrict_name;
    }

    public void setOfc_ditrict_name(String ofc_ditrict_name) {
        this.ofc_ditrict_name = ofc_ditrict_name;
    }

    public String getYearly_salary() {
        return yearly_salary;
    }

    public void setYearly_salary(String yearly_salary) {
        this.yearly_salary = yearly_salary;
    }

    public String getBirth_time() {
        return birth_time;
    }

    public void setBirth_time(String birth_time) {
        this.birth_time = birth_time;
    }

    public String getManglik() {
        return manglik;
    }

    public void setManglik(String manglik) {
        this.manglik = manglik;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getMarital_status_id() {
        return marital_status_id;
    }

    public void setMarital_status_id(String marital_status_id) {
        this.marital_status_id = marital_status_id;
    }

    public String getMarital_status_name() {
        return marital_status_name;
    }

    public void setMarital_status_name(String marital_status_name) {
        this.marital_status_name = marital_status_name;
    }

    public String getDietry_id() {
        return dietry_id;
    }

    public void setDietry_id(String dietry_id) {
        this.dietry_id = dietry_id;
    }

    public String getDietry_name() {
        return dietry_name;
    }

    public void setDietry_name(String dietry_name) {
        this.dietry_name = dietry_name;
    }

    public String getLifestyle_id() {
        return lifestyle_id;
    }

    public void setLifestyle_id(String lifestyle_id) {
        this.lifestyle_id = lifestyle_id;
    }

    public String getLifestyle_name() {
        return lifestyle_name;
    }

    public void setLifestyle_name(String lifestyle_name) {
        this.lifestyle_name = lifestyle_name;
    }

    public String getBloodgroup_id() {
        return bloodgroup_id;
    }

    public void setBloodgroup_id(String bloodgroup_id) {
        this.bloodgroup_id = bloodgroup_id;
    }

    public String getBloodgroup_name() {
        return bloodgroup_name;
    }

    public void setBloodgroup_name(String bloodgroup_name) {
        this.bloodgroup_name = bloodgroup_name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getColor_complex_id() {
        return color_complex_id;
    }

    public void setColor_complex_id(String color_complex_id) {
        this.color_complex_id = color_complex_id;
    }

    public String getColor_complex_name() {
        return color_complex_name;
    }

    public void setColor_complex_name(String color_complex_name) {
        this.color_complex_name = color_complex_name;
    }

    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }

    public String getHandicap_name() {
        return handicap_name;
    }

    public void setHandicap_name(String handicap_name) {
        this.handicap_name = handicap_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getFather_occ_id() {
        return father_occ_id;
    }

    public void setFather_occ_id(String father_occ_id) {
        this.father_occ_id = father_occ_id;
    }

    public String getFather_occ_name() {
        return father_occ_name;
    }

    public void setFather_occ_name(String father_occ_name) {
        this.father_occ_name = father_occ_name;
    }

    public String getFather_property() {
        return father_property;
    }

    public void setFather_property(String father_property) {
        this.father_property = father_property;
    }

    public String getProperty_loc() {
        return property_loc;
    }

    public void setProperty_loc(String property_loc) {
        this.property_loc = property_loc;
    }

    public String getProperty_type_name() {
        return property_type_name;
    }

    public void setProperty_type_name(String property_type_name) {
        this.property_type_name = property_type_name;
    }

    public String getFather_mobile() {
        return father_mobile;
    }

    public void setFather_mobile(String father_mobile) {
        this.father_mobile = father_mobile;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getMother_occ() {
        return mother_occ;
    }

    public void setMother_occ(String mother_occ) {
        this.mother_occ = mother_occ;
    }

    public String getMother_mobile() {
        return mother_mobile;
    }

    public void setMother_mobile(String mother_mobile) {
        this.mother_mobile = mother_mobile;
    }

    public String getFamily_loc() {
        return family_loc;
    }

    public void setFamily_loc(String family_loc) {
        this.family_loc = family_loc;
    }

    public String getFamily_class() {
        return family_class;
    }

    public void setFamily_class(String family_class) {
        this.family_class = family_class;
    }

    public String getFamily_type() {
        return family_type;
    }

    public void setFamily_type(String family_type) {
        this.family_type = family_type;
    }

    public String getMama_name() {
        return mama_name;
    }

    public void setMama_name(String mama_name) {
        this.mama_name = mama_name;
    }

    public String getMamekul() {
        return mamekul;
    }

    public void setMamekul(String mamekul) {
        this.mamekul = mamekul;
    }

    public String getMama_mobile() {
        return mama_mobile;
    }

    public void setMama_mobile(String mama_mobile) {
        this.mama_mobile = mama_mobile;
    }

    public String getMama_occ_id() {
        return mama_occ_id;
    }

    public void setMama_occ_id(String mama_occ_id) {
        this.mama_occ_id = mama_occ_id;
    }

    public String getMama_occ_name() {
        return mama_occ_name;
    }

    public void setMama_occ_name(String mama_occ_name) {
        this.mama_occ_name = mama_occ_name;
    }

    public String getSibling_count() {
        return sibling_count;
    }

    public void setSibling_count(String sibling_count) {
        this.sibling_count = sibling_count;
    }

    public String getSibling_names() {
        return sibling_names;
    }

    public void setSibling_names(String sibling_names) {
        this.sibling_names = sibling_names;
    }

    public String getMarried_sibling() {
        return married_sibling;
    }

    public void setMarried_sibling(String married_sibling) {
        this.married_sibling = married_sibling;
    }

    public String getSibling_occ_id() {
        return sibling_occ_id;
    }

    public void setSibling_occ_id(String sibling_occ_id) {
        this.sibling_occ_id = sibling_occ_id;
    }

    public String getSibling_occ_name() {
        return sibling_occ_name;
    }

    public void setSibling_occ_name(String sibling_occ_name) {
        this.sibling_occ_name = sibling_occ_name;
    }

    public String getPref_marital_id() {
        return pref_marital_id;
    }

    public void setPref_marital_id(String pref_marital_id) {
        this.pref_marital_id = pref_marital_id;
    }

    public String getPref_marital_name() {
        return pref_marital_name;
    }

    public void setPref_marital_name(String pref_marital_name) {
        this.pref_marital_name = pref_marital_name;
    }

    public String getPref_agefrom() {
        return pref_agefrom;
    }

    public void setPref_agefrom(String pref_agefrom) {
        this.pref_agefrom = pref_agefrom;
    }

    public String getPref_ageto() {
        return pref_ageto;
    }

    public void setPref_ageto(String pref_ageto) {
        this.pref_ageto = pref_ageto;
    }

    public String getPref_height_from() {
        return pref_height_from;
    }

    public void setPref_height_from(String pref_height_from) {
        this.pref_height_from = pref_height_from;
    }

    public String getPref_height_to() {
        return pref_height_to;
    }

    public void setPref_height_to(String pref_height_to) {
        this.pref_height_to = pref_height_to;
    }

    public String getPref_complex_id() {
        return pref_complex_id;
    }

    public void setPref_complex_id(String pref_complex_id) {
        this.pref_complex_id = pref_complex_id;
    }

    public String getPref_complex_name() {
        return pref_complex_name;
    }

    public void setPref_complex_name(String pref_complex_name) {
        this.pref_complex_name = pref_complex_name;
    }

    public String getPref_edu_id() {
        return pref_edu_id;
    }

    public void setPref_edu_id(String pref_edu_id) {
        this.pref_edu_id = pref_edu_id;
    }

    public String getPref_edu_name() {
        return pref_edu_name;
    }

    public void setPref_edu_name(String pref_edu_name) {
        this.pref_edu_name = pref_edu_name;
    }

    public String getPref_religion_id() {
        return pref_religion_id;
    }

    public void setPref_religion_id(String pref_religion_id) {
        this.pref_religion_id = pref_religion_id;
    }

    public String getPref_religion_name() {
        return pref_religion_name;
    }

    public void setPref_religion_name(String pref_religion_name) {
        this.pref_religion_name = pref_religion_name;
    }

    public String getPref_caste_id() {
        return pref_caste_id;
    }

    public void setPref_caste_id(String pref_caste_id) {
        this.pref_caste_id = pref_caste_id;
    }

    public String getPref_caste_name() {
        return pref_caste_name;
    }

    public void setPref_caste_name(String pref_caste_name) {
        this.pref_caste_name = pref_caste_name;
    }

    public String getPref_subcaste_id() {
        return pref_subcaste_id;
    }

    public void setPref_subcaste_id(String pref_subcaste_id) {
        this.pref_subcaste_id = pref_subcaste_id;
    }

    public String getPref_subcaste_name() {
        return pref_subcaste_name;
    }

    public void setPref_subcaste_name(String pref_subcaste_name) {
        this.pref_subcaste_name = pref_subcaste_name;
    }

    public String getPref_lifestyle_id() {
        return pref_lifestyle_id;
    }

    public void setPref_lifestyle_id(String pref_lifestyle_id) {
        this.pref_lifestyle_id = pref_lifestyle_id;
    }

    public String getPref_lifestyle_name() {
        return pref_lifestyle_name;
    }

    public void setPref_lifestyle_name(String pref_lifestyle_name) {
        this.pref_lifestyle_name = pref_lifestyle_name;
    }

    public String getPref_diet_id() {
        return pref_diet_id;
    }

    public void setPref_diet_id(String pref_diet_id) {
        this.pref_diet_id = pref_diet_id;
    }

    public String getPref_diet_name() {
        return pref_diet_name;
    }

    public void setPref_diet_name(String pref_diet_name) {
        this.pref_diet_name = pref_diet_name;
    }

    public String getPref_expect() {
        return pref_expect;
    }

    public void setPref_expect(String pref_expect) {
        this.pref_expect = pref_expect;
    }

    public String getPref_sampraday_id() {
        return pref_sampraday_id;
    }

    public void setPref_sampraday_id(String pref_sampraday_id) {
        this.pref_sampraday_id = pref_sampraday_id;
    }

    public String getPref_sampraday_name() {
        return pref_sampraday_name;
    }

    public void setPref_sampraday_name(String pref_sampraday_name) {
        this.pref_sampraday_name = pref_sampraday_name;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public List<SliderListImage> getImages() {
        return images;
    }

    public void setImages(List<SliderListImage> images) {
        this.images = images;
    }

    public String getVerification_img() {
        return verification_img;
    }

    public void setVerification_img(String verification_img) {
        this.verification_img = verification_img;
    }

    public String getVerification_doc() {
        return verification_doc;
    }

    public void setVerification_doc(String verification_doc) {
        this.verification_doc = verification_doc;
    }

    public String getEducation_certificate() {
        return education_certificate;
    }

    public void setEducation_certificate(String education_certificate) {
        this.education_certificate = education_certificate;
    }

    public String getOccupation_certificate() {
        return occupation_certificate;
    }

    public void setOccupation_certificate(String occupation_certificate) {
        this.occupation_certificate = occupation_certificate;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String acc_type) {
        this.acc_type = acc_type;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getAcc_verify() {
        return acc_verify;
    }

    public void setAcc_verify(String acc_verify) {
        this.acc_verify = acc_verify;
    }


    public String getChkonline() {
        return chkonline;
    }

    public void setChkonline(String chkonline) {
        this.chkonline = chkonline;
    }

    public String getJust_joined() {
        return just_joined;
    }

    public void setJust_joined(String just_joined) {
        this.just_joined = just_joined;
    }

    public String getAccount_verify() {
        return account_verify;
    }

    public void setAccount_verify(String account_verify) {
        this.account_verify = account_verify;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }


    public String getNo_of_child() {
        return no_of_child;
    }

    public void setNo_of_child(String no_of_child) {
        this.no_of_child = no_of_child;
    }

    public String getChild_stay() {
        return child_stay;
    }

    public void setChild_stay(String child_stay) {
        this.child_stay = child_stay;
    }

    public String getAge_of_child() {
        return age_of_child;
    }

    public void setAge_of_child(String age_of_child) {
        this.age_of_child = age_of_child;
    }

    public String getIs_shortlist() {
        return is_shortlist;
    }

    public void setIs_shortlist(String is_shortlist) {
        this.is_shortlist = is_shortlist;
    }


    public String getIs_connected() {
        return is_connected;
    }

    public void setIs_connected(String is_connected) {
        this.is_connected = is_connected;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }

    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getAlready_seen() {
        return already_seen;
    }

    public void setAlready_seen(String already_seen) {
        this.already_seen = already_seen;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }


    public String getSampraday_name() {
        return sampraday_name;
    }

    public void setSampraday_name(String sampraday_name) {
        this.sampraday_name = sampraday_name;
    }

    public String getRashi_name() {
        return rashi_name;
    }

    public void setRashi_name(String rashi_name) {
        this.rashi_name = rashi_name;
    }


    public String getMain_education_id() {
        return main_education_id;
    }

    public void setMain_education_id(String main_education_id) {
        this.main_education_id = main_education_id;
    }

    public String getMain_education_name() {
        return main_education_name;
    }

    public void setMain_education_name(String main_education_name) {
        this.main_education_name = main_education_name;
    }

    public String getPrefmain_edu_name() {
        return prefmain_edu_name;
    }

    public void setPrefmain_edu_name(String prefmain_edu_name) {
        this.prefmain_edu_name = prefmain_edu_name;
    }

    public String getMain_occupation_name() {
        return main_occupation_name;
    }

    public void setMain_occupation_name(String main_occupation_name) {
        this.main_occupation_name = main_occupation_name;
    }

    public String getPrefmain_occ_name() {
        return prefmain_occ_name;
    }

    public void setPrefmain_occ_name(String prefmain_occ_name) {
        this.prefmain_occ_name = prefmain_occ_name;
    }

    public String getPref_occ_name() {
        return pref_occ_name;
    }

    public void setPref_occ_name(String pref_occ_name) {
        this.pref_occ_name = pref_occ_name;
    }

    public String getPref_country_id() {
        return pref_country_id;
    }

    public void setPref_country_id(String pref_country_id) {
        this.pref_country_id = pref_country_id;
    }

    public String getPref_country_name() {
        return pref_country_name;
    }

    public void setPref_country_name(String pref_country_name) {
        this.pref_country_name = pref_country_name;
    }

    public String getPref_state_id() {
        return pref_state_id;
    }

    public void setPref_state_id(String pref_state_id) {
        this.pref_state_id = pref_state_id;
    }

    public String getPref_state_name() {
        return pref_state_name;
    }

    public void setPref_state_name(String pref_state_name) {
        this.pref_state_name = pref_state_name;
    }

    public String getPref_dist_id() {
        return pref_dist_id;
    }

    public void setPref_dist_id(String pref_dist_id) {
        this.pref_dist_id = pref_dist_id;
    }

    public String getPref_dist_name() {
        return pref_dist_name;
    }

    public void setPref_dist_name(String pref_dist_name) {
        this.pref_dist_name = pref_dist_name;
    }

    public String getPref_mothertongue_id() {
        return pref_mothertongue_id;
    }

    public void setPref_mothertongue_id(String pref_mothertongue_id) {
        this.pref_mothertongue_id = pref_mothertongue_id;
    }

    public String getPref_mothertongue_name() {
        return pref_mothertongue_name;
    }

    public void setPref_mothertongue_name(String pref_mothertongue_name) {
        this.pref_mothertongue_name = pref_mothertongue_name;
    }
}
