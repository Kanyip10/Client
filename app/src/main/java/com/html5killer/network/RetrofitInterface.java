package com.html5killer.network;

import com.html5killer.model.ReferenceList;
import com.html5killer.model.Response;
import com.html5killer.model.User;
import com.html5killer.model.Quiz;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);

    @GET("quizzes/{name}")
    Observable<Quiz> getQuiz(@Path("name") String name);

    @POST("quizzes")
    Observable<Response> setQuiz(@Body Quiz quiz);

    @GET("ReferenceList/{email}/position")
    Observable <ReferenceList> sort(@Path("email")String email, @Query("sort") String sort);

    @PUT("users/{email}/experience")
    Observable<Response> changeExp(@Path("email") String email, @Body User user);

}