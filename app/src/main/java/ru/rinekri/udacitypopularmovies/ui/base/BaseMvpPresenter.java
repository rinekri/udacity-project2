package ru.rinekri.udacitypopularmovies.ui.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ui.base.functions.UnsafeSupplier;
import ru.rinekri.udacitypopularmovies.ui.base.models.AsyncRequestWrapper;
import ru.rinekri.udacitypopularmovies.ui.base.models.ErrorConfig;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;
import timber.log.Timber;

abstract public class BaseMvpPresenter<D, V extends BaseMvpView<D>> extends MvpPresenter<V> {
  protected List<AsyncTask> asyncRequests = new ArrayList<>();

  @Override
  public void onDestroy() {
    abortAsyncRequests();
    super.onDestroy();
  }

  protected void abortAsyncRequests() {
    StreamSupport
      .stream(asyncRequests)
      .forEach((request) -> request.cancel(true));
    asyncRequests.clear();
  }

  /**
   * @param onLoad load function
   */
  protected void elceAsyncRequestL(UnsafeSupplier<D> onLoad) {
    elceAsyncRequestLE(
      onLoad,
      (error) -> getViewState().showError(ErrorConfig.createFrom(error))
    );
  }

  /**
   * @param onLoad    load function
   * @param onSuccess success function
   */
  protected void elceAsyncRequestLS(UnsafeSupplier<D> onLoad,
                                    Consumer<D> onSuccess) {
    elceAsyncRequest(
      () -> getViewState().showLoading(),
      onLoad,
      onSuccess,
      (error) -> getViewState().showError(ErrorConfig.createFrom(error)));
  }

  /**
   * @param onLoad  load function
   * @param onError error function
   */
  protected void elceAsyncRequestLE(UnsafeSupplier<D> onLoad,
                                    Consumer<Throwable> onError) {
    elceAsyncRequest(
      () -> getViewState().showLoading(),
      onLoad,
      (result) -> getViewState().showViewContent(result),
      onError);
  }

  /**
   * Base function to async request
   */
  @SuppressWarnings("unchecked")
  protected <T> void elceAsyncRequest(Runnable onPrepare,
                                      UnsafeSupplier<T> onLoad,
                                      Consumer<T> onSuccess,
                                      Consumer<Throwable> onError) {

    AsyncTask request = new NetworkRequest(
      onPrepare,
      () -> AsyncRequestWrapper.content(onLoad.get()),
      onSuccess,
      onError
    );
    asyncRequests.add(request);
    request.execute();
  }

  private class NetworkRequest<T> extends AsyncTask<Object, Void, AsyncRequestWrapper<T>> {
    @Nullable
    private Runnable onPrepare;
    @NonNull
    private UnsafeSupplier<AsyncRequestWrapper<T>> onLoad;
    @Nullable
    private Consumer<T> onSuccess;
    @Nullable
    private Consumer<Throwable> onError;

    NetworkRequest(@Nullable Runnable onPrepare,
                   @NonNull UnsafeSupplier<AsyncRequestWrapper<T>> onLoad,
                   @Nullable Consumer<T> onSuccess,
                   @Nullable Consumer<Throwable> onError) {
      this.onPrepare = onPrepare;
      this.onLoad = onLoad;
      this.onSuccess = onSuccess;
      this.onError = onError;
    }

    @Override
    protected void onPreExecute() {
      LangUtils.safeInvoke(onPrepare, Runnable::run);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AsyncRequestWrapper<T> doInBackground(Object... params) {
      try {
        return onLoad.get();
      } catch (Exception ex) {
        return AsyncRequestWrapper.error(ex);
      }
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    protected void onPostExecute(@NonNull AsyncRequestWrapper<T> result) {
      if (result.data() != null) {
        LangUtils.safeInvoke(onSuccess, s -> s.accept(result.data()));
      } else if (result.error() != null) {
        Timber.e(result.error(), "Loading error occurred: %s", result.error().getMessage());
        LangUtils.safeInvoke(onError, e -> e.accept(result.error()));
      }
    }
  }
}