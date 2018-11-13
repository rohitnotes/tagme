package com.dankook.tagme.data.source;

import com.dankook.tagme.data.remote.RetrofitApi;
import com.dankook.tagme.data.remote.RetrofitClient;
import com.dankook.tagme.data.remote.StoreDetailRequest;
import com.dankook.tagme.data.remote.StoreListRequest;
import com.dankook.tagme.model.Category;
import com.dankook.tagme.model.Store;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class StoreRemoteDataSource implements StoreDataSource {

    private static StoreRemoteDataSource storeRemoteDataSource;

    private StoreRemoteDataSource(){}

    public static StoreRemoteDataSource getInstance(){
        if(storeRemoteDataSource == null){
            storeRemoteDataSource = new StoreRemoteDataSource();
        }
        return storeRemoteDataSource;
    }

    @Override
    public Observable<List<Category>> getCategories() {
        return RetrofitClient.getClient().create(RetrofitApi.class)
                .getCategories()
                .subscribeOn(Schedulers.newThread())
                .map(response -> {
                    Category category = new Category();
                    category.setCategoryKey(0);
                    category.setCategoryName("all");
                    category.setCategoryNameKor("전체");
                    response.add(0, category);
                    return response;
                });
    }

    @Override
    public Observable<List<Store>> getStores(StoreListRequest request) {
        return RetrofitClient.getClient().create(RetrofitApi.class)
                .getStores(request)
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<Store> getStore(StoreDetailRequest request) {
        return RetrofitClient.getClient().create(RetrofitApi.class)
                .getStore(request)
                .subscribeOn(Schedulers.newThread());
    }
}
