package com.example.trackmybus.Responses

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
@FormUrlEncoded
@POST("addroutes.php")
fun addroute(
    @Field("fromplace")fromplace:String,
    @Field("toplace")toplace:String,
    @Field("fromlatlon")fromlatlon:String,
    @Field("tolatlon")tolatlon:String,
    @Field("driverid")driverid:String
):Call<CommonResponse>

@GET("functions.php")
fun getdataofusers(
    @Query("condition")condition:String
):Call<RouteResponse>
@FormUrlEncoded
@POST("adddrvier.php")
fun addDriver(
    @Field("name")name:String,
    @Field("mobile")mobile:String,
    @Field("mail")mail:String,
    @Field("password")password:String,
    @Field("profile")profile:String,
    @Field("type")type:String,
    @Field("drivinglicense")drivinglicense:String,
):Call<CommonResponse>
@FormUrlEncoded
@POST("adddrvier.php")
fun addUsers(
   @Field("name")name:String,
   @Field("mobile")mobile:String,
   @Field("mail")mail:String,
   @Field("password")password:String,
   @Field("type")type:String,
    ):Call<CommonResponse>

@FormUrlEncoded
@POST("functions.php?condition=login")
fun logindata(
    @Field("mail")mail:String,
    @Field("password")password:String,
):Call<CustomResponse>

    @GET("functions.php")
    fun getdrviers(
        @Query("condition")condition:String
    ):Call<CustomResponse>

    @FormUrlEncoded
    @POST("assignduty.php")
    fun assignduty(
        @Field("driverid")driverid:String,
        @Field("routeid")routeid:String,
        @Field("starttime")starttime:String,
        @Field("endtime")endtime:String,
        @Field("vehiclenumber")vehiclenumber:String,
        @Field("perkm")perkm:String,
        @Field("days")days:String,
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getData(
@Query("condition")condition:String,
@Field("id")id:String
    ):Call<AssignedResponse>


    @FormUrlEncoded
    @POST("functions.php")
    fun searchdata(
        @Query("condition")condition:String,
        @Field("search")search:String
    ):Call<SearchResponse>

    @FormUrlEncoded
    @POST("addseats.php")
    fun seatPayment(
        @Field("routeid")routeid:String,
        @Field("payment")payment:String,
        @Field("seats")seats:String,
        @Field("datepayed")datepayed:String,
        @Field("userid")userid:String,
        @Field("total")total:String,
        @Field("driverid")driverid:String,
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getAllseats(
        @Query("condition")condition:String,
        @Field("date")date:String,
        @Field("routeid")routeid:String
    ):Call<GetSeats>

    @FormUrlEncoded
    @POST("functions.php")
    fun getMyHistroy(
        @Query("condition")condition:String,
        @Field("id")id:String
    ):Call<GetHistoryUser>

@FormUrlEncoded
@POST("functions.php")
fun getRouteTrack(
    @Query("condition")condition: String,
    @Field("search")search:String
):Call<RouteResponse>

@FormUrlEncoded
@POST("functions.php")
    fun getDayHistory(
        @Query("condition")condition:String,
        @Field("id")id:String,
        @Field("date")date:String
    ):Call<LastResponse>

    @FormUrlEncoded
    @POST("updatelocation.php")
    fun updatelocation(
        @Field("latlon")latlon:String,
        @Field("driverid")driverid:String
    ):Call<CommonResponse>
}