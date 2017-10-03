package app.banking.com.demobankingapp;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import retrofit2.http.Query;

import static android.R.attr.name;

public interface AppApi {

    String Base_API= "http://bankingappwebapi.azurewebsites.net/api/";

    String API = Base_API;

//    @FormUrlEncoded
//    @POST("home")
//    Call<CREDENTIAL> getHome(@Field("name") String name, @Field("token") String token);
//
//    @POST("home/{id}")
//    Call<SubCategoryModel> getSubHome(@Path("id") int name, @Query("offset") int offset);
//
//    @FormUrlEncoded
//    @POST("Current_Event/{id}")
//    Call<CurrentModel> getCurrent_Event(@Path("id") int name, @Query("offset") int offset, @FieldMap() HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("Upcoming_Event/{id}")
//    Call<UpcomingModel> getUpcoming_Event(@Path("id") int name, @Query("offset") int offset, @FieldMap() HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("Eventtellus_Upload/{id}")
//    Call<UploadModel> getUpload_Event(@Path("id") int name, @FieldMap() HashMap<String, String> hashMap);
//
//    @GET("Events_details/{id}")
//    Call<EventDetailsResponse> getEventDetails(@Path("id") int name);
//
//    @POST("Event_Like/{id}")
//    Call<FavEventResponse> favEvent(@Path("id") int name);
//
//    @POST("Event_unLike/{id}")
//    Call<UnFavEventResponse> unFavEvent(@Path("id") int name);
//
//    @POST("Experience_Like/{id}")
//    Call<FavExperienceResponse> favExperience(@Path("id") int name);
//
//    @POST("Experience_unLike/{id}")
//    Call<UnFavExperienceResponse> unFavExperience(@Path("id") int name);
//
//    @POST("Facilities_Like/{id}")
//    Call<FavFacilitiesResponse> favFacilities(@Path("id") int name);
//
//    @POST("Facilities_unLike/{id}")
//    Call<UnFavFacilitiesResponse> unFavFacilities(@Path("id") int name);
//
//
//    @FormUrlEncoded
//    @POST("Facilities_Like/{id}")
//    Call<FavFacilitiesResponse> favWithFacilities(@Path("id") int name, @Field("cat_id") int cat_id);
//
//    @FormUrlEncoded
//    @POST("Facilities_unLike/{id}")
//    Call<UnFavFacilitiesResponse> unFavWithFacilities(@Path("id") int name, @Field("cat_id") int cat_id);
//
//    @GET("Experiences/{id}")
//    Call<ExperienceResponse> getExperienceList(@Path("id") int name, @Query("offset") int offset);
//
//
 //   @GET("procedures")
 //   Call<ParentCascadeResponse[]> GetAllProperties();

  /*  @GET("procedures/{id}/cascades")
    Call<ChildCascadeResponse[]> GetChildCascade(@Path("id") int name);

    @GET("check_versions/Android - App")
    Call<CheckVersionResponse> CheckVersion(@Query("api_version") String api_version, @Query("build_number") String build_number);

    @GET("coupons")
    Call<CheckDeviceCouponResponse[]> CheckCoupon(@Query("q[code_eq]") String api_version);

    @GET("cascades/{id}/children")
    Call<User> GetSuperChildCascade(@Path("id") String name);






    @GET("cascades/{id}/content_blocks")
    Call<DescriptionResponse[]> GetContentBlock(@Path("id") String name);

    @GET("procedures/{id}/data")
    Call<UniversalDataModel> GetAllData(@Path("id") int name);

    @GET("cascades/{id}")
    Call<CheckCascadeResponse> Getcheckdata(@Path("id") String name); */

    @POST("Users/PostUser")
    Call<UserModelResponse> UserLogin(@Body UserModelResponse user);
    @POST("Cheques/PostCheque")
    Call<ChequeModel> SaveCheque(@Body ChequeModel chequeModel);

//
//
//    @FormUrlEncoded
//    @POST("Send_Inquiry")
//    Call<InquiryResponse> sendInquiry(@FieldMap() HashMap<String, String> hashMap);
//
//    @GET("Itineraries/{id}")
//    Call<ItinerariesResponse> getItinerariesList(@Path("id") int name, @Query("offset") int offset);
//
//
//    @FormUrlEncoded
//    @POST("Places/{id}")
//    Call<PlaceListResponse> getPlace(@Path("id") int name, @Query("offset") int offset, @FieldMap() HashMap<String, String> hashMap);
//
//
//    @GET("Places_details/{id}")
//    Call<PlacesDetailsResponse> getPlaceDetails(@Path("id") int name);
//
//
//    @POST("Places_Like/{id}")
//    Call<FavPlaceResponse> favPlace(@Path("id") int name);
//
//    @POST("Places_unLike/{id}")
//    Call<UnFavPlaceResponse> unFavPlace(@Path("id") int name);
//
//
//    @GET("Itineraries_details/{id}")
//    Call<ItinerariesDetailsResponse> getItinerariesDetails(@Path("id") int name);
//
//
//    @POST("Itineraries_Like/{id}")
//    Call<FavitineriesResponse> favItineraries(@Path("id") int name);
//
//    @POST("Itineraries_unLike/{id}")
//    Call<UnFavitineriesResponse> unFavItineraries(@Path("id") int name);
//
//    @GET("Facilities_subCategories/{id}")
//    Call<FacilitiesListResponse> getFacilitiesList(@Path("id") int name, @Query("offset") int offset);
//
//
//    @FormUrlEncoded
//    @POST("Facilities_list/{id}")
//    Call<FacilitiesInResponse> getFacilityInList(@Path("id") int name, @Query("offset") int offset, @FieldMap() HashMap<String, String> hashMap);
//
//    @GET("Facilities_details/{id}")
//    Call<FacilitiesResponse> getFacilitiesDetails(@Path("id") int name);
//
//    @GET("Facilities_details/{id}")
//    Call<FacilitiesResponse> getCatFacilitiesDetails(@Path("id") int name, @Query("cat_id") int cat_id);
//
//
//    @GET("info")
//    Call<InfoResponse> getInfo();
//
//    @GET("main")
//    Call<HomeDetailsResponse> getMain();
//
//    @GET("Favourite")
//    Call<FavoriteResponse> getFav();


}
