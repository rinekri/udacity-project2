package ru.rinekri.udacitypopularmovies.ui.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;
import ru.rinekri.udacitypopularmovies.ui.base.functions.UnsafeSupplier;
import ru.rinekri.udacitypopularmovies.ui.base.models.AsyncRequestWrapper;
import ru.rinekri.udacitypopularmovies.ui.base.models.ErrorConfig;
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
      onSuccess,
      onLoad,
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
      (result) -> getViewState().showViewContent(result),
      onLoad,
      onError);
  }

  /**
   * Base function to async request
   */
  @SuppressWarnings("unchecked")
  protected void elceAsyncRequest(Runnable onPrepare,
                                  Consumer<D> onSuccess,
                                  UnsafeSupplier<D> onLoad,
                                  Consumer<Throwable> onError) {

    AsyncTask request = new NetworkRequest(
      onPrepare,
      onSuccess,
      () -> AsyncRequestWrapper.content(onLoad.get()),
      onError
    );
    asyncRequests.add(request);
    request.execute();
  }

  private class NetworkRequest extends AsyncTask<Object, Void, AsyncRequestWrapper<D>> {
    private Runnable onPrepare;
    private Consumer<D> onSuccess;
    private UnsafeSupplier<AsyncRequestWrapper<D>> onLoad;
    private Consumer<Throwable> onError;

    NetworkRequest(Runnable onPrepare,
                   Consumer<D> onSuccess,
                   UnsafeSupplier<AsyncRequestWrapper<D>> onLoad,
                   Consumer<Throwable> onError) {
      this.onPrepare = onPrepare;
      this.onLoad = onLoad;
      this.onSuccess = onSuccess;
      this.onError = onError;
    }

    @Override
    protected void onPreExecute() {
      onPrepare.run();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AsyncRequestWrapper<D> doInBackground(Object... params) {
      try {
        return onLoad.get();
      } catch (Exception ex) {
        return AsyncRequestWrapper.error(ex);
      }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    protected void onPostExecute(@NonNull AsyncRequestWrapper<D> result) {
      if (result.data() != null) {
        onSuccess.accept(result.data());
      } else if (result.error() != null) {
        Timber.e("Loading error occurred: %s", result.error().getMessage());
        onError.accept(result.error());
      }
    }
  }
}