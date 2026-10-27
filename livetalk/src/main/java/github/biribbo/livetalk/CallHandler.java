package github.biribbo.livetalk;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.concurrent.CompletableFuture;

public class CallHandler {

    public static <T> CompletableFuture<T> enqueue(Call<T> call) {
        CompletableFuture<T> future = new CompletableFuture<>();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new RuntimeException("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
