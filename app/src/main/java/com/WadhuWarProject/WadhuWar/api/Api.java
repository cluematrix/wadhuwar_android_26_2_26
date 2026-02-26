package com.WadhuWarProject.WadhuWar.api;

import com.WadhuWarProject.WadhuWar.model.AdvertiseDetail;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.WadhuWarProject.WadhuWar.model.AdvisorPlan;
import com.WadhuWarProject.WadhuWar.model.AgeList;
import com.WadhuWarProject.WadhuWar.model.BlogDetail;
import com.WadhuWarProject.WadhuWar.model.BlogList;
import com.WadhuWarProject.WadhuWar.model.BloodGroupList;
import com.WadhuWarProject.WadhuWar.model.CasteList;
import com.WadhuWarProject.WadhuWar.model.Chat;
import com.WadhuWarProject.WadhuWar.model.ChatList;
import com.WadhuWarProject.WadhuWar.model.ChatStatusResponse;
import com.WadhuWarProject.WadhuWar.model.Checksum;
import com.WadhuWarProject.WadhuWar.model.ColorcomplexList;
import com.WadhuWarProject.WadhuWar.model.CommentList;
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.CreateOrderId;
import com.WadhuWarProject.WadhuWar.model.DeleteProfile;
import com.WadhuWarProject.WadhuWar.model.DietaryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.DocumentTypeList;
import com.WadhuWarProject.WadhuWar.model.EbookList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.EducationType;
import com.WadhuWarProject.WadhuWar.model.FetchAboutMe;
import com.WadhuWarProject.WadhuWar.model.FetchAccommodation;
import com.WadhuWarProject.WadhuWar.model.FetchAstroLifestyle;
import com.WadhuWarProject.WadhuWar.model.FetchBasicInfo;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.FetchReligion;
import com.WadhuWarProject.WadhuWar.model.GetInterested;
import com.WadhuWarProject.WadhuWar.model.GetOnlineDate;
import com.WadhuWarProject.WadhuWar.model.GetPaymentDetails;
import com.WadhuWarProject.WadhuWar.model.HeightList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LifeSettingList;
import com.WadhuWarProject.WadhuWar.model.LikeProfileResponse;
import com.WadhuWarProject.WadhuWar.model.Likes;
import com.WadhuWarProject.WadhuWar.model.LoginData;
import com.WadhuWarProject.WadhuWar.model.MaritalSettingList;
import com.WadhuWarProject.WadhuWar.model.MatchesTabList;
import com.WadhuWarProject.WadhuWar.model.MelawaList;
import com.WadhuWarProject.WadhuWar.model.MemberList;
import com.WadhuWarProject.WadhuWar.model.MembershipPlan;
import com.WadhuWarProject.WadhuWar.model.MessageLimit;
import com.WadhuWarProject.WadhuWar.model.MotherOccupationList;
import com.WadhuWarProject.WadhuWar.model.MothertoungeList;
import com.WadhuWarProject.WadhuWar.model.NewMatchesList;
import com.WadhuWarProject.WadhuWar.model.NewmatchesTabList;
import com.WadhuWarProject.WadhuWar.model.NewsDetail;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationType;
import com.WadhuWarProject.WadhuWar.model.PaymentResponse;
import com.WadhuWarProject.WadhuWar.model.PersonalDataInsert1;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.ProfileCreatedList;
import com.WadhuWarProject.WadhuWar.model.ProfilePercent;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.WadhuWarProject.WadhuWar.model.RecentVisitorMatchesList;
import com.WadhuWarProject.WadhuWar.model.RegisterResponse;
import com.WadhuWarProject.WadhuWar.model.ReligionList;
import com.WadhuWarProject.WadhuWar.model.RemoveHistory;
import com.WadhuWarProject.WadhuWar.model.SalaryList;
import com.WadhuWarProject.WadhuWar.model.SampradayList;
import com.WadhuWarProject.WadhuWar.model.SaveHistory;
import com.WadhuWarProject.WadhuWar.model.SearchData;
import com.WadhuWarProject.WadhuWar.model.SearchDataMore;
import com.WadhuWarProject.WadhuWar.model.SendCommentData;
import com.WadhuWarProject.WadhuWar.model.SendMatchingProfiles;
import com.WadhuWarProject.WadhuWar.model.SendNotificationToOtherUser;
import com.WadhuWarProject.WadhuWar.model.SendProfileViewNoti;
import com.WadhuWarProject.WadhuWar.model.ShowTabList;
import com.WadhuWarProject.WadhuWar.model.SliderList;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.TabNearMeMatchesList;
import com.WadhuWarProject.WadhuWar.model.TabRecentlyViewedList;
import com.WadhuWarProject.WadhuWar.model.TabShortList;
import com.WadhuWarProject.WadhuWar.model.TabStateNearMeMatchesList;
import com.WadhuWarProject.WadhuWar.model.TalukaList;
import com.WadhuWarProject.WadhuWar.model.UpdateResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.WadhuWarProject.WadhuWar.model.WeightList;
import com.WadhuWarProject.WadhuWar.model.newEmailData;
import com.WadhuWarProject.WadhuWar.model.newLoginData;
import com.WadhuWarProject.WadhuWar.model.newMobileData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface Api {

    @POST("upgrade-plan-offline/{id}")
    Call<PaymentResponse> upgradePlanOffline(
            @Path("id") Integer id
    );

    @FormUrlEncoded
    @POST("SliderList")
    Call<SliderList> sliderList(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("blog")
    Call<BlogList> blogList(
            @Field("req_id") String req_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("news")
    Call<NewsList> newsList(
            @Field("req_id") String req_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("Advertisement")
    Call<AdvertiseList> advertiseList(
            @Field("req_id") String req_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("blogDetails")
    Call<BlogDetail> blogDetails(
            @Field("req_id") String req_id,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("newsDetails")
    Call<NewsDetail> newsDetails(
            @Field("req_id") String req_id,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("AdvertDetails")
    Call<AdvertiseDetail> advertDetails(
            @Field("req_id") String req_id,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("CountryList")
    Call<CountryList> countryList(
            @Field("req_id") String req_id
    );


    @FormUrlEncoded
    @POST("ReligionList")
    Call<ReligionList> religionList(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("CasteList")
    Call<CasteList> casteList(
            @Field("req_id") String req_id,
            @Field("religion_id") String religion_id
    );

    @FormUrlEncoded
    @POST("DistrictList")
    Call<DistrictList> districtList(
            @Field("req_id") String req_id,
            @Field("state_id") String state_id
    );

    @FormUrlEncoded
    @POST("TalukaList")
    Call<TalukaList> talukaList(
            @Field("req_id") String req_id,
            @Field("district_id") String state_id
    );


    @FormUrlEncoded
    @POST("MotherTounge")
    Call<MothertoungeList> motherTounge(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("Occupation")
    Call<OccupationList> occupation(
            @Field("req_id") String req_id);


    @FormUrlEncoded
    @POST("Education")
    Call<EducationList> education(
            @Field("req_id") String req_id,
            @Field("main_education_id") String main_education_id
    );

    @FormUrlEncoded
    @POST("Marital_Setting")
    Call<MaritalSettingList> maritalSetting(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("Dietary")
    Call<DietaryList> dietary(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("LifeSetting")
    Call<LifeSettingList> lifeSetting(
            @Field("req_id") String req_id
    );


    @GET("HeightList")
    Call<HeightList> heightList();

    @GET("PrefHeightListFrom")
    Call<HeightList> prefheightListFrom();

    @GET("PrefHeightListTo")
    Call<HeightList> prefheightListTo();


    @GET("WeightList")
    Call<WeightList> weightList();

    @GET("SalaryAPI")
    Call<SalaryList> salaryList();

    @FormUrlEncoded
    @POST("Colorcomplex")
    Call<ColorcomplexList> colorcomplexList(
            @Field("req_id") String req_id
    );


    @FormUrlEncoded
    @POST("BloodGroup")
    Call<BloodGroupList> bloodGroupList(
            @Field("req_id") String req_id
    );

    @GET("MotherOccupation")
    Call<MotherOccupationList> motherOccupationList();

    @GET("AgeList")
    Call<AgeList> ageList();


    @GET("SampradayList")
    Call<SampradayList> sampradayList();


    @GET("ProfileCreated")
    Call<ProfileCreatedList> profileCreatedList();

    @GET("DocumentType")
    Call<DocumentTypeList> documentTypeList();


    @FormUrlEncoded
    @POST("InsertStep1")
    Call<PersonalDataInsert1> insertStep1(
            @Field("fname") String fname,
            @Field("mname") String mname,
            @Field("lname") String lname,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("email") String email,
            @Field("alt_email") String alt_email,
            @Field("mobile_no") String mobile_no,
            @Field("wtsapp_no") String wtsapp_no,
            @Field("password") String password,
            @Field("age") String age,
            @Field("referral_code") String ref_code
    );

    @FormUrlEncoded
    @POST("hide_profile_con")
    Call<ResponseBody> hideProfile(
            @Field("user_id") String userId,
            @Field("hide") int hide

    );


    @FormUrlEncoded
    @POST("InsertStep2")
    Call<InsertResponse> insertStep2(
            @Field("current_address") String current_address,
            @Field("current_country") String current_country,
            @Field("current_state") String current_state,
            @Field("current_district") String current_district,
            @Field("current_taluka1") String current_taluka,
            @Field("current_village") String current_village,
            @Field("permnt_address") String permnt_address,
            @Field("permnt_country") String permnt_country,
            @Field("permnt_state") String permnt_state,
            @Field("permnt_district") String permnt_district,
            @Field("permnt_taluka1") String permnt_taluka,
            @Field("permnt_village") String permnt_village,
            @Field("religion_id") String religion_id,
            @Field("caste_id") String caste_id,
            @Field("subcaste_id") String subcaste_id,
            @Field("mothertounge_id") String mothertounge_id,
            @Field("user_id") String user_id,
            @Field("sampraday_id") String sampraday_id,
            @Field("rashi") String rashi

    );

    @FormUrlEncoded
    @POST("InsertStep3")
    Call<InsertResponse> insertStep3(
            @Field("main_education_id") String main_education_id,
            @Field("education") String education,
            @Field("other_education") String other_education,
            @Field("college_name") String college_name,
            @Field("occupation") String occupation,
            @Field("post_name") String post_name,
            @Field("ofc_address") String ofc_address,
            @Field("ofc_loc") String ofc_loc,
            @Field("ofc_country") String ofc_country,
            @Field("ofc_state") String ofc_state,
            @Field("ofc_district") String ofc_district,
            @Field("yearly_salary") String yearly_salary,
            @Field("birth_time") String birth_time,
            @Field("manglik") String manglik,
            @Field("hobbies") String hobbies,
            @Field("marital_status") String marital_status,
            @Field("no_of_child") String no_of_child,
            @Field("age_of_child") String age_of_child,
            @Field("child_stay") String child_stay,
            @Field("dietry") String dietry,
            @Field("lifestyle") String lifestyle,
            @Field("height") String height,
            @Field("weight") String weight,
            @Field("gotra") String gotra,
            @Field("color_complex") String color_complex,
            @Field("handicap") String handicap,
            @Field("handicap_name") String handicap_name,
            @Field("bloodgroup") String bloodgroup,
            @Field("user_id") String user_id,
            @Field("main_occupation_id") String main_occupation_id

    );


    @FormUrlEncoded
    @POST("InsertStep4")
    Call<InsertResponse> insertStep4(
            @Field("father_name") String father_name,
            @Field("father_occ") String father_occ,
            @Field("father_property") String father_property,
            @Field("property_loc") String property_loc,
            @Field("property_type_name") String property_type_name,
            @Field("father_mobile") String father_mobile,
            @Field("mother_name") String mother_name,
            @Field("mother_occ") String mother_occ,
            @Field("mother_mobile") String mother_mobile,
            @Field("family_loc") String family_loc,
            @Field("family_class") String family_class,
            @Field("family_type") String family_type,
            @Field("mama_name") String mama_name,
            @Field("mamekul") String mamekul,
            @Field("mama_mobile") String mama_mobile,
            @Field("mama_occ") String mama_occ,
            @Field("sibling_count") String sibling_count,
            @Field("sibling_names") String sibling_names,
            @Field("married_sibling") String married_sibling,
            @Field("sibling_occ") String sibling_occ,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("InsertStep5")
    Call<InsertResponse> insertStep5(
            @Field("pref_marital") String pref_marital,
            @Field("pref_complex") String pref_complex,
            @Field("pref_height_from") String pref_height_from,
            @Field("pref_height_to") String pref_height_to,
            @Field("pref_religion") String pref_religion,
            @Field("pref_caste") String pref_caste,
            @Field("pref_subcaste") String pref_subcaste,
            @Field("pref_agefrom") String pref_agefrom,
            @Field("pref_ageto") String pref_ageto,
            @Field("pref_edu") String pref_edu,
            @Field("pref_lifestyle") String pref_lifestyle,
            @Field("pref_diet") String pref_diet,
            @Field("pref_expect") String pref_expect,
            @Field("pref_sampraday") String pref_sampraday,
            @Field("user_id") String user_id,
            @Field("prefmain_edu") String prefmain_edu,
            @Field("prefmain_occ") String prefmain_occ,
            @Field("pref_occ") String pref_occ,
            @Field("pref_yearsalary") String pref_yearsalary,
            @Field("pref_country") String pref_country,
            @Field("pref_state") String pref_state,
            @Field("pref_dist") String pref_dist,
            @Field("pref_mothertongue") String pref_mothertongue

    );

    @Multipart
    @POST("InsertStep6")
    Call<InsertResponse> insertStep6(
            @Part("aboutme") RequestBody aboutme,
            @Part("profiles") RequestBody profiles,
            @Part("verification_doc") RequestBody documet_type_str,
            @Part MultipartBody.Part education_certificate_file,
            @Part MultipartBody.Part occupation_certificate_file,
            @Part MultipartBody.Part document_img,
            @Part MultipartBody.Part[] img,
            @Part("user_id") RequestBody user_id
    );

  /*  @Multipart
    @POST("InsertStep6")
    Call<InsertResponse> insertStep6(
            @Part("verification_doc") RequestBody documet_type_str,
            @Part MultipartBody.Part education_certificate_file,
            @Part MultipartBody.Part occupation_certificate_file,
            @Part MultipartBody.Part document_img,
            @Part("user_id") RequestBody user_id
    );*/


    @FormUrlEncoded
    @POST("ProfileSlider")
    Call<ProfileSlider> profileSlider(
            @Field("req_id") String req_id,
            @Field("login_user_id") String login_user_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no

    );


    @FormUrlEncoded
    @POST("Login")
    Call<LoginData> login(
            @Field("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("VerifyOTP")
    Call<UserData> verifyOTP(
            @Field("user_id") String user_id,
            @Field("deviceid") String deviceid
    );

    @FormUrlEncoded
    @POST("FetchProfile")
    Call<FetchProfile> fetchProfile(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("FetchBasicInfo")
    Call<FetchBasicInfo> fetchBasicInfo(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("EditBasicInfo")
    Call<InsertResponse> editBasicInfo(
            @Field("fname") String fname,
            @Field("mname") String mname,
            @Field("lname") String lname,
            @Field("user_id") String user_id,
            @Field("profilefor") String profilefor,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("dob") String dob,
            @Field("email") String email,
            @Field("alt_email") String alt_email,
            @Field("mobile_no") String mobile_no,
            @Field("wtsapp_no") String wtsapp_no,
            @Field("handicap") String handicap,
            @Field("handicap_name") String handicap_name,
            @Field("weight") String weight,
            @Field("height") String height
    );


    @FormUrlEncoded
    @POST("FetchAboutReligion")
    Call<FetchAboutMe> fetchAboutMe(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor
    );

    @FormUrlEncoded
    @POST("EditAboutReligion")
    Call<InsertResponse> editAboutMe(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor,
            @Field("about_me") String about_me
    );

    @FormUrlEncoded
    @POST("FetchAboutReligion")
    Call<FetchReligion> fetchReligion(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor
    );


    @FormUrlEncoded
    @POST("EditAboutReligion")
    Call<InsertResponse> editReligion(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor,
            @Field("religion_id") String religion_id,
            @Field("caste_id") String caste_id,
            @Field("subcaste_id") String subcaste_id,
            @Field("mothertounge_id") String mothertounge_id,
            @Field("gotra") String gotra,
            @Field("sampraday_id") String sampraday_id,
            @Field("rashi") String rashi
    );

    @FormUrlEncoded
    @POST("FetchAccommodation")
    Call<FetchAccommodation> fetchAccommodation(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("EditAccommodation")
    Call<InsertResponse> editAccommodation(
            @Field("user_id") String user_id,
            @Field("current_address") String current_address,
            @Field("current_country") String current_country,
            @Field("current_state") String current_state,
            @Field("current_district") String current_district,
            @Field("current_taluka1") String current_taluka1,
            @Field("current_village") String current_village,
            @Field("permnt_address") String permnt_address,
            @Field("permnt_country") String permnt_country,
            @Field("permnt_state") String permnt_state,
            @Field("permnt_district") String permnt_district,
            @Field("permnt_taluka1") String permnt_taluka1,
            @Field("permnt_village") String permnt_village
    );


    @FormUrlEncoded
    @POST("FetchFamilyDetails")
    Call<FetchProfile> fetchFamilyDetails(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("EditFamilyDetails")
    Call<InsertResponse> editFamilyDetails(
            @Field("user_id") String user_id,
            @Field("father_name") String father_name,
            @Field("father_occ") String father_occ,
            @Field("father_property") String father_property,
            @Field("property_loc") String property_loc,
            @Field("property_type_name") String property_type_name,
            @Field("father_mobile") String father_mobile,
            @Field("mother_name") String mother_name,
            @Field("mother_occ") String mother_occ,
            @Field("mother_mobile") String mother_mobile,
            @Field("family_loc") String family_loc,
            @Field("family_class") String family_class,
            @Field("family_type") String family_type,
            @Field("mama_name") String mama_name,
            @Field("mamekul") String mamekul,
            @Field("mama_mobile") String mama_mobile,
            @Field("mama_occ") String mama_occ,
            @Field("bro_count") String bro_count,
            @Field("sis_count") String sis_count,
            @Field("sibling_names") String sibling_names,
            @Field("married_sibling") String married_sibling,
            @Field("sibling_occ") String sibling_occ
    );


    @FormUrlEncoded
    @POST("FetchAstroLifestyle")
    Call<FetchAstroLifestyle> fetchAstroLifestyle(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor
    );

    @FormUrlEncoded
    @POST("EditAstroLifestyle")
    Call<InsertResponse> editAstroLifestyle(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor,
            @Field("manglik") String manglik
    );

    @FormUrlEncoded
    @POST("FetchEducationDetails")
    Call<FetchProfile> fetchEducationDetails(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("EditEducationDetails")
    Call<InsertResponse> editEducationDetails(
            @Field("user_id") String user_id,
            @Field("main_education_id") String main_education_id,
            @Field("education") String education,
            @Field("other_education") String other_education,
            @Field("main_occupation_id") String main_occupation_id,
            @Field("occupation") String occupation,
            @Field("college_name") String college_name,
            @Field("post_name") String post_name,
            @Field("ofc_address") String ofc_address,
            @Field("ofc_loc") String ofc_loc,
            @Field("ofc_country") String ofc_country,
            @Field("ofc_state") String ofc_state,
            @Field("ofc_district") String ofc_district,
            @Field("yearly_salary") String yearly_salary
    );

    @FormUrlEncoded
    @POST("FetchAstroLifestyle")
    Call<FetchProfile> fetchLifestyle(
            @Field("user_id") String user_id,
            @Field("apifor") String apifor
    );


    @FormUrlEncoded
    @POST("EditAstroLifestyle")
    Call<InsertResponse> editLifestyle(
            @Field("user_id") String user_id,
            @Field("apifor") String education,
            @Field("birth_time") String other_education,
            @Field("hobbies") String occupation,
            @Field("marital_status") String post_name,
            @Field("no_of_child") String no_of_child,
            @Field("age_of_child") String age_of_child,
            @Field("child_stay") String child_stay,
            @Field("lifestyle") String ofc_address,
            @Field("color_complex") String ofc_loc,
            @Field("bloodgroup") String ofc_country,
            @Field("dietry") String dietry
    );


    @FormUrlEncoded
    @POST("FetchPartnerPrefrence")
    Call<FetchProfile> fetchPartnerPrefrence(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("EditPartnerPrefrence1")
    Call<InsertResponse> editPartnerPrefrence(
            @Field("user_id") String user_id,
            @Field("pref_marital") String pref_marital,
            @Field("pref_complex") String pref_complex,
            @Field("pref_height_from") String pref_height_from,
            @Field("pref_height_to") String pref_height_to,
            @Field("pref_religion") String pref_religion,
            @Field("pref_caste") String pref_caste,
            @Field("pref_subcaste") String pref_subcaste,
            @Field("pref_agefrom") String pref_agefrom,
            @Field("pref_ageto") String pref_ageto,
            @Field("pref_edu") String pref_edu,
            @Field("pref_lifestyle") String pref_lifestyle,
            @Field("pref_diet") String pref_diet,
            @Field("pref_expect") String pref_expect,
            @Field("pref_sampraday") String pref_sampraday,
            @Field("prefmain_edu") String prefmain_edu,
            @Field("pref_occ") String pref_occ,
            @Field("prefmain_occ") String prefmain_occ,
            @Field("pref_yearsalary") String pref_yearsalary,
            @Field("pref_country") String pref_country,
            @Field("pref_state") String pref_state,
            @Field("pref_dist") String pref_dist,
            @Field("pref_mothertongue") String pref_mothertongue,
            @Field("pref_ofc_country") String pref_ofc_country,
            @Field("pref_ofc_state") String pref_ofc_state,
            @Field("pref_ofc_district") String pref_ofc_dist
    );


    @FormUrlEncoded
    @POST("PremiumMatches")
    Call<PremiunMatchesList> premiumMatches(
            @Field("userid") String user_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("NewMatches")
    Call<NewMatchesList> newMatches(
            @Field("userid") String user_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("RecentVisitors")
    Call<RecentVisitorMatchesList> recentVisitors(
            @Field("userid") String user_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("PeopleDetails")
    Call<FetchProfile> peopleDetails(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("MembershipPlan")
    Call<MembershipPlan> membershipPlan(
            @Field("req_id") String req_id,
            @Field("login_userid") String login_userid
    );


    @FormUrlEncoded
    @POST("MatchesTabList")
    Call<MatchesTabList> matchesTabList(
            @Field("login_userid") String login_userid
    );


    @FormUrlEncoded
    @POST("MatchesTabNew")
    Call<NewmatchesTabList> newmatchesTabList(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("MatchesTabAll")
    Call<NewmatchesTabList> MatchesTabAllList(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("MatchesTabMyMatches")
    Call<TabMyMatchesList> matchesTabMyMatches(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("MatchesTabShort")
    Call<TabShortList> tabShortList(
            @Field("tabid") String tabid,
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("MatchesTabRecent")
    Call<TabRecentlyViewedList> matchesTabRecent(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("SearchProfile")
    Call<SearchData> searchProfile(
            @Field("gender") String gender,
            @Field("userid") String userid,
            @Field("agefrom") String agefrom,
            @Field("ageto") String ageto,
            @Field("religion") String religion,
            @Field("caste") String caste,
            @Field("subcaste") String subcaste,
            @Field("educations") String educations,
            @Field("occupation") String occupation,
            @Field("state") String state,
            @Field("district") String district,
            @Field("mstatus") String mstatus,
            @Field("searchbyname") String searchbyname,
            @Field("diet") String diet,
            @Field("sampraday") String sampraday,
            @Field("manglik") String manglik,
            @Field("handicap") String handicap,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no,
            @Field("main_education") String main_education,
            @Field("main_occupation") String main_occupation,
            @Field("heightfrom") String heightfrom,
            @Field("heightto") String heightto,
            @Field("yearsalary") String yearsalary,
            @Field("pref_mothertongue") String pref_mothertongue

    );


    @FormUrlEncoded
    @POST("DeleteImage")
    Call<InsertResponse> deleteImage(
            @Field("userid") String userid,
            @Field("pos") String pos
    );


    @Multipart
    @POST("EditImage")
    Call<InsertResponse> editImage(
            @Part("userid") RequestBody userid,
            @Part MultipartBody.Part[] img
    );


    @FormUrlEncoded
    @POST("ShortListProfile")
    Call<InsertResponse> shortListProfile(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("RemoveShortListProfile")
    Call<InsertResponse> removeShortListProfile(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("ConnectNow")
    Call<InsertResponse> connectNow(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("ShowConnectTabList")
    Call<ShowTabList> showConnectTabList(
            @Field("login_userid") String login_user_id
    );


    @FormUrlEncoded
    @POST("RecivedConnection")
    Call<MemberList> recivedConnection(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("RequestRejected")
    Call<InsertResponse> requestRejected(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("RequestAccepted")
    Call<InsertResponse> requestAccepted(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("AcceptConnection")
    Call<MemberList> acceptConnection(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("SendConnection")
    Call<MemberList> sendConnection(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("ShowComments")
    Call<CommentList> commentList(
            @Field("blog_id") String blog_id
    );


    @FormUrlEncoded
    @POST("SendComment")
    Call<SendCommentData> sendComment(
            @Field("blog_id") String blog_id,
            @Field("login_user_id") String login_user_id,
            @Field("name") String name,
            @Field("comment") String comment
    );

    @FormUrlEncoded
    @POST("DeleteComment")
    Call<InsertResponse> deleteComment(
            @Field("cmt_id") String cmt_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("EditComment")
    Call<InsertResponse> editComment(
            @Field("cmt_id") String blog_id,
            @Field("login_user_id") String login_user_id,
            @Field("comment") String comment
    );


    @GET("CreateChecksum")
    Call<Checksum> getChecksum();


    @FormUrlEncoded
    @POST("MelawaList")
    Call<MelawaList> getMelawaList(
            @Field("login_userid") String login_user_id
    );


    @FormUrlEncoded
    @POST("PaymentSuccessMember")
    Call<InsertResponse> paymentSuccessMember(
            @Field("login_userid") String login_userid,
            @Field("plan_id") String plan_id,
            @Field("order_id") String order_id,
            @Field("paid_amount") String paid_amount,
            @Field("payment_mode") String payment_mode,
            @Field("response_code") String response_code,
            @Field("response_message") String response_message,
            @Field("transaction_status") String transaction_status,
            @Field("transaction_id") String transaction_id,
            @Field("gateway_name") String gateway_name,
            @Field("bank_txt_id") String bank_txt_id,
            @Field("bankname") String bankname
    );


    @FormUrlEncoded
    @POST("PaymentSuccessMelawa")
    Call<InsertResponse> paymentSuccessMelawa(
            @Field("login_userid") String login_userid,
            @Field("melawa_id") String plan_id,
            @Field("order_id") String order_id,
            @Field("paid_amount") String paid_amount,
            @Field("payment_mode") String payment_mode,
            @Field("response_code") String response_code,
            @Field("response_message") String response_message,
            @Field("transaction_status") String transaction_status,
            @Field("transaction_id") String transaction_id,
            @Field("gateway_name") String gateway_name,
            @Field("bank_txt_id") String bank_txt_id,
            @Field("bankname") String bankname
    );

    @FormUrlEncoded
    @POST("PaymentSuccessEbook")
    Call<InsertResponse> paymentSuccessEbook(
            @Field("login_userid") String login_userid,
            @Field("ebook_id") String plan_id,
            @Field("order_id") String order_id,
            @Field("paid_amount") String paid_amount,
            @Field("payment_mode") String payment_mode,
            @Field("response_code") String response_code,
            @Field("response_message") String response_message,
            @Field("transaction_status") String transaction_status,
            @Field("transaction_id") String transaction_id,
            @Field("gateway_name") String gateway_name,
            @Field("bank_txt_id") String bank_txt_id,
            @Field("bankname") String bankname
    );


    @FormUrlEncoded
    @POST("viewcount")
    Call<InsertResponse> getViewcount(
            @Field("login_userid") String login_userid,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("likeunlike")
    Call<LikeProfileResponse> getlLikeunlike(
            @Field("login_userid") String login_userid,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("likemembes")
    Call<Likes> getLikemembes(
            @Field("user_id") String user_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("OnlineOffline")
    Call<InsertResponse> checkOnlineOffline(
            @Field("login_userid") String login_userid,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("getChatting")
    Call<Chat> getChatting(
            @Field("sender_id") String sender_id,
            @Field("reciver_id") String receiver_id,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );


    @FormUrlEncoded
    @POST("SaveChat")
    Call<InsertResponse> saveChatting(
            @Field("sender_id") String sender_id,
            @Field("reciver_id") String receiver_id,
            @Field("message") String message
    );


    @FormUrlEncoded
    @POST("GetAdvisoryPlan")
    Call<AdvisorPlan> getAdvisoryPlan(
            @Field("login_userid") String login_userid
    );


    @FormUrlEncoded
    @POST("PaymentSuccessActivity")
    Call<InsertResponse> paymentSuccessActivity(
            @Field("login_userid") String login_userid,
            @Field("plan_id") String plan_id,
            @Field("order_id") String order_id,
            @Field("paid_amount") String paid_amount,
            @Field("payment_mode") String payment_mode,
            @Field("response_code") String response_code,
            @Field("response_message") String response_message,
            @Field("transaction_status") String transaction_status,
            @Field("transaction_id") String transaction_id,
            @Field("gateway_name") String gateway_name,
            @Field("bank_txt_id") String bank_txt_id,
            @Field("bankname") String bankname
    );

    @FormUrlEncoded
    @POST("GetChatList")
    Call<ChatList> getChatList(
            @Field("login_userid") String login_userid,
            @Field("page_no") String page_no,
            @Field("total_cnt") String total_cnt
    );


    @FormUrlEncoded
    @POST("GetnewchatidStatus")
    Call<InsertResponse> getnewchatidStatus(
            @Field("reciver_id") String reciver_id,
            @Field("sender_id") String sender_id,
            @Field("last_id") String last_id
    );


    @FormUrlEncoded
    @POST("EbookList")
    Call<EbookList> getEbookList(
            @Field("login_userid") String login_userid
    );

    @FormUrlEncoded
    @POST("Deleteprofile")
    Call<DeleteProfile> deleteProfile(
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("StatusOnline")
    Call<InsertResponse> setStatusOnline(
            @Field("login_user_id") String login_user_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("getOnline")
    Call<ChatStatusResponse> getOnline(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("profilecompelte")
    Call<ProfilePercent> profilecompelte(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("MatchesTabNear")
    Call<TabNearMeMatchesList> matchesTabNear(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("MatchesTabNearState")
    Call<TabStateNearMeMatchesList> matchesTabStateNear(
            @Field("tabid") String tabid,
            @Field("userid") String userid,
            @Field("total_cnt") String total_cnt,
            @Field("page_no") String page_no
    );



    @Multipart
    @POST("delete_profile_con")
    Call<InsertResponse> deleteProfile(
            @Part("user_id") RequestBody userid,
            @Part("delete_reason") RequestBody delete_reason,
            @Part MultipartBody.Part file
    );


    @FormUrlEncoded
    @POST("notification_count")
    Call<InsertResponse> notificationCount(
            @Field("login_userid") String user_id
    );

    @FormUrlEncoded
    @POST("update_chat_request")
    Call<InsertResponse> updateChatRequest(
            @Field("reciver_id") String reciver_id,
            @Field("login_user_id") int login_user_id);


    @FormUrlEncoded
    @POST("checkversion")
    Call<UpdateResponse> checkUpdate(
            @Field("code") String reciver_id);

    @FormUrlEncoded
    @POST("Education_type")
    Call<EducationType> educationType(
            @Field("req_id") String req_id);


    @FormUrlEncoded
    @POST("Occupation_type")
    Call<OccupationType> occupationType(
            @Field("req_id") String req_id
    );

    @FormUrlEncoded
    @POST("Occupation2")
    Call<OccupationList> occupationNew(
            @Field("req_id") String req_id,
            @Field("main_occupation_id") String main_occupation_id
    );


    //***********

    @Multipart
    @POST("EditEducationCertificate")
    Call<InsertResponse> editEducationCertificate(
            @Part MultipartBody.Part education_certificate,
            @Part("userid") RequestBody user_id
    );

    @Multipart
    @POST("EditOccupationCertificate")
    Call<InsertResponse> editOccupationCertificate(
            @Part MultipartBody.Part occupation_certificate,
            @Part("userid") RequestBody user_id
    );

    @Multipart
    @POST("EditKycImage")
    Call<InsertResponse> EditKycImage(
            @Part("verification_doc") RequestBody verification_doc,
            @Part MultipartBody.Part document_img,
            @Part("userid") RequestBody user_id
    );


    //--------------
    @FormUrlEncoded
    @POST("BlockAccount")
    Call<CommonResponse> blockAccount(
            @Field("userid") String userid,
            @Field("login_userid") String login_userid);

    @FormUrlEncoded
    @POST("ReportAccount")
    Call<CommonResponse> reportAccount(
            @Field("userid") String userid,
            @Field("login_userid") String login_userid,
            @Field("reason_title") String reason_title,
            @Field("reason") String reason);

    @FormUrlEncoded
    @POST("BlockComment")
    Call<CommonResponse> blockComment(
            @Field("comment_id") String comment_id,
            @Field("login_userid") String login_userid);

    @FormUrlEncoded
    @POST("ReportComment")
    Call<CommonResponse> reportComment(
            @Field("comment_id") String userid,
            @Field("login_userid") String login_userid,
            @Field("reason_title") String reason_title,
            @Field("reason") String reason);

    @FormUrlEncoded
    @POST("NewRegister")
    Call<RegisterResponse> registerResponse(
            @Field("fname") String fname,
            @Field("mname") String mname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("mobile_no") String mobile_no,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("newRegisterOTP")
    Call<newLoginData> newReg(
            @Field("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("NewMobileRegister")
    Call<newMobileData> newRegMobile(
            @Field("mobile_no") String mobile
    );

    @FormUrlEncoded
    @POST("NewEmailRegister")
    Call<newEmailData> newEmail(
            @Field("email") String mobile
    );

    @FormUrlEncoded
    @POST("sendNotificationToOtherUser")
    Call<SendNotificationToOtherUser> sendNotificationToOtherUser(
            @Field("user_id") String user_id,
            @Field("caste_id") String caste_id,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("send_profile_view_notification")
    Call<SendProfileViewNoti> sendProfileViewNotification(
            @Field("user_id") String user_id,
            @Field("login_user_id") String login_user_id
    );

    @FormUrlEncoded
    @POST("get_payment_details")
    Call<GetPaymentDetails> getPaymentDetails(
            @Field("transaction_id") String transaction_id
    );

    @FormUrlEncoded
    @POST("send_matching_profiles")
    Call<SendMatchingProfiles> sendMatchingProfiles(
            @Field("member_id") String member_id,
            @Field("caste_id") String gender
    );

    @FormUrlEncoded
    @POST("msg_set1")
    Call<MessageLimit> messageLimit(
            @Field("member_id") String member_id
    );

     @FormUrlEncoded
    @POST("create_order_id")
    Call<CreateOrderId> createOrderId(
            @Field("user_id") String user_id,
            @Field("amount") int amount
    );

    @FormUrlEncoded
    @POST("getLastFourProfile")
    Call<GetOnlineDate> getOnlineDate(
            @Field("user_id") String user_id,
            @Field("today_date") String todayDate,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("create_advisory_interest")
    Call<GetInterested> getInterested(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("save_members_history")
    Call<SaveHistory> getSaveHistory(
            @Field("login_user_id") String user_id,
            @Field("user_ids") String view_id
    );

    @FormUrlEncoded
    @POST("more_matches")
    Call<SearchDataMore> getSaveHistory(
            @Field("login_user_id") String user_id
    );

    @FormUrlEncoded
    @POST("delete_more_match")
    Call<RemoveHistory> getRemoveHistory(
            @Field("login_user_id") String user_id,
            @Field("delete_user_id") String delete_user_id
    );


}
