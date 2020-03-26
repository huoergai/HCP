package com.huoergai.rxjava2;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetApi {

    @GET("users/{username}/repos")
    Single<List<Repo>> getRepos(@Path("username") String username);

}
