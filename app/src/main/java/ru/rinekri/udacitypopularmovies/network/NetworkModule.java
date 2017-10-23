package ru.rinekri.udacitypopularmovies.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ru.rinekri.udacitypopularmovies.BuildConfig;
import ru.rinekri.udacitypopularmovies.annotations.ApplicationScope;
import ru.rinekri.udacitypopularmovies.network.interceptors.ApiRequestInterceptor;
import ru.rinekri.udacitypopularmovies.network.json_adapters.MoshiAutoValueAdapterFactory;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.network.type_adapters.BackdropUrlAdapter;
import ru.rinekri.udacitypopularmovies.network.type_adapters.PosterUrlAdapter;

import static ru.rinekri.udacitypopularmovies.network.NetworkConstants.API_VERSION;
import static ru.rinekri.udacitypopularmovies.network.NetworkConstants.DEFAULT_CONNECT_TIMEOUT_MS;
import static ru.rinekri.udacitypopularmovies.network.NetworkConstants.HTTP_LOG_LEVEL;

@Module
public class NetworkModule {
  @Provides
  @ApplicationScope
  OkHttpClient provideOkHttp() {
    return new OkHttpClient.Builder()
      .readTimeout(DEFAULT_CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
      .writeTimeout(DEFAULT_CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
      .addInterceptor(new ApiRequestInterceptor())
      .addInterceptor(new StethoInterceptor())
      .addInterceptor(new HttpLoggingInterceptor().setLevel(HTTP_LOG_LEVEL))
      .build();
  }

  @Provides
  @ApplicationScope
  Moshi provideMoshi() {
    return new Moshi.Builder()
      .add(MoshiAutoValueAdapterFactory.create())
      .add(new PosterUrlAdapter())
      .add(new BackdropUrlAdapter())
      .build();
  }

  @Provides
  @ApplicationScope
  Retrofit provideRetrofit(OkHttpClient okHttpClient,
                           Moshi moshi) {
    HttpUrl baseUrl = HttpUrl
      .parse(BuildConfig.API_BASE_URL)
      .newBuilder()
      .addPathSegment(API_VERSION)
      .build();

    return new Retrofit.Builder()
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .baseUrl(baseUrl + "/")
      .build();
  }

  @Provides
  @ApplicationScope
  public MainServiceApi mainServiceApi(Retrofit retrofit) {
    return retrofit.create(MainServiceApi.class);
  }
}