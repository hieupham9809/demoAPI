package com.example.zingdemoapi.request;

import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.datamodel.ProgramInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("3.1.4/home?api_key=7d3343b073f9fb9ec75e53335111dcc1&os=android&app_version=140033031&session_key=FPRM.22273794.213.rXxBXqEX7kj1VatgGw4UwqEX7kj3eHxhGgHsunwX7ki&ctime=1560833889494&scrsize=445x445&api_sig=28b2e4e1af486d2f5eed05d0d6f9a39d&ignoresig=true")
    Observable<Home> register();

    @GET("3.1/program/info?api_key=7d3343b073f9fb9ec75e53335111dcc1&os=android&app_version=140033031&session_key=&ctime=1561515525790&scrsize=210x210&api_sig=aabb3a00ade4bd64a06c2e8a83d4512c&ignoresig=true")
    Observable<ProgramInfo> getProgramInfo(@Query("program_id") String id);
}
