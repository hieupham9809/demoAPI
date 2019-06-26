package com.example.zingdemoapi.type;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("3.1.4/home?api_key=7d3343b073f9fb9ec75e53335111dcc1&os=android&app_version=140033031&session_key=FPRM.22273794.213.rXxBXqEX7kj1VatgGw4UwqEX7kj3eHxhGgHsunwX7ki&ctime=1560833889494&scrsize=445x445&api_sig=28b2e4e1af486d2f5eed05d0d6f9a39d&ignoresig=true")
    Observable<Home> register();
}
